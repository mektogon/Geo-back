package ru.dorofeev.mobilemap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoMobile;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoWeb;
import ru.dorofeev.mobilemap.service.dto.interf.GeographicalObjectDtoService;
import ru.dorofeev.mobilemap.service.interf.AudioService;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.service.interf.VideoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/geo")
@RequiredArgsConstructor
@Tag(name = "Географический объект", description = "Контроллер для работы с гео-объектами.")
public class GeographicalObjectController {
    private final GeographicalObjectService geographicalObjectService;
    private final GeographicalObjectDtoService geographicalObjectDtoService;
    private final PhotoService photoService;
    private final VideoService videoService;
    private final AudioService audioService;

    @Operation(
            summary = "Получить все гео-объекты",
            description = "Позволяет получить все гео-объекты. Файлы приходят без ID."
    )
    @GetMapping()
    public List<GeographicalObjectDtoMobile> getAllForMobile() {
        return geographicalObjectDtoService.getAll();
    }

    @Operation(
            summary = "Получить все гео-объекты",
            description = "Позволяет получить все гео-объекты. Файлы приходят с ID."
    )
    @GetMapping("/web")
    public List<GeographicalObjectDtoWeb> getAllForWeb() {
        return geographicalObjectDtoService.getAllForWeb();
    }

    @Operation(
            summary = "Получить гео-объект по ID",
            description = "Позволяет получить гео-объект по его идентификатору."
    )
    @GetMapping("/getById/{id}")
    public Optional<GeographicalObjectDtoMobile> getById(@PathVariable UUID id) {
        return geographicalObjectDtoService.findById(id);
    }

    @Operation(
            summary = "Получить гео-объекты по наименованию",
            description = "Позволяет получить все гео-объекты с переданным именем."
    )
    @GetMapping("/{name}")
    public List<GeographicalObjectDtoMobile> getAllByName(@PathVariable String name) {
        return geographicalObjectDtoService.getAllByName(name);
    }

    @Operation(
            summary = "Сохранение гео-объекта",
            description = "Позволяет сохранить гео-объект (текстовые поля)."
    )
    @PostMapping()
    public ResponseEntity<String> save(@RequestBody GeographicalObjectDtoMobile object) {
        return ResponseEntity.ok(String.format("{\"id\": \"%s\"}", geographicalObjectDtoService.saveAndReturnId(object)));
    }

    @Operation(
            summary = "Сохранение гео-объекта с файлами",
            description = "Позволяет сохранить гео-объект с полным набором полей."
    )
    @Transactional
    @PostMapping("/save")
    public void save(
            @RequestParam("name") String name,
            @RequestParam(value = "type", defaultValue = "Отсутствует") String type,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("description") String description,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "isPlaying", required = false) Boolean isPlaying,
            @RequestParam(value = "designation", defaultValue = "Отсутствует") String designation,
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
        GeographicalObjectDtoMobile entity = new GeographicalObjectDtoMobile();
        entity.setName(name);
        entity.setType(type);
        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        entity.setDescription(description);
        entity.setNote(note);
        entity.setIsPlaying(isPlaying);
        entity.setDesignation(designation);
        entity.setAddressDto(
                AuxiliaryUtils.ValidationAddress(
                        region,
                        typeLocality,
                        locality,
                        district,
                        street,
                        houseNumber
                )
        );

        UUID id = geographicalObjectDtoService.saveAndReturnId(entity);

        if (audio != null) {
            photoService.upload(photo, id);
        }

        if (audio != null) {
            audioService.upload(audio, id);
        }

        if (video != null) {
            videoService.upload(video, id);
        }
    }

    @Operation(
            summary = "Удаление гео-объектов по наименованию",
            description = "Позволяет удалить все гео-объекты с переданным именем."
    )
    @DeleteMapping("/deleteAllByName/{name}")
    public void deleteByName(@PathVariable String name) {
        geographicalObjectService.deleteByName(name);
    }

    @Operation(
            summary = "Удаление гео-объекта по ID",
            description = "Позволяет удалить гео-объект по его идентификатору."
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        geographicalObjectService.deleteById(id);
    }

    @Operation(
            summary = "Обновление гео-объекта",
            description = "Позволяет обновить переданные поля гео-объекта."
    )
    @PatchMapping()
    public void update(@RequestBody GeographicalObjectDtoMobile object) {
        geographicalObjectDtoService.update(object);
    }

    @Operation(
            summary = "Обновление гео-объекта с файлами",
            description = "Позволяет обновить переданные поля гео-объекта."
    )
    @PatchMapping("/update")
    public void update(
            @RequestParam("id") UUID id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "designation", required = false) String designation,
            @RequestParam(value = "isPlaying", required = false) Boolean isPlaying,
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
                isPlaying,
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
