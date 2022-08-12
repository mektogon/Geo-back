package ru.dorofeev.mobilemap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dorofeev.mobilemap.model.base.Video;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
}
