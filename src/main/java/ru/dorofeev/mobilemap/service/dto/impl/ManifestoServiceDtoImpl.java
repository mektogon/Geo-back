package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.ManifestoMapper;
import ru.dorofeev.mobilemap.model.dto.ManifestoDto;
import ru.dorofeev.mobilemap.service.dto.interf.ManifestoServiceDto;
import ru.dorofeev.mobilemap.service.interf.ManifestoService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManifestoServiceDtoImpl implements ManifestoServiceDto {

    private final ManifestoService service;
    private final ManifestoMapper mapper;

    @Override
    public List<ManifestoDto> getAll() {
        return mapper.toDtoList(service.getAll());
    }

    @Override
    public ManifestoDto getById(UUID id) {
        return service.getById(id).map(mapper::toDto).orElse(null);
    }

    @Override
    public ManifestoDto getByName(String name) {
        return mapper.toDto(service.getByName(name));
    }

    @Override
    public ManifestoDto getFirstManifesto(String sortName) {
        return mapper.toDto(service.getFirstManifesto(sortName));
    }
}
