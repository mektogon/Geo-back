package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dorofeev.mobilemap.model.base.TypeLocality;

import java.util.UUID;

public interface TypeLocalityRepository extends JpaRepository<TypeLocality, UUID> {
}
