package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Region;
import ru.dorofeev.mobilemap.model.base.Street;
import ru.dorofeev.mobilemap.repository.StreetRepository;
import ru.dorofeev.mobilemap.service.interf.StreetService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {
    private final StreetRepository streetRepository;

    @Override
    public List<Street> getAll() {
        return streetRepository.findAll();
    }

    @Override
    public Optional<Street> findById(UUID id) {
        return streetRepository.findById(id);
    }

    @Override
    public void save(Street street) {
        streetRepository.save(street);
        log.info("IN save() - Улица сохранена!");
    }

    @Override
    public void update(Street street) {
        Optional<Street> byId = streetRepository.findById(street.getId());

        if (byId.isPresent()) {
            streetRepository.save(street);
            log.info("IN update() - Обновлена улица с ID: {}", byId.get().getId());
        }

        log.info("IN update() - Не удалось найти улицу с ID: {}", street.getId());
    }

    @Override
    public void deleteById(UUID id) {
        streetRepository.deleteById(id);
        log.info("IN deleteById() - Улица с ID: {} удалена!", id);
    }

    @Override
    public void deleteByName(String name) {
        streetRepository.deleteByName(name);
        log.info("IN deleteByName() - Улица с name: {} удалена!", name);
    }
}
