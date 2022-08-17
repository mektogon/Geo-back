package ru.dorofeev.mobilemap.service.dto.interf;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;

import java.util.UUID;

/**
 * Является service-прослойкой, существующей для преобразования entity в dto.
 */
public interface GeographicalObjectDtoService extends AbstractServiceDto<GeographicalObjectDto> {
    UUID saveAndReturnId(GeographicalObjectDto geographicalObjectDto);

    void update(
            UUID id,
            String name,
            String type,
            String latitude,
            String longitude,
            String description,
            String note,
            String designation,
            String region,
            String district,
            String typeLocality,
            String locality,
            String street,
            String houseNumber,
            MultipartFile[] photo,
            MultipartFile[] audio,
            MultipartFile[] video
    );
}

