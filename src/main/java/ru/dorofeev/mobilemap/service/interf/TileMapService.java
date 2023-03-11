package ru.dorofeev.mobilemap.service.interf;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.TileMap;
import ru.dorofeev.mobilemap.model.dto.TileCoordinateDto;
import ru.dorofeev.mobilemap.model.dto.TilePointDto;

import java.util.List;
import java.util.UUID;

public interface TileMapService extends AbstractFileServiceV2<TileMap> {
    /**
     * Позволяет скачать файл по ID
     *
     * @param id идентификатор скачиваемого файла.
     * @return файл.
     */
    ResponseEntity<Resource> downloadFileById(UUID id);

    /**
     * Позволяет скачать файл по наименованию.
     *
     * @param name наименование скачиваемого файла.
     * @return файл.
     */
    ResponseEntity<Resource> downloadFileByName(String name);

    /**
     * Позволяет скачать архив с тайлами карт, у которого isMain = true.
     *
     * @return файл.
     */
    ResponseEntity<Resource> downloadMainTileMap();


    /**
     * Метод позволяет скачать и сохранить тайлы карт по списку точек.
     *
     * @param points список точек.
     * @return UUID архива с тайлами.
     */
    ResponseEntity<String> createTilesByPoints(List<TilePointDto> points);

    /**
     * Метод позволяет скачать и сохранить тайлы по заданным координатам и масштабу.
     * Строит квадрат по заданным координатам и скачивает тайлы из этого диапазона и масштабу.
     * @param coordinates координаты и масштаб.
     * @return UUID архива с тайлами.
     */
    ResponseEntity<String> createTilesByCoordinates(TileCoordinateDto coordinates);

    /**
     * Позволяет установить флаг isMain = true на архив с тайлами карт.
     *
     * @param id идентификатор архива с тайлами.
     * @return true/false в зависимости от выполнения операции.
     */
    boolean makeTilesIsMain(UUID id);
}
