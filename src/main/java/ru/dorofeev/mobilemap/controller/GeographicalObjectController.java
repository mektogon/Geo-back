package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.address.AddressFieldNotFoundException;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDto;
import ru.dorofeev.mobilemap.service.dto.interf.GeographicalObjectDtoService;
import ru.dorofeev.mobilemap.service.interf.AudioService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.service.interf.VideoService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/geo")
@RequiredArgsConstructor
public class GeographicalObjectController {
    private final GeographicalObjectService geographicalObjectService;
    private final GeographicalObjectDtoService geographicalObjectDtoService;
    private final PhotoService photoService;
    private final VideoService videoService;
    private final AudioService audioService;

    @GetMapping()
    public List<GeographicalObjectDto> getAll() {
        return geographicalObjectDtoService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<GeographicalObjectDto> getById(@PathVariable UUID id) {
        return geographicalObjectDtoService.findById(id);
    }

    @GetMapping("/{name}")
    public List<GeographicalObjectDto> getByName(@PathVariable String name) {
        return geographicalObjectDtoService.getByName(name);
    }

    @Transactional
    @PostMapping()
    public void save(
            @RequestParam("name") String name,
            @RequestParam(value = "type", defaultValue = "Отсутствует") String type,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("description") String description,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "designation", defaultValue = "Отсутствует") String designation,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "typeLocality", required = false) String typeLocality,
            @RequestParam(value = "locality", required = false) String locality,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "houseNumber", required = false) String houseNumber,
            @RequestParam("photo") MultipartFile[] photo,
            @RequestParam("audio") MultipartFile[] audio,
            @RequestParam(value = "video", required = false) MultipartFile[] video
    ) {
        GeographicalObjectDto entity = new GeographicalObjectDto();
        entity.setName(name);
        entity.setType(type);
        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        entity.setDescription(description);
        entity.setNote(note);
        entity.setDesignation(designation);

        if (region == null && typeLocality == null && locality == null && district == null && street == null && houseNumber == null) {
            entity.setAddressDto(null);
        } else if (region != null && typeLocality != null && locality != null) {
            AddressDto addressDto = AddressDto.builder()
                    .region(region)
                    .district(Objects.requireNonNullElse(district, "Отсутствует"))
                    .typeLocality(typeLocality)
                    .locality(locality)
                    .street(Objects.requireNonNullElse(street, "Отсутствует"))
                    .houseNumber(Objects.requireNonNullElse(houseNumber, "Отсутствует"))
                    .build();
            entity.setAddressDto(addressDto);
        } else {
            if (region == null) {
                throw new AddressFieldNotFoundException("Ошибка! Без поля \"region\" формирование адреса невозможно!");
            }

            if (typeLocality == null) {
                throw new AddressFieldNotFoundException("Ошибка! Без поля \"typeLocality\" формирование адреса невозможно!");
            }

            throw new AddressFieldNotFoundException("Ошибка! Без поля \"locality\" формирование адреса невозможно!");
        }

        UUID id = geographicalObjectDtoService.saveAndReturnId(entity);

        photoService.upload(photo, id);
        audioService.upload(audio, id);

        if (video != null) {
            videoService.upload(video, id);
        }
    }

    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name) {
        geographicalObjectService.deleteByName(name);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable UUID id) {
        geographicalObjectService.deleteById(id);
    }

    @PatchMapping()
    public void update(
            @RequestParam("id") UUID id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "designation", required = false) String designation,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "typeLocality", required = false) String typeLocality,
            @RequestParam(value = "locality", required = false) String locality,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "houseNumber", required = false) String houseNumber,
            @RequestParam(value = "photo", required = false) MultipartFile[] photo,
            @RequestParam(value = "audio", required = false) MultipartFile[] audio,
            @RequestParam(value = "video", required = false) MultipartFile[] video
    ) {
        geographicalObjectService.update(
                id,
                name,
                type,
                latitude,
                longitude,
                description,
                note,
                designation,
                region,
                district,
                typeLocality,
                locality,
                street,
                houseNumber,
                photo,
                audio,
                video
        );
    }
}
