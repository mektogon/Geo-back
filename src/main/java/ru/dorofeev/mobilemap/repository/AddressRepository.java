package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.Address;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Address getAddressByRegionNameAndDistrictNameAndTypeLocalityNameAndLocalityNameAndStreetNameAndHouseNumber(String regionName,
                                                                                                               String districtName,
                                                                                                               String typeLocalityName,
                                                                                                               String localityName,
                                                                                                               String streetName,
                                                                                                               String houseNumber);
}