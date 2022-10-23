package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Designation;
import ru.dorofeev.mobilemap.repository.DesignationRepository;
import ru.dorofeev.mobilemap.service.interf.DesignationService;
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
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    @Value("${file.storage.designation.location}")
    private String directoryToSave;

    private final List<String> EXTENSIONS = List.of("jpeg", "png");

    @Override
    public void upload(MultipartFile[] designation) {
        if (designation.length > 1) {
            log.error("IN deleteById() - Превышен допустимый предел загрузки обозначений!");
            throw new RuntimeException("Превышен допустимый предел загрузки обозначений!");
        }

        Arrays.stream(designation).forEach(
                currentDesignation -> designationRepository.save(Designation.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, currentDesignation, EXTENSIONS))
                        .name(currentDesignation.getOriginalFilename())
                        .build())
        );

        log.info("IN upload() - Обозначение сохранено!");
    }

    @Override
    public void update(Designation file) {
        Optional<Designation> byId = designationRepository.findById(file.getId());

        if (byId.isPresent()) {
            designationRepository.save(file);
            log.info("IN upload() - Обновлено обозначение с ID: {}", byId.get().getId());
        }

        log.info("IN upload() - Не найдено обозначение с ID: {}", file.getId());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        String urlToFile = designationRepository.findById(id).get().getUrl();
        String directoryToDelete = String.format("%s%s", directoryToSave, urlToFile.substring(0, directoryToSave.length()));
        AuxiliaryUtils.DeleteFile(directoryToDelete);

        designationRepository.deleteById(id);
        log.info("IN deleteById() - Удалено обозначение с ID: {} из базы данных!", id);
    }


    @Transactional
    @Override
    public void deleteByName(String name) {
        String urlToFile = designationRepository.getDesignationByName(name).getUrl();
        String directoryToDelete = String.format("%s%s", directoryToSave, urlToFile.substring(0, directoryToSave.length()));
        AuxiliaryUtils.DeleteFile(directoryToDelete);

        designationRepository.deleteByName(name);
        log.info("IN deleteByName() - Удалено обозначение с name: {} из базы данных!", name);
    }

    @Override
    public List<Designation> getAll() {
        return designationRepository.findAll();
    }

    @Override
    public Optional<Designation> findById(UUID id) {
        Optional<Designation> byId = designationRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN findById() - Найдено обозначение с ID: {}", id);
            return byId;
        } else {
            log.info("IN findById() - Не найдено обозначение с ID: {}", id);
            return Optional.of(new Designation());
        }
    }

    @Override
    public List<Designation> findAllInfoByName(String name) {
        return List.of(getDesignationByName(name));
    }

    @Override
    public Designation getDesignationByName(String name) {
        Designation designationByName = designationRepository.getDesignationByName(name);

        log.info("IN getDesignationByName() - Найдено обозначение с name: {}", name);

        if (designationByName == null) {
            log.info("IN getDesignationByName() - Не найдено обозначение с name: {}", name);
            return designationRepository.getDesignationByName("Отсутствует");
        }

        return designationByName;
    }

    @Override
    public ResponseEntity<byte[]> getFileById(UUID id) throws IOException {
        Optional<Designation> foundFile = designationRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.info("IN getFileById() - Найдено обозначение с ID: {}", id);
            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Не найдено обозначение с ID: {}", id);
            return ResponseEntity.ok().body(new byte[0]);
        }
    }
}
