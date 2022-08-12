package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.Street;
import ru.dorofeev.mobilemap.service.interf.StreetService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/street")
@RequiredArgsConstructor
public class StreetController implements AbstractController<Street> {
    private final StreetService streetService;

    @GetMapping()
    @Override
    public List<Street> getAll() {
        return streetService.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public Optional<Street> getById(@PathVariable UUID id) {
        return streetService.findById(id);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody Street object) {
        streetService.save(object);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable UUID id) {
        streetService.deleteById(id);
    }

    @PutMapping()
    @Override
    public void update(@PathVariable Street object) {
        streetService.update(object);
    }
}
