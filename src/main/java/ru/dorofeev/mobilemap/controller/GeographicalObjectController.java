package ru.dorofeev.mobilemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.service.interf.GeographicalObjectService;

@RestController
public class GeographicalObjectController {
    GeographicalObjectService geographicalObjectService;

    @Autowired
    public GeographicalObjectController(GeographicalObjectService geographicalObjectService) {
        this.geographicalObjectService = geographicalObjectService;
    }

    @GetMapping("/hello")
    public String geographicalObject() {
        return "Hello GEO!";
    }
}
