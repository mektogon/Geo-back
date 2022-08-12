package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.TypeObject;

import java.util.List;
import java.util.UUID;

@Repository
public interface TypeObjectRepository extends JpaRepository<TypeObject, UUID> {
    List<TypeObject> findAllByName(String name);
}