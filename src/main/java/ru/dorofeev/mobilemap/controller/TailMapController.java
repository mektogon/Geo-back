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
import ru.dorofeev.mobilemap.model.base.TailMap;
import ru.dorofeev.mobilemap.model.dto.TailMapDto;
import ru.dorofeev.mobilemap.service.dto.interf.TailMapDtoService;
import ru.dorofeev.mobilemap.service.interf.TailMapService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tail-map")
@RequiredArgsConstructor
@Tag(name = "Тайлы карт", description = "Контроллер для работы с тайлами карт.")
public class TailMapController {

    private final TailMapService tailMapService;
    private final TailMapDtoService tailMapDtoService;

    @Operation(
            summary = "Загрузка тайлов карты",
            description = "Позволяет загрузить тайлы карты."
    )
    @PostMapping()
    public void upload(@RequestParam("tail-map") MultipartFile[] file, @RequestParam("name") String name) {
        tailMapService.upload(file, name);
    }

    @Operation(
            summary = "Обновление тайлов карты",
            description = "Позволяет обновить переданные поля тайлов карты."
    )
    @PatchMapping()
    public void update(@RequestBody TailMap file) {
        tailMapService.update(file);
    }

    @Operation(
            summary = "Удаление тайлов карты по ID",
            description = "Позволяет удалить тайлы карты по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        tailMapService.deleteById(id);
    }

    @Operation(
            summary = "Удаление тайлов карты по наименованию",
            description = "Позволяет удалить тайлы карты по переданному имени."
    )
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        tailMapService.deleteByName(name);
    }

    @Operation(
            summary = "Получить все тайлы карты с системным url",
            description = "Позволяет получить список сохраненных обозначений с url, где хранится фотография в системе."
    )
    @GetMapping("/all")
    public List<TailMap> getAll() {
        return tailMapService.getAll();
    }

    @Operation(
            summary = "Получить все тайлы карт с ссылкой на скачивание",
            description = "Позволяет получить список сохраненных тайлов карт."
    )
    @GetMapping()
    public List<TailMapDto> getAllWithLinkDownload() {
        return tailMapDtoService.getAllWithLinkDownload();
    }

    @Operation(
            summary = "Получить все тайлы карт по имени с ссылкой на скачивание",
            description = "Позволяет получить список сохраненных тайлов карт по имени."
    )
    @GetMapping("/getAllByName/{name}")
    public List<TailMapDto> getAllByNameWithLinkDownload(@PathVariable String name) {
        return tailMapDtoService.getAllByNameWithLinkDownload(name);
    }

    @Operation(
            summary = "Получить информацию о тайлах карты по ID",
            description = "Позволяет получить полную информацию о тайлах карты по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    public Optional<TailMap> getInfoById(@PathVariable UUID id) {
        return tailMapService.findById(id);
    }

    @Operation(
            summary = "Получить информацию о тайлах карты по наименованию",
            description = "Позволяет получить полную информацию о тайлах карты по переданному имени."
    )
    @GetMapping("/getAllInfoByName/{name}")
    public List<TailMap> getAllInfoByName(@PathVariable String name) {
        return tailMapService.getAllByName(name);
    }

    @Operation(
            summary = "Скачать тайл карты по ID",
            description = "Позволяет скачать тайлы карты по идентифкатору."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFileById(@PathVariable UUID id) throws IOException {
        return tailMapService.downloadFileById(id);
    }

}
