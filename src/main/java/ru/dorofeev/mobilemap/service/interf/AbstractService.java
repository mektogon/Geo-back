package ru.dorofeev.mobilemap.service.interf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Контрак с базовыми методами для работы с объектами.
 * @param <T>
 */
public interface AbstractService<T> {

    /**
     * Получить весь список объектов.
     * @return список объектов.
     */
    List<T> getAll();

    /**
     * Получить объект по ID.
     * @param id идентификатор объекта.
     * @return
     */
    Optional<T> getById(UUID id);

    /**
     * Сохранить объект.
     * @param t сохраняемый объект.
     */
    void save(T t);

    /**
     * Обновить объект.
     * @param t обновляемый объект.
     */
    void update(T t);

    /**
     * Удалить объект по ID.
     * @param id идентификатор объекта.
     */
    void deleteById(UUID id);

    /**
     * Удалить объект по наименованию.
     * @param name наименование объекта.
     */
    void deleteByName(String name);
}
