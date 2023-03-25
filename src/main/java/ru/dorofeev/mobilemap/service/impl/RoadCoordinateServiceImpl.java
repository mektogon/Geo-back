package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.RoadCoordinate;
import ru.dorofeev.mobilemap.repository.RoadCoordinateRepository;
import ru.dorofeev.mobilemap.service.interf.RoadCoordinateService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoadCoordinateServiceImpl implements RoadCoordinateService {

    private final RoadCoordinateRepository repository;

    @Override
    public List<RoadCoordinate> getAll() {
        return repository.findAll();
    }


    @Override
    public List<RoadCoordinate> getByAllId(List<UUID> listId) {
        return repository.findByIdIn(listId);
    }

    @Override
    public Optional<RoadCoordinate> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public void save(RoadCoordinate roadCoordinate) {
        repository.save(roadCoordinate);
    }

    @Override
    public ResponseEntity<String> saveAndReturnId(RoadCoordinate roadCoordinate) {
        return ResponseEntity.ok(String.format("{\"id\": \"%s\"}", repository.save(roadCoordinate).getId()));

    }

    @Override
    public void update(RoadCoordinate roadCoordinate) {
        Optional<RoadCoordinate> byId = repository.findById(roadCoordinate.getId());

        if (byId.isPresent()) {
            repository.save(roadCoordinate);
            log.debug("IN update() - Обновлен объект \"Координаты дороги\" с ID: {}", byId.get().getId());
        } else {
            log.info("IN update() - Не удалось найти объект \"Координаты дороги\" с ID: {}", roadCoordinate.getId());
        }
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }
}
