package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.model.dto.FileDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoMobile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoWeb;
import ru.dorofeev.mobilemap.service.interf.AddressService;
import ru.dorofeev.mobilemap.service.interf.AudioService;
import ru.dorofeev.mobilemap.service.interf.DesignationService;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;
import ru.dorofeev.mobilemap.service.interf.VideoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппер предназначенный для преобразования Entity -> DTO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GeographicalObjectMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_GET_PHOTO_BY_ID = "/api/v1/photo/view/";
    private final String ENDPOINT_GET_PREVIEW_PHOTO_BY_ID = "/api/v1/photo/preview/view/";
    private final String ENDPOINT_GET_VIDEO_BY_ID = "/api/v1/video/view/";
    private final String ENDPOINT_GET_AUDIO_BY_ID = "/api/v1/audio/view/";
    private final String ENDPOINT_GET_DESIGNATION_BY_ID = "/api/v1/designation/view/";
    private final PhotoService photoService;
    private final VideoService videoService;
    private final AudioService audioService;
    private final TypeObjectService typeObjectService;
    private final AddressService addressService;
    private final DesignationService designationService;

    /**
     * Метод, преобразующий список geo-объектов в DTO-список, направленный для Mobile-версии.
     *
     * @param geographicalObjectList список geo-объектов.
     * @return список dto.
     */
    public List<GeographicalObjectDtoMobile> toDtoList(List<GeographicalObject> geographicalObjectList) {
        if (geographicalObjectList == null) {
            return Collections.emptyList();
        }

        List<GeographicalObjectDtoMobile> result = new ArrayList<>(geographicalObjectList.size());

        for (var item : geographicalObjectList) {
            result.add(toDtoMobile(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий список geo-объектов в DTO-список, направленный для Web-версии. (Панели администратора)
     *
     * @param geographicalObjectList список geo-объектов.
     * @return список dto.
     */
    public List<GeographicalObjectDtoWeb> toDtoWebList(List<GeographicalObject> geographicalObjectList) {
        if (geographicalObjectList == null) {
            return Collections.emptyList();
        }

        List<GeographicalObjectDtoWeb> result = new ArrayList<>(geographicalObjectList.size());

        for (var item : geographicalObjectList) {
            result.add(toDtoWeb(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий список DTO-объектов в geo-объекты
     *
     * @param geographicalObjectDtoMobileList список dto-объектов.
     * @return список geo-объектов.
     */
    public List<GeographicalObject> toEntityList(List<GeographicalObjectDtoMobile> geographicalObjectDtoMobileList) {
        if (geographicalObjectDtoMobileList == null) {
            return Collections.emptyList();
        }

        List<GeographicalObject> result = new ArrayList<>(geographicalObjectDtoMobileList.size());

        for (var item : geographicalObjectDtoMobileList) {
            result.add(toEntity(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий geo-объект в DTO (Mobile)
     *
     * @param geographicalObject объект, который подлежит преобразованию.
     * @return DTO-объект.
     */
    public GeographicalObjectDtoMobile toDtoMobile(GeographicalObject geographicalObject) {
        GeographicalObjectDtoMobile geographicalObjectDtoMobile = new GeographicalObjectDtoMobile(getGeographicalObjectDto(geographicalObject));

        geographicalObjectDtoMobile.setDesignation(String.format("%s%s%s", rootUrl, ENDPOINT_GET_DESIGNATION_BY_ID, geographicalObject.getDesignation().getId()));

        geographicalObjectDtoMobile.setPhotoList(
                photoService.getAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .sorted((Comparator.comparing(o -> AuxiliaryUtils.getOriginalNameWithoutExtension(o.getFileName()))))
                        .map(el -> {
                                    if (el.getUrlPhotoPreview() != null) {
                                        geographicalObjectDtoMobile.setPreviewMainPhoto(
                                                String.format("%s%s%s", rootUrl, ENDPOINT_GET_PREVIEW_PHOTO_BY_ID, el.getId())
                                        );
                                    }
                                    return String.format("%s%s%s", rootUrl, ENDPOINT_GET_PHOTO_BY_ID, el.getId());
                                }
                        )
                        .collect(Collectors.toList())
        );
        geographicalObjectDtoMobile.setVideoList(
                videoService.getAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> String.format("%s%s%s", rootUrl, ENDPOINT_GET_VIDEO_BY_ID, el.getId()))
                        .collect(Collectors.toList())
        );
        geographicalObjectDtoMobile.setAudioList(
                audioService.getAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> String.format("%s%s%s.%s", rootUrl, ENDPOINT_GET_AUDIO_BY_ID, el.getId(), AuxiliaryUtils.getExtensionFile(el.getFileName())))
                        .collect(Collectors.toList())
        );

        return geographicalObjectDtoMobile;
    }

    /**
     * Метод, преобразующий geo-объект в DTO (Web)
     *
     * @param geographicalObject объект, который подлежит преобразованию.
     * @return DTO-объект.
     */
    public GeographicalObjectDtoWeb toDtoWeb(GeographicalObject geographicalObject) {
        GeographicalObjectDtoWeb geographicalObjectDtoWeb = new GeographicalObjectDtoWeb(getGeographicalObjectDto(geographicalObject));

        geographicalObjectDtoWeb.setDesignation(
                FileDto.builder()
                        .id(geographicalObject.getDesignation().getId())
                        .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_DESIGNATION_BY_ID, geographicalObject.getDesignation().getId()))
                        .build()
        );

        geographicalObjectDtoWeb.setPhotoList(
                photoService.getAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .sorted((Comparator.comparing(o -> AuxiliaryUtils.getOriginalNameWithoutExtension(o.getFileName()))))
                        .map(el -> {
                                    if (el.getUrlPhotoPreview() != null) {
                                        geographicalObjectDtoWeb.setPreviewMainPhoto(
                                                FileDto.builder()
                                                        .id(el.getId())
                                                        .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_PREVIEW_PHOTO_BY_ID, el.getId()))
                                                        .build()
                                        );
                                    }

                                    return FileDto.builder()
                                            .id(el.getId())
                                            .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_PHOTO_BY_ID, el.getId()))
                                            .build();
                                }
                        )
                        .collect(Collectors.toList())
        );
        geographicalObjectDtoWeb.setVideoList(
                videoService.getAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> FileDto.builder()
                                .id(el.getId())
                                .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_VIDEO_BY_ID, el.getId()))
                                .build())
                        .collect(Collectors.toList())
        );
        geographicalObjectDtoWeb.setAudioList(
                audioService.getAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> FileDto.builder()
                                .id(el.getId())
                                .url(String.format("%s%s%s.%s", rootUrl, ENDPOINT_GET_AUDIO_BY_ID, el.getId(), AuxiliaryUtils.getExtensionFile(el.getFileName())))
                                .build())
                        .collect(Collectors.toList())
        );

        return geographicalObjectDtoWeb;
    }

    /**
     * Метод, преобразующий DTO-объекты (Mobile) в geo.
     * Mobile-сущность является основной (входной).
     *
     * @param geographicalObjectDtoMobile объект, который подлежит преобразованию.
     * @return geo-объект.
     */
    public GeographicalObject toEntity(GeographicalObjectDtoMobile geographicalObjectDtoMobile) {

        GeographicalObject geographicalObject = new GeographicalObject();

        geographicalObject.setId(geographicalObjectDtoMobile.getId());
        geographicalObject.setType(typeObjectService.getByName(geographicalObjectDtoMobile.getType()));
        geographicalObject.setName(geographicalObjectDtoMobile.getName());
        geographicalObject.setLatitude(geographicalObjectDtoMobile.getLatitude());
        geographicalObject.setLongitude(geographicalObjectDtoMobile.getLongitude());
        geographicalObject.setDescription(geographicalObjectDtoMobile.getDescription());
        geographicalObject.setNote(geographicalObjectDtoMobile.getNote());
        geographicalObject.setDistanceToPlayback(geographicalObjectDtoMobile.getDistanceToPlayback());
        geographicalObject.setIsPlaying(geographicalObjectDtoMobile.getIsPlaying() != null ?
                geographicalObjectDtoMobile.getIsPlaying() : false
        );
        geographicalObject.setDesignation(designationService.getByName(geographicalObjectDtoMobile.getDesignation()));

        if (geographicalObjectDtoMobile.getAddressDto() == null) {
            geographicalObject.setAddress(null);
        } else {
            geographicalObject.setAddress(
                    addressService.getAddress(
                            geographicalObjectDtoMobile.getAddressDto().getRegion(),
                            geographicalObjectDtoMobile.getAddressDto().getDistrict(),
                            geographicalObjectDtoMobile.getAddressDto().getTypeLocality(),
                            geographicalObjectDtoMobile.getAddressDto().getLocality(),
                            geographicalObjectDtoMobile.getAddressDto().getStreet(),
                            geographicalObjectDtoMobile.getAddressDto().getHouseNumber())
            );
        }

        return geographicalObject;
    }

    /**
     * Метод для преобразования DTO в Entity с последующим обновлением сущности.
     *
     * @param geographicalObjectDtoMobile Обновляющий объект
     * @param entity                      Обновляемый объект
     * @return Обновленный объект.
     */
    public GeographicalObject toConvertForUpdateEntity(GeographicalObjectDtoMobile geographicalObjectDtoMobile, GeographicalObject entity) {

        if (geographicalObjectDtoMobile.getName() != null) {
            entity.setName(geographicalObjectDtoMobile.getName());
        }

        if (geographicalObjectDtoMobile.getType() != null) {
            entity.setType(
                    typeObjectService.getByName(geographicalObjectDtoMobile.getType())
            );
        }

        if (geographicalObjectDtoMobile.getLatitude() != null) {
            entity.setLatitude(geographicalObjectDtoMobile.getLatitude());
        }

        if (geographicalObjectDtoMobile.getLongitude() != null) {
            entity.setLongitude(geographicalObjectDtoMobile.getLongitude());
        }

        if (geographicalObjectDtoMobile.getDescription() != null) {
            entity.setDescription(geographicalObjectDtoMobile.getDescription());
        }

        if (geographicalObjectDtoMobile.getNote() != null) {
            entity.setNote(geographicalObjectDtoMobile.getNote());
        }

        if (geographicalObjectDtoMobile.getDistanceToPlayback() != null) {
            entity.setDistanceToPlayback(geographicalObjectDtoMobile.getDistanceToPlayback());
        }

        if (geographicalObjectDtoMobile.getDesignation() != null) {
            entity.setDesignation(
                    designationService.getByName(geographicalObjectDtoMobile.getDesignation())
            );
        }

        if (geographicalObjectDtoMobile.getIsPlaying() != null) {
            entity.setIsPlaying(geographicalObjectDtoMobile.getIsPlaying());
        }

        if (geographicalObjectDtoMobile.getAddressDto() != null) {

            AddressDto addressDto = AuxiliaryUtils.validationAddress(
                    geographicalObjectDtoMobile.getAddressDto().getRegion(),
                    geographicalObjectDtoMobile.getAddressDto().getTypeLocality(),
                    geographicalObjectDtoMobile.getAddressDto().getLocality(),
                    geographicalObjectDtoMobile.getAddressDto().getDistrict(),
                    geographicalObjectDtoMobile.getAddressDto().getStreet(),
                    geographicalObjectDtoMobile.getAddressDto().getHouseNumber()
            );

            entity.setAddress(addressService.getAddress(
                    addressDto.getRegion(),
                    addressDto.getDistrict(),
                    addressDto.getTypeLocality(),
                    addressDto.getLocality(),
                    addressDto.getStreet(),
                    addressDto.getHouseNumber()
            ));
        }

        return entity;
    }

    /**
     * Метод, преобразующий geo-объект в DTO (Базовые поля).
     *
     * @param geographicalObject
     * @return
     */
    private GeographicalObjectDto getGeographicalObjectDto(GeographicalObject geographicalObject) {
        String currentRegion = "Отсутствует";
        String currentTypeLocality = "Отсутствует";
        String currentLocality = "Отсутствует";
        String currentStreet = "Отсутствует";
        String currentDistrict = "Отсутствует";
        String currentHouseNumber = "Отсутствует";


        if (geographicalObject.getAddress() != null) {
            currentRegion = geographicalObject.getAddress().getRegion().getName();
            currentTypeLocality = geographicalObject.getAddress().getTypeLocality().getName();
            currentLocality = geographicalObject.getAddress().getLocality().getName();
            currentStreet = geographicalObject.getAddress().getStreet().getName();
            currentDistrict = geographicalObject.getAddress().getDistrict().getName();
            currentHouseNumber = geographicalObject.getAddress().getHouseNumber();
        }

        GeographicalObjectDto geographicalObjectDto = new GeographicalObjectDto();
        geographicalObjectDto.setId(geographicalObject.getId());
        geographicalObjectDto.setName(geographicalObject.getName());
        geographicalObjectDto.setType(geographicalObject.getType().getName());
        geographicalObjectDto.setLatitude(geographicalObject.getLatitude());
        geographicalObjectDto.setLongitude(geographicalObject.getLongitude());
        geographicalObjectDto.setDescription(geographicalObject.getDescription());
        geographicalObjectDto.setNote(geographicalObject.getNote());
        geographicalObjectDto.setDistanceToPlayback(geographicalObject.getDistanceToPlayback());
        geographicalObjectDto.setIsPlaying(geographicalObject.getIsPlaying());

        if (geographicalObject.getAddress() != null) {
            geographicalObjectDto.setAddressDto(AddressDto.builder()
                    .region(currentRegion)
                    .typeLocality(currentTypeLocality)
                    .locality(currentLocality)
                    .street(currentStreet)
                    .district(currentDistrict)
                    .houseNumber(currentHouseNumber)
                    .fullAddress(
                            getFullAddress(currentRegion,
                                    currentDistrict,
                                    currentTypeLocality,
                                    currentLocality,
                                    currentStreet,
                                    currentHouseNumber)
                    )
                    .build());
        } else {
            //Адрес может быть null, т.к. он не всегда нужен
            // или в принципе есть у географического объекта.
            geographicalObjectDto.setAddressDto(null);
        }
        return geographicalObjectDto;
    }

    /**
     * Метод позволяющий получить полный адрес географического объекта.
     * <p>
     * Формат: Регион, Район, тип местности Местность, Улица, Номер
     * <p>
     * Пример: Алтайский край, Железнодорожный район, Город Барнаул, Ленина, 61
     *
     * @param region       Регион
     * @param district     Район
     * @param typeLocality Тип местности
     * @param locality     Местность
     * @param street       Улица
     * @param houseNumber  Номер дома
     * @return Полный адрес, зависящий от наличия передаваемых параметров.
     */
    private String getFullAddress(String region, String district, String typeLocality, String locality, String street, String houseNumber) {

        StringBuilder result = new StringBuilder();

        result.append(region)
                .append(", ");

        if (!district.equals("Отсутствует")) {
            result.append(district)
                    .append(", ");
        }

        result.append(typeLocality)
                .append(" ")
                .append(locality);

        if (!street.equals("Отсутствует")) {
            result.append(", ")
                    .append(street);

            if (!houseNumber.equals("Отсутствует")) {
                result.append(", ")
                        .append(houseNumber);
            }
        }

        return result.toString();
    }
}
