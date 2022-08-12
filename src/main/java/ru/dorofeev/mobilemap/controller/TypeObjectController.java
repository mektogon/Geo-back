package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.TypeObject;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
public class TypeObjectController implements AbstractObjectDataController<TypeObject> {
    private final TypeObjectService typeObjectService;

    @GetMapping()
    @Override
    public List<TypeObject> getAll() {
        return typeObjectService.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public Optional<TypeObject> getById(@PathVariable UUID id) {
        return typeObjectService.findById(id);
    }

    @GetMapping("/name/{name}")
    @Override
    public List<TypeObject> getByName(@PathVariable String name) {
        return typeObjectService.findAllByName(name);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody TypeObject object) {
        typeObjectService.save(object);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable UUID id) {
        typeObjectService.deleteById(id);
    }

    @PutMapping()
    @Override
    public void update(@RequestBody TypeObject object) {
        typeObjectService.update(object);
    }
}
