package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.TypeLocality;
import ru.dorofeev.mobilemap.repository.TypeLocalityRepository;
import ru.dorofeev.mobilemap.service.interf.TypeLocalityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeLocalityServiceImpl implements TypeLocalityService {

    private final TypeLocalityRepository typeLocalityRepository;

    @Override
    public List<TypeLocality> getAll() {
        return typeLocalityRepository.findAll();
    }

    @Override
    public Optional<TypeLocality> getById(UUID id) {
        return typeLocalityRepository.findById(id);
    }

    @Override
    public TypeLocality getByName(String name) {
        return typeLocalityRepository.findByName(name);
    }

    @Override
    public List<TypeLocality> getAllByName(String name) {
        return typeLocalityRepository.findAllByNameIsContainingIgnoreCase(name);
    }

    @Override
    public void save(TypeLocality typeLocality) {
        typeLocalityRepository.save(typeLocality);
        log.info("IN save() - Тип местности сохранен!");
    }

    @Override
    public void update(TypeLocality typeLocality) {
        Optional<TypeLocality> byId = typeLocalityRepository.findById(typeLocality.getId());

        if (byId.isPresent()) {
            typeLocalityRepository.save(typeLocality);
            log.info("IN update() - Тип местности с ID: {} обновлен!", typeLocality.getId());
        } else {
            log.info("IN update() - Не удалось найти тип местности с ID: {}", typeLocality.getId());
        }
    }

    @Override
    public void deleteById(UUID id) {
        typeLocalityRepository.deleteById(id);
        log.info("IN deleteById() - Тип объекта с ID: {} удален!", id);
    }

    @Override
    public void deleteByName(String name) {
        typeLocalityRepository.deleteByName(name);
        log.info("IN deleteByName() - Тип объекта с name: {} удален!", name);
    }
}
