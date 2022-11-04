package ru.dorofeev.mobilemap.model.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
        @UniqueConstraint(columnNames = {"name", "type_id", "latitude", "longitude", "description"}),
        @UniqueConstraint(columnNames = {"name", "type_id", "latitude", "longitude", "description", "note"})
})
public class GeographicalObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Transient
    // [-90; 90]
    private final String regexLatitude = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    @JsonIgnore
    @Transient
    // [-180; 180]
    private final String regexLongitude = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "Поле не должно быть равно null!")
    @NotBlank(message = "Поле не должно быть пустое!")
    private String name;

    @ManyToOne(optional = false)
    private TypeObject type;

    @NotNull(message = "Поле не должно быть равно null!")
    @NotBlank(message = "Поле не должно быть пустое!")
    @Pattern(regexp = regexLatitude, message = "Широта должна быть в диапазоне [-90; 90]")
    private String latitude;

    @NotNull(message = "Поле не должно быть равно null!")
    @NotBlank(message = "Поле не должно быть пустое!")
    @Pattern(regexp = regexLongitude, message = "Долгота должна быть в диапазоне [-180; 180]")
    private String longitude;

    @Size(max = 1000, message = "Количество символов не должно превышать 1000")
    private String description;

    private String note;

    private Boolean isPlaying;

    @ManyToOne
    private Address address;

    @ManyToOne(optional = false)
    private Designation designation;
}
