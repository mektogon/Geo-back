package ru.dorofeev.mobilemap.service.dto.interf;

import ru.dorofeev.mobilemap.model.dto.FileDto;

import java.util.List;
import java.util.UUID;

public interface FileServiceDto {
    List<FileDto> getAllByGeoId(UUID id);
}
