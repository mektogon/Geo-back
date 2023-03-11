package ru.dorofeev.mobilemap.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Точки тайла карты")
public class TileCoordinateDto {

    @JsonIgnore
    // [-90; 90]
    // [-89.(30 знаков); -89.(30 знаков)]
    private final String regexLatitude = "^(\\+|-)?(?:90(?:(?:\\.0{1,30})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,30})?))$";

    @JsonIgnore
    // [-180; 180]
    // [-179.(30 знаков); -179.(30 знаков)]
    private final String regexLongitude = "^(\\+|-)?(?:180(?:(?:\\.0{1,30})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,30})?))$";

    @Schema(description = "Верхняя долгота", example = "180")
    @Pattern(regexp = regexLongitude, message = "Долгота должна быть в диапазоне [-180; 180]! Не более 30 знаков после запятой!")
    private String upperLongitude;

    @Schema(description = "Верхняя широта", example = "90")
    @Pattern(regexp = regexLatitude, message = "Широта должна быть в диапазоне [-90; 90]! Не более 30 знаков после запятой!")
    private String upperLatitude;

    @Schema(description = "Нижняя долгота", example = "180")
    @Pattern(regexp = regexLongitude, message = "Долгота должна быть в диапазоне [-180; 180]! Не более 30 знаков после запятой!")
    private String lowerLongitude;

    @Schema(description = "Нижняя широта", example = "90")
    @Pattern(regexp = regexLatitude, message = "Широта должна быть в диапазоне [-90; 90]! Не более 30 знаков после запятой!")
    private String lowerLatitude;

    @Min(0)
    @Max(19)
    @Schema(description = "Минимальный масштаб, от которого осуществляется перебор", example = "0")
    private int minScale;


    @Min(0)
    @Max(19)
    @Schema(description = "Максимальный масштаб, до которого осуществляется перебор", example = "19")
    private int maxScale;


}
