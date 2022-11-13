package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.TileMapMapper;
import ru.dorofeev.mobilemap.model.dto.TileMapDto;
import ru.dorofeev.mobilemap.service.dto.interf.TailMapDtoService;
import ru.dorofeev.mobilemap.service.interf.TileMapService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TailMapDtoServiceImpl implements TailMapDtoService {

    private final TileMapService tileMapService;
    private final TileMapMapper tileMapMapper;

    @Override
    public List<TileMapDto> getAllWithLinkDownload() {
        log.info("IN getAllWithLinkDownload() - Преобразование List<Entity> в List<DTO>.");

        return tileMapMapper.toDtoList(tileMapService.getAll());
    }

    @Override
    public List<TileMapDto> getAllByNameWithLinkDownload(String name) {
        log.info("IN getAllByNameWithLinkDownload() - Преобразование List<Entity> в List<DTO>.");

        return tileMapMapper.toDtoList(tileMapService.getAllByName(name));
    }
}
