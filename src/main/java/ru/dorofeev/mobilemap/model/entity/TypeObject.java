package ru.dorofeev.mobilemap.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_object_generator")
    @SequenceGenerator(name = "type_object_generator", sequenceName = "type_object_seq")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<GeographicalObject> geographicalObjectList;
}
