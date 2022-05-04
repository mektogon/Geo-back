package ru.dorofeev.mobilemap.controller;

import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.TypeObject;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/type")
public class TypeObjectController implements AbstractObjectDataController<TypeObject>{
    TypeObjectService typeObjectService;

    public TypeObjectController(TypeObjectService typeObjectService) {
        this.typeObjectService = typeObjectService;
    }

    @GetMapping("list")
    @Override
    public List<TypeObject> getAll() {
        return typeObjectService.findALl();
    }

    @GetMapping("find/{id}")
    @Override
    public Optional<TypeObject> getById(@PathVariable Long id) {
        return typeObjectService.findById(id);
    }

    @GetMapping("find_by_name/{name}")
    @Override
    public List<TypeObject> getById(@PathVariable String name) {
        return typeObjectService.findAllByName(name);
    }

    @PostMapping("add")
    @Override
    public void add(TypeObject object) {
        typeObjectService.save(object);
    }

    @DeleteMapping("delete/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        typeObjectService.deleteById(id);
    }

    @PutMapping("update")
    @Override
    public void update(@RequestBody TypeObject object) {
        typeObjectService.update(object);
    }
}
