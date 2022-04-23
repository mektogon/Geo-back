package ru.dorofeev.mobilemap.service.interf;

import java.util.Optional;

public interface AbstractGeographicalObjectDataService<T> extends AbstractService<T>{
    Optional<T> findAllByName(String name);
}
