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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Video;
import ru.dorofeev.mobilemap.service.interf.VideoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
@Tag(name="Видеозапись", description="Контроллер для работы с видеозаписями.")
public class VideoController implements AbstractFileController<Video> {

    private final VideoService videoService;

    @Operation(
            summary = "Загрузка видеозаписи",
            description = "Загружает видеозапись и привязывает к переданному гео-объекту по его ID."
    )
    @PostMapping()
    @Override
    public void upload(@RequestParam("video") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        videoService.upload(file, id);
    }

    @Operation(
            summary = "Обновление видеозаписи",
            description = "Позволяет обновить переданные поля видеозаписи."
    )
    @PatchMapping()
    @Override
    public void update(@RequestBody Video file) {
        videoService.update(file);
    }

    @Operation(
            summary = "Удаление видеозаписи по ID",
            description = "Позволяет удалить видеозапись по идентификатору."
    )
    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        videoService.deleteById(id);
    }

    @Operation(
            summary = "Получить все видеозаписи",
            description = "Позволяет получить список сохраненных видеозаписей."
    )
    @GetMapping()
    @Override
    public List<Video> getAll() {
        return videoService.getAll();
    }

    @Operation(
            summary = "Получить информацию о видеозаписях по ID",
            description = "Позволяет получить полную информацию о видеозаписях по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    @Override
    public Optional<Video> getInfoById(@PathVariable UUID id) {
        return videoService.findById(id);
    }

    @Operation(
            summary = "Получить информацию о видеозаписях по наименованию",
            description = "Позволяет получить полную информацию о видеозаписях по переданному имени."
    )
    @GetMapping("/getAllInfoByName/{name}")
    @Override
    public List<Video> getAllInfoByName(@PathVariable String name) {
        return videoService.findAllInfoByName(name);
    }

    @Operation(
            summary = "Получить 'представление' видеозаписи",
            description = "Позволяет отобразить видеозапись."
    )
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return videoService.getFileById(id);
    }

}
