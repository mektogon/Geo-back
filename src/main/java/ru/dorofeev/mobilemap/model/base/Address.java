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
        @UniqueConstraint(columnNames = {"locality_id", "street_id", "houseNumber"})
})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    private Locality locality;

    @ManyToOne
    private Street street;

    private String houseNumber;
}
