package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Обозначение")
    private FileDto designation;

    @Schema(description = "Список фотографий")
    private List<FileDto> photoList;

    @Schema(description = "Список видеозаписей")
    private List<FileDto> videoList;

    @Schema(description = "Список аудиозаписей")
    private List<FileDto> audioList;
}
