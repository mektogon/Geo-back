package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Locality;
import ru.dorofeev.mobilemap.repository.LocalityRepository;
import ru.dorofeev.mobilemap.service.interf.LocalityService;

import java.util.List;
import java.util.Optional;

@Service
public class LocalityImpl implements LocalityService {
    private final LocalityRepository localityRepository;

    public LocalityImpl(LocalityRepository localityRepository) {
        this.localityRepository = localityRepository;
    }

    @Override
    public List<Locality> findALl() {
        return localityRepository.findAll();
    }

    @Override
    public Optional<Locality> findById(Long id) {
        return localityRepository.findById(id);
    }

    @Override
    public Optional<Locality> save(Locality locality) {
        return Optional.of(localityRepository.save(locality));
    }

    @Override
    public Optional<Locality> update(Locality locality) {
        return Optional.of(localityRepository.save(locality));
    }

    @Override
    public void deleteById(Long id) {
        localityRepository.deleteById(id);
    }
}
