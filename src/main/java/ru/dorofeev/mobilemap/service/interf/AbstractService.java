package ru.dorofeev.mobilemap.service.interf;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T> {
    List<T> findALl();

    Optional<T> findById(Long id);

    Optional<T> save(T t);

    void update(T t);

    void deleteById(Long id);
}
