package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.VideoMapper;
import ru.dorofeev.mobilemap.model.dto.FileDto;
import ru.dorofeev.mobilemap.service.dto.interf.FileServiceDto;
import ru.dorofeev.mobilemap.service.interf.VideoService;

import java.util.List;
import java.util.UUID;

@Service("VideoServiceDtoImpl")
@Slf4j
@RequiredArgsConstructor
public class VideoServiceDtoImpl implements FileServiceDto {

    private final VideoMapper videoMapper;
    private final VideoService videoService;

    @Override
    public List<FileDto> getAllByGeoId(UUID id) {
        return videoMapper.toDtoList(videoService.getAllFilesByGeographicalObjectId(id));
    }

}
