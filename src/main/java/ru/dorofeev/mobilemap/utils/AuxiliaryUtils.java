package ru.dorofeev.mobilemap.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.address.AddressFieldNotFoundException;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoMobile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс, содержащий утилитные методы.
 */
@Slf4j
public class AuxiliaryUtils {
    /**
     * Сохранение файла в директорию.
     * Генерируется случайное имя в целях исключения ошибки одноименности.
     *
     * @param directoryToSave Путь для сохранения.
     * @param file            Сохраняемые файлы.
     * @param extension       Список разрешенных расширений файлов.
     * @return путь до файла.
     */
    public static String SavingFile(String directoryToSave, MultipartFile file, List<String> extension) {
        String ext = Objects.requireNonNull(file.getContentType()).split("/")[1];

        if (!checkExtensionFile(ext, extension)) {
            log.error("IN SavingFile() - Тип загружаемого файла {} не удовлетворяет требованиям!", file.getName());
            throw new RuntimeException("Тип загружаемого файла " + file.getName() + " не удовлетворяет требованиям!");
        }

        String name = String.format("%s%s%s", UUID.randomUUID(), ".", ext);
        Path fullPathToSave = Paths.get(directoryToSave + File.separator + name);

        try {
            Files.copy(file.getInputStream(), fullPathToSave, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("IN SavingFile() - Ошибка при сохранении файла в директорию!");
            throw new RuntimeException(e);
        }

        return fullPathToSave.toString();
    }

    /**
     * Удаление файла из директории.
     *
     * @param directoryToDelete путь до удаляемого файла.
     */
    public static void DeleteFile(String directoryToDelete) {
        try {
            boolean result = Files.deleteIfExists(Path.of(directoryToDelete));

            if (result) {
                log.info("IN DeleteFile() - Файл успешно удален из директории!");
            } else {
                log.error("IN DeleteFile() - Не удалось найти файл!");
            }

        } catch (IOException e) {
            log.error("IN DeleteFile() - Ошибка при удалении файла из директории!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод валидации гео-объекта на наличие определенных полей.
     *
     * @param geo валидируемый объект.
     * @return результат валидации - гео-объект.
     */
    public static GeographicalObjectDtoMobile ValidationGeoObject(GeographicalObjectDtoMobile geo) {
        GeographicalObjectDtoMobile entity = new GeographicalObjectDtoMobile();
        entity.setName(geo.getName());
        entity.setType(geo.getType());
        entity.setLatitude(geo.getLatitude());
        entity.setLongitude(geo.getLongitude());
        entity.setDescription(geo.getDescription());
        entity.setNote(geo.getNote());
        entity.setDesignation(geo.getDesignation());

        if (geo.getAddressDto() != null) {
            AddressDto address = geo.getAddressDto();
            geo.setAddressDto(ValidationAddress(
                    address.getRegion(),
                    address.getTypeLocality(),
                    address.getLocality(),
                    address.getDistrict(),
                    address.getStreet(),
                    address.getHouseNumber()
            ));
        }

        return entity;
    }

    /**
     * Метод валидации адреса на наличие определенных полей.
     *
     * @param region       регион.
     * @param typeLocality тип местности.
     * @param locality     местность.
     * @param district     район.
     * @param street       улица.
     * @param houseNumber  номер дома.
     * @return результат валидации - адрес.
     */
    public static AddressDto ValidationAddress(String region, String typeLocality, String locality, String district, String street, String houseNumber) {
        if (region == null && typeLocality == null && locality == null && district == null && street == null && houseNumber == null) {
            return null;
        } else if (region != null && typeLocality != null && locality != null) {
            return AddressDto.builder()
                    .region(region)
                    .district(Objects.requireNonNullElse(district, "Отсутствует"))
                    .typeLocality(typeLocality)
                    .locality(locality)
                    .street(Objects.requireNonNullElse(street, "Отсутствует"))
                    .houseNumber(Objects.requireNonNullElse(houseNumber, "Отсутствует"))
                    .build();
        } else {
            if (region == null) {
                throw new AddressFieldNotFoundException("Ошибка! Без поля \"region\" формирование адреса невозможно!");
            }

            if (typeLocality == null) {
                throw new AddressFieldNotFoundException("Ошибка! Без поля \"typeLocality\" формирование адреса невозможно!");
            }

            throw new AddressFieldNotFoundException("Ошибка! Без поля \"locality\" формирование адреса невозможно!");
        }
    }

    private static boolean checkExtensionFile(String extCurrentFile, List<String> extension) {
        for (var ext : extension) {
            if (ext.equals(extCurrentFile.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
