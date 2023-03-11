package ru.dorofeev.mobilemap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.TileMap;
import ru.dorofeev.mobilemap.model.dto.TileCoordinateDto;
import ru.dorofeev.mobilemap.model.dto.TileMapDto;
import ru.dorofeev.mobilemap.model.dto.TilePointDto;
import ru.dorofeev.mobilemap.service.dto.interf.TailMapDtoService;
import ru.dorofeev.mobilemap.service.interf.TileMapService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tile-map")
@RequiredArgsConstructor
@Tag(name = "Тайлы карт", description = "Контроллер для работы с тайлами карт.")
public class TileMapController {
    private final TileMapService tileMapService;
    private final TailMapDtoService tailMapDtoService;

    @Operation(
            summary = "Загрузка тайлов карты",
            description = "Позволяет загрузить тайлы карты."
    )
    @PostMapping()
    public void upload(@RequestParam("tile-map") MultipartFile file, @RequestParam("name") String name) {
        tileMapService.upload(file, name);
    }

    @Operation(
            summary = "Обновление тайлов карты",
            description = "Позволяет обновить переданные поля тайлов карты."
    )
    @PatchMapping()
    public void update(@RequestParam("id") UUID id,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "tile-map", required = false) MultipartFile file) {
        tileMapService.update(id, name, file);
    }

    @Operation(
            summary = "Удаление тайлов карты по ID",
            description = "Позволяет удалить тайлы карты по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        tileMapService.deleteById(id);
    }

    @Operation(
            summary = "Удаление тайлов карты по наименованию",
            description = "Позволяет удалить тайлы карты по переданному имени."
    )
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        tileMapService.deleteByName(name);
    }

    @Operation(
            summary = "Получить все тайлы карты с системным url",
            description = "Позволяет получить список сохраненных обозначений с url, где хранится фотография в системе."
    )
    @GetMapping("/all")
    public List<TileMap> getAll() {
        return tileMapService.getAll();
    }

    @Operation(
            summary = "Получить все тайлы карт с ссылкой на скачивание",
            description = "Позволяет получить список сохраненных тайлов карт."
    )
    @GetMapping()
    public List<TileMapDto> getAllWithLinkDownload() {
        return tailMapDtoService.getAllWithLinkDownload();
    }

    @Operation(
            summary = "Получить все тайлы карт по имени с ссылкой на скачивание",
            description = "Позволяет получить список сохраненных тайлов карт по имени."
    )
    @GetMapping("/getAllByName/{name}")
    public List<TileMapDto> getAllByNameWithLinkDownload(@PathVariable String name) {
        return tailMapDtoService.getAllByNameWithLinkDownload(name);
    }

    @Operation(
            summary = "Получить информацию о тайлах карты по ID",
            description = "Позволяет получить полную информацию о тайлах карты по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    public TileMap getInfoById(@PathVariable UUID id) {
        return tileMapService.getById(id);
    }

    @Operation(
            summary = "Получить информацию о тайлах карты по наименованию",
            description = "Позволяет получить полную информацию о тайлах карты по переданному имени."
    )
    @GetMapping("/getAllInfoByName/{name}")
    public List<TileMap> getAllInfoByName(@PathVariable String name) {
        return tileMapService.getAllByName(name);
    }

    @Operation(
            summary = "Скачать главный архив с тайлами карт",
            description = "Позволяет скачать главный архив с тайлами карт."
    )
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadMainTileMap() {
        return tileMapService.downloadMainTileMap();
    }

    @Operation(
            summary = "Скачать тайл карты по ID",
            description = "Позволяет скачать тайлы карты по идентифкатору."
    )
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFileById(@PathVariable UUID id) {
        return tileMapService.downloadFileById(id);
    }

    @Operation(
            summary = "Скачать тайл карты по наименованию",
            description = "Позволяет скачать тайлы карты по наименованию файла."
    )
    @GetMapping("/downloadByName/{name}")
    public ResponseEntity<Resource> downloadFileByName(@PathVariable String name) {
        return tileMapService.downloadFileByName(name);
    }

    @Operation(
            summary = "Создать архив тайлов по списку точек (Z X Y)",
            description = "Позволяет создать архив с тайлами карты по списку точек."
    )
    @PostMapping("/createTilesByPoints")
    public ResponseEntity<String> createTilesByPoints(@RequestBody List<TilePointDto> points) {
        return tileMapService.createTilesByPoints(points);
    }

    @Operation(
            summary = "Создать архив тайлов по крайним координатам",
            description = "Позволяет создать архив с тайлами карты по крайним координатам (широта и долгота)."
    )
    @PostMapping("/createTilesByCoordinates")
    public ResponseEntity<String> createTilesByCoordinates(@Valid @RequestBody TileCoordinateDto coordinates) {
        return tileMapService.createTilesByCoordinates(coordinates);
    }

    @Operation(
            summary = "Позволяет обозначить архив, как главный на текущий момент"
    )
    @GetMapping("/makeTileIsMain/{id}")
    public boolean makeTileIsMain(@PathVariable UUID id) {
        return tileMapService.makeTilesIsMain(id);
    }

}
