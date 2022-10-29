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
@Schema(description = "Сущность файла для WEB")
public class FileDto {
    @Schema(description = "Идентификатор", example = "9c03f297-d59e-4c4d-94f6-107db6fe4db4")
    private UUID id;

    @Schema(description = "Путь до файла в системе", example = "/home/folder/file.extension")
    private String url;
}
