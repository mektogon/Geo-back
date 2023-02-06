package ru.dorofeev.mobilemap.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ru.dorofeev.mobilemap.model.base.Audio;
import ru.dorofeev.mobilemap.model.dto.FileDto;
import ru.dorofeev.mobilemap.service.dto.interf.FileServiceDto;
import ru.dorofeev.mobilemap.service.interf.AudioService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audio")
@Tag(name = "Аудиозапись", description = "Контроллер для работы с аудиозаписями.")
public class AudioController {
    private final AudioService audioService;
    private final FileServiceDto audioServiceDto;

    public AudioController(AudioService audioService, @Qualifier("AudioServiceDtoImpl") FileServiceDto audioServiceDto) {
        this.audioService = audioService;
        this.audioServiceDto = audioServiceDto;
    }

    @Operation(
            summary = "Загрузка аудиозаписи",
            description = "Загружает аудиозапись и привязывает к переданному гео-объекту по его ID."
    )
    @PostMapping()
    public void upload(@RequestParam("audio") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        audioService.upload(file, id);
    }

    @Operation(
            summary = "Обновление аудиозаписи",
            description = "Позволяет обновить переданные поля аудиозаписи."
    )
    @PatchMapping()
    public void update(@RequestParam("id") UUID id,
                       @RequestParam(value = "audio") MultipartFile file) {
        audioService.update(id, file);
    }

    @Operation(
            summary = "Удаление аудиозаписи по ID",
            description = "Позволяет удалить аудиозапись по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        audioService.deleteById(id);
    }

    @Operation(
            summary = "Получить все аудиозаписи",
            description = "Позволяет получить список сохраненных аудиозаписей."
    )
    @GetMapping()
    public List<Audio> getAll() {
        return audioService.getAll();
    }

    @Operation(
            summary = "Получить все аудиозаписи по ID гео-объекта",
            description = "Позволяет получить список аудиозаписей, привязанных к гео-объекту."
    )
    @GetMapping("/getAllByGeoId/{id}")
    public List<FileDto> getAllByGeoId(@PathVariable UUID id) {
        return audioServiceDto.getAllByGeoId(id);
    }

    @Operation(
            summary = "Получить информацию об аудиозаписи по ID",
            description = "Позволяет получить полную информацию об аудиозаписи по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    public Audio getInfoById(@PathVariable UUID id) {
        return audioService.getById(id);
    }

    @Operation(
            summary = "Получить 'представление' аудиозаписи",
            description = "Позволяет отобразить аудиозапись."
    )
    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable String id) {
        return audioService.getViewFileById(UUID.fromString(AuxiliaryUtils.getOriginalNameWithoutExtension(id)));
    }

}
