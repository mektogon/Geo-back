package ru.dorofeev.mobilemap.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.base.Manifesto;
import ru.dorofeev.mobilemap.model.dto.ManifestoDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManifestoMapper {

    private final ObjectMapper objectMapper;

    /**
     * Метод, преобразующий entity-список в DTO-список
     *
     * @param manifestoList entity-список.
     * @return список dto.
     */
    public List<ManifestoDto> toDtoList(List<Manifesto> manifestoList) {
        if (manifestoList == null) {
            return Collections.emptyList();
        }

        List<ManifestoDto> result = new ArrayList<>(manifestoList.size());

        for (var item : manifestoList) {
            result.add(toDto(item));
        }

        return result;
    }

    /**
     * Метод, преобразующий DTO-список в entity-список
     *
     * @param list DTO-список.
     * @return список dto.
     */
    public List<Manifesto> toEntityList(List<ManifestoDto> list) {
        if (list == null) {
            return Collections.emptyList();
        }

        List<Manifesto> result = new ArrayList<>(list.size());

        for (var item : list) {
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
    public ManifestoDto toDto(Manifesto entity) {

        if (entity == null) {
            return null;
        }

        ManifestoDto result = new ManifestoDto();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setCreatedDate(entity.getCreatedDate());
        result.setLastUpdate(entity.getLastUpdate());
        result.setVersion(entity.getVersion());

        try {
            result.setMapNameArchiveLink(
                    objectMapper.readValue(entity.getMapNameArchiveLink(), new TypeReference<>() {
                    })
            );
        } catch (JsonProcessingException e) {
            throw new GeneralErrorException("Ошибка! Не удалось получить мапу с данными!");
        }


        return result;
    }

    /**
     * Метод, преобразующий DTO в entity
     *
     * @param entity преобразуемая сущность.
     * @return результат преобразований.
     */
    public Manifesto toEntity(ManifestoDto entity) {

        if (entity == null) {
            return null;
        }

        try {
            return Manifesto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .mapNameArchiveLink(objectMapper.writeValueAsString(entity.getMapNameArchiveLink()))
                    .build();
        } catch (JsonProcessingException e) {
            throw new GeneralErrorException("Ошибка! Не удалось сохранить мапу с данными!");
        }
    }
}
