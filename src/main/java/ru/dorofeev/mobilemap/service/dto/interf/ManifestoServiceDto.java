package ru.dorofeev.mobilemap.service.dto.interf;

import ru.dorofeev.mobilemap.model.dto.ManifestoDto;

import java.util.List;
import java.util.UUID;

public interface ManifestoServiceDto {

    List<ManifestoDto> getAll();

    ManifestoDto getById(UUID id);

    ManifestoDto getByName(String name);

    ManifestoDto getFirstManifesto(String sortName);
}
