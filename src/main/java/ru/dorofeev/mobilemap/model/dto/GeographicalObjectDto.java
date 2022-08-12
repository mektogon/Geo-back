package ru.dorofeev.mobilemap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeographicalObjectDto {
    private String name;

    private String type;

    private String latitude;

    private String longitude;

    private String description;

    private String note;

    private String designation;

    private AddressDto addressDto;

    private List<String> photoList;

    private List<String> videoList;

    private List<String> audioList;

}
