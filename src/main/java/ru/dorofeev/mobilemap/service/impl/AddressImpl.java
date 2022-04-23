package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Address;
import ru.dorofeev.mobilemap.repository.AddressRepository;
import ru.dorofeev.mobilemap.service.interf.AddressService;

import java.util.List;
import java.util.Optional;

@Service
public class AddressImpl implements AddressService {
    AddressRepository addressRepository;

    public AddressImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findALl() {
        return null;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Address> save(Address address) {
        return Optional.empty();
    }

    @Override
    public void delete(Address address) {

    }

    @Override
    public void deleteById(Address address) {

    }
}
