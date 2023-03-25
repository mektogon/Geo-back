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
import ru.dorofeev.mobilemap.model.dto.ManifestoDto;
import ru.dorofeev.mobilemap.model.dto.ManifestoRequest;
import ru.dorofeev.mobilemap.service.dto.interf.ManifestoServiceDto;
import ru.dorofeev.mobilemap.service.interf.ManifestoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/manifesto")
@RequiredArgsConstructor
@Tag(name = "Координаты дороги", description = "Контроллер для работы с координатами дороги.")
public class ManifestoController {

    private final ManifestoServiceDto serviceDto;
    private final ManifestoService service;

    @Operation(
            summary = "Получить все манифесты тайлов карт",
            description = "Позволяет получить список манифестов."
    )
    @GetMapping("/all")
    public List<ManifestoDto> getAll() {
        return serviceDto.getAll();
    }

    @Operation(
            summary = "Получить манифест по id",
            description = "Позволяет получить объект по идентификатору."
    )
    @GetMapping("/{id}")
    public ManifestoDto getById(@PathVariable UUID id) {
        return serviceDto.getById(id);
    }

    @Operation(
            summary = "Получить манифест по id",
            description = "Позволяет получить объект по идентификатору."
    )
    @GetMapping("/getByName/{name}")
    public ManifestoDto getById(@PathVariable String name) {
        return serviceDto.getByName(name);
    }

    @Operation(
            summary = "Получить первый манифест",
            description = "Позволяет получить первый манифест, согласно сортировке (ASC(DEFAULT VALUE), DESC)."
    )
    @GetMapping()
    public ManifestoDto getFirstManifest(@RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortName) {
        return serviceDto.getFirstManifesto(sortName);
    }

    @Operation(
            summary = "Удаление манифеста по ID",
            description = "Позволяет удалить объект по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @Operation(
            summary = "Обновление манифеста",
            description = "Позволяет обновить переданные поля объекта."
    )
    @PatchMapping()
    public void update(@RequestBody ManifestoRequest manifestoRequest) {
        service.update(manifestoRequest);
    }

    @Operation(
            summary = "Сохранение манифеста",
            description = "Позволяет сохранить манифест с переданными идентификаторам архивов тайлов и координат дорог."
    )
    @PostMapping()
    public ResponseEntity<String> save(@RequestBody ManifestoRequest manifestoRequest) {
        return service.save(manifestoRequest);
    }
}
