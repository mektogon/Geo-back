package ru.dorofeev.mobilemap.model.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.UUID;

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
public class GeographicalObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Transient
    private final String regexLatitude = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    @JsonIgnore
    @Transient
    private final String regexLongitude = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "The field should not be null!")
    @NotBlank(message = "The field should not be empty!")
    private String name;
    @ManyToOne
    private TypeObject type;

    @NotNull(message = "The field should not be null!")
    @NotBlank(message = "The field should not be empty!")
    @Pattern(regexp = regexLatitude, message = "Must match a regular expression!")
    private String latitude;

    @NotNull(message = "The field should not be null!")
    @NotBlank(message = "The field should not be empty!")
    @Pattern(regexp = regexLongitude, message = "Must match a regular expression!")
    private String longitude;

    private String description;

    @ManyToOne
    private Locality locality;

    @ManyToOne
    private Street street;

    private String houseNumber;

    private String note;

    @OneToOne
    private Designation designation;
}
