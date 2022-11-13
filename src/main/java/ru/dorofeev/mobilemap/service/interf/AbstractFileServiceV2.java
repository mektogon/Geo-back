package ru.dorofeev.mobilemap.service.interf;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Контракт с методами для файлов v2.
 *
 * @param <T> тип сущности.
 */
public interface AbstractFileServiceV2<T> {

    /**
     * Получить все файлы.
     *
     * @return список файлов.
     */
    List<T> getAll();

    /**
     * Получить файл по ID.
     *
     * @param id идентификатор файла.
     * @return файл.
     */
    T getById(UUID id);

    /**
     * Загрузить файл с пользовательским названием.
     * @param file загружаемый файл.
     * @param name наименование файла.
     */
    void upload(MultipartFile file, String name);

    /**
     * Обновить файл по ID.
     * @param id идентификатор файла.
     * @param name наименование файла.
     * @param file обвноляющий файл.
     */
    void update(UUID id, String name, MultipartFile file);


    /**
     * Удалить файл по ID.
     *
     * @param id идентификатор файла.
     */
    void deleteById(UUID id);

    /**
     * Удалить файл по наименованию.
     *
     * @param name наименование файла.
     */
    void deleteByName(String name);

    /**
     * Получить файл по наименованию.
     *
     * @param name наименование файла.
     * @return файл.
     */
    T getByName(String name);

    /**
     * Получить список файлов по наименованию.
     *
     * @param name наименование файла.
     * @return список файлов.
     */
    List<T> getAllByName(String name);
}
