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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"locality_id", "street_id", "houseNumber"})
})
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
