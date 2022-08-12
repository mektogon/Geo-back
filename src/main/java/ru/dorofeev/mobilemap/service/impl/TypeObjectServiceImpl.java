package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.TypeObject;
import ru.dorofeev.mobilemap.repository.TypeObjectRepository;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            return Collections.emptyList();
        }
        return typeObjectRepository.findAllByName(name);
    }

    @Override
    public void save(TypeObject typeObject) {
       typeObjectRepository.save(typeObject);
    }

    @Override
    public void update(TypeObject typeObject) {
        typeObjectRepository.save(typeObject);
    }

    @Override
    public void deleteById(UUID id) {
        typeObjectRepository.deleteById(id);
    }


}
