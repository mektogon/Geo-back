package ru.dorofeev.mobilemap.service.interf;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.TailMap;

import java.io.IOException;
import java.util.UUID;

public interface TailMapService extends AbstractFileService<TailMap> {

    void upload(MultipartFile[] tailMaps, String name);

    void deleteByName(String name);

    ResponseEntity<Resource> downloadFileById(UUID id) throws IOException;
}
