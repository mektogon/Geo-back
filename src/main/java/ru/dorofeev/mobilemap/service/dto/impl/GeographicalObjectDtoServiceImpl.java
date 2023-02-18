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
        return geographicalObjectMapper.toDtoList(geographicalObjectService.getAll());
    }

    @Override
    public List<GeographicalObjectDtoWeb> getAllForWeb() {
        return geographicalObjectMapper.toDtoWebList(geographicalObjectService.getAll());
    }

    @Override
    public GeographicalObjectDtoWeb getByIdForWeb(UUID id) {
        return geographicalObjectService.getById(id)
                .map(geographicalObjectMapper::toDtoWeb)
                .or(() -> Optional.of(new GeographicalObjectDtoWeb())).get();
    }

    @Override
    public GeographicalObjectDtoMobile findById(UUID id) {
        return geographicalObjectService.getById(id)
                .map(geographicalObjectMapper::toDtoMobile)
                .or(() -> Optional.of(new GeographicalObjectDtoMobile())).get();
    }

    @Override
    public List<GeographicalObjectDtoMobile> getAllByName(@PathVariable String name) {
        List<GeographicalObject> allByName = geographicalObjectService.getAllByName(name);

        if (allByName != null) {
            return geographicalObjectMapper.toDtoList(allByName);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {
        geographicalObjectService.save(
                geographicalObjectMapper.toEntity(
                        AuxiliaryUtils.validationGeoObject(geographicalObjectDtoMobile)
                )
        );
    }

    @Override
    public UUID saveAndReturnId(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {
        return geographicalObjectService.saveAndReturnId(
                geographicalObjectMapper.toEntity(
                        AuxiliaryUtils.validationGeoObject(geographicalObjectDtoMobile)
                )
        );

    }

    @Override
    public void update(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {
        Optional<GeographicalObject> byId = geographicalObjectRepository.findById(geographicalObjectDtoMobile.getId());

        byId.ifPresent(geographicalObject -> geographicalObjectService.update(
                geographicalObjectMapper.toConvertForUpdateEntity(geographicalObjectDtoMobile, geographicalObject)
        ));
    }
}
