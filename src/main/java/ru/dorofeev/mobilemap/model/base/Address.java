package ru.dorofeev.mobilemap.model.base;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
        @UniqueConstraint(columnNames = {"region_id", "type_locality_id", "district_id", "locality_id", "street_id", "houseNumber"}),
        @UniqueConstraint(columnNames = {"region_id", "type_locality_id", "locality_id", "street_id", "houseNumber"}),
        @UniqueConstraint(columnNames = {"region_id", "type_locality_id", "district_id", "locality_id"}),
        @UniqueConstraint(columnNames = {"region_id", "type_locality_id", "locality_id"})
})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    private Region region;

    @ManyToOne(optional = false)
    private TypeLocality typeLocality;

    @ManyToOne(optional = false)
    private Locality locality;

    @ManyToOne
    private Street street;

    @ManyToOne
    private District district;

    private String houseNumber;
}
