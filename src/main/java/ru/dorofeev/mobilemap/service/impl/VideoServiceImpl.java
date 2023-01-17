package ru.dorofeev.mobilemap.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.base.Video;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.repository.VideoRepository;
import ru.dorofeev.mobilemap.service.interf.VideoService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

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
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final GeographicalObjectRepository geographicalObjectRepository;

    @Value("${file.storage.video.location}")
    private String directoryToSave;

    @Value("${file.extension.video}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile[] video, UUID id) {
        if (video.length > 1) {
            log.error("IN upload() - Превышен допустимый предел загрузки видеозаписей!");
            throw new GeneralErrorException("Превышен допустимый предел загрузки видеозаписей!");
        }

        Arrays.stream(video).forEach(
                currentVideo -> videoRepository.save(Video.builder()
                        .url(AuxiliaryUtils.savingFile(directoryToSave, currentVideo, EXTENSIONS, false))
                        .fileName(currentVideo.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.getById(id))
                        .build())
        );
    }

    @Override
    public void update(UUID id, MultipartFile file) {
        Optional<Video> byId = videoRepository.findById(id);

        if (byId.isPresent()) {
            Video updatedVideo = byId.get();

            updatedVideo.setFileName(file.getOriginalFilename());
            updatedVideo.setUrl(AuxiliaryUtils.savingFile(directoryToSave, file, EXTENSIONS, false));
            AuxiliaryUtils.deleteFile(updatedVideo.getUrl());

            videoRepository.save(updatedVideo);

            log.info("IN upload() - Обновлена видеозапись с ID: {}", id);
        } else {
            log.info("IN upload() - Не найдена видеозапись с ID: {}", id);
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<Video> byId = videoRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.deleteFile(urlToFile);

            videoRepository.deleteById(id);
            log.info("IN deleteById() - Видеозапись с ID: {} удалено из базы данных!", id);
        } else {
            log.info("IN deleteById() - Не удалось найти и удалить видеозапись с ID: {} из базы данных!", id);
        }
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll();
    }

    @Override
    public Video getById(UUID id) {
        Optional<Video> byId = videoRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN findById() - Видео с ID: {} найдено!", id);
            return byId.get();
        } else {
            log.info("IN findById() - Видео с ID: {} не найдено!", id);
            return new Video();
        }
    }

    @Override
    public ResponseEntity<byte[]> getViewFileById(UUID id) throws IOException {
        Optional<Video> foundFile = videoRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.info("IN getFileById() - Видео с ID: {} найдено!", id);
            String fileExtension = file.getName().split("\\.")[1];

            return ResponseEntity.ok().contentType((MediaType.parseMediaType("video/" + fileExtension)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Видео с ID: {} не найдено!", id);
            return ResponseEntity.status(204).body(new byte[0]);
        }
    }

    @Override
    public List<Video> getAllFilesByGeographicalObjectId(UUID id) {
        return videoRepository.findAllVideoByGeographicalObjectId(id);
    }
}
