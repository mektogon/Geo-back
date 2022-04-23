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
public class GeographicalObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geographical_object_generator")
    @SequenceGenerator(name = "geographical_object_generator", sequenceName = "geographical_object_seq")
    private Long id;


    @ManyToOne
    private TypeObject type;

    private String latitude;

    private String longitude;

    private String description;

    @ManyToOne
    private Locality locality;

    @ManyToOne
    private Street street;

    private String houseNumber;

}
