package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Locality;
import ru.dorofeev.mobilemap.repository.LocalityRepository;
import ru.dorofeev.mobilemap.service.interf.LocalityService;

import java.util.List;
import java.util.Optional;

@Service
public class LocalityImpl implements LocalityService {
    LocalityRepository localityRepository;

    public LocalityImpl(LocalityRepository localityRepository) {
        this.localityRepository = localityRepository;
    }

    @Override
    public List<Locality> findALl() {
        return null;
    }

    @Override
    public Optional<Locality> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Locality> save(Locality locality) {
        return Optional.empty();
    }

    @Override
    public void delete(Locality locality) {

    }

    @Override
    public void deleteById(Locality locality) {

    }
}
