package ru.dorofeev.mobilemap.model.base;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "region_id"})
})
public class Locality implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "The field should not be null!")
    @NotBlank(message = "The field should not be empty!")
    private String name;

    @ManyToOne
    private Region region;

    @ManyToMany
    @JoinTable(
            name = "locality_street",
            joinColumns = @JoinColumn(name = "locality_id"),
            inverseJoinColumns = @JoinColumn(name = "street_id"))
    private Collection<Street> streets;
}
