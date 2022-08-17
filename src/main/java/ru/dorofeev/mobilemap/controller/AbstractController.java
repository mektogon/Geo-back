package ru.dorofeev.mobilemap.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractController<T> {
    List<T> getAll();

    /**
     * @param id идентификатор искомого объекта.
     * @return возвращает искомый объект.
     */
    Optional<T> getById(UUID id);

    /**
     * Метод осуществляет поиск по имени.
     *
     * @param name имя искомого объекта.
     * @return возвращает искомый объект.
     */
    List<T> getByName(String name);

    /**
     * Метод осуществляет сохранение объекта.
     *
     * @param object сохраняемый объект.
     */
    void save(T object);

    /**
     * @param id идентификатор удаляемого объекта.
     */
    void deleteById(UUID id);

    /**
     * @param name имя удаляемого объекта.
     */
    void deleteByName(String name);

    /**
     * Метод осуществляет обновление объекта.
     *
     * @param object обновляемый объект.
     */
    void update(T object);
}
