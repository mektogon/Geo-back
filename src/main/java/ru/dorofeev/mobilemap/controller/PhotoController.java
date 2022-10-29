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
import ru.dorofeev.mobilemap.model.base.Photo;
import ru.dorofeev.mobilemap.service.interf.PhotoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
@Tag(name="Фотография", description="Контроллер для работы с фотографиями.")
public class PhotoController implements AbstractFileController<Photo> {

    private final PhotoService photoService;

    @Operation(
            summary = "Загрузка фотографий",
            description = "Загружает фотографии и привязывает к переданному гео-объекту по его ID."
    )
    @PostMapping()
    @Override
    public void upload(@RequestParam("image") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        photoService.upload(file, id);
    }

    @Operation(
            summary = "Обновление фотографии",
            description = "Позволяет обновить переданные поля фотографии."
    )
    @PatchMapping()
    @Override
    public void update(@RequestBody Photo file) {
        photoService.update(file);
    }

    @Operation(
            summary = "Удаление фотографии по ID",
            description = "Позволяет удалить фотографию по идентификатору."
    )
    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        photoService.deleteById(id);
    }

    @Operation(
            summary = "Получить все фотографии",
            description = "Позволяет получить список сохраненных фотографий."
    )
    @GetMapping()
    @Override
    public List<Photo> getAll() {
        return photoService.getAll();
    }

    @Operation(
            summary = "Получить информацию о фотографии по ID",
            description = "Позволяет получить полную информацию о фотографии по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    @Override
    public Optional<Photo> getInfoById(@PathVariable UUID id) {
        return photoService.findById(id);
    }

    @Operation(
            summary = "Получить информацию о фотографиях по наименованию",
            description = "Позволяет получить полную информацию о фотографиях по переданному имени."
    )
    @GetMapping("/getAllInfoByName/{name}")
    @Override
    public List<Photo> getAllInfoByName(@PathVariable String name) {
        return photoService.findAllInfoByName(name);
    }

    @Operation(
            summary = "Получить 'представление' фотографии",
            description = "Позволяет отобразить фотографию."
    )
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return photoService.getFileById(id);
    }

}
