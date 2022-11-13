package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.base.Designation;
import ru.dorofeev.mobilemap.repository.DesignationRepository;
import ru.dorofeev.mobilemap.service.interf.DesignationService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.activation.FileTypeMap;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @Value("${file.extension.designation}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile designation, String name) {

        designationRepository.save(Designation.builder()
                .url(AuxiliaryUtils.SavingFile(directoryToSave, designation, EXTENSIONS))
                .name(name)
                .fileName(designation.getOriginalFilename())
                .build());
    }

    @Override
    public void update(UUID id, String name, MultipartFile file) {
        Optional<Designation> byId = designationRepository.findById(id);

        if (byId.isPresent()) {
            Designation updatedDesignation = byId.get();

            if (name != null) {
                updatedDesignation.setName(name);
                log.info("IN update() - Обновлено наименование обозначения с ID: {}", id);
            }

            if (file != null) {
                updatedDesignation.setFileName(file.getOriginalFilename());
                AuxiliaryUtils.DeleteFile(updatedDesignation.getUrl());
                updatedDesignation.setUrl(AuxiliaryUtils.SavingFile(directoryToSave, file, EXTENSIONS));
                log.info("IN update() - Обновлена фотография обозначения с ID: {}", id);
            }

            designationRepository.save(updatedDesignation);
        } else {
            log.info("IN upload() - Не удалось найти и обновить обозначение с ID: {}", id);
            throw new GeneralErrorException(String.format("Не удалось найти и обновить обозначение с ID: %s", id));
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<Designation> byId = designationRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.DeleteFile(urlToFile);

            designationRepository.deleteById(id);
            log.info("IN deleteById() - Удалено обозначение с ID: {} из базы данных!", id);
        } else {
            log.info("IN deleteById() - Не удалось найти и удалить обозначение с ID: {} из базы данных!", id);
        }
    }


    @Transactional
    @Override
    public void deleteByName(String name) {
        Designation byName = designationRepository.getDesignationsByName(name);

        if (byName != null) {
            String urlToFile = byName.getUrl();
            AuxiliaryUtils.DeleteFile(urlToFile);

            designationRepository.deleteByName(name);
            log.info("IN deleteByName() - Удалено обозначение с name: {} из базы данных!", name);
        } else {
            log.info("IN deleteByName() - Не удалось найти и удалить обозначение с именем: {} из базы данных!", name);
        }
    }

    @Override
    public List<Designation> getAll() {
        return designationRepository.findAll();
    }

    @Override
    public Designation getById(UUID id) {
        Optional<Designation> byId = designationRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN findById() - Найдено обозначение с ID: {}", id);
            return byId.get();
        } else {
            log.info("IN findById() - Не найдено обозначение с ID: {}", id);
            return new Designation();
        }
    }

    @Override
    public List<Designation> getAllByName(String name) {
        return designationRepository.findAllByNameIsContainingIgnoreCase(name);
    }

    @Override
    public Designation getByName(String name) {
        Designation designationByName = designationRepository.getDesignationsByName(name);

        if (designationByName == null) {
            log.info("IN getByName() - Не найдено обозначение с name: {}", name);
            return designationRepository.getDesignationsByName("Отсутствует");
        }

        log.info("IN getByName() - Найдено обозначение с name: {}", name);

        return designationByName;
    }

    @Override
    public ResponseEntity<byte[]> getViewFileById(UUID id) throws IOException {
        Optional<Designation> foundFile = designationRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            log.info("IN getFileById() - Найдено обозначение с ID: {}", id);
            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            log.info("IN getFileById() - Не найдено обозначение с ID: {}", id);
            return ResponseEntity.status(204).body(new byte[0]);
        }
    }
}
