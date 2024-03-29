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
@Schema(description = "Сущность гео-объекта для мобильного приложения. Входящая DTO-сущность.")
public class GeographicalObjectDtoMobile extends GeographicalObjectDto {

    public GeographicalObjectDtoMobile(GeographicalObjectDto geographicalObjectDto) {
        setId(geographicalObjectDto.getId());
        setName(geographicalObjectDto.getName());
        setType(geographicalObjectDto.getType());
        setLatitude(geographicalObjectDto.getLatitude());
        setLongitude(geographicalObjectDto.getLongitude());
        setDescription(geographicalObjectDto.getDescription());
        setNote(geographicalObjectDto.getNote());
        setIsPlaying(geographicalObjectDto.getIsPlaying());
        setDistanceToPlayback(geographicalObjectDto.getDistanceToPlayback());
        setAddressDto(geographicalObjectDto.getAddressDto());
    }

    @Schema(description = "Ссылка на превью главной фотографии")
    private String previewMainPhoto;

    @Schema(description = "Обозначение", example = "Кемпинг")
    private String designation;

    @Schema(description = "Список фотографий", example = "http://localhost:8080/api/v1/photo/3ace1b46-7129-40b1-8cb2-2063dc762d68")
    private List<String> photoList;

    @Schema(description = "Список видеозаписей", example = "http://localhost:8080/api/v1/video/3ace1b46-7129-40b1-8cb2-2063dc762d68")
    private List<String> videoList;

    @Schema(description = "Список аудиозаписей", example = "http://localhost:8080/api/v1/audio/3ace1b46-7129-40b1-8cb2-2063dc762d68")
    private List<String> audioList;
}
