package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.GeographicalObject;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/geo")
public class GeographicalObjectController {
    GeographicalObjectService geographicalObjectService;

    @Autowired
    public GeographicalObjectController(GeographicalObjectService geographicalObjectService) {
        this.geographicalObjectService = geographicalObjectService;
    }

    @GetMapping("list")
    public List<GeographicalObject> getAllGeographicalObject() {
        return geographicalObjectService.findALl();
    }

    @GetMapping("find/{id}")
    public Optional<GeographicalObject> getByIdGeographicalObject(@PathVariable Long id) {
        return geographicalObjectService.findById(id);
    }

    @GetMapping("find_by_name/{name}")
    public List<GeographicalObject> getByIdGeographicalObject(@PathVariable String name) {
        return geographicalObjectService.findAllByName(name);
    }

    @PostMapping("add")
    public void addGeographicalObject(@RequestBody GeographicalObject geographicalObject) {
        geographicalObjectService.save(geographicalObject);
    }

    @DeleteMapping("delete/{id}")
    public void deleteGeographicalObject(@PathVariable Long id) {
        geographicalObjectService.deleteById(id);
    }

    @PutMapping("update")
    public void updateGeographicalObject(@RequestBody GeographicalObject geographicalObject) {
        geographicalObjectService.update(geographicalObject);
    }
}
