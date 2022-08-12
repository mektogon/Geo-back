package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.GeographicalObjectMapper;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.service.dto.interf.GeographicalObjectDtoService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

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
        log.info("IN getAll() - Converting entity into dto.");

        return geographicalObjectMapper.toDtoList(geographicalObjectService.getAll());
    }

    @Override
    public Optional<GeographicalObjectDto> findById(UUID id) { //TODO: TESTING THIS

        log.info("IN findById() - Converting entity into dto.");

        return Optional.ofNullable(
                geographicalObjectMapper.toDto(geographicalObjectService.findById(id)
                        .orElse(new GeographicalObject())
                )
        );
    }

    @Override
    public void save(GeographicalObjectDto geographicalObjectDto) {
        geographicalObjectService.save(geographicalObjectMapper.toEntity(geographicalObjectDto));

        log.info("IN save() - Converting dto into entity.");
    }

    @Override
    public void update(GeographicalObjectDto geographicalObjectDto) {

    }

}
