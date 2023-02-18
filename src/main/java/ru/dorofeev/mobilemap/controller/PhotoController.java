package ru.dorofeev.mobilemap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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
import ru.dorofeev.mobilemap.model.dto.FileDto;
import ru.dorofeev.mobilemap.model.dto.PhotoRotateDto;
import ru.dorofeev.mobilemap.service.dto.interf.FileServiceDto;
import ru.dorofeev.mobilemap.service.interf.PhotoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/photo")
@Tag(name = "Фотография", description = "Контроллер для работы с фотографиями.")
public class PhotoController {

    private final PhotoService photoService;

    private final FileServiceDto photoServiceDto;

    public PhotoController(PhotoService photoService, @Qualifier("PhotoServiceDtoImpl") FileServiceDto photoServiceDto) {
        this.photoService = photoService;
        this.photoServiceDto = photoServiceDto;
    }

    @Operation(
            summary = "Загрузка фотографий",
            description = "Загружает фотографии и привязывает к переданному гео-объекту по его ID."
    )
    @PostMapping()
    public void upload(@RequestParam("image") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        photoService.upload(file, id);
    }

    @Operation(
            summary = "Обновление фотографии",
            description = "Позволяет обновить переданные поля фотографии."
    )
    @PatchMapping()
    public void update(@RequestParam("id") UUID id,
                       @RequestParam(value = "photo") MultipartFile file) {
        photoService.update(id, file);
    }

    @Operation(
            summary = "Удаление фотографии по ID",
            description = "Позволяет удалить фотографию по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        photoService.deleteById(id);
    }

    @Operation(
            summary = "Получить все фотографии",
            description = "Позволяет получить список сохраненных фотографий."
    )
    @GetMapping()
    public List<Photo> getAll() {
        return photoService.getAll();
    }

    @Operation(
            summary = "Получить все фотографии по ID гео-объекта",
            description = "Позволяет получить список фотографий, привязанных к гео-объекту."
    )
    @GetMapping("/getAllByGeoId/{id}")
    public List<FileDto> getAllByGeoId(@PathVariable UUID id) {
        return photoServiceDto.getAllByGeoId(id);
    }

    @Operation(
            summary = "Получить информацию о фотографии по ID",
            description = "Позволяет получить полную информацию о фотографии по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    public Photo getInfoById(@PathVariable UUID id) {
        return photoService.getById(id);
    }

    @Operation(
            summary = "Получить 'представление' фотографии",
            description = "Позволяет отобразить фотографию."
    )
    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) {
        return photoService.getViewFileById(id);
    }

    @Operation(
            summary = "Получить 'представление' превью главной фотографии",
            description = "Позволяет отобразить фотографию."
    )
    @GetMapping("/preview/view/{id}")
    public ResponseEntity<byte[]> getPreviewPhotoById(@PathVariable UUID id) {
        return photoService.getPreviewPhotoById(id);
    }

    @Operation(
            summary = "Позволяет повернуть изображение",
            description = "Позволяет повернуть изображение на целочисленный градус."
    )
    @PostMapping("/rotate")
    public void rotatePhoto(@RequestParam("photoId") UUID photoId,
                            @RequestParam("angle") int rotationAngle,
                            @RequestParam(required = false)
                            @Parameter(description = "Флаг, указывающий что поворачиваем превью у фотографии.")
                            boolean isPreview
    ) {
        photoService.rotatePhoto(photoId, rotationAngle, isPreview);
    }

    @Operation(
            summary = "Позволяет повернуть изображение",
            description = "Позволяет повернуть изображение на целочисленный градус."
    )
    @PostMapping(value = "/rotate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void rotatePhoto(@RequestBody PhotoRotateDto photoRotateDto) {
        photoService.rotatePhoto(photoRotateDto);
    }

}
