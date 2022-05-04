package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.GeographicalObject;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/geo")
public class GeographicalObjectController implements AbstractObjectDataController<GeographicalObject>{
    GeographicalObjectService geographicalObjectService;

    @Autowired
    public GeographicalObjectController(GeographicalObjectService geographicalObjectService) {
        this.geographicalObjectService = geographicalObjectService;
    }

    @GetMapping("list")
    @Override
    public List<GeographicalObject> getAll() {
        return geographicalObjectService.findALl();
    }

    @GetMapping("find/{id}")
    @Override
    public Optional<GeographicalObject> getById(@PathVariable Long id) {
        return geographicalObjectService.findById(id);
    }

    @GetMapping("find_by_name/{name}")
    @Override
    public List<GeographicalObject> getById(@PathVariable String name) {
        return geographicalObjectService.findAllByName(name);
    }

    @PostMapping("add")
    @Override
    public void add(@RequestBody GeographicalObject object) {
        geographicalObjectService.save(object);
    }

    @DeleteMapping("delete/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        geographicalObjectService.deleteById(id);
    }

    @PutMapping("update")
    @Override
    public void update(@RequestBody GeographicalObject object) {
        geographicalObjectService.update(object);
    }
}
