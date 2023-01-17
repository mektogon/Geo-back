package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Locality;
import ru.dorofeev.mobilemap.repository.LocalityRepository;
import ru.dorofeev.mobilemap.service.interf.LocalityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements LocalityService {
    private final LocalityRepository localityRepository;

    @Override
    public List<Locality> getAll() {
        return localityRepository.findAll();
    }

    @Override
    public Optional<Locality> getById(UUID id) {
        return localityRepository.findById(id);
    }

    @Override
    public void save(Locality locality) {
        localityRepository.save(locality);
        log.debug("IN save() - Местность с ID: {} сохранена", locality.getId());
    }

    @Override
    public void update(Locality locality) {
        Optional<Locality> byId = localityRepository.findById(locality.getId());

        if (byId.isPresent()) {
            localityRepository.save(locality);
            log.debug("IN update() - Обновлена местность с ID: {}", byId.get().getId());
        } else {
            log.info("IN update() - Не удалось найти местность с ID: {}", locality.getId());
        }
    }

    @Override
    public void deleteById(UUID id) {
        localityRepository.deleteById(id);
        log.debug("IN deleteById() - Местность с ID: {} удалена", id);
    }

    @Override
    public void deleteByName(String name) {
        localityRepository.deleteByName(name);
        log.debug("IN deleteByName() - Местность с name: {} удалена", name);
    }
}
