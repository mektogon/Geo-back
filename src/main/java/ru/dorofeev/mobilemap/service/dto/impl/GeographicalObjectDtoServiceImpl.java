package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.dorofeev.mobilemap.mapper.GeographicalObjectMapper;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoMobile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoWeb;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.service.dto.interf.GeographicalObjectDtoService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

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
    private final GeographicalObjectRepository geographicalObjectRepository;

    @Override
    public List<GeographicalObjectDtoMobile> getAll() {
        log.info("IN getAll() - Преобразование List<Entity> в List<DTO>.");

        return geographicalObjectMapper.toDtoList(geographicalObjectService.getAll());
    }

    @Override
    public List<GeographicalObjectDtoWeb> getAllForWeb() {
        log.info("IN getAllForAdminPanel() - Преобразование List<Entity> в List<DTO>.");

        return geographicalObjectMapper.toDtoWebList(geographicalObjectService.getAll());
    }

    @Override
    public GeographicalObjectDtoWeb getByIdForWeb(UUID id) {
        log.info("IN getAllForAdminPanel() - Преобразование List<Entity> в List<DTO>.");

        return geographicalObjectService.getById(id)
                .map(geographicalObjectMapper::toDtoWeb)
                .or(() -> Optional.of(new GeographicalObjectDtoWeb())).get();
    }

    @Override
    public GeographicalObjectDtoMobile findById(UUID id) {
        log.info("IN findById() - Преобразование entity в dto.");

        return geographicalObjectService.getById(id)
                .map(geographicalObjectMapper::toDtoMobile)
                .or(() -> Optional.of(new GeographicalObjectDtoMobile())).get();
    }

    @Override
    public List<GeographicalObjectDtoMobile> getAllByName(@PathVariable String name) {
        log.info("IN getByName() - Преобразование List<Entity> в List<DTO>.");

        List<GeographicalObject> allByName = geographicalObjectService.getAllByName(name);

        if (allByName != null) {
            log.info("IN getByName() - return List<DTO>.");

            return geographicalObjectMapper.toDtoList(allByName);
        } else {
            log.info("IN getByName() - return List<Empty>.");

            return Collections.emptyList();
        }
    }

    @Override
    public void save(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {
        geographicalObjectService.save(
                geographicalObjectMapper.toEntity(
                        AuxiliaryUtils.ValidationGeoObject(geographicalObjectDtoMobile)
                )
        );

        log.info("IN save() - Преобразование dto в entity.");
    }

    @Override
    public UUID saveAndReturnId(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {
        log.info("IN saveAndReturnId() - Преобразование dto в entity.");

        return geographicalObjectService.saveAndReturnId(
                geographicalObjectMapper.toEntity(
                        AuxiliaryUtils.ValidationGeoObject(geographicalObjectDtoMobile)
                )
        );

    }

    @Override
    public void update(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {
        Optional<GeographicalObject> byId = geographicalObjectRepository.findById(geographicalObjectDtoMobile.getId());

        if (byId.isPresent()) {
            log.info("IN update() - Преобразование dto в entity.");

            geographicalObjectService.update(
                    geographicalObjectMapper.toConvertForUpdateEntity(geographicalObjectDtoMobile, byId.get())
            );
        }
    }
}
