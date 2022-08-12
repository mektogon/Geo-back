package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeographicalObjectServiceImpl implements GeographicalObjectService {
    private final GeographicalObjectRepository geographicalObjectRepository;

    @Override
    public List<GeographicalObject> getAll() {
        return geographicalObjectRepository.findAll();
    }

    @Override
    public Optional<GeographicalObject> findById(UUID id) {
        return geographicalObjectRepository.findById(id);
    }

    @Override
    public List<GeographicalObject> findAllByName(String name) {
        if (name == null) {
            return Collections.emptyList();
        }

        return geographicalObjectRepository.findAllByName(name);
    }

    @Override
    public void save(GeographicalObject geographicalObject) {
        geographicalObjectRepository.save(geographicalObject);
    }

    @Override
    public void update(GeographicalObject geographicalObject) {
        Optional<GeographicalObject> row = geographicalObjectRepository.findById(geographicalObject.getId());

        if (row.isPresent()) {
            geographicalObjectRepository.save(geographicalObject);
        }
    }

    @Override
    public void deleteById(UUID id) {
        geographicalObjectRepository.deleteById(id);
    }
}
