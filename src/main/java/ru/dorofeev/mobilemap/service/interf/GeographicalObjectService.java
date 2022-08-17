package ru.dorofeev.mobilemap.service.interf;

import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;

import java.util.UUID;

public interface GeographicalObjectService extends AbstractDataObjectService<GeographicalObject> {
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
