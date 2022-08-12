package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dorofeev.mobilemap.model.base.District;

import java.util.UUID;

public interface DistrictRepository extends JpaRepository<District, UUID> {
}
