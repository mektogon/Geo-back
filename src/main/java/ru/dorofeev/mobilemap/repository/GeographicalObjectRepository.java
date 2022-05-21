package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;

import java.util.List;

@Repository
public interface GeographicalObjectRepository extends JpaRepository<GeographicalObject, Long> {
    List<GeographicalObject> findAllByName(String name);
}