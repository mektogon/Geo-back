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
import ru.dorofeev.mobilemap.model.base.TypeObject;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type-object")
@RequiredArgsConstructor
@Tag(name="Тип гео-объекта", description="Контроллер для работы с типами гео-объектов.")
public class TypeObjectController implements AbstractController<TypeObject> {

    private final TypeObjectService typeObjectService;

    @Operation(
            summary = "Получить все типы гео-объектов",
            description = "Позволяет получить список всех типов гео-объектов."
    )
    @GetMapping()
    @Override
    public List<TypeObject> getAll() {
        return typeObjectService.getAll();
    }

    @Operation(
            summary = "Получить тип гео-объекта по ID",
            description = "Позволяет получить тип гео-объекта по идентификатору."
    )
    @GetMapping("/getById/{id}")
    @Override
    public Optional<TypeObject> getById(@PathVariable UUID id) {
        return typeObjectService.findById(id);
    }

    @Operation(
            summary = "Получить тип гео-объекта по наименованию",
            description = "Позволяет получить тип гео-объекта по переданному имени."
    )
    @GetMapping("/{name}")
    @Override
    public TypeObject getByName(@PathVariable String name) {
        return typeObjectService.getByName(name);
    }

    @Operation(
            summary = "Сохранение типа гео-объекта",
            description = "Позволяет сохранить тип гео-объекта."
    )
    @PostMapping()
    @Override
    public void save(@RequestBody TypeObject object) {
        typeObjectService.save(object);
    }

    @Operation(
            summary = "Удаление типа гео-объекта по ID",
            description = "Позволяет удалить тип гео-объекта по идентификатору."
    )
    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        typeObjectService.deleteById(id);
    }

    @Operation(
            summary = "Удаление типа гео-объекта по наименованию",
            description = "Позволяет удалить тип гео-объекта по переданному имени."
    )
    @DeleteMapping("/deleteByName/{name}")
    @Override
    public void deleteByName(@PathVariable String name) {
        typeObjectService.deleteByName(name);
    }

    @Operation(
            summary = "Обновление типа гео-объекта",
            description = "Позволяет обновить переданные поля типа гео-объекта."
    )
    @PatchMapping()
    @Override
    public void update(@RequestBody TypeObject object) {
        typeObjectService.save(object);
    }
}
