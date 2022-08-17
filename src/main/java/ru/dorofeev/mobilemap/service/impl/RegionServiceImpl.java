package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Region;
import ru.dorofeev.mobilemap.repository.RegionRepository;
import ru.dorofeev.mobilemap.service.interf.RegionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    @Override
    public Optional<Region> findById(UUID id) {
        return regionRepository.findById(id);
    }

    @Override
    public void save(Region region) {
        regionRepository.save(region);
    }

    @Override
    public void update(Region region) {
        regionRepository.save(region);
    }

    @Override
    public void deleteById(UUID id) {
        regionRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        regionRepository.deleteByName(name);
    }
}
