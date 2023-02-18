package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
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
    public Optional<GeographicalObject> getById(UUID id) {
        return geographicalObjectRepository.findById(id);
    }

    @Override
    public List<GeographicalObject> getAllByName(String name) {
        if (name == null) {
            log.debug("IN findAllByName() - Имя отсутствует!");
            return Collections.emptyList();
        }

        return geographicalObjectRepository.findAllByNameIsContainingIgnoreCase(name);
    }

    @Override
    public void save(GeographicalObject geographicalObject) {
        geographicalObjectRepository.save(geographicalObject);
        log.debug("IN save() - Сохранен гео-объект");
    }

    @Override
    public UUID saveAndReturnId(GeographicalObject geographicalObject) {
        GeographicalObject savedEntity = geographicalObjectRepository.save(geographicalObject);
        log.debug("IN saveAndReturnId() - Сохранен гео-объект");
        return savedEntity.getId();
    }

    @Override
    public void update(GeographicalObject geographicalObject) {
        Optional<GeographicalObject> byId = geographicalObjectRepository.findById(geographicalObject.getId());

        if (byId.isPresent()) {
            geographicalObjectRepository.save(geographicalObject);
            log.debug("IN update() - Обновлен гео-объект с ID: {}", byId.get().getId());
        } else {
            log.info("IN update() - Не удалось найти и обновить гео-объект с ID: {}", geographicalObject.getId());
        }
    }

    @Override
    public void deleteById(UUID id) {
        cascadeDeleteFilesByIdGeo(id);
        geographicalObjectRepository.deleteById(id);
        log.debug("IN deleteById() - Удален гео-объект с ID: {}", id);
    }

    @Override
    public void deleteByName(String name) {
        List<GeographicalObject> geoListToDelete = geographicalObjectRepository.findAllByName(name);
        geoListToDelete.forEach(geo -> {
                    deleteById(geo.getId());
                    log.debug("IN deleteByName() - Удален гео-объект с name: {}", name);
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
                entity.setDesignation(designationService.getByName(designation));
            }

            if (isPlaying != null) {
                entity.setIsPlaying(isPlaying);
            }

            if (distanceToPlayback != null) {
                entity.setDistanceToPlayback(distanceToPlayback);
            }

            AddressDto addressDto = AuxiliaryUtils.validationAddress(region, typeLocality, locality, district, street, houseNumber);

            if (addressDto != null) {
                entity.setAddress(addressService.getAddress(
                                addressDto.getRegion(),
                                addressDto.getDistrict(),
                                addressDto.getTypeLocality(),
                                addressDto.getLocality(),
                                addressDto.getStreet(),
                                addressDto.getHouseNumber()
                        )
                );
            } else {
                //Учитываем случай, когда пользователь захотел удалить адрес.
                //Нам приходят пустые строчки, мы возвращаем null и сеттим его в поле адреса.
                entity.setAddress(null);
            }

            if (photo != null) {
                int totalCount = photoService.getAllFilesByGeographicalObjectId(id).size() + photo.length;

                if (photoService.getAllFilesByGeographicalObjectId(id).size() + photo.length > 5) {
                    log.error("IN update() - Невозможно загрузить больше фотографий, чем положено иметь гео-объекту.");
                    throw new GeneralErrorException(String.format("Невозможно загрузить больше фотографий, чем положено иметь гео-объекту. Имеется: %s Передано: %s",
                            totalCount - photo.length, photo.length)
                    );
                }

                photoService.upload(photo, id);
                photoService.initializePreviewPhoto(id);
            }

            if (video != null) {
                int totalCount = videoService.getAllFilesByGeographicalObjectId(id).size() + video.length;

                if (totalCount > 1) {
                    log.error("IN update() - Невозможно загрузить больше видеозаписей, чем положено иметь гео-объекту.");
                    throw new GeneralErrorException(String.format("Невозможно загрузить больше видеозаписей, чем положено иметь гео-объекту. Имеется: %s Передано: %s",
                            totalCount - video.length, video.length)
                    );
                }

                videoService.upload(video, id);
            }

            if (audio != null) {
                int totalCount = audioService.getAllFilesByGeographicalObjectId(id).size() + audio.length;

                if (audioService.getAllFilesByGeographicalObjectId(id).size() + audio.length > 1) {
                    log.error("IN update() - Невозможно загрузить больше аудиозаписей, чем положено иметь гео-объекту.");
                    throw new GeneralErrorException(String.format("Невозможно загрузить больше аудиозаписей, чем положено иметь гео-объекту. Имеется: %s Передано: %s",
                            totalCount - audio.length, audio.length)
                    );
                }

                audioService.upload(audio, id);
            }

            geographicalObjectRepository.save(entity);
            log.debug("IN update() - Обновлен гео-объект с ID: {}", byId.get().getId());
        } else {
            log.info("IN update() - Не удалось найти и обновить гео-объект с ID: {}", id);
            throw new GeneralErrorException(String.format("Не удалось найти и обновить гео-объект с ID: %s", id));
        }
    }

    /**
     * Метод удаляет зависимые сущности(фото, видео, аудио), прежде чем удалить гео-объект.
     *
     * @param id идентификатор удаляемого объекта
     */
    private void cascadeDeleteFilesByIdGeo(UUID id) {
        photoService.getAllFilesByGeographicalObjectId(id)
                .forEach(photo -> photoService.deleteById(photo.getId()));
        videoService.getAllFilesByGeographicalObjectId(id)
                .forEach(video -> videoService.deleteById(video.getId()));
        audioService.getAllFilesByGeographicalObjectId(id)
                .forEach(audio -> audioService.deleteById(audio.getId()));
    }
}
