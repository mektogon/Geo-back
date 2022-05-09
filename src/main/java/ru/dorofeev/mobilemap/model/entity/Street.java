package ru.dorofeev.mobilemap.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "street_generator")
    @SequenceGenerator(name = "street_generator", sequenceName = "street_seq")
    private Long id;

    @NotBlank(message = "The field should not be empty!")
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "streets")
    private Collection<Locality> localities;
}
