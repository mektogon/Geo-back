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
public class GeographicalObjectDtoWeb extends GeographicalObjectDto {

    public GeographicalObjectDtoWeb(GeographicalObjectDto geographicalObjectDto) {
        setId(geographicalObjectDto.getId());
        setName(geographicalObjectDto.getName());
        setType(geographicalObjectDto.getType());
        setLatitude(geographicalObjectDto.getLatitude());
        setLongitude(geographicalObjectDto.getLongitude());
        setDescription(geographicalObjectDto.getDescription());
        setNote(geographicalObjectDto.getNote());
        setAddressDto(geographicalObjectDto.getAddressDto());
    }

    private FileDto designation;

    private List<FileDto> photoList;

    private List<FileDto> videoList;

    private List<FileDto> audioList;
}
