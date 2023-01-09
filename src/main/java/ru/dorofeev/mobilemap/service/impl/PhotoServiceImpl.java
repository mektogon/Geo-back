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
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.repository.PhotoRepository;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.activation.FileTypeMap;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
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
    private String directoryToSave;

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
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, img, EXTENSIONS))
                        .fileName(img.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.getById(id))
                        .build())
        );

        log.info("IN upload() - Фотографии сохранены в количестве: {} шт.", images.length);
    }

    @Override
    public void update(UUID id, MultipartFile file) {
        Optional<Photo> byId = photoRepository.findById(id);

        if (byId.isPresent()) {
            Photo updatedPhoto = byId.get();

            updatedPhoto.setFileName(file.getOriginalFilename());
            updatedPhoto.setUrl(AuxiliaryUtils.SavingFile(directoryToSave, file, EXTENSIONS));
            AuxiliaryUtils.DeleteFile(updatedPhoto.getUrl());

            photoRepository.save(updatedPhoto);

            log.info("IN upload() - Обновлена фотография с ID: {}", id);
        } else {
            log.info("IN upload() - Не найдена фотография с ID: {}", id);
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<Photo> byId = photoRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.DeleteFile(urlToFile);

            photoRepository.deleteById(id);
            log.info("IN deleteById() - Фотография с ID: {} удалена из базы данных!", id);
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
            log.info("IN findById() - Фотография с ID: {} найдена!", id);
            return byId.get();
        } else {
            log.info("IN findById() - Фотография с ID: {} не найдена!", id);
            return new Photo();
        }
    }

    @Override
    public ResponseEntity<byte[]> getViewFileById(UUID id) throws IOException {
        Optional<Photo> foundFile = photoRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.info("IN getFileById() - Фотография с ID: {} найдена!", id);
            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Фотография с ID: {} не найдена!", id);
            return ResponseEntity.status(204).body(new byte[0]);
        }
    }

    @Override
    public List<Photo> getAllFilesByGeographicalObjectId(UUID id) {
        return photoRepository.findAllPhotoByGeographicalObjectId(id);
    }
}
