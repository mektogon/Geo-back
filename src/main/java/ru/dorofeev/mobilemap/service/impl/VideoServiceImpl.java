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
        if (video.length > 2) {
            log.error("IN upload() - Превышен допустимый предел загрузки видеозаписей!");
            throw new RuntimeException("Превышен допустимый предел загрузки видеозаписей!");
        }

        Arrays.stream(video).forEach(
                currentVideo -> videoRepository.save(Video.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, currentVideo, EXTENSIONS))
                        .name(currentVideo.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.getById(id))
                        .build())
        );
    }

    @Override
    public void update(Video file) {
        Optional<Video> byId = videoRepository.findById(file.getId());

        if (byId.isPresent()) {
            videoRepository.save(file);
        }
    }

    @Override
    public void deleteById(UUID id) {
        videoRepository.deleteById(id);
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll();
    }

    @Override
    public Optional<Video> findById(UUID id) {
        Optional<Video> byId = videoRepository.findById(id);

        if (byId.isPresent()) {
            return byId;
        } else {
            return Optional.of(new Video());
        }
    }

    @Override
    public List<Video> findAllInfoByName(String name) {
        return videoRepository.findAllVideoByName(name);
    }

    @Override
    public ResponseEntity<byte[]> getFileById(UUID id) throws IOException {
        Optional<Video> foundFile = videoRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            String fileExtension = file.getName().split("\\.")[1];

            return ResponseEntity.ok().contentType((MediaType.parseMediaType("video/" + fileExtension)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            return ResponseEntity.ok().body(new byte[0]);
        }
    }

    @Override
    public List<Video> findAllFilesByGeographicalObjectId(UUID id) {
        return videoRepository.findAllVideoByGeographicalObjectId(id);
    }
}
