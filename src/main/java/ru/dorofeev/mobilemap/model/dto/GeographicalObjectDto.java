package ru.dorofeev.mobilemap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeographicalObjectDto {
    private UUID id;

    private String name;

    private String type;

    private String latitude;

    private String longitude;

    private String description;

    private String note;

    private AddressDto addressDto;
}
