package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.TypeObject;

import java.util.UUID;

@Repository
public interface TypeObjectRepository extends JpaRepository<TypeObject, UUID> {
    TypeObject findByNameIsIgnoreCase(String name);

    @Override
    <S extends TypeObject> S save(S entity);

    void deleteByNameIsIgnoreCase(String name);
}