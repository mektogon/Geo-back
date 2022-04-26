package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Street;
import ru.dorofeev.mobilemap.repository.StreetRepository;
import ru.dorofeev.mobilemap.service.interf.StreetService;

import java.util.List;
import java.util.Optional;

@Service
public class StreetImpl implements StreetService {
    private final StreetRepository streetRepository;

    public StreetImpl(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }

    @Override
    public List<Street> findALl() {
        return streetRepository.findAll();
    }

    @Override
    public Optional<Street> findById(Long id) {
        return streetRepository.findById(id);
    }

    @Override
    public Optional<Street> save(Street street) {
        return Optional.of(streetRepository.save(street));
    }

    @Override
    public void update(Street street) {
        streetRepository.save(street);
    }

    @Override
    public void deleteById(Long id) {
        streetRepository.deleteById(id);
    }
}
