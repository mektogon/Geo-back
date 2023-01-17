package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Контракт с методами для файлов v1.
 * @param <T> тип сущности.
 */
public interface AbstractFileService<T> {

    /**
     * Получить все файлы.
     * @return список файлов.
     */
    List<T> getAll();

    /**
     * Получить файл по ID.
     * @param id идентификатор файла.
     * @return файл.
     */
    T getById(UUID id);


    /**
     * Загрузить файл и привязать к объекту.
     * @param file загружаемый файл.
     * @param id идентификатор объекта.
     */
    void upload(MultipartFile[] file, UUID id);

    /**
     * Обновить файл по ID.
     * @param id идентификатор объекта.
     * @param file обновляющий файл.
     */
    void update(UUID id, MultipartFile file);

    /**
     * Удалить файл по ID.
     * @param id идентификатор файла.
     */
    void deleteById(UUID id);

    /**
     * Получить список файлов по ID гео-объекта.
     * @param id идентификатор гео-объекта.
     * @return список файлов.
     */
    List<T> getAllFilesByGeographicalObjectId(UUID id);

    /**
     * Получить 'отображение' файла по ID.
     * @param id идентификатор файла.
     * @return 'отображение' файла.
     * @throws IOException
     */
    ResponseEntity<byte[]> getViewFileById(UUID id);
}

