package ru.dorofeev.mobilemap.service.impl;

import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.entity.Street;
import ru.dorofeev.mobilemap.repository.StreetRepository;
import ru.dorofeev.mobilemap.service.interf.RegionService;
import ru.dorofeev.mobilemap.service.interf.StreetService;

import java.util.List;
import java.util.Optional;

@Service
public class StreetImpl implements StreetService {
    StreetRepository streetRepository;

    public StreetImpl(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }

    @Override
    public List<Street> findALl() {
        return null;
    }

    @Override
    public Optional<Street> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Street> save(Street street) {
        return Optional.empty();
    }

    @Override
    public void delete(Street street) {

    }

    @Override
    public void deleteById(Street street) {

    }
}
