package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.RoadCoordinate;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoadCoordinateRepository extends JpaRepository<RoadCoordinate, UUID> {

    List<RoadCoordinate> findByIdIn(List<UUID> id);

    void deleteByName(String name);
}
