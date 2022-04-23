package ru.dorofeev.mobilemap.model.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TypeObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_object_generator")
    @SequenceGenerator(name = "type_object_generator", sequenceName = "type_object_seq")
    private Long id;

    private String name;
}
