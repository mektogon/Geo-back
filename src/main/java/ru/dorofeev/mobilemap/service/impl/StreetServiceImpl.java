package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.base.Street;
import ru.dorofeev.mobilemap.repository.StreetRepository;
import ru.dorofeev.mobilemap.service.interf.StreetService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {
    private final StreetRepository streetRepository;

    @Override
    public List<Street> getAll() {
        return streetRepository.findAll();
    }

    @Override
    public Optional<Street> findById(UUID id) {
        return streetRepository.findById(id);
    }

    @Override
    public void save(Street street) {
        streetRepository.save(street);
    }

    @Override
    public void update(Street street) {
        streetRepository.save(street);
    }

    @Override
    public void deleteById(UUID id) {
        streetRepository.deleteById(id);
    }
}
