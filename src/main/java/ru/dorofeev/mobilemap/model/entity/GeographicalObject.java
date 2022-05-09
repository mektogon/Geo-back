package ru.dorofeev.mobilemap.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "type_id", "latitude", "longitude"}),
        @UniqueConstraint(columnNames = {"name", "type_id", "latitude", "longitude", "locality_id", "street_id", "houseNumber"}),
        @UniqueConstraint(columnNames = {"name", "type_id", "latitude", "longitude", "description", "locality_id", "street_id", "houseNumber"})
})
public class GeographicalObject{
    @JsonIgnore
    private final String regexLatitude = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    @JsonIgnore
    private final String regexLongitude = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geographical_object_generator")
    @SequenceGenerator(name = "geographical_object_generator", sequenceName = "geographical_object_seq")
    private Long id;

    @NotBlank(message = "The field should not be empty!")
    private String name;

    @ManyToOne
    private TypeObject type;

    @NotBlank(message = "The field should not be empty!")
    @Pattern(regexp = regexLatitude)
    private String latitude;

    @NotBlank(message = "The field should not be empty!")
    @Pattern(regexp = regexLongitude)
    private String longitude;

    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Locality locality;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Street street;

    private String houseNumber;

}
