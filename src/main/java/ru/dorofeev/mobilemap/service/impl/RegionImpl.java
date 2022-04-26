package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Region;
import ru.dorofeev.mobilemap.repository.RegionRepository;
import ru.dorofeev.mobilemap.service.interf.RegionService;

import java.util.List;
import java.util.Optional;

@Service
public class RegionImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<Region> findALl() {
        return regionRepository.findAll();
    }

    @Override
    public Optional<Region> findById(Long id) {
        return regionRepository.findById(id);
    }

    @Override
    public Optional<Region> save(Region region) {
        return Optional.of(regionRepository.save(region));
    }

    @Override
    public void update(Region region) {
        regionRepository.save(region);
    }

    @Override
    public void deleteById(Long id) {
        regionRepository.deleteById(id);
    }
}
