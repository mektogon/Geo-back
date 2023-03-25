package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.RoadCoordinateMapper;
import ru.dorofeev.mobilemap.model.dto.RoadCoordinateDto;
import ru.dorofeev.mobilemap.service.dto.interf.RoadCoordinateServiceDto;
import ru.dorofeev.mobilemap.service.interf.RoadCoordinateService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoadCoordinateServiceDtoImpl implements RoadCoordinateServiceDto {
    private final RoadCoordinateService service;
    private final RoadCoordinateMapper mapper;

    @Override
    public List<RoadCoordinateDto> getAll() {
        return mapper.toDtoList(service.getAll());
    }

    @Override
    public List<RoadCoordinateDto> getByAllId(List<UUID> id) {
        return mapper.toDtoList(service.getByAllId(id));
    }

    @Override
    public RoadCoordinateDto getById(UUID id) {
        return service.getById(id).map(mapper::toDto).orElse(null);
    }

    @Override
    public ResponseEntity<String> save(RoadCoordinateDto roadCoordinateDto) {
        return service.saveAndReturnId(mapper.toEntity(roadCoordinateDto));
    }

    @Override
    public void update(RoadCoordinateDto roadCoordinateDto) {
        service.update(mapper.toEntity(roadCoordinateDto));
    }
}
