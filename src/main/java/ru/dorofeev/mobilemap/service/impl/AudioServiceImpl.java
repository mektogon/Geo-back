package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
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

    @Value("${file.extension.audio}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile[] audio, UUID id) {
        if (audio.length > 1) {
            log.error("IN upload() - Превышен допустимый предел загрузки ауодизаписей!");
            throw new GeneralErrorException("Превышен допустимый предел загрузки ауодизаписей!");
        }

        Arrays.stream(audio).forEach(
                currentVideo -> audioRepository.save(Audio.builder()
                        .url(AuxiliaryUtils.savingFile(directoryToSave, currentVideo, EXTENSIONS, false))
                        .fileName(currentVideo.getOriginalFilename())
                        .geographicalObject(geographicalObjectRepository.findById(id).get())
                        .build())
        );

        log.info("IN upload() - Аудиозапись сохранена!");
    }

    @Override
    public void update(UUID id, MultipartFile file) {
        Optional<Audio> byId = audioRepository.findById(id);

        if (byId.isPresent()) {
            Audio updatedAudio = byId.get();

            updatedAudio.setFileName(file.getOriginalFilename());
            updatedAudio.setUrl(AuxiliaryUtils.savingFile(directoryToSave, file, EXTENSIONS, false));
            AuxiliaryUtils.deleteFile(updatedAudio.getUrl());

            audioRepository.save(updatedAudio);

            log.info("IN upload() - Обновлена аудиозапись с ID: {}", id);
        } else {
            log.info("IN upload() - Не найдено обозначение с ID: {}", id);
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<Audio> byId = audioRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.deleteFile(urlToFile);

            audioRepository.deleteById(id);
            log.info("IN deleteById() - Аудиозапись с ID: {} удалена из базы данных!", id);
        } else {
            log.info("IN deleteById() - Не удалось найти и удалить аудиозапись с ID: {} из базы данных!", id);
        }
    }

    @Override
    public List<Audio> getAll() {
        return audioRepository.findAll();
    }

    @Override
    public Audio getById(UUID id) {
        Optional<Audio> byId = audioRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN findById() - Найдена аудиозапись с ID: {}", id);
            return byId.get();
        } else {
            log.info("IN findById() - Не найдена аудиозапись с ID: {}", id);
            return new Audio();
        }
    }

    @Override
    public ResponseEntity<byte[]> getViewFileById(UUID id) throws IOException {
        Optional<Audio> foundFile = audioRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            String fileExtension = file.getName().split("\\.")[1];

            log.info("IN getFileById() - Найдена аудиозапись с ID: {}", id);

            return ResponseEntity.ok().contentType((MediaType.parseMediaType("audio/" + fileExtension)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Не найдена аудиозапись с ID: {}", id);
            return ResponseEntity.status(204).body(new byte[0]);
        }
    }

    @Override
    public List<Audio> getAllFilesByGeographicalObjectId(UUID id) {
        return audioRepository.findAllAudioByGeographicalObjectId(id);
    }
}
