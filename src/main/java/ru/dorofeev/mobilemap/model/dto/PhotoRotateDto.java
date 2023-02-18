package ru.dorofeev.mobilemap.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность с информацией для поворота фотографии")
public class PhotoRotateDto {

    @Schema(description = "Идентификатор", example = "9c03f297-d59e-4c4d-94f6-107db6fe4db4")
    private UUID photoId;

    @Schema(description = "Целочисленный угол поворота", example = "90")
    private int rotationAngle;

    @Schema(description = "Флаг, обозначающий, что поворачиваем превью у фотографии", example = "false")
    @JsonProperty("isPreview")
    private boolean isPreview;
}
