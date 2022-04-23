package ru.dorofeev.mobilemap.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.service.interf.TypeObjectService;

@RestController
public class TypeObjectController {
    TypeObjectService typeObjectService;

    public TypeObjectController(TypeObjectService typeObjectService) {
        this.typeObjectService = typeObjectService;
    }
}
