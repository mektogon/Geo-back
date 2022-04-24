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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    @SequenceGenerator(name = "address_generator", sequenceName = "address_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Locality locality;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Street street;

    private String houseNumber;

}
