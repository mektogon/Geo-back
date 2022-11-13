package ru.dorofeev.mobilemap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.model.base.TypeLocality;
import ru.dorofeev.mobilemap.service.interf.TypeLocalityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type-locality")
@RequiredArgsConstructor
@Tag(name = "Тип местности", description = "Контроллер для работы с типами местности.")
public class TypeLocalityController {

    private final TypeLocalityService typeLocalityService;

    @Operation(
            summary = "Получить все типы местностей",
            description = "Позволяет получить список всех типов местностей."
    )
    @GetMapping()
    public List<TypeLocality> getAll() {
        return typeLocalityService.getAll();
    }

    @Operation(
            summary = "Получить тип местности по ID",
            description = "Позволяет получить тип местности по идентификатору."
    )
    @GetMapping("/getById/{id}")
    public Optional<TypeLocality> getById(@PathVariable UUID id) {
        return typeLocalityService.getById(id);
    }

    @Operation(
            summary = "Получить тип местности по наименованию",
            description = "Позволяет получить тип местности по переданному имени."
    )
    @GetMapping("/{name}")
    public TypeLocality getByName(@PathVariable String name) {
        return typeLocalityService.getByName(name);
    }

    @Operation(
            summary = "Получить все типы местности по наименованию",
            description = "Позволяет получить все типы местности по переданному имени."
    )
    @GetMapping("/getAllByName/{name}")
    public List<TypeLocality> getAllByName(@PathVariable String name) {
        return typeLocalityService.getAllByName(name);
    }

    @Operation(
            summary = "Сохранение типа местности",
            description = "Позволяет сохранить тип местности."
    )
    @PostMapping()
    public void save(@RequestBody TypeLocality typeLocality) {
        typeLocalityService.save(typeLocality);
    }

    @Operation(
            summary = "Удаление типа местности по ID",
            description = "Позволяет удалить тип местности по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        typeLocalityService.deleteById(id);
    }

    @Operation(
            summary = "Удаление типа местности по наименованию",
            description = "Позволяет удалить тип местности по переданному имени."
    )
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        typeLocalityService.deleteByName(name);
    }

    @Operation(
            summary = "Обновление типа местности",
            description = "Позволяет обновить переданные поля типа местности."
    )
    @PatchMapping()
    public void update(@RequestBody TypeLocality typeLocality) {
        typeLocalityService.update(typeLocality);
    }
}
