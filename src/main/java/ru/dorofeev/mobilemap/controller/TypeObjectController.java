package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.TypeObject;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type-object")
@RequiredArgsConstructor
public class TypeObjectController implements AbstractController<TypeObject> {

    private final TypeObjectService typeObjectService;

    @GetMapping()
    @Override
    public List<TypeObject> getAll() {
        return typeObjectService.getAll();
    }

    @GetMapping("/getById/{id}")
    @Override
    public Optional<TypeObject> getById(@PathVariable UUID id) {
        return typeObjectService.findById(id);
    }

    @GetMapping("/{name}")
    @Override
    public List<TypeObject> getByName(@PathVariable String name) {
        return typeObjectService.findAllByName(name);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody TypeObject object) {
        typeObjectService.save(object);
    }

    @DeleteMapping("/deleteById/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        typeObjectService.deleteById(id);
    }

    @DeleteMapping("/{name}")
    @Override
    public void deleteByName(@PathVariable String name) {
        typeObjectService.deleteByName(name);
    }

    @PutMapping()
    @Override
    public void update(@RequestBody TypeObject object) {
        Optional<TypeObject> byId = typeObjectService.findById(object.getId());

        if (byId.isPresent()) {
            typeObjectService.save(object);
        }
    }
}
