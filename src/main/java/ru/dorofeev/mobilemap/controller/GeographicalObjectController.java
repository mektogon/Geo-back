package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.service.dto.impl.GeographicalObjectDtoServiceImpl;
import ru.dorofeev.mobilemap.service.dto.interf.GeographicalObjectDtoService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/geo")
@RequiredArgsConstructor
public class GeographicalObjectController implements AbstractObjectDataController<GeographicalObject> {
    private final GeographicalObjectService geographicalObjectService;

    private final GeographicalObjectDtoService geographicalObjectDtoService;

    @GetMapping()
    @Override
    public List<GeographicalObject> getAll() {
        return geographicalObjectService.getAll();
    }

    @GetMapping("/getDto")
    public List<GeographicalObjectDto> getAllDto() {
        return geographicalObjectDtoService.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public Optional<GeographicalObject> getById(@PathVariable UUID id) {
        return geographicalObjectService.findById(id);
    }

    @GetMapping("/name/{name}")
    @Override
    public List<GeographicalObject> getByName(@PathVariable String name) {
        return geographicalObjectService.findAllByName(name);
    }

    @PostMapping()
    @Override
    public void save(@Valid @RequestBody GeographicalObject object) {
        geographicalObjectService.save(object);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable UUID id) {
        geographicalObjectService.deleteById(id);
    }

    @PutMapping()
    @Override
    public void update(@RequestBody GeographicalObject object) {
        geographicalObjectService.update(object);
    }
}
