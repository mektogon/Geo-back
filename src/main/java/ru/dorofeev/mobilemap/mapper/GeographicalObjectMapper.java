package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeographicalObjectMapper {
    private final GeographicalObjectRepository geographicalObjectRepository;

    public List<GeographicalObjectDto> toDtoList() {
        return List.of();
    }
}
