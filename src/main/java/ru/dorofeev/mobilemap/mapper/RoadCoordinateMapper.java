package ru.dorofeev.mobilemap.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.base.RoadCoordinate;
import ru.dorofeev.mobilemap.model.dto.RoadCoordinateDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoadCoordinateMapper {

    private final ObjectMapper objectMapper;

    /**
     * Метод, преобразующий entity-список в DTO-список
     *
     * @param roadCoordinateList entity-список.
     * @return список dto.
     */
    public List<RoadCoordinateDto> toDtoList(List<RoadCoordinate> roadCoordinateList) {
        if (roadCoordinateList == null) {
            return Collections.emptyList();
        }

        List<RoadCoordinateDto> result = new ArrayList<>(roadCoordinateList.size());

        for (var item : roadCoordinateList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий DTO-список в entity-список
     *
     * @param roadCoordinateDtoList DTO-список.
     * @return список dto.
     */
    public List<RoadCoordinate> toEntityList(List<RoadCoordinateDto> roadCoordinateDtoList) {
        if (roadCoordinateDtoList == null) {
            return Collections.emptyList();
        }

        List<RoadCoordinate> result = new ArrayList<>(roadCoordinateDtoList.size());

        for (var item : roadCoordinateDtoList) {
            result.add(toEntity(item));
        }

        return result;
    }


    /**
     * Метод, преобразующий entity в DTO
     *
     * @param entity преобразуемая сущность.
     * @return результат преобразований.
     */
    public RoadCoordinateDto toDto(RoadCoordinate entity) {
        RoadCoordinateDto result = new RoadCoordinateDto();

        result.setId(entity.getId());
        result.setName(entity.getName());

        try {
            result.setListCoordinate(
                    objectMapper.readValue(entity.getListCoordinate(), new TypeReference<>() {
                    })
            );
        } catch (JsonProcessingException e) {
            throw new GeneralErrorException("Ошибка! Не удалось получить список координат!");
        }

        return result;
    }

    /**
     * Метод, преобразующий DTO в entity
     *
     * @param entity преобразуемая сущность.
     * @return результат преобразований.
     */
    public RoadCoordinate toEntity(RoadCoordinateDto entity) {
        try {
            return RoadCoordinate.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .listCoordinate(objectMapper.writeValueAsString(entity.getListCoordinate()))
                    .build();
        } catch (JsonProcessingException e) {
            throw new GeneralErrorException("Ошибка! Не удалось сохранить список с координатами!");
        }
    }
}
