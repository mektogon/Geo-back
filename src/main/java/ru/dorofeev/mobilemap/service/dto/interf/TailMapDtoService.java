package ru.dorofeev.mobilemap.service.dto.interf;

import ru.dorofeev.mobilemap.model.dto.TailMapDto;

import java.util.List;

/**
 * Является service-прослойкой, существующей для преобразования entity в dto.
 */
public interface TailMapDtoService {
    List<TailMapDto> getAllWithLinkDownload();

    List<TailMapDto> getAllByNameWithLinkDownload(String name);
}
