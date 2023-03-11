package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Точки тайла карты")
public class TilePointDto {

    @Schema(description = "Точка на аппликате (zoom)", example = "10")
    private int z;

    @Schema(description = "Точка на абсциссе", example = "10")
    private int x;

    @Schema(description = "Точка на ординате", example = "10")
    private int y;
}
