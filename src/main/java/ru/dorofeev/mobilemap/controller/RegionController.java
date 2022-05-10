package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.Region;
import ru.dorofeev.mobilemap.service.interf.RegionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/region")
public class RegionController implements AbstractController<Region>{
    RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("list")
    @Override
    public List<Region> getAll() {
        return regionService.findALl();
    }

    @GetMapping("find/{id}")
    @Override
    public Optional<Region> getById(@PathVariable Long id) {
        return regionService.findById(id);
    }

    @PostMapping("add")
    @Override
    public void add(@RequestBody Region object) {
        regionService.save(object);
    }

    @DeleteMapping("delete")
    @Override
    public void delete(@PathVariable Long id) {
        regionService.deleteById(id);
    }

    @PutMapping("update")
    @Override
    public void update(@PathVariable Region object) {
        regionService.update(object);
    }
}
