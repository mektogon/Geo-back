package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Address;
import ru.dorofeev.mobilemap.model.base.District;
import ru.dorofeev.mobilemap.model.base.Locality;
import ru.dorofeev.mobilemap.model.base.Region;
import ru.dorofeev.mobilemap.model.base.Street;
import ru.dorofeev.mobilemap.model.base.TypeLocality;
import ru.dorofeev.mobilemap.repository.AddressRepository;
import ru.dorofeev.mobilemap.repository.DistrictRepository;
import ru.dorofeev.mobilemap.repository.LocalityRepository;
import ru.dorofeev.mobilemap.repository.RegionRepository;
import ru.dorofeev.mobilemap.repository.StreetRepository;
import ru.dorofeev.mobilemap.repository.TypeLocalityRepository;
import ru.dorofeev.mobilemap.service.interf.AddressService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final TypeLocalityRepository typeLocalityRepository;
    private final LocalityRepository localityRepository;
    private final StreetRepository streetRepository;


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

    @Override
    public Address getAddress(String regionName, String districtName, String typeLocalityName, String localityName, String streetName, String houseNumber) {
        //Код, на который без слез не взглянешь.
        if (regionName == null && districtName == null && typeLocalityName == null && localityName == null && streetName == null && houseNumber == null) {
            return null;
        }

        //Необязательное поле в адресе.
        if (districtName != null && districtName.isEmpty()) {
            districtName = "Отсутствует";
        }

        //Необязательное поле в адресе.
        if (streetName != null && streetName.isEmpty()) {
            streetName = "Отсутствует"; //Если нет улицы => не может быть и номера дома
            houseNumber = "Отсутствует";
        }

        //Необязательное поле в адресе.
        if (houseNumber != null && houseNumber.isEmpty() || Objects.equals(streetName, "Отсутствует")) {
            houseNumber = "Отсутствует";
        }

        Address foundAddress;

        foundAddress = addressRepository.getAddressByRegionNameAndDistrictNameAndTypeLocalityNameAndLocalityNameAndStreetNameAndHouseNumber(
                regionName,
                districtName,
                typeLocalityName,
                localityName,
                streetName,
                houseNumber
        );

        if (foundAddress == null) {
            Region region = regionRepository.findByName(regionName);
            District district = districtRepository.findByName(districtName);
            TypeLocality typeLocality = typeLocalityRepository.findByName(typeLocalityName);
            Locality locality = localityRepository.findByName(localityName);
            Street street = streetRepository.findByName(streetName);

            if (region == null) {
                region = new Region();
                region.setName(regionName);
                regionRepository.save(region);
                region = regionRepository.findByName(regionName);
            }

            if (district == null) {
                district = new District();
                district.setName(districtName);
                districtRepository.save(district);
                district = districtRepository.findByName(districtName);
            }

            if (typeLocality == null) {
                typeLocality = new TypeLocality();
                typeLocality.setName(typeLocalityName);
                typeLocalityRepository.save(typeLocality);
                typeLocality = typeLocalityRepository.findByName(typeLocalityName);
            }

            if (locality == null) {
                locality = new Locality();
                locality.setName(localityName);
                localityRepository.save(locality);
                locality = localityRepository.findByName(localityName);
            }

            if (street == null) {
                street = new Street();
                street.setName(streetName);
                streetRepository.save(street);
                street = streetRepository.findByName(streetName);
            }

            addressRepository.save(
                    Address.builder()
                            .region(region)
                            .district(district)
                            .typeLocality(typeLocality)
                            .locality(locality)
                            .street(street)
                            .houseNumber(houseNumber)
                            .build());

            foundAddress = addressRepository.getAddressByRegionNameAndDistrictNameAndTypeLocalityNameAndLocalityNameAndStreetNameAndHouseNumber(
                    regionName,
                    districtName,
                    typeLocalityName,
                    localityName,
                    streetName,
                    houseNumber
            );
        }

        return foundAddress;
    }
}
