package ru.dorofeev.mobilemap.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.dorofeev.mobilemap.model.entity.GeographicalObject;

import java.util.List;
import java.util.Optional;

public interface AbstractController<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    void add(T object);
    void delete(Long id);
    void update(T object);
}
