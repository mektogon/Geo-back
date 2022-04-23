package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.entity.GeographicalObject;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T> {
    List<T> findALl();
    Optional<T> findById(Long id);
    Optional<T> save(T t);
    void delete(T t);
    void deleteById(T t);
}
