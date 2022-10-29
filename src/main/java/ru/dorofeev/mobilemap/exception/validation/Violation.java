package ru.dorofeev.mobilemap.exception.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Сущность ошибки")
public class Violation {
    @Schema(description = "Название класса", example = "GeographicalObject")
    private final String className;

    @Schema(description = "Название поля", example = "latitude")
    private final String fieldName;

    @Schema(description = "Сообщение ошибки", example = "Широта должна быть в диапазоне [-90; 90]")
    private final String message;
}
