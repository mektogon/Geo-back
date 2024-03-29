package ru.dorofeev.mobilemap.model.dto;

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
@Schema(description = "Сущность обозначения")
public class DesignationDto {
    @Schema(description = "Идентификатор", example = "2f00f240-7e40-43c8-afac-2554a03d316c")
    private UUID id;

    @Schema(description = "URL-картинки", example = "http://address-server/api/v1/designation/2f00f240-7e40-43c8-afac-2554a03d316c")
    private String url;

    @Schema(description = "Название", example = "Кемпинг")
    private String name;
}
