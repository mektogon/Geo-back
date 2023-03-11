package ru.dorofeev.mobilemap.service.interf;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * Адаптер для работы с Open Street Map для получения тайлов карт.
 */
public interface OpenStreetMapAdapter {

    /**
     * Метод вызывающий API tile.openstreetmap.org/zoom/x/y.png для получения тайла по координатам.
     * Документация:
     *
     * @param x    точка на абсциссе.
     * @param y    точка на ординате.
     * @param zoom точка на аппликате [0;19].
     * @return файл.
     * @see <a href="https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Lon./lat._to_tile_numbers">Документация OSM</a>
     */
    ResponseEntity<Resource> getTileByPoints(int zoom, int x, int y);
}
