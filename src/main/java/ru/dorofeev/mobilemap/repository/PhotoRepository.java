package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.Photo;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    List<Photo> findAllPhotoByGeographicalObjectId(UUID id);

    @Query("SELECT p FROM Photo p WHERE  p.id = :id and p.urlPhotoPreview IS NOT NULL")
    Photo getPhotoPreviewById(UUID id);

    @Query("SELECT p FROM Photo p WHERE  p.geographicalObject.id = :id and p.urlPhotoPreview IS NOT NULL")
    Photo getPhotoPreviewByGeographicalObjectId(UUID id);
}
