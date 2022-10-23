package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.TypeObject;
import ru.dorofeev.mobilemap.repository.TypeObjectRepository;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeObjectServiceImpl implements TypeObjectService {
    private final TypeObjectRepository typeObjectRepository;

    @Override
    public List<TypeObject> getAll() {
        return typeObjectRepository.findAll();
    }

    @Override
    public Optional<TypeObject> findById(UUID id) {
        return typeObjectRepository.findById(id);
    }

    @Override
    public List<TypeObject> findAllByName(String name) {
        if (name == null) {
            log.error("IN findAllByName() - Имя отсутствует!");
            return Collections.emptyList();
        }
        return typeObjectRepository.findAllByName(name);
    }

    @Override
    public UUID saveAndReturnId(TypeObject typeObject) {
        TypeObject savedEntity = typeObjectRepository.save(typeObject);
        log.info("IN saveAndReturnId() - Тип объекта сохранен!");

        return savedEntity.getId();
    }

    @Override
    public void save(TypeObject typeObject) {
        typeObjectRepository.save(typeObject);
        log.info("IN save() - Тип объекта сохранен!");
    }

    @Override
    public void update(TypeObject typeObject) {
        typeObjectRepository.save(typeObject);
        log.info("IN update() - Тип объекта с ID: {} обновлен!", typeObject.getId());
    }

    @Override
    public void deleteById(UUID id) {
        typeObjectRepository.deleteById(id);
        log.info("IN deleteById() - Тип объекта с ID: {} удален!", id);
    }

    @Override
    public void deleteByName(String name) {
        typeObjectRepository.deleteByName(name);
        log.info("IN deleteByName() - Тип объекта с name: {} удален!", name);
    }

    @Override
    public TypeObject getTypeObjectByName(String typeName) {
        TypeObject typeObjectByName = typeObjectRepository.getTypeObjectByName(typeName);

        if (typeObjectByName == null) {
            log.error("IN getTypeObjectByName() - Имя отсутствует!");
            return typeObjectRepository.getTypeObjectByName("Отсутствует");
        }

        log.info("IN getTypeObjectByName() - Тип объекта с name: {} найден!", typeName);
        return typeObjectByName;
    }
}
