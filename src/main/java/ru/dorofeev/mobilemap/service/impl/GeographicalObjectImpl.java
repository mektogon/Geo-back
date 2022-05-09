package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.GeographicalObject;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GeographicalObjectImpl implements GeographicalObjectService {
    private final GeographicalObjectRepository geographicalObjectRepository;

    public GeographicalObjectImpl(GeographicalObjectRepository geographicalObjectRepository) {
        this.geographicalObjectRepository = geographicalObjectRepository;
    }

    @Override
    public List<GeographicalObject> findALl() {
        return geographicalObjectRepository.findAll();
    }

    @Override
    public Optional<GeographicalObject> findById(Long id) {
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
    public Optional<GeographicalObject> save(GeographicalObject geographicalObject) {
        return Optional.of(geographicalObjectRepository.save(geographicalObject));
    }

    @Override
    public void update(GeographicalObject geographicalObject) {
        Optional<GeographicalObject> row = geographicalObjectRepository.findById(geographicalObject.getId());

        if (row.isPresent()) {
            geographicalObjectRepository.save(geographicalObject);
        }
    }

    @Override
    public void deleteById(Long id) {
        geographicalObjectRepository.deleteById(id);
    }
}
