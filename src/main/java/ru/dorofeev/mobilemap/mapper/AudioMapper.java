package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.Audio;
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
public class AudioMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_GET_AUDIO_BY_ID = "/api/v1/audio/view/";


    /**
     * Метод, преобразующий список Entity в список DTO.
     *
     * @param audioList список, подлежащий преобразованию.
     * @return преобразованный список.
     */
    public List<FileDto> toDtoList(List<Audio> audioList) {
        if (audioList == null) {
            return Collections.emptyList();
        }

        List<FileDto> result = new ArrayList<>(audioList.size());

        for (var item : audioList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий Entity в DTO.
     *
     * @param audio объект, который подлежит преобразованию.
     * @return преобразованный объект.
     */
    public FileDto toDto(Audio audio) {
        return FileDto.builder()
                .id(audio.getId())
                .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_AUDIO_BY_ID, audio.getId()))
                .build();
    }
}
