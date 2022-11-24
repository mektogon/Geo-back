package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.AudioMapper;
import ru.dorofeev.mobilemap.model.dto.FileDto;
import ru.dorofeev.mobilemap.service.dto.interf.FileServiceDto;
import ru.dorofeev.mobilemap.service.interf.AudioService;

import java.util.List;
import java.util.UUID;

@Service("AudioServiceDtoImpl")
@Slf4j
@RequiredArgsConstructor
public class AudioServiceDtoImpl implements FileServiceDto {

    private final AudioMapper audioMapper;
    private final AudioService audioService;

    @Override
    public List<FileDto> getAllByGeoId(UUID id) {
        log.info("IN getAllByGeoId() - Преобразование List<Entity> в List<DTO>.");

        return audioMapper.toDtoList(audioService.getAllFilesByGeographicalObjectId(id));
    }

}
