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
import ru.dorofeev.mobilemap.model.dto.RoadCoordinateDto;
import ru.dorofeev.mobilemap.service.dto.interf.RoadCoordinateServiceDto;
import ru.dorofeev.mobilemap.service.interf.RoadCoordinateService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/road")
@RequiredArgsConstructor
@Tag(name = "Координаты дороги", description = "Контроллер для работы с координатами дороги.")
public class RoadCoordinateController {
    private final RoadCoordinateServiceDto serviceDto;
    private final RoadCoordinateService service;

    @Operation(
            summary = "Получить все объекты \"Координаты дорог\"",
            description = "Позволяет получить список всех объектов данного типа."
    )
    @GetMapping()
    public List<RoadCoordinateDto> getAll() {
        return serviceDto.getAll();
    }

    @Operation(
            summary = "Получить объект \"Координаты дорог\" по ID",
            description = "Позволяет получить объект по идентификатору."
    )
    @GetMapping("/getAllById")
    public List<RoadCoordinateDto> getByAllId(@RequestParam("listId") List<UUID> listId) {
        return serviceDto.getByAllId(listId);
    }


    @Operation(
            summary = "Получить объект \"Координаты дорог\" по ID",
            description = "Позволяет получить объект по идентификатору."
    )
    @GetMapping("/{id}")
    public RoadCoordinateDto getById(@PathVariable UUID id) {
        return serviceDto.getById(id);
    }

    @Operation(
            summary = "Сохранение объект \"Координаты дорог\"",
            description = "Позволяет сохранить переданный объект."
    )
    @PostMapping()
    public ResponseEntity<String> save(@RequestBody RoadCoordinateDto RoadCoordinate) {
        return serviceDto.save(RoadCoordinate);
    }

    @Operation(
            summary = "Удаление объекта \"Координаты дорог\" по ID",
            description = "Позволяет удалить объект по идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @Operation(
            summary = "Удаление объект \"Координаты дорог\" по наименованию",
            description = "Позволяет удалить объект по переданному имени."
    )
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @Operation(
            summary = "Обновление объекта \"Координаты дорог\"",
            description = "Позволяет обновить переданные поля объекта."
    )
    @PatchMapping()
    public void update(@RequestBody @Valid RoadCoordinateDto RoadCoordinate) {
        serviceDto.update(RoadCoordinate);
    }
}
