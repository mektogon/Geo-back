package ru.dorofeev.mobilemap.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dorofeev.mobilemap.model.base.Manifesto;

import java.util.UUID;

public interface ManifestoRepository extends JpaRepository<Manifesto, UUID> {
    Manifesto getByName(String name);

    Manifesto findTopBy(Sort sort);
}
