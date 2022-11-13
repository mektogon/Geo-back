package ru.dorofeev.mobilemap.exception.generalerror;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorGeneralResponse {
    @Schema(description = "Статус ошибки", example = "500")
    private final int status;

    @Schema(description = "Сообщение ошибки", example = "Ошибка!")
    private final String message;
}