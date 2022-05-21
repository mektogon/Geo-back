package ru.dorofeev.mobilemap.controller;

import java.util.List;
import java.util.Optional;

public interface AbstractController<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    void save(T object);
    void delete(Long id);
    void update(T object);
}
