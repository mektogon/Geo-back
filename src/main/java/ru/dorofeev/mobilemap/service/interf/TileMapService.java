package ru.dorofeev.mobilemap.service.interf;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.TileMap;

import java.io.IOException;
import java.util.UUID;

public interface TileMapService extends AbstractFileServiceV2<TileMap> {
    /**
     * Позволяет скачать файл по ID
     * @param id идентификатор скачиваемого файла.
     * @return файл.
     * @throws IOException
     */
    ResponseEntity<Resource> downloadFileById(UUID id) throws IOException;

    /**
     * Позволяет скачать файл по наименованию.
     * @param name наименование скачиваемого файла.
     * @return файл.
     * @throws IOException
     */
    ResponseEntity<Resource> downloadFileByName(String name) throws IOException;
}
