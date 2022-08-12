package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Locality;
import ru.dorofeev.mobilemap.repository.LocalityRepository;
import ru.dorofeev.mobilemap.service.interf.LocalityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements LocalityService {
    private final LocalityRepository localityRepository;

    @Override
    public List<Locality> getAll() {
        return localityRepository.findAll();
    }

    @Override
    public Optional<Locality> findById(UUID id) {
        return localityRepository.findById(id);
    }

    @Override
    public void save(Locality locality) {
        localityRepository.save(locality);
    }

    @Override
    public void update(Locality locality) {
        localityRepository.save(locality);
    }

    @Override
    public void deleteById(UUID id) {
        localityRepository.deleteById(id);
    }
}
