package ru.dorofeev.mobilemap.service.dto.interf;

import ru.dorofeev.mobilemap.model.dto.TileMapDto;

import java.util.List;

/**
 * Является service-прослойкой, существующей для преобразования entity в dto.
 */
public interface TailMapDtoService {
    List<TileMapDto> getAllWithLinkDownload();

    List<TileMapDto> getAllByNameWithLinkDownload(String name);
}
