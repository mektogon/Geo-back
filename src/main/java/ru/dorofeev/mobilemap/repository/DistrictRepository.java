package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.District;

import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID> {
    District findByNameIsIgnoreCase(String name);

    void deleteByNameIsIgnoreCase(String name);
}
