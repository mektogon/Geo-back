package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.TypeLocality;

import java.util.List;
import java.util.UUID;

@Repository
public interface TypeLocalityRepository extends JpaRepository<TypeLocality, UUID> {
    List<TypeLocality> findAllByNameIsContainingIgnoreCase(String name);

    TypeLocality findByName(String name);

    void deleteByName(String name);
}
