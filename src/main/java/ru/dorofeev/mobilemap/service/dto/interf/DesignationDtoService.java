package ru.dorofeev.mobilemap.service.dto.interf;

import ru.dorofeev.mobilemap.model.dto.DesignationDto;

import java.util.List;

/**
 * Является service-прослойкой, существующей для преобразования entity в dto.
 */
public interface DesignationDtoService {
    List<DesignationDto> getAllWithPhoto();

    List<DesignationDto> getAllByNameWithPhoto(String name);
}
