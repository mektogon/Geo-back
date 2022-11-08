package ru.dorofeev.mobilemap.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.mapper.DesignationMapper;
import ru.dorofeev.mobilemap.model.dto.DesignationDto;
import ru.dorofeev.mobilemap.service.dto.interf.DesignationDtoService;
import ru.dorofeev.mobilemap.service.interf.DesignationService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DesignationDtoServiceImpl implements DesignationDtoService {

    private final DesignationService designationService;
    private final DesignationMapper designationMapper;


    @Override
    public List<DesignationDto> getAllWithPhoto() {
        log.info("IN getAllWithPhoto() - Преобразование List<Entity> в List<DTO>.");

        return designationMapper.toDtoList(designationService.getAll());
    }

}
