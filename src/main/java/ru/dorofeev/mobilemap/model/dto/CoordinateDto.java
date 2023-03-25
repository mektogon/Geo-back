package ru.dorofeev.mobilemap.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность координат")
public class CoordinateDto {

    @JsonIgnore
    @Transient
    // [-90; 90]
    // [-89.(30 знаков); -89.(30 знаков)]
    private final String regexLatitude = "^(\\+|-)?(?:90(?:(?:\\.0{1,30})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,30})?))$";

    @JsonIgnore
    @Transient
    // [-180; 180]
    // [-179.(30 знаков); -179.(30 знаков)]
    private final String regexLongitude = "^(\\+|-)?(?:180(?:(?:\\.0{1,30})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,30})?))$";

    @Pattern(regexp = regexLatitude, message = "Широта должна быть в диапазоне [-90; 90]! Не более 30 знаков после запятой!")
    private String latitude;

    @Pattern(regexp = regexLongitude, message = "Долгота должна быть в диапазоне [-90; 90]! Не более 30 знаков после запятой!")
    private String longitude;
}
