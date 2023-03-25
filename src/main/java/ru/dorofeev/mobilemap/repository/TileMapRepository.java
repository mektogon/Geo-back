package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.TileMap;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TileMapRepository extends JpaRepository<TileMap, UUID> {

    void deleteByName(String name);

    Optional<TileMap> getTailMapByName(String name);

    List<TileMap> findAllByNameIsContainingIgnoreCase(String name);

    List<TileMap> findAllByIsMainIsTrue();
}
