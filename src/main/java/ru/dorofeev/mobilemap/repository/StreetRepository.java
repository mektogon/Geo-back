package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.Street;

import java.util.UUID;

@Repository
public interface StreetRepository extends JpaRepository<Street, UUID> {
    Street findByNameIsIgnoreCase(String name);

    void deleteByNameIsIgnoreCase(String name);
}