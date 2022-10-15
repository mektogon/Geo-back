package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Photo;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.repository.PhotoRepository;
import ru.dorofeev.mobilemap.service.interf.PhotoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.activation.FileTypeMap;
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

    private final List<String> EXTENSIONS = List.of("jpeg", "png");

    @Override
    public void upload(MultipartFile[] images, UUID id) {

        if (images.length > 5) {
            log.error("IN upload() - Превышен допустимый предел загрузки изображений!");
            throw new RuntimeException("Превышен допустимый предел загрузки изображений!");
        }

        Arrays.stream(images).forEach(
                img -> photoRepository.save(Photo.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, img, EXTENSIONS))
                        .name(img.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.getById(id))
                        .build())
        );

        log.error("IN upload() - Фотографии сохранены в количестве: {} шт.", images.length);
    }

    @Override
    public void update(Photo file) {
        Optional<Photo> byId = photoRepository.findById(file.getId());

        if (byId.isPresent()) {
            photoRepository.save(file);
            log.error("IN update() - Фотография с ID: {} обновлена!", byId.get().getId());
        }

        log.error("IN deleteById() - Фотография с ID: {} не найдена!", file.getId());
    }

    @Override
    public void deleteById(UUID id) {
        photoRepository.deleteById(id);
        log.error("IN deleteById() - Фотография с ID: {} удалена!", id);
    }

    @Override
    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    @Override
    public Optional<Photo> findById(UUID id) {
        Optional<Photo> byId = photoRepository.findById(id);

        if (byId.isPresent()) {
            log.error("IN findById() - Фотография с ID: {} найдена!", id);
            return byId;
        } else {
            log.error("IN findById() - Фотография с ID: {} не найдена!", id);
            return Optional.of(new Photo());
        }
    }

    @Override
    public List<Photo> findAllInfoByName(String name) {
        return photoRepository.findAllPhotoByName(name);
    }

    @Override
    public ResponseEntity<byte[]> getFileById(UUID id) throws IOException {
        Optional<Photo> foundFile = photoRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.error("IN getFileById() - Фотография с ID: {} найдена!", id);
            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.error("IN getFileById() - Фотография с ID: {} не найдена!", id);
            return ResponseEntity.ok().body(new byte[0]);
        }
    }

    @Override
    public List<Photo> findAllFilesByGeographicalObjectId(UUID id) {
        return photoRepository.findAllPhotoByGeographicalObjectId(id);
    }
}
