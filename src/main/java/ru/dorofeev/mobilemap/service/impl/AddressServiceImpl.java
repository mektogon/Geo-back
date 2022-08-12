package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Address;
import ru.dorofeev.mobilemap.repository.AddressRepository;
import ru.dorofeev.mobilemap.service.interf.AddressService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> findById(UUID id) {
        return addressRepository.findById(id);
    }

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void update(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void deleteById(UUID id) {
        addressRepository.deleteById(id);
    }
}
