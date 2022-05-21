package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.Region;
import ru.dorofeev.mobilemap.service.interf.RegionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
public class RegionController implements AbstractController<Region> {
    private final RegionService regionService;

    @GetMapping()
    @Override
    public List<Region> getAll() {
        return regionService.findALl();
    }

    @GetMapping("/{id}")
    @Override
    public Optional<Region> getById(@PathVariable Long id) {
        return regionService.findById(id);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody Region object) {
        regionService.save(object);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        regionService.deleteById(id);
    }

    @PutMapping()
    @Override
    public void update(@PathVariable Region object) {
        regionService.update(object);
    }
}
