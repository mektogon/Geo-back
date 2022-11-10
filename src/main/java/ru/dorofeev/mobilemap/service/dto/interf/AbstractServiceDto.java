package ru.dorofeev.mobilemap.service.dto.interf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractServiceDto<T> {
    List<T> getAll();

    Optional<T> findById(UUID id);

    List<T> getAllByName(String name);

    void save(T t);

    void update(T t);

}
