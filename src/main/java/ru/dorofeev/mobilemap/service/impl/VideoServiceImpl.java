package ru.dorofeev.mobilemap.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    private final List<String> EXTENSIONS = List.of("avi", "mp4", "mkv", "wmv", "asf", "mpeg");

    @Override
    public void upload(MultipartFile[] video, UUID id) {
        if (video.length > 1) {
            log.error("IN upload() - Превышен допустимый предел загрузки видеозаписей!");
            throw new RuntimeException("Превышен допустимый предел загрузки видеозаписей!");
        }

        Arrays.stream(video).forEach(
                currentVideo -> videoRepository.save(Video.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, currentVideo, EXTENSIONS))
                        .name(currentVideo.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.findById(id).get())
                        .build())
        );
    }

    @Override
    public void update(Video file) {
        Optional<Video> byId = videoRepository.findById(file.getId());

        if (byId.isPresent()) {
            videoRepository.save(file);
            log.info("IN update() - Видео с ID: {} обновлено!", byId.get().getId());
        }

        log.info("IN update() - Видео с ID: {} не найдено!", file.getId());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        String urlToFile = videoRepository.findById(id).get().getUrl();
        AuxiliaryUtils.DeleteFile(urlToFile);

        videoRepository.deleteById(id);
        log.info("IN deleteById() - Видео с ID: {} удалено из базы данных!", id);
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll();
    }

    @Override
    public Optional<Video> findById(UUID id) {
        Optional<Video> byId = videoRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN findById() - Видео с ID: {} найдено!", id);
            return byId;
        } else {
            log.info("IN findById() - Видео с ID: {} не найдено!", id);
            return Optional.of(new Video());
        }
    }

    @Override
    public List<Video> findAllInfoByName(String name) {
        return videoRepository.findAllVideoByNameIsIgnoreCase(name);
    }

    @Override
    public ResponseEntity<byte[]> getFileById(UUID id) throws IOException {
        Optional<Video> foundFile = videoRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.info("IN getFileById() - Видео с ID: {} найдено!", id);
            String fileExtension = file.getName().split("\\.")[1];

            return ResponseEntity.ok().contentType((MediaType.parseMediaType("video/" + fileExtension)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Видео с ID: {} не найдено!", id);
            return ResponseEntity.ok().body(new byte[0]);
        }
    }

    @Override
    public List<Video> findAllFilesByGeographicalObjectId(UUID id) {
        return videoRepository.findAllVideoByGeographicalObjectId(id);
    }
}
