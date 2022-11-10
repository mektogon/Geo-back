package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Designation;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DesignationService extends AbstractFileService<Designation> {
    Designation getDesignationByName(String name);

    List<Designation> getAllByName(String name);

    void upload(MultipartFile[] designation, String name);

    void deleteByName(String name);

    ResponseEntity<byte[]> getFileById(UUID id) throws IOException;
}
