package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dorofeev.mobilemap.model.base.Designation;

import java.util.UUID;

public interface DesignationRepository extends JpaRepository<Designation, UUID> {
}
