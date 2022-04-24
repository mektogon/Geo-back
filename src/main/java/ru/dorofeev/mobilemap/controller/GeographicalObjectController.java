package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.service.interf.ObjectService;

@RestController
public class GeographicalObjectController {
    ObjectService geographicalObjectService;

    @Autowired
    public GeographicalObjectController(ObjectService geographicalObjectService) {
        this.geographicalObjectService = geographicalObjectService;
    }

    @GetMapping("/hello")
    public String geographicalObject() {
        return "Hello GEO!";
    }
}
