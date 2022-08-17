package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AbstractDataFileService<T> extends AbstractFileService<T> {
    List<T> findAllFilesByGeographicalObjectId(UUID id);

    void upload(MultipartFile[] file, UUID id);

    ResponseEntity<byte[]> getFileById(UUID id) throws IOException;
}
