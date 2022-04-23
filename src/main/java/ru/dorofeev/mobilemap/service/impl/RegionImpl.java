package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Region;
import ru.dorofeev.mobilemap.repository.RegionRepository;
import ru.dorofeev.mobilemap.service.interf.RegionService;

import java.util.List;
import java.util.Optional;

@Service
public class RegionImpl implements RegionService {
    RegionRepository regionRepository;

    public RegionImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<Region> findALl() {
        return null;
    }

    @Override
    public Optional<Region> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Region> save(Region region) {
        return Optional.empty();
    }

    @Override
    public void delete(Region region) {

    }

    @Override
    public void deleteById(Region region) {

    }
}
