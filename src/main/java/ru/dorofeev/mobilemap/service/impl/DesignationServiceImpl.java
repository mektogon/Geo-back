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
            log.error("IN upload()");
            throw new RuntimeException("Превышен допустимый предел загрузки обозначений!");
        }

        Arrays.stream(designation).forEach(
                currentDesignation -> designationRepository.save(Designation.builder()
                        .url(AuxiliaryUtils.SavingFile(directoryToSave, currentDesignation, EXTENSIONS))
                        .name(currentDesignation.getOriginalFilename())
                        .build())
        );
    }

    @Override
    public void update(Designation file) {
        Optional<Designation> byId = designationRepository.findById(file.getId());

        if (byId.isPresent()) {
            designationRepository.save(file);
        }
    }

    @Override
    public void deleteById(UUID id) {
        designationRepository.deleteById(id);
    }


    @Override
    public void deleteByName(String name) {
        designationRepository.deleteByName(name);
    }

    @Override
    public List<Designation> getAll() {
        return designationRepository.findAll();
    }

    @Override
    public Optional<Designation> findById(UUID id) {
        Optional<Designation> byId = designationRepository.findById(id);

        if (byId.isPresent()) {
            return byId;
        } else {
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

        if (designationByName == null) {
            return designationRepository.getDesignationByName("Отсутствует");
        }

        return designationByName;
    }

    @Override
    public ResponseEntity<byte[]> getFileById(UUID id) throws IOException {
        Optional<Designation> foundFile = designationRepository.findById(id);

        if (foundFile.isPresent()) {
            File file = new File(foundFile.get().getUrl());
            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        } else {
            return ResponseEntity.ok().body(new byte[0]);
        }
    }
}
