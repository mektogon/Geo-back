package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.TileMap;
import ru.dorofeev.mobilemap.model.dto.TileMapDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Маппер предназначенный для преобразования Entity -> DTO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TileMapMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_DOWNLOAD_TILE_BY_NAME_WITH_EXTENSION = "/api/v1/tile-map/download/";

    /**
     * Метод, преобразующий список Entity в список DTO.
     *
     * @param tileMapList список, подлежащий преобразованию.
     * @return преобразованный список.
     */
    public List<TileMapDto> toDtoList(List<TileMap> tileMapList) {
        if (tileMapList == null) {
            return Collections.emptyList();
        }

        List<TileMapDto> result = new ArrayList<>(tileMapList.size());

        for (var item : tileMapList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий Entity в DTO.
     *
     * @param tileMap объект, который подлежит преобразованию.
     * @return преобразованный объект.
     */
    public TileMapDto toDto(TileMap tileMap) {
        return TileMapDto.builder()
                .id(tileMap.getId())
                .url(String.format("%s%s%s", rootUrl, ENDPOINT_DOWNLOAD_TILE_BY_NAME_WITH_EXTENSION, tileMap.getId()))
                .name(tileMap.getName())
                .build();
    }

}
