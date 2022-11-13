package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.TailMapMapper;
import ru.dorofeev.mobilemap.model.dto.TailMapDto;
import ru.dorofeev.mobilemap.service.dto.interf.TailMapDtoService;
import ru.dorofeev.mobilemap.service.interf.TailMapService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TailMapDtoServiceImpl implements TailMapDtoService {

    private final TailMapService tailMapService;
    private final TailMapMapper tailMapMapper;

    @Override
    public List<TailMapDto> getAllWithLinkDownload() {
        log.info("IN getAllWithLinkDownload() - Преобразование List<Entity> в List<DTO>.");

        return tailMapMapper.toDtoList(tailMapService.getAll());
    }

    @Override
    public List<TailMapDto> getAllByNameWithLinkDownload(String name) {
        log.info("IN getAllByNameWithLinkDownload() - Преобразование List<Entity> в List<DTO>.");

        return tailMapMapper.toDtoList(tailMapService.getAllByName(name));
    }
}
