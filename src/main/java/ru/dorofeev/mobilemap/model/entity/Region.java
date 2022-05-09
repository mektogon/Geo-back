package ru.dorofeev.mobilemap.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_generator")
    @SequenceGenerator(name = "region_generator", sequenceName = "region_seq")
    private Long id;

    @NotBlank(message = "The field should not be empty!")
    @Column(unique = true)
    private String name;
}
