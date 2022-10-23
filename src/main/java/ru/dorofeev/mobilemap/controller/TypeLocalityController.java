package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.model.base.TypeLocality;
import ru.dorofeev.mobilemap.service.interf.TypeLocalityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type-locality")
@RequiredArgsConstructor
public class TypeLocalityController implements AbstractController<TypeLocality> {

    private final TypeLocalityService typeLocalityService;

    @GetMapping()
    @Override
    public List<TypeLocality> getAll() {
        return typeLocalityService.getAll();
    }

    @GetMapping("/getById/{id}")
    @Override
    public Optional<TypeLocality> getById(@PathVariable UUID id) {
        return typeLocalityService.findById(id);
    }

    @GetMapping("/{name}")
    @Override
    public TypeLocality getByName(@PathVariable String name) {
        return typeLocalityService.getByName(name);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody TypeLocality typeLocality) {
        typeLocalityService.save(typeLocality);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        typeLocalityService.deleteById(id);
    }

    @DeleteMapping("/deleteByName/{name}")
    @Override
    public void deleteByName(@PathVariable String name) {
        typeLocalityService.deleteByName(name);
    }

    @PatchMapping()
    @Override
    public void update(@RequestBody TypeLocality typeLocality) {
        typeLocalityService.update(typeLocality);
    }
}
