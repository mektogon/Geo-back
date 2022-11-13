package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.Designation;

import java.io.IOException;
import java.util.UUID;

public interface DesignationService extends AbstractFileServiceV2<Designation> {
    ResponseEntity<byte[]> getViewFileById(UUID id) throws IOException;
}
