package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.TypeObject;
import ru.dorofeev.mobilemap.repository.TypeObjectRepository;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.List;
import java.util.Optional;

@Service
public class TypeObjectImpl implements TypeObjectService {
    TypeObjectRepository typeObjectRepository;

    public TypeObjectImpl(TypeObjectRepository typeObjectRepository) {
        this.typeObjectRepository = typeObjectRepository;
    }

    @Override
    public List<TypeObject> findALl() {
        return null;
    }

    @Override
    public Optional<TypeObject> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<TypeObject> save(TypeObject typeObject) {
        return Optional.empty();
    }

    @Override
    public void delete(TypeObject typeObject) {

    }

    @Override
    public void deleteById(TypeObject typeObject) {

    }
}
