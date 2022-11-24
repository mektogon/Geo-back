package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.Video;
import ru.dorofeev.mobilemap.model.dto.FileDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Маппер предназначенный для преобразования Entity -> DTO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VideoMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_GET_VIDEO_BY_ID = "/api/v1/video/view/";


    /**
     * Метод, преобразующий список Entity в список DTO.
     *
     * @param videoList список, подлежащий преобразованию.
     * @return преобразованный список.
     */
    public List<FileDto> toDtoList(List<Video> videoList) {
        if (videoList == null) {
            return Collections.emptyList();
        }

        List<FileDto> result = new ArrayList<>(videoList.size());

        for (var item : videoList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий Entity в DTO.
     *
     * @param video объект, который подлежит преобразованию.
     * @return преобразованный объект.
     */
    public FileDto toDto(Video video) {
        return FileDto.builder()
                .id(video.getId())
                .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_VIDEO_BY_ID, video.getId()))
                .build();
    }
}
