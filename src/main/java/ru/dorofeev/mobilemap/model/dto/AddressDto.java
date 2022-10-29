package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность адреса")
public class AddressDto {

    @Schema(description = "Регион", example = "Алтайский край")
    private String region;

    @Schema(description = "Тип местности", example = "Город")
    private String typeLocality;

    @Schema(description = "Местность", example = "Барнаул")
    private String locality;

    @Schema(description = "Улица", example = "Ленина")
    private String street;

    @Schema(description = "Район", example = "Железнодорожный")
    private String district;

    @Schema(description = "Номер дома", example = "61")
    private String houseNumber;

    @Schema(description = "Полный адрес", example = "Алтайский край, Железнодорожный, Город Барнаул, Ленина, 61")
    private String fullAddress;

}
