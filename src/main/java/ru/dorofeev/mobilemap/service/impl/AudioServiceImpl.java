package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Audio;
import ru.dorofeev.mobilemap.repository.AudioRepository;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;
import ru.dorofeev.mobilemap.service.interf.AudioService;
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
public class AudioServiceImpl implements AudioService {

    private final AudioRepository audioRepository;
    private final GeographicalObjectRepository geographicalObjectRepository;

    @Value("${file.storage.audio.location}")
    private String directoryToSave;

    private final List<String> EXTENSIONS = List.of("mp3", "ogg", "wav", "aiff", "ape", "flac", "mpeg", "m4a", "mp4");

    @Override
    public void upload(MultipartFile[] audio, UUID id) {
        if (audio.length > 1) {
            log.error("IN upload() - Превышен допустимый предел загрузки ауодизаписей!");
            throw new RuntimeException("Превышен допустимый предел загрузки ауодизаписей!");
        }

        Arrays.stream(audio).forEach(
                currentVideo -> audioRepository.save(Audio.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, currentVideo, EXTENSIONS))
                        .name(currentVideo.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.findById(id).get())
                        .build())
        );

        log.info("IN upload() - Аудиозапись сохранена!");
    }

    @Override
    public void update(Audio file) {
        Optional<Audio> byId = audioRepository.findById(file.getId());

        if (byId.isPresent()) {
            audioRepository.save(file);
            log.info("IN upload() - Обновлена аудиозапись с ID: {}", byId.get().getId());
        }

        log.info("IN upload() - Не найдена аудиозапись с ID: {}", file.getId());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        String urlToFile = audioRepository.findById(id).get().getUrl();
        AuxiliaryUtils.DeleteFile(urlToFile);

        audioRepository.deleteById(id);
        log.info("IN deleteById() - Аудиозапись с ID: {} удалена из базы данных!", id);
    }

    @Override
    public List<Audio> getAll() {
        return audioRepository.findAll();
    }

    @Override
    public Optional<Audio> findById(UUID id) {
        Optional<Audio> byId = audioRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN findById() - Найдена аудиозапись с ID: {}", id);
            return byId;
        } else {
            log.info("IN findById() - Не найдена аудиозапись с ID: {}", id);
            return Optional.of(new Audio());
        }
    }

    @Override
    public List<Audio> findAllInfoByName(String name) {
        return audioRepository.findAllAudioByName(name);
    }

    @Override
    public ResponseEntity<byte[]> getFileById(UUID id) throws IOException {
        Optional<Audio> foundFile = audioRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            String fileExtension = file.getName().split("\\.")[1];

            log.info("IN getFileById() - Найдена аудиозапись с ID: {}", id);

            return ResponseEntity.ok().contentType((MediaType.parseMediaType("audio/" + fileExtension)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Не найдена аудиозапись с ID: {}", id);
            return ResponseEntity.ok().body(new byte[0]);
        }
    }

    @Override
    public List<Audio> findAllFilesByGeographicalObjectId(UUID id) {
        return audioRepository.findAllAudioByGeographicalObjectId(id);
    }
}
