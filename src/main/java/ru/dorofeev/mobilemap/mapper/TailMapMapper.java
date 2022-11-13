package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.TailMap;
import ru.dorofeev.mobilemap.model.dto.TailMapDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Маппер предназначенный для преобразования Entity -> DTO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TailMapMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_DOWNLOAD_TAIL_BY_NAME_WITH_EXTENSION = "/api/v1/tail-map/";

    /**
     * Метод, преобразующий список Entity в список DTO.
     *
     * @param tailMapList список, подлежащий преобразованию.
     * @return преобразованный список.
     */
    public List<TailMapDto> toDtoList(List<TailMap> tailMapList) {
        if (tailMapList == null) {
            return Collections.emptyList();
        }

        List<TailMapDto> result = new ArrayList<>(tailMapList.size());

        for (var item : tailMapList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий Entity в DTO.
     *
     * @param tailMap объект, который подлежит преобразованию.
     * @return преобразованный объект.
     */
    public TailMapDto toDto(TailMap tailMap) {
        return TailMapDto.builder()
                .id(tailMap.getId())
                .url(String.format("%s%s%s", rootUrl, ENDPOINT_DOWNLOAD_TAIL_BY_NAME_WITH_EXTENSION, tailMap.getId()))
                .name(tailMap.getName())
                .build();
    }

}
