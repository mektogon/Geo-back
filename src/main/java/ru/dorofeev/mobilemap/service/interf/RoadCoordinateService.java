package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.RoadCoordinate;

import java.util.List;
import java.util.UUID;

public interface RoadCoordinateService extends AbstractService<RoadCoordinate> {
    List<RoadCoordinate> getByAllId(List<UUID> id);

    ResponseEntity<String> saveAndReturnId(RoadCoordinate roadCoordinate);
}
