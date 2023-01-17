package ru.dorofeev.mobilemap.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.dto.AddressDto;
import ru.dorofeev.mobilemap.model.dto.GeographicalObjectDtoMobile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    //Параметр отвечающий за качество сжатия
    private static final float COMPRESSION_QUALITY = 0.3f;
    private static final float TOTAL_COUNT_OF_PIXELS_IN_2K = 2048 * 1152; //2_359_296 pixels
    private static final float MEGABYTE_IN_BYTES = 1 * 1024 * 1024; //1_048_576byte in 1MG

    /**
     * Сохранение файла в директорию.
     * Генерируется случайное имя в целях исключения ошибки одноименности.
     * В случае сохранения изображений позволяет уменьшить их вес.
     * При соответствующем значении флага. (Коэффициент сжатия 0.15f)
     *
     * @param directoryToSave Путь для сохранения.
     * @param file            Сохраняемые файлы.
     * @param extension       Список разрешенных расширений файлов.
     * @param compressImage   Флаг, отвечающий за сжатие изображений.
     * @return путь до файла.
     */
    public static String savingFile(String directoryToSave, MultipartFile file, List<String> extension, boolean compressImage) {
        String ext = getExtensionFile(file);

        if (!checkExtensionFile(ext, extension)) {
            log.error("IN SavingFile() - Тип загружаемого файла {} не удовлетворяет требованиям! Передаваемое расширение: {} Список доступных расширений: {}", file.getName(), ext, extension);
            throw new GeneralErrorException("Тип загружаемого файла " + file.getName() + " не удовлетворяет требованиям! Передаваемое расширение: " + ext + " Список доступных: " + extension);
        }

        String name = String.format("%s%s%s", UUID.randomUUID(), ".", ext);
        Path fullPathToSave = Paths.get(directoryToSave + File.separator + name);

        try {
            InputStream inputImage = file.getInputStream();

            if (compressImage) {
                inputImage = new ByteArrayInputStream(compressedImage(file));
            }

            Files.copy(inputImage, fullPathToSave, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("IN SavingFile() - Ошибка при сохранении файла в директорию!", e);
            throw new GeneralErrorException("Ошибка при сохранении файла: " + file.getOriginalFilename());
        }

        return fullPathToSave.toString();
    }

    /**
     * Метод сжатия изображения.
     * Если изображение <= 1MB сжатие не используется
     *
     * @param image сжимаемое изображение.
     * @return массив байтов (сжатое изображение).
     * @throws IOException
     */
    private static byte[] compressedImage(MultipartFile image) throws IOException {

        //Если размер изображения <= 1MB, то мы не используем сжатие.
        if (image.getSize() <= MEGABYTE_IN_BYTES) {
            return image.getBytes();
        }

        byte[] compressedImageBytes;

        BufferedImage currentImage = ImageIO.read(image.getInputStream());

        BufferedImage resizeImage = resize(currentImage, currentImage.getWidth() / 2, currentImage.getHeight() / 2);

        ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName(getExtensionFile(image)).next();
        ImageWriteParam jpegWriteParam = jpegWriter.getDefaultWriteParam();
        jpegWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegWriteParam.setCompressionQuality(COMPRESSION_QUALITY);

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageOutputStream output = new MemoryCacheImageOutputStream(baos)
        ) {
            jpegWriter.setOutput(output);
            IIOImage outputImage = new IIOImage(resizeImage, null, null);
            jpegWriter.write(null, outputImage, jpegWriteParam);
            compressedImageBytes = baos.toByteArray();
        } catch (IOException e) {
            log.error("IN compressedImage() - Ошибка при сжатии изображения!");
            throw new RuntimeException(e);
        } finally {
            jpegWriter.dispose();
        }

        return compressedImageBytes;
    }


    /**
     * Метод изменения разрешения изображения.
     * Если разрешение <= 2К, resize не используется.
     *
     * @param image  изменяемое изображение.
     * @param width  требуемая ширина.
     * @param height требуемая высота.
     * @return измененное изображение.
     */
    private static BufferedImage resize(BufferedImage image, int width, int height) {

        if (image.getWidth() * image.getHeight() <= TOTAL_COUNT_OF_PIXELS_IN_2K) {
            return image;
        }

        double scaleX = (double) width / image.getWidth();
        double scaleY = (double) height / image.getHeight();
        double minScale = Math.min(scaleX, scaleY);

        int w = (int) (image.getWidth() * minScale);
        int h = (int) (image.getHeight() * minScale);

        Image originalImage = image.getScaledInstance(w, h, Image.SCALE_FAST);

        BufferedImage resized = new BufferedImage(w, h, image.getType());
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    /**
     * Метод, позволяющий получить расширение файла.
     *
     * @param file передаваемый файл.
     * @return расширение файла.
     */
    public static String getExtensionFile(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).split("/")[1];
    }

    /**
     * Метод, позволяющий получить расширение файла.
     *
     * @param name передаваемое имя файла с расширением.
     * @return расширение файла.
     */
    public static String getExtensionFile(String name) {
        return name.substring(name.lastIndexOf('.'));
    }

    /**
     * Метод, позволяющий получить имя файла без расширения.
     *
     * @param name передаваемое имя файла с расширением.
     * @return имя без расширения.
     */
    public static String getOriginalNameWithoutExtension(String name) {
        return name.substring(0, name.lastIndexOf('.'));
    }

    /**
     * Удаление файла из директории.
     *
     * @param directoryToDelete путь до удаляемого файла.
     */
    public static void deleteFile(String directoryToDelete) {
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
    public static GeographicalObjectDtoMobile validationGeoObject(GeographicalObjectDtoMobile geo) {
        GeographicalObjectDtoMobile entity = new GeographicalObjectDtoMobile();
        entity.setName(geo.getName());
        entity.setType(geo.getType());
        entity.setLatitude(geo.getLatitude());
        entity.setLongitude(geo.getLongitude());
        entity.setDescription(geo.getDescription());
        entity.setNote(geo.getNote());
        entity.setDesignation(geo.getDesignation());
        entity.setIsPlaying(geo.getIsPlaying());

        if (geo.getAddressDto() != null) {
            entity.setAddressDto(validationAddress(
                    geo.getAddressDto().getRegion(),
                    geo.getAddressDto().getTypeLocality(),
                    geo.getAddressDto().getLocality(),
                    geo.getAddressDto().getDistrict(),
                    geo.getAddressDto().getStreet(),
                    geo.getAddressDto().getHouseNumber()
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
    public static AddressDto validationAddress(String region, String typeLocality, String locality, String district, String street, String houseNumber) {
        if (region == null && typeLocality == null && locality == null && district == null && street == null && houseNumber == null) {
            return null;
        } else if (region.isEmpty() && typeLocality.isEmpty() && locality.isEmpty() && district.isEmpty() && street.isEmpty() && houseNumber.isEmpty()) {
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
                throw new GeneralErrorException("Ошибка! Без поля \"region\" формирование адреса невозможно!");
            }

            if (typeLocality == null) {
                throw new GeneralErrorException("Ошибка! Без поля \"typeLocality\" формирование адреса невозможно!");
            }

            throw new GeneralErrorException("Ошибка! Без поля \"locality\" формирование адреса невозможно!");
        }
    }

    /**
     * Метод для проверки допустимых расширений файлов.
     *
     * @param extCurrentFile текущее расширение
     * @param extension      список допустим расширений
     * @return результат проверки (Прошло/Не прошло)
     */
    private static boolean checkExtensionFile(String extCurrentFile, List<String> extension) {
        for (var ext : extension) {
            if (ext.equals(extCurrentFile.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
