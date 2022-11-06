package ru.dorofeev.mobilemap.service.dto.interf;

import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoMobile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoWeb;

import java.util.List;
import java.util.UUID;

/**
 * Является service-прослойкой, существующей для преобразования entity в dto.
 */
public interface GeographicalObjectDtoService extends AbstractServiceDto<GeographicalObjectDtoMobile> {
    UUID saveAndReturnId(GeographicalObjectDtoMobile geographicalObjectDtoMobile);

    List<GeographicalObjectDtoWeb> getAllForWeb();
}

