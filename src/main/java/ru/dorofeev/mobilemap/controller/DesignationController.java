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
import ru.dorofeev.mobilemap.model.base.Designation;
import ru.dorofeev.mobilemap.model.dto.DesignationDto;
import ru.dorofeev.mobilemap.service.dto.interf.DesignationDtoService;
import ru.dorofeev.mobilemap.service.interf.DesignationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/designation")
@RequiredArgsConstructor
@Tag(name = "Обозначения", description = "Контроллер для работы с обозначениями.")
public class DesignationController {

    private final DesignationService designationService;
    private final DesignationDtoService designationDtoService;

    @Operation(
            summary = "Загрузка обозначения",
            description = "Позволяет загрузить обозначение."
    )
    @PostMapping()
    public void upload(@RequestParam("designation") MultipartFile[] file, @RequestParam("name") String name) {
        designationService.upload(file, name);
    }

    @Operation(
            summary = "Обновление обозначения",
            description = "Позволяет обновить переданные поля обозначения."
    )
    @PatchMapping()
    public void update(@RequestBody Designation file) {
        designationService.update(file);
    }

    @Operation(
            summary = "Удаление обозначения по ID",
            description = "Позволяет удалить обозначение по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        designationService.deleteById(id);
    }

    @Operation(
            summary = "Удаление обозначения по наименованию",
            description = "Позволяет удалить обозначение по переданному имени."
    )
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        designationService.deleteByName(name);
    }

    @Operation(
            summary = "Получить все обозначения с системным url",
            description = "Позволяет получить список сохраненных обозначений с url, где хранится фотография в системе."
    )
    @GetMapping("/all")
    public List<Designation> getAll() {
        return designationService.getAll();
    }

    @Operation(
            summary = "Получить все обозначения с фотографиями",
            description = "Позволяет получить список сохраненных обозначений."
    )
    @GetMapping()
    public List<DesignationDto> getAllWithPhoto() {
        return designationDtoService.getAllWithPhoto();
    }

    @Operation(
            summary = "Получить все обозначения по имени с фотографиями",
            description = "Позволяет получить список сохраненных обозначений по имени."
    )
    @GetMapping("/getAllByName/{name}")
    public List<DesignationDto> getAllByNameWithPhoto(@PathVariable String name) {
        return designationDtoService.getAllByNameWithPhoto(name);
    }

    @Operation(
            summary = "Получить информацию об обозначение по ID",
            description = "Позволяет получить полную информацию об обозначении по идентификатору."
    )
    @GetMapping("/getInfoById/{id}")
    public Optional<Designation> getInfoById(@PathVariable UUID id) {
        return designationService.findById(id);
    }

    @Operation(
            summary = "Получить информацию об обозначениях по наименованию",
            description = "Позволяет получить полную информацию об обозначениях по переданному имени."
    )
    @GetMapping("/getAllInfoByName/{name}")
    public List<Designation> getAllInfoByName(@PathVariable String name) {
        return designationService.getAllByName(name);
    }

    @Operation(
            summary = "Получить 'представление' обозначения",
            description = "Позволяет отобразить обозначение."
    )
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return designationService.getFileById(id);
    }
}
