package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность для сохранении манифеста")
public class ManifestoRequest {
    UUID id;

    String name;

    List<UUID> listIdTile;

    List<UUID> listIdRoad;
}
