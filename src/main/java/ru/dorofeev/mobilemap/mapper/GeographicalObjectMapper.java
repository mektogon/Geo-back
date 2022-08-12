package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeographicalObjectMapper {
    private final GeographicalObjectRepository geographicalObjectRepository;

    public List<GeographicalObjectDto> toDtoList(List<GeographicalObject> geographicalObjectList) {
        if (geographicalObjectList == null) {
            return Collections.emptyList();
        }

        List<GeographicalObjectDto> result = new ArrayList<>(geographicalObjectList.size());

        for (var item : geographicalObjectList) {
            result.add(toDto(item));
        }

        return result;
    }

    public List<GeographicalObject> toEntityList(List<GeographicalObjectDto> geographicalObjectDtoList) {
        return List.of();
    }

    public GeographicalObjectDto toDto(GeographicalObject geographicalObject) {

        String currentRegion = geographicalObject.getAddress().getRegion().getName();

        String currentTypeLocality = geographicalObject.getAddress().getTypeLocality().getName();

        String currentLocality = geographicalObject.getAddress().getLocality().getName();

        String currentStreet = geographicalObject.getAddress().getStreet().getName();

        String currentDistrict = geographicalObject.getAddress().getDistrict() == null ?
                "Отсутствует" : geographicalObject.getAddress().getDistrict().getName();

        String currentHouseNumber = geographicalObject.getAddress().getHouseNumber() == null ?
                "Отсутствует" : geographicalObject.getAddress().getHouseNumber();



        return GeographicalObjectDto.builder()
                .name(geographicalObject.getName())
                .type(geographicalObject.getType().getName())
                .latitude(geographicalObject.getLatitude())
                .longitude(geographicalObject.getLongitude())
                .description(geographicalObject.getDescription())
                .note(geographicalObject.getNote())
                .designation(geographicalObject.getDesignation().getUrl())
                .addressDto(AddressDto.builder()
                        .region(currentRegion)
                        .typeLocality(currentTypeLocality)
                        .locality(currentLocality)
                        .street(currentStreet)
                        .district(currentDistrict)
                        .houseNumber(currentHouseNumber)
                        .fullAddress(
                                getFullAddress(currentRegion,
                                currentDistrict,
                                currentTypeLocality,
                                currentLocality,
                                currentStreet,
                                currentHouseNumber)
                        )
                        .build())
                .photoList(null)
                .videoList(null)
                .audioList(null)
                .build();
    }

    public GeographicalObject toEntity(GeographicalObjectDto geographicalObjectDto) {
        return null;
    }

    private String getFullAddress(String region, String district, String typeLocality, String locality, String street, String houseNumber) {

        StringBuilder result = new StringBuilder();

        result.append(region)
                .append(", ");

        if (!district.equals("Отсутствует")) {
            result.append(district)
                    .append(", ");
        }

        result.append(typeLocality)
                .append(" ")
                .append(locality)
                .append(", ")
                .append(street);

        if (!houseNumber.equals("Отсутствует")) {
            result.append(", ")
                    .append(houseNumber);
        }

        return result.toString();
    }
}
