package ru.dorofeev.mobilemap.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Manifesto;
import ru.dorofeev.mobilemap.model.dto.ManifestoRequest;
import ru.dorofeev.mobilemap.repository.ManifestoRepository;
import ru.dorofeev.mobilemap.repository.RoadCoordinateRepository;
import ru.dorofeev.mobilemap.repository.TileMapRepository;
import ru.dorofeev.mobilemap.service.interf.ManifestoService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManifestoServiceImpl implements ManifestoService {

    @Value("${server.url}")
    private String rootUrl;

    private final String ENDPOINT_GET_TILE_BY_ID = "/api/v1/tile-map/download/";
    private final String ENDPOINT_GET_ROAD_BY_ID = "/api/v1/road/getById/";

    private final ManifestoRepository repository;
    private final TileMapRepository tileRepository;
    private final RoadCoordinateRepository roadRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Manifesto> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Manifesto> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Manifesto getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public Manifesto getFirstManifesto(String sortName) {

        if ("DESC".equalsIgnoreCase(sortName)) {
            return repository.findTopBy(Sort.by("createdDate").descending());
        }

        return repository.findTopBy(Sort.by("createdDate").ascending());
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ResponseEntity<String> save(ManifestoRequest manifestoRequest) {
        try {
            return ResponseEntity.ok(String.format("{\"id\": \"%s\"}",
                    repository.save(Manifesto.builder()
                            .name(manifestoRequest.getName())
                            .mapNameArchiveLink(
                                    objectMapper.writeValueAsString(
                                            createLinkArchive(
                                                    manifestoRequest.getListIdTile(),
                                                    manifestoRequest.getListIdRoad()
                                            )
                                    )
                            )
                            .build()).getId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сериализации мапы: {} ", e);
        }
    }

    @Override
    public void update(ManifestoRequest manifestoRequest) {

        if (manifestoRequest == null) {
            return;
        }

        Optional<Manifesto> byId = repository.findById(manifestoRequest.getId());

        if (byId.isPresent()) {

            Manifesto manifesto = byId.get();

            if (manifestoRequest.getName() != null) {
                manifesto.setName(manifestoRequest.getName());
            }

            manifesto.setVersion(manifesto.getVersion() + 1);
            manifesto.setLastUpdate(new Date());

            if (manifestoRequest.getListIdTile() != null && manifestoRequest.getListIdRoad() != null) {
                try {
                    manifesto.setMapNameArchiveLink(
                            objectMapper.writeValueAsString(createLinkArchive(manifestoRequest.getListIdTile(), manifestoRequest.getListIdRoad()))
                    );
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Ошибка при сериализации мапы: {} ", e);
                }
            }

            repository.save(manifesto);

            log.debug("IN update() - Обновлен объект манифеста с ID: {}", manifestoRequest.getId());
        } else {
            log.info("IN update() - Не удалось найти объект манифеста с ID: {}", manifestoRequest.getId());
        }
    }


    private Map<String, String> createLinkArchive(List<UUID> listIdTile, List<UUID> listIdRoad) {
        Map<String, String> result = new HashMap<>();

        if (listIdTile != null) {
            listIdTile.forEach(tileId -> {
                tileRepository.findById(tileId)
                        .ifPresent(tileMap -> result.put(
                                        tileMap.getName(),
                                        String.format("%s%s%s", rootUrl, ENDPOINT_GET_TILE_BY_ID, tileId)
                                )
                        );
            });
        }

        if (listIdRoad != null) {
            listIdRoad.forEach(roadId -> {
                roadRepository.findById(roadId)
                        .ifPresent(road -> result.put(
                                        road.getName(),
                                        String.format("%s%s%s", rootUrl, ENDPOINT_GET_ROAD_BY_ID, roadId)
                                )
                        );
            });
        }

        return result;
    }
}
