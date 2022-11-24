package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.Photo;
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
public class PhotoMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_GET_PHOTO_BY_ID = "/api/v1/photo/view/";


    /**
     * Метод, преобразующий список Entity в список DTO.
     *
     * @param photoList список, подлежащий преобразованию.
     * @return преобразованный список.
     */
    public List<FileDto> toDtoList(List<Photo> photoList) {
        if (photoList == null) {
            return Collections.emptyList();
        }

        List<FileDto> result = new ArrayList<>(photoList.size());

        for (var item : photoList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий Entity в DTO.
     *
     * @param photo объект, который подлежит преобразованию.
     * @return преобразованный объект.
     */
    public FileDto toDto(Photo photo) {
        return FileDto.builder()
                .id(photo.getId())
                .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_PHOTO_BY_ID, photo.getId()))
                .build();
    }
}
