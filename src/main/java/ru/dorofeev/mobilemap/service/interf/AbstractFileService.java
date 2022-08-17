package ru.dorofeev.mobilemap.service.interf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractFileService<T> {

    void update(T file);

    void deleteById(UUID id);

    List<T> getAll();

    Optional<T> findById(UUID id);

    List<T> findAllInfoByName(String name);
}
