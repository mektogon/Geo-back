package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dorofeev.mobilemap.model.entity.Address;
import ru.dorofeev.mobilemap.service.interf.AddressService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/address")
public class AddressController implements AbstractController<Address>{
    AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("list")
    @Override
    public List<Address> getAll() {
        return addressService.findALl();
    }

    @GetMapping("find/{id}")
    @Override
    public Optional<Address> getById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping("add")
    @Override
    public void add(@RequestBody Address object) {
        addressService.save(object);
    }

    @DeleteMapping("delete")
    @Override
    public void delete(@PathVariable Long id) {
        addressService.deleteById(id);
    }

    @PutMapping("update")
    @Override
    public void update(@PathVariable Address object) {
        addressService.update(object);
    }
}
