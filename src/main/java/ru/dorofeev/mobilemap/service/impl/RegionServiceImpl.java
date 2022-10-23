package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Region;
import ru.dorofeev.mobilemap.repository.RegionRepository;
import ru.dorofeev.mobilemap.service.interf.RegionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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
        log.info("IN save() - Регион сохранен!");
    }

    @Override
    public void update(Region region) {
        Optional<Region> byId = regionRepository.findById(region.getId());

        if (byId.isPresent()) {
            regionRepository.save(region);
            log.info("IN update() - Обновлен регион с ID: {}", byId.get().getId());
        }

        log.info("IN update() - Не удалось найти регион с ID: {}", region.getId());
    }

    @Override
    public void deleteById(UUID id) {
        regionRepository.deleteById(id);
        log.info("IN deleteById() - Регион с ID: {} удален!", id);
    }

    @Override
    public void deleteByName(String name) {
        regionRepository.deleteByName(name);
        log.info("IN deleteByName() - Регион с name: {} удален!", name);
    }
}
