package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dorofeev.mobilemap.model.base.Audio;

import java.util.List;
import java.util.UUID;

@Repository
public interface AudioRepository extends JpaRepository<Audio, UUID> {
    List<Audio> findAllAudioByGeographicalObjectId(UUID id);

    List<Audio> findAllAudioByNameIsIgnoreCase(String name);
}
