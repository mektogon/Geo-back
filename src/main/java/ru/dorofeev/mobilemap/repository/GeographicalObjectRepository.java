package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.GeographicalObject;

import java.util.List;
import java.util.UUID;

@Repository
public interface GeographicalObjectRepository extends JpaRepository<GeographicalObject, UUID> {
    List<GeographicalObject> findAllByNameIsContainingIgnoreCase(String name);
    List<GeographicalObject> findAllByNameIsIgnoreCase(String name);

    void deleteByName(String name);

    @Override
    <S extends GeographicalObject> S save(S entity);
}