package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.base.Photo;
import ru.dorofeev.mobilemap.model.dto.PhotoRotateDto;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.repository.PhotoRepository;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.activation.FileTypeMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final GeographicalObjectRepository geographicalObjectRepository;

    @Value("${file.storage.photo.location}")
    private String directoryToSavePhoto;

    @Value("${file.storage.photoPreview.location}")
    private String directoryToSavePhotoPreview;

    @Value("${file.extension.photo}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile[] images, UUID id) {

        if (images.length > 5) {
            log.error("IN upload() - Превышен допустимый предел загрузки изображений!");
            throw new GeneralErrorException("Превышен допустимый предел загрузки изображений!");
        }

        Arrays.stream(images).forEach(
                img -> photoRepository.save(Photo.builder()
                        .url(AuxiliaryUtils.savingFile(directoryToSavePhoto, img, EXTENSIONS, true))
                        .fileName(img.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.getById(id))
                        .build())
        );

        log.debug("IN upload() - Фотографии сохранены в количестве: {} шт.", images.length);
    }

    @Override
    public void update(UUID id, MultipartFile file) {
        Optional<Photo> byId = photoRepository.findById(id);
        if (byId.isPresent()) {
            List<String> candidatesForDelete = new ArrayList<>();
            Photo updatedPhoto = byId.get();
            candidatesForDelete.add(updatedPhoto.getUrl());
            updatedPhoto.setFileName(file.getOriginalFilename());
            updatedPhoto.setUrl(AuxiliaryUtils.savingFile(directoryToSavePhoto, file, EXTENSIONS, true));

            if (updatedPhoto.getUrlPhotoPreview() != null) {
                candidatesForDelete.add(updatedPhoto.getUrlPhotoPreview());
                updatedPhoto.setUrlPhotoPreview(null);
            }

            photoRepository.save(updatedPhoto);

            AuxiliaryUtils.deleteFile(candidatesForDelete);

            initializePreviewPhoto(updatedPhoto.getGeographicalObject().getId());

            log.debug("IN upload() - Обновлена фотография с ID: {}", id);
        } else {
            log.debug("IN upload() - Не найдена фотография с ID: {}", id);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Optional<Photo> byId = photoRepository.findById(id);

        if (byId.isPresent()) {
            List<String> candidatesForDelete = new ArrayList<>();
            Photo photoToDeleted = byId.get();
            candidatesForDelete.add(photoToDeleted.getUrl());
            photoRepository.deleteById(id);


            if (photoToDeleted.getUrlPhotoPreview() != null) {
                candidatesForDelete.add(photoToDeleted.getUrlPhotoPreview());
                initializePreviewPhoto(photoToDeleted.getGeographicalObject().getId());
            }

            AuxiliaryUtils.deleteFile(candidatesForDelete);

            log.debug("IN deleteById() - Фотография с ID: {} удалена из базы данных!", id);
        } else {
            log.info("IN deleteById() - Не удалось найти и удалить фотографию с ID: {} из базы данных!", id);
        }
    }


    @Override
    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    @Override
    public Photo getById(UUID id) {
        Optional<Photo> byId = photoRepository.findById(id);

        if (byId.isPresent()) {
            log.debug("IN findById() - Фотография с ID: {} найдена!", id);
            return byId.get();
        } else {
            log.info("IN findById() - Фотография с ID: {} не найдена!", id);
            return new Photo();
        }
    }

    @Override
    public ResponseEntity<byte[]> getViewFileById(UUID id) {
        Optional<Photo> foundFile = photoRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.debug("IN getFileById() - Фотография с ID: {} найдена!", id);
            try {
                return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                        .body(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("IN getFileById() - Фотография с ID: {} не найдена!", id);
            return ResponseEntity.status(204).body(new byte[0]);
        }
    }

    @Override
    public List<Photo> getAllFilesByGeographicalObjectId(UUID id) {
        return photoRepository.findAllPhotoByGeographicalObjectId(id);
    }

    @Override
    public Photo getMainPhoto(UUID id) {
        return photoRepository.findAllPhotoByGeographicalObjectId(id).stream()
                .min(Comparator.comparing(o -> AuxiliaryUtils.getOriginalNameWithoutExtension(o.getFileName()))).orElse(null);
    }

    @Override
    public ResponseEntity<byte[]> getPreviewPhotoById(UUID id) {
        Photo photoPreview = photoRepository.getPhotoPreviewById(id);

        if (photoPreview != null) {
            File file = new File(photoPreview.getUrlPhotoPreview());
            log.debug("IN getFileById() - Превью фотография с ID: {} найдена!", id);
            try {
                return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                        .body(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("IN getFileById() - Фотография с ID: {} не найдена!", id);
            return ResponseEntity.status(204).body(new byte[0]);
        }
    }

    @Override
    public void initializePreviewPhoto(UUID id) {
        //Избавляемся от старого превью
        Photo oldPhotoPreview = photoRepository.getPhotoPreviewByGeographicalObjectId(id);

        if (oldPhotoPreview != null && oldPhotoPreview.getUrlPhotoPreview() != null) {
            AuxiliaryUtils.deleteFile(oldPhotoPreview.getUrlPhotoPreview());
            oldPhotoPreview.setUrlPhotoPreview(null);
            photoRepository.save(oldPhotoPreview);
        }

        //Генерируем новое превью
        createPreviewMainPhoto(getMainPhoto(id));
    }

    @Override
    public void rotatePhoto(UUID photoId, int rotationAngle, boolean isPreview) {
        Optional<Photo> byId = photoRepository.findById(photoId);

        if (byId.isPresent()) {

            BufferedImage destination;
            String originalName;
            String directoryToSave;

            if (byId.get().getUrlPhotoPreview() != null && isPreview) {
                destination = AuxiliaryUtils.rotateImageByDegrees(byId.get().getUrlPhotoPreview(), rotationAngle);
                originalName = AuxiliaryUtils.getDirectoryNameWithExtension(byId.get().getUrlPhotoPreview());
                directoryToSave = directoryToSavePhotoPreview;
            } else {
                destination = AuxiliaryUtils.rotateImageByDegrees(byId.get().getUrl(), rotationAngle);
                originalName = AuxiliaryUtils.getDirectoryNameWithExtension(byId.get().getUrl());
                directoryToSave = directoryToSavePhoto;
            }

            saveRotatedPhoto(destination, directoryToSave, originalName);
        }
    }

    @Override
    public void rotatePhoto(PhotoRotateDto photoRotateDto) {
        rotatePhoto(
                photoRotateDto.getPhotoId(),
                photoRotateDto.getRotationAngle(),
                photoRotateDto.isPreview()
        );
    }


    /**
     * Метод позволяет заменить существующую фотографию повернутым изображением.
     *
     * @param currentImage    сохраняемое изображение.
     * @param directoryToSave путь для сохранения.
     * @param originalName    текущее имя в директории.
     */
    private void saveRotatedPhoto(BufferedImage currentImage, String directoryToSave, String originalName) {
        try {
            Path fullPathToSave = Paths.get(directoryToSave + File.separator + originalName);
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            ImageIO.write(currentImage, AuxiliaryUtils.getExtensionFile(originalName), os);
            //TODO: Рассмотреть вариант сохранения в отдельном потоке. В данный момент уязвимое место.
            Files.copy(new ByteArrayInputStream(os.toByteArray()), fullPathToSave, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("IN saveRotatedPhoto() - Ошибка при сохранении фотографии при повороте: {}", e.toString());
            throw new GeneralErrorException("Ошибка! Не удалось сохранить фотографию после поворота!");
        }
    }

    /**
     * Метод создает превью фотографии.
     *
     * @param photo фотография для превью.
     * @return путь до превью, хранимой в директории системы.
     */
    private String createPreviewMainPhoto(Photo photo) {

        if (photo == null) {
            return null;
        }

        Path fullPathToSave;

        try {
            String name = String.format("%s%s%s", UUID.randomUUID(), ".", AuxiliaryUtils.getExtensionFile(photo.getFileName()));
            fullPathToSave = Paths.get(directoryToSavePhotoPreview + File.separator + name);

            BufferedImage image = ImageIO.read(new FileInputStream(photo.getUrl()));
            BufferedImage resizeImage = AuxiliaryUtils.resize(image, image.getWidth() / 2, image.getHeight() / 2, false);

            float compressionQuality = 0.15f;

            byte[] result = AuxiliaryUtils.compressedImage(resizeImage, compressionQuality);

            while (result.length > 102400) {
                compressionQuality -= 0.01;
                result = AuxiliaryUtils.compressedImage(resizeImage, compressionQuality);
            }

            Files.copy(new ByteArrayInputStream(result), fullPathToSave, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("IN createPreviewMainPhoto() - Произошла ошибка при формирование превью фотографии с ID: {}", photo.getId());
            throw new RuntimeException(e);
        }

        photo.setUrlPhotoPreview(fullPathToSave.toString());
        photoRepository.save(photo);

        return fullPathToSave.toString();
    }
}
