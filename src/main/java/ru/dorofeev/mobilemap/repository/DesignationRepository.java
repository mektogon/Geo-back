package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.Designation;

import java.util.List;
import java.util.UUID;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, UUID> {
    List<Designation> findAllByNameIsContainingIgnoreCase(String name);

    Designation getDesignationsByName(String name);

    void deleteByName(String name);

}
