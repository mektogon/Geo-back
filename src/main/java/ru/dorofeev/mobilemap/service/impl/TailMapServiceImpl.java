package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.TailMap;
import ru.dorofeev.mobilemap.repository.TailMapRepository;
import ru.dorofeev.mobilemap.service.interf.TailMapService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TailMapServiceImpl implements TailMapService {

    private final TailMapRepository tailMapRepository;

    @Value("${file.storage.tail-map.location}")
    private String directoryToSave;

    @Value("${file.extension.tail-map}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile[] tailMaps, String name) {

        if (tailMaps.length > 1) {
            log.error("IN upload() - Превышен допустимый предел загрузки тайлов карт!");
            throw new RuntimeException("Превышен допустимый предел загрузки тайлов карт!");
        }

        Arrays.stream(tailMaps).forEach(
                file -> tailMapRepository.save(TailMap.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, file, EXTENSIONS))
                        .name(name)
                        .fileName(file.getOriginalFilename())
                        .build())
        );

        log.info("IN upload() - Тайлы карты сохранены в количестве: {} шт.", tailMaps.length);
    }


    @Override
    public void update(TailMap file) {
        Optional<TailMap> byId = tailMapRepository.findById(file.getId());

        if (byId.isPresent()) {
            tailMapRepository.save(file);
            log.info("IN update() - Тайл карты с ID: {} обновлен!", byId.get().getId());
        }

        log.info("IN update() - Тайл карты с ID: {} не найден!", file.getId());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<TailMap> byId = tailMapRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.DeleteFile(urlToFile);

            tailMapRepository.deleteById(id);
            log.info("IN deleteById() - Тайл карты с ID: {} удален из базы данных!", id);
        }

        log.info("IN deleteById() - Не удалось найти и удалить тайлы карты с ID: {} из базы данных!", id);
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        Optional<TailMap> byName = tailMapRepository.getTailMapByName(name);

        if (byName.isPresent()) {
            String urlToFile = byName.get().getUrl();
            AuxiliaryUtils.DeleteFile(urlToFile);

            tailMapRepository.deleteByName(name);
            log.info("IN deleteByName() - Удалены тайлы карт с name: {} из базы данных!", name);
        }

        log.info("IN deleteByName() - Не удалось найти и удалить тайлы карты с именем: {} из базы данных!", name);
    }

    @Override
    public ResponseEntity<Resource> downloadFileById(UUID id) throws IOException {
        Optional<TailMap> foundFile = tailMapRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.info("IN getFileById() - Найдены тайлы карты с ID: {}", id);

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()))
                    .body(resource);
        } else {
            log.info("IN getFileById() - Не найдены тайлы карты с ID: {}", id);
            return ResponseEntity.status(204).body(new ByteArrayResource(new byte[0]));
        }
    }

    @Override
    public List<TailMap> getAll() {
        return tailMapRepository.findAll();
    }

    @Override
    public Optional<TailMap> findById(UUID id) {
        Optional<TailMap> byId = tailMapRepository.findById(id);

        if (byId.isPresent()) {
            log.error("IN findById() - Тайл карты с ID: {} найден!", id);
            return byId;
        } else {
            log.error("IN findById() - Тайл карты с ID: {} не найден!", id);
            return Optional.of(new TailMap());
        }
    }

    @Override
    public List<TailMap> getAllByName(String name) {
        return tailMapRepository.findAllByNameIsContainingIgnoreCase(name);
    }


}
