package ru.dorofeev.mobilemap.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "The field should not be null!")
    @NotBlank(message = "The field should not be empty!")
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private List<GeographicalObject> geographicalObjectList;
}
