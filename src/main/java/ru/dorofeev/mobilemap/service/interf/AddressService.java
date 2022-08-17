package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.base.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {
    List<Address> getAll();

    Optional<Address> findById(UUID id);

    void save(Address address);

    void update(Address address);

    void deleteById(UUID id);

    Address getAddress(String regionName,
                       String districtName,
                       String typeLocalityName,
                       String localityName,
                       String streetName,
                       String houseNumber);
}
