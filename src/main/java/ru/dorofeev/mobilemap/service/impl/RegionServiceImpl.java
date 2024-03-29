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
    public Optional<Region> getById(UUID id) {
        return regionRepository.findById(id);
    }

    @Override
    public void save(Region region) {
        regionRepository.save(region);
        log.debug("IN save() - Регион сохранен!");
    }

    @Override
    public void update(Region region) {
        Optional<Region> byId = regionRepository.findById(region.getId());

        if (byId.isPresent()) {
            regionRepository.save(region);
            log.debug("IN update() - Обновлен регион с ID: {}", byId.get().getId());
        } else {
            log.info("IN update() - Не удалось найти регион с ID: {}", region.getId());
        }
    }

    @Override
    public void deleteById(UUID id) {
        regionRepository.deleteById(id);
        log.debug("IN deleteById() - Регион с ID: {} удален!", id);
    }

    @Override
    public void deleteByName(String name) {
        regionRepository.deleteByName(name);
        log.debug("IN deleteByName() - Регион с name: {} удален!", name);
    }
}
