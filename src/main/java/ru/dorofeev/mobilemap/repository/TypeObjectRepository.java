package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.entity.TypeObject;

import java.util.List;

@Repository
public interface TypeObjectRepository extends JpaRepository<TypeObject, Long> {
    List<TypeObject> findAllByName(String name);
}