package ru.dorofeev.mobilemap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeographicalObjectDtoMobile extends GeographicalObjectDto {

    public GeographicalObjectDtoMobile(GeographicalObjectDto geographicalObjectDto) {
        setId(geographicalObjectDto.getId());
        setName(geographicalObjectDto.getName());
        setType(geographicalObjectDto.getType());
        setLatitude(geographicalObjectDto.getLatitude());
        setLongitude(geographicalObjectDto.getLongitude());
        setDescription(geographicalObjectDto.getDescription());
        setNote(geographicalObjectDto.getNote());
        setAddressDto(geographicalObjectDto.getAddressDto());
    }

    private String designation;

    private List<String> photoList;

    private List<String> videoList;

    private List<String> audioList;
}
