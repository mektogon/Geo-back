package ru.dorofeev.mobilemap.service.dto.interf;

import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.dto.RoadCoordinateDto;

import java.util.List;
import java.util.UUID;

public interface RoadCoordinateServiceDto {
    List<RoadCoordinateDto> getAll();

    List<RoadCoordinateDto> getByAllId(List<UUID> id);

    RoadCoordinateDto getById(UUID id);

    ResponseEntity<String> save(RoadCoordinateDto object);

    void update(RoadCoordinateDto object);
}
