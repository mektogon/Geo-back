package ru.dorofeev.mobilemap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private String region;

    private String typeLocality;

    private String locality;

    private String street;

    private String district;

    private String houseNumber;

    private String fullAddress;

}
