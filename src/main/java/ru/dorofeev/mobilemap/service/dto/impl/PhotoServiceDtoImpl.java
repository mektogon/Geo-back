package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.PhotoMapper;
import ru.dorofeev.mobilemap.model.dto.FileDto;
import ru.dorofeev.mobilemap.service.dto.interf.FileServiceDto;
import ru.dorofeev.mobilemap.service.interf.PhotoService;

import java.util.List;
import java.util.UUID;

@Service("PhotoServiceDtoImpl")
@Slf4j
@RequiredArgsConstructor
public class PhotoServiceDtoImpl implements FileServiceDto {

    private final PhotoMapper photoMapper;
    private final PhotoService photoService;

    @Override
    public List<FileDto> getAllByGeoId(UUID id) {
        return photoMapper.toDtoList(photoService.getAllFilesByGeographicalObjectId(id));
    }

}
