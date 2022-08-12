package ru.dorofeev.mobilemap.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractController<T> {
    List<T> getAll();
    Optional<T> getById(UUID id);
    void save(T object);
    void delete(UUID id);
    void update(T object);
}
