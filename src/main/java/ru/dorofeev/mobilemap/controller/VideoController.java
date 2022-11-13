package ru.dorofeev.mobilemap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Video;
import ru.dorofeev.mobilemap.service.interf.VideoService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
@Tag(name = "Видеозапись", description = "Контроллер для работы с видеозаписями.")
public class VideoController {

    private final VideoService videoService;

    @Operation(
            summary = "Загрузка видеозаписи",
            description = "Загружает видеозапись и привязывает к переданному гео-объекту по его ID."
    )
    @PostMapping()
    public void upload(@RequestParam("video") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        videoService.upload(file, id);
    }

    @Operation(
            summary = "Обновление видеозаписи",
            description = "Позволяет обновить переданные поля видеозаписи."
    )
    @PatchMapping()
    public void update(@RequestParam("id") UUID id,
                       @RequestParam(value = "video") MultipartFile file) {
        videoService.update(id, file);
    }

    @Operation(
            summary = "Удаление видеозаписи по ID",
            description = "Позволяет удалить видеозапись по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        videoService.deleteById(id);
    }

    @Operation(
            summary = "Получить все видеозаписи",
            description = "Позволяет получить список сохраненных видеозаписей."
    )
    @GetMapping()
    public List<Video> getAll() {
        return videoService.getAll();
    }

    @Operation(
            summary = "Получить информацию о видеозаписях по ID",
            description = "Позволяет получить полную информацию о видеозаписях по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    public Video getInfoById(@PathVariable UUID id) {
        return videoService.getById(id);
    }

    @Operation(
            summary = "Получить 'представление' видеозаписи",
            description = "Позволяет отобразить видеозапись."
    )
    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return videoService.getViewFileById(id);
    }

}
