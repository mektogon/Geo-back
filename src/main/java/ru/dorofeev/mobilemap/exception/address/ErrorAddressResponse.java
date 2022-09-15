package ru.dorofeev.mobilemap.exception.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorAddressResponse {
    @Schema(description = "Статус ошибки", example = "500")
    private final int status;

    @Schema(description = "Сообщение ошибки", example = "Поле X отсутствует!")
    private final String message;
}