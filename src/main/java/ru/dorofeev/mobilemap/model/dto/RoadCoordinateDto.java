package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность для передачи информации об объекте \"Координаты дороги\"")
public class RoadCoordinateDto {

    /**
     * Идентификатор сущности
     */
    private UUID id;

    /**
     * Наименование объекта
     */
    private String name;

    /**
     * Список координат дороги (Функциональные требования)
     */
    @Valid
    private List<CoordinateDto> listCoordinate;
}
