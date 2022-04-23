package ru.dorofeev.mobilemap.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.service.interf.StreetService;

@RestController
public class StreetController {
    StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }
}
