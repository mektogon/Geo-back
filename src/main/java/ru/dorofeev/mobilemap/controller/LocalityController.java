package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.Locality;
import ru.dorofeev.mobilemap.service.interf.LocalityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/locality")
@RequiredArgsConstructor
public class LocalityController implements AbstractController<Locality> {
    private final LocalityService localityService;

    @GetMapping()
    @Override
    public List<Locality> getAll() {
        return localityService.findALl();
    }

    @GetMapping("/{id}")
    @Override
    public Optional<Locality> getById(@PathVariable Long id) {
        return localityService.findById(id);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody Locality object) {
        localityService.save(object);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        localityService.deleteById(id);
    }

    @PutMapping()
    @Override
    public void update(@PathVariable Locality object) {
        localityService.update(object);
    }
}
