package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.TailMap;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TailMapRepository extends JpaRepository<TailMap, UUID> {

    void deleteByName(String name);

    Optional<TailMap> getTailMapByName(String name);

    List<TailMap> findAllByNameIsContainingIgnoreCase(String name);

}
