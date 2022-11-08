package ru.dorofeev.mobilemap.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.model.base.Designation;
import ru.dorofeev.mobilemap.model.dto.DesignationDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Маппер предназначенный для преобразования Entity -> DTO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DesignationMapper {

    @Value("${server.url}")
    private String rootUrl;
    private final String ENDPOINT_GET_DESIGNATION_BY_ID = "/api/v1/designation/";


    /**
     * Метод, преобразующий список Entity в список DTO.
     *
     * @param designationList список, подлежащий преобразованию.
     * @return преобразованный список.
     */
    public List<DesignationDto> toDtoList(List<Designation> designationList) {
        if (designationList == null) {
            return Collections.emptyList();
        }

        List<DesignationDto> result = new ArrayList<>(designationList.size());

        for (var item : designationList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий Entity в DTO.
     *
     * @param designation объект, который подлежит преобразованию.
     * @return преобразованный объект.
     */
    public DesignationDto toDto(Designation designation) {
        return DesignationDto.builder()
                .id(designation.getId())
                .url(String.format("%s%s%s", rootUrl, ENDPOINT_GET_DESIGNATION_BY_ID, designation.getId()))
                .name(designation.getName())
                .build();
    }
}
