package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.base.Address;
import ru.dorofeev.mobilemap.service.interf.AddressService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController implements AbstractController<Address> {
    private final AddressService addressService;

    @GetMapping()
    @Override
    public List<Address> getAll() {
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public Optional<Address> getById(@PathVariable UUID id) {
        return addressService.findById(id);
    }

    @PostMapping()
    @Override
    public void save(@RequestBody Address object) {
        addressService.save(object);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable UUID id) {
        addressService.deleteById(id);
    }

    @PutMapping()
    @Override
    public void update(@PathVariable Address object) {
        addressService.update(object);
    }
}
