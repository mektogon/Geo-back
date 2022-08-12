package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dorofeev.mobilemap.model.base.Audio;

import java.util.UUID;

public interface AudioRepository extends JpaRepository<Audio, UUID> {
}
