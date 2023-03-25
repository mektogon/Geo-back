package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.Manifesto;
import ru.dorofeev.mobilemap.model.dto.ManifestoRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManifestoService {
    /**
     * Получить все манифесты.
     *
     * @return список манифестов.
     */
    List<Manifesto> getAll();

    /**
     * Получить манифест по идентификатору.
     *
     * @param id идентификатор манифеста.
     * @return манифест.
     */
    Optional<Manifesto> getById(UUID id);

    /**
     * Получить манифест по наименование.
     *
     * @param name наименование манифеста.
     * @return манифест.
     */
    Manifesto getByName(String name);

    /**
     * Получить первый манифест в БД
     *
     * @return первый манифест в бд.
     */
    Manifesto getFirstManifesto(String sortName);

    /**
     * Удалить манифест по идентификатору.
     *
     * @param id идентификатор удаляемого объекта.
     */
    void deleteById(UUID id);

    /**
     * Создание манифеста по идентификаторам архивов с тайлами и координатами дорог
     *
     * @param manifestoRequest тело, с сохраняемой информацией.
     */
    ResponseEntity<String> save(ManifestoRequest manifestoRequest);

    /**
     * Обновление манифеста
     *
     * @param manifestoRequest тело, с сохраняемой информацией.
     */
    void update(ManifestoRequest manifestoRequest);
}
