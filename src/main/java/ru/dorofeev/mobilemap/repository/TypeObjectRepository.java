package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.entity.TypeObject;

@Repository
public interface TypeObjectRepository extends JpaRepository<TypeObject, Long> {
}