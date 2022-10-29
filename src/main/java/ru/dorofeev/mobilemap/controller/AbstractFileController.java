package ru.dorofeev.mobilemap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractFileController<T> {
    /**
     * Загрузка файлов
     * @param file загружаемый файл
     * @param id идентификатор гео-объекта, к которому осуществляется привязка файла.
     */
    void upload(MultipartFile[] file, UUID id);

    /**
     * Метод осуществляет обновление информации о файле.
     *
     * @param file обновляемый файл.
     */
    void update(T file);

    /**
     * Метод осуществляет удаление файла.
     *
     * @param id удаляемый файл.
     */
    void deleteById(UUID id);

    /**
     * Метод позвоялет получить информацию о всех сохраненных файлах.
     *
     * @return возвращает информацию о файлах.
     */
    List<T> getAll();

    /**
     * Позволяет получить информацию о файле.
     *
     * @param id идентификатор искомого файла.
     * @return возвращает информацию о файле.
     */
    Optional<T> getInfoById(UUID id);

    /**
     * Позволяет получить информацию о файлах.
     *
     * @param name имя искомых файлов.
     *             <p>
     *             Пример: имя.расширение
     * @return возвращает информацию о файле.
     */
    List<T> getAllInfoByName(String name);


    /**
     * Метод позволяет получить "представление" файла.
     *
     * @param id идентификатор искомого файла.
     * @return возвращает представление файла.
     * @throws IOException
     */
    ResponseEntity<byte[]> getFileById(UUID id) throws IOException;
}
