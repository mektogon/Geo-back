package ru.dorofeev.mobilemap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.GeographicalObject;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import java.util.List;
import java.util.Optional;

@Service
public class GeographicalObjectImpl implements GeographicalObjectService{
    GeographicalObjectRepository geographicalObjectRepository;

    public GeographicalObjectImpl(GeographicalObjectRepository geographicalObjectRepository) {
        this.geographicalObjectRepository = geographicalObjectRepository;
    }

    @Override
    public List<GeographicalObject> findALl() {
        return geographicalObjectRepository.findAll();
    }

    @Override
    public Optional<GeographicalObject> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<GeographicalObject> save(GeographicalObject geographicalObject) {
        return Optional.empty();
    }

    @Override
    public void delete(GeographicalObject geographicalObject) {

    }

    @Override
    public void deleteById(GeographicalObject geographicalObject) {

    }

    @Override
    public Optional<GeographicalObject> findAllByName(String name) {
        return Optional.empty();
    }

}
