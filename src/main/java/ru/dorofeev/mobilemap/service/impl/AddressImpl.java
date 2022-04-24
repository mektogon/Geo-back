package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Address;
import ru.dorofeev.mobilemap.repository.AddressRepository;
import ru.dorofeev.mobilemap.service.interf.AddressService;

import java.util.List;
import java.util.Optional;

@Service
public class AddressImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findALl() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> save(Address address) {
        return Optional.of(addressRepository.save(address));
    }

    @Override
    public Optional<Address> update(Address address) {
        return Optional.of(addressRepository.save(address));
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
