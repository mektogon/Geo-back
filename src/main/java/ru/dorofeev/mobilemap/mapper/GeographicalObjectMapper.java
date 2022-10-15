package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.service.interf.AddressService;
import ru.dorofeev.mobilemap.service.interf.AudioService;
import ru.dorofeev.mobilemap.service.interf.DesignationService;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;
import ru.dorofeev.mobilemap.service.interf.VideoService;

import java.util.ArrayList;
import java.util.Collections;
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
    private final String ENDPOINT_GET_PHOTO_BY_ID = "/api/v1/photo/";
    private final String ENDPOINT_GET_VIDEO_BY_ID = "/api/v1/video/";
    private final String ENDPOINT_GET_AUDIO_BY_ID = "/api/v1/audio/";
    private final String ENDPOINT_GET_DESIGNATION_BY_ID = "/api/v1/designation/";
    private final PhotoService photoService;
    private final VideoService videoService;
    private final AudioService audioService;
    private final TypeObjectService typeObjectService;
    private final AddressService addressService;
    private final DesignationService designationService;

    /**
     * Метод преобразующий список geo-объектов в DTO
     *
     * @param geographicalObjectList список geo-объектов.
     * @return список dto.
     */
    public List<GeographicalObjectDto> toDtoList(List<GeographicalObject> geographicalObjectList) {
        if (geographicalObjectList == null) {
            return Collections.emptyList();
        }

        List<GeographicalObjectDto> result = new ArrayList<>(geographicalObjectList.size());

        for (var item : geographicalObjectList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод преобразующий список DTO-объектов в geo-объекты
     *
     * @param geographicalObjectDtoList список dto-объектов.
     * @return список geo-объектов.
     */
    public List<GeographicalObject> toEntityList(List<GeographicalObjectDto> geographicalObjectDtoList) {
        if (geographicalObjectDtoList == null) {
            return Collections.emptyList();
        }

        List<GeographicalObject> result = new ArrayList<>(geographicalObjectDtoList.size());

        for (var item : geographicalObjectDtoList) {
            result.add(toEntity(item));
        }

        return result;
    }

    /**
     * Метод преобразующий geo-объект в DTO
     *
     * @param geographicalObject объект, который подлежит преобразованию.
     * @return DTO-объект.
     */
    public GeographicalObjectDto toDto(GeographicalObject geographicalObject) {
        String currentRegion = "Отсуствует";
        String currentTypeLocality = "Отсуствует";
        String currentLocality = "Отсуствует";
        String currentStreet = "Отсуствует";
        String currentDistrict = "Отсуствует";
        String currentHouseNumber = "Отсуствует";


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
        geographicalObjectDto.setDesignation(String.format("%s%s%s", rootUrl, ENDPOINT_GET_DESIGNATION_BY_ID, geographicalObject.getDesignation().getId()));

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
            // или в прицнипе есть у географического объекта.
            geographicalObjectDto.setAddressDto(null);
        }
        geographicalObjectDto.setPhotoList(
                photoService.findAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> String.format("%s%s%s", rootUrl, ENDPOINT_GET_PHOTO_BY_ID, el.getId()))
                        .collect(Collectors.toList())
        );
        geographicalObjectDto.setVideoList(
                videoService.findAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> String.format("%s%s%s", rootUrl, ENDPOINT_GET_VIDEO_BY_ID, el.getId()))
                        .collect(Collectors.toList())
        );
        geographicalObjectDto.setAudioList(
                audioService.findAllFilesByGeographicalObjectId(geographicalObject.getId()).stream()
                        .map(el -> String.format("%s%s%s", rootUrl, ENDPOINT_GET_AUDIO_BY_ID, el.getId()))
                        .collect(Collectors.toList())
        );

        return geographicalObjectDto;
    }

    /**
     * Метод преобразующий DTO-объекты в geo.
     *
     * @param geographicalObjectDto объект, который подлежит преобразованию.
     * @return geo-объект.
     */
    public GeographicalObject toEntity(GeographicalObjectDto geographicalObjectDto) {

        GeographicalObject geographicalObject = new GeographicalObject();

        geographicalObject.setType(typeObjectService.getTypeObjectByName(geographicalObjectDto.getType()));
        geographicalObject.setName(geographicalObjectDto.getName());
        geographicalObject.setLatitude(geographicalObjectDto.getLatitude());
        geographicalObject.setLongitude(geographicalObjectDto.getLongitude());
        geographicalObject.setDescription(geographicalObjectDto.getDescription());
        geographicalObject.setNote(geographicalObjectDto.getNote());
        geographicalObject.setDesignation(designationService.getDesignationByName(geographicalObjectDto.getDesignation()));

        if (geographicalObjectDto.getAddressDto() == null) {
            geographicalObject.setAddress(null);
        } else {
            geographicalObject.setAddress(
                    addressService.getAddress(
                            geographicalObjectDto.getAddressDto().getRegion(),
                            geographicalObjectDto.getAddressDto().getDistrict(),
                            geographicalObjectDto.getAddressDto().getTypeLocality(),
                            geographicalObjectDto.getAddressDto().getLocality(),
                            geographicalObjectDto.getAddressDto().getStreet(),
                            geographicalObjectDto.getAddressDto().getHouseNumber())
            );
        }

        return geographicalObject;
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
