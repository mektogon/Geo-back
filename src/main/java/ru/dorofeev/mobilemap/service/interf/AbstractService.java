package ru.dorofeev.mobilemap.service.interf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractService<T> {
    List<T> getAll();

    Optional<T> findById(UUID id);

    void save(T t);

    void update(T t);

    void deleteById(UUID id);

    void deleteByName(String name);
}
