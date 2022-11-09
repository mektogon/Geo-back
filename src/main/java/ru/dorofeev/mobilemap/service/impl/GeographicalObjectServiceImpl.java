package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.service.interf.AddressService;
import ru.dorofeev.mobilemap.service.interf.AudioService;
import ru.dorofeev.mobilemap.service.interf.DesignationService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;
import ru.dorofeev.mobilemap.service.interf.VideoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeographicalObjectServiceImpl implements GeographicalObjectService {
    private final GeographicalObjectRepository geographicalObjectRepository;

    private final TypeObjectService typeObjectService;

    private final DesignationService designationService;

    private final AddressService addressService;

    private final PhotoService photoService;

    private final VideoService videoService;

    private final AudioService audioService;

    @Override
    public List<GeographicalObject> getAll() {
        return geographicalObjectRepository.findAll();
    }

    @Override
    public Optional<GeographicalObject> findById(UUID id) {
        return geographicalObjectRepository.findById(id);
    }

    @Override
    public List<GeographicalObject> findAllByName(String name) {
        if (name == null) {
            log.error("IN findAllByName() - Имя отсутствует!");
            return Collections.emptyList();
        }

        return geographicalObjectRepository.findAllByNameIsContainingIgnoreCase(name);
    }

    @Override
    public void save(GeographicalObject geographicalObject) {
        geographicalObjectRepository.save(geographicalObject);
        log.info("IN save() - Сохранен гео-объект");
    }

    @Override
    public UUID saveAndReturnId(GeographicalObject geographicalObject) {
        GeographicalObject savedEntity = geographicalObjectRepository.save(geographicalObject);
        log.info("IN saveAndReturnId() - Сохранен гео-объект");
        return savedEntity.getId();
    }

    @Override
    public void update(GeographicalObject geographicalObject) {
        Optional<GeographicalObject> byId = geographicalObjectRepository.findById(geographicalObject.getId());

        if (byId.isPresent()) {
            geographicalObjectRepository.save(geographicalObject);
            log.info("IN update() - Обновлен гео-объект с ID: {}", byId.get().getId());
        }

        log.info("IN update() - Не удалось найти и обновить гео-объект с ID: {}", geographicalObject.getId());
    }

    @Override
    public void deleteById(UUID id) {
        cascadeDeleteFilesByIdGeo(id);
        geographicalObjectRepository.deleteById(id);
        log.info("IN deleteById() - Удален гео-объект с ID: {}", id);
    }

    @Override
    public void deleteByName(String name) {
        List<GeographicalObject> geoListToDelete = geographicalObjectRepository.findAllByName(name);
        geoListToDelete.forEach(geo -> {
                    deleteById(geo.getId());
                    log.info("IN deleteByName() - Удален гео-объект с name: {}", name);
                }
        );

    }

    @Override
    public void update(
            UUID id,
            String name,
            String type,
            String latitude,
            String longitude,
            String description,
            String note,
            String designation,
            Boolean isPlaying,
            String region,
            String district,
            String typeLocality,
            String locality,
            String street,
            String houseNumber,
            MultipartFile[] photo,
            MultipartFile[] audio,
            MultipartFile[] video
    ) {
        Optional<GeographicalObject> byId = geographicalObjectRepository.findById(id);

        if (byId.isPresent()) {
            GeographicalObject entity = byId.get();

            if (name != null) {
                entity.setName(name);
            }

            if (type != null) {
                entity.setType(typeObjectService.getByName(type));
            }

            if (latitude != null) {
                entity.setLatitude(latitude);
            }

            if (longitude != null) {
                entity.setLongitude(longitude);
            }

            if (description != null) {
                entity.setDescription(description);
            }

            if (note != null) {
                entity.setNote(note);
            }

            if (designation != null) {
                entity.setDesignation(designationService.getDesignationByName(designation));
            }

            if (isPlaying != null) {
                entity.setIsPlaying(isPlaying);
            }

            AddressDto addressDto = AuxiliaryUtils.ValidationAddress(region, typeLocality, locality, district, street, houseNumber);

            if (addressDto != null) {
                entity.setAddress(addressService.getAddress(
                        addressDto.getRegion(),
                        addressDto.getDistrict(),
                        addressDto.getTypeLocality(),
                        addressDto.getLocality(),
                        addressDto.getStreet(),
                        addressDto.getHouseNumber()
                ));
            }

            if (photo != null) {
                photoService.upload(photo, id);
            }

            if (video != null) {
                videoService.upload(video, id);
            }

            if (audio != null) {
                audioService.upload(audio, id);
            }

            geographicalObjectRepository.save(entity);
            log.info("IN update() - Обновлен гео-объект с ID: {}", byId.get().getId());
        }

        log.info("IN update() - Не удалось найти и обновить гео-объект с ID: {}", id);
    }

    /**
     * Метод удаляет зависимые сущности(фото, видео, аудио), прежде чем удалить гео-объект.
     *
     * @param id индетификатор удаляемого объекта
     */
    private void cascadeDeleteFilesByIdGeo(UUID id) {
        photoService.findAllFilesByGeographicalObjectId(id)
                .forEach(photo -> photoService.deleteById(photo.getId()));
        videoService.findAllFilesByGeographicalObjectId(id)
                .forEach(video -> videoService.deleteById(video.getId()));
        audioService.findAllFilesByGeographicalObjectId(id)
                .forEach(audio -> audioService.deleteById(audio.getId()));
    }
}
