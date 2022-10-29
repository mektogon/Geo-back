package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность гео-объекта")
public class GeographicalObjectDto {
    @Schema(description = "Идентификатор", example = "9c03f297-d59e-4c4d-94f6-107db6fe4db4")
    private UUID id;

    @Schema(description = "Наименование", example = "Барнаул")
    private String name;

    @Schema(description = "Наименование", example = "Город")
    private String type;

    @Schema(description = "Широта", example = "53.3477")
    private String latitude;

    @Schema(description = "Долгота", example = "83.77552")
    private String longitude;

    @Schema(description = "Описание", example = "Описание гео-объекта")
    private String description;

    @Schema(description = "Заметка", example = "Заметка о гео-объекте")
    private String note;

    @Schema(description = "Адрес")
    private AddressDto addressDto;
}
