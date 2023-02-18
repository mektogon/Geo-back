package ru.dorofeev.mobilemap.service.interf;

import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;

import java.util.List;
import java.util.UUID;

public interface GeographicalObjectService extends AbstractService<GeographicalObject> {
    List<GeographicalObject> getAllByName(String name);

    UUID saveAndReturnId(GeographicalObject geographicalObject);

    void update(
            UUID id,
            String name,
            String type,
            String latitude,
            String longitude,
            String description,
            String note,
            String designation,
            Boolean isPlaying,
            Integer distanceToPlayback,
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
