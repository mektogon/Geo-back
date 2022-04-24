package ru.dorofeev.mobilemap.model.entity;


import lombok.*;

import javax.persistence.*;


import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locality_generator")
    @SequenceGenerator(name = "locality_generator", sequenceName = "locality_seq")
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Region region;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "locality_street",
            joinColumns = @JoinColumn(name = "locality_id"),
            inverseJoinColumns = @JoinColumn(name = "street_id"))
    private Collection<Street> streets;
}
