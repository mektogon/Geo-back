package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.Locality;
import ru.dorofeev.mobilemap.service.interf.LocalityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/locality")
public class LocalityController implements AbstractController<Locality>{
    LocalityService localityService;

    @Autowired
    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    @GetMapping("list")
    @Override
    public List<Locality> getAll() {
        return localityService.findALl();
    }

    @GetMapping("find/{id}")
    @Override
    public Optional<Locality> getById(@PathVariable Long id) {
        return localityService.findById(id);
    }

    @PostMapping("add")
    @Override
    public void add(@RequestBody Locality object) {
        localityService.save(object);
    }

    @DeleteMapping("delete")
    @Override
    public void delete(@PathVariable Long id) {
        localityService.deleteById(id);
    }

    @PutMapping("update")
    @Override
    public void update(@PathVariable Locality object) {
        localityService.update(object);
    }
}
