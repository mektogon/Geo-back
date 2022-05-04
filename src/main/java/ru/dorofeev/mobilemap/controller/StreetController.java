package ru.dorofeev.mobilemap.controller;

import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.Region;
import ru.dorofeev.mobilemap.model.entity.Street;
import ru.dorofeev.mobilemap.service.interf.StreetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/street")
public class StreetController implements AbstractController<Street>{
    StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @GetMapping("list")
    @Override
    public List<Street> getAll() {
        return streetService.findALl();
    }

    @GetMapping("find/{id}")
    @Override
    public Optional<Street> getById(@PathVariable Long id) {
        return streetService.findById(id);
    }

    @PostMapping("add")
    @Override
    public void add(@RequestBody Street object) {
        streetService.save(object);
    }

    @DeleteMapping("delete")
    @Override
    public void delete(@PathVariable Long id) {
        streetService.deleteById(id);
    }

    @PutMapping("update")
    @Override
    public void update(@PathVariable Street object) {
        streetService.update(object);
    }
}
