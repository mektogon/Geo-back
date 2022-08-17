package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.mapper.GeographicalObjectMapper;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.service.dto.interf.GeographicalObjectDtoService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeographicalObjectDtoServiceImpl implements GeographicalObjectDtoService {

    private final GeographicalObjectMapper geographicalObjectMapper;

    private final GeographicalObjectService geographicalObjectService;

    @Override
    public List<GeographicalObjectDto> getAll() {
        log.info("IN getAll() - Converting List<Entity> into List<DTO>.");

        return geographicalObjectMapper.toDtoList(geographicalObjectService.getAll());
    }

    @Override
    public Optional<GeographicalObjectDto> findById(UUID id) {
        log.info("IN findById() - Converting entity into dto.");

        return geographicalObjectService.findById(id)
                .map(geographicalObjectMapper::toDto)
                .or(() -> Optional.of(new GeographicalObjectDto()));
    }

    @Override
    public List<GeographicalObjectDto> getByName(@PathVariable String name) {
        log.info("IN getByName() - Converting List<Entity> into List<DTO>.");

        List<GeographicalObject> allByName = geographicalObjectService.findAllByName(name);

        if (allByName != null) {
            log.info("IN getByName() - return List<DTO>.");

            return geographicalObjectMapper.toDtoList(allByName);
        } else {
            log.info("IN getByName() - return List<Empty>.");

            return Collections.emptyList();
        }
    }

    @Override
    public void save(GeographicalObjectDto geographicalObjectDto) {
        geographicalObjectService.save(geographicalObjectMapper.toEntity(geographicalObjectDto));

        log.info("IN save() - Converting dto into entity.");
    }

    @Override
    public UUID saveAndReturnId(GeographicalObjectDto geographicalObjectDto) {
        log.info("IN saveAndReturnId() - Converting dto into entity.");

        return geographicalObjectService.saveAndReturnId(geographicalObjectMapper.toEntity(geographicalObjectDto));

    }

    @Override
    public void update(
            UUID id,
            String name,
            String type,
            String latitude,
            String longitude,
            String description,
            String note,
            String designation,
            String region,
            String district,
            String typeLocality,
            String locality,
            String street,
            String houseNumber,
            MultipartFile[] photo,
            MultipartFile[] audio,
            MultipartFile[] video
    ) {

    }

    @Override
    public void update(GeographicalObjectDto geographicalObjectDto) {
        log.info("IN update() - Converting dto into entity.");

        geographicalObjectService.update(geographicalObjectMapper.toEntity(geographicalObjectDto));
    }


}
