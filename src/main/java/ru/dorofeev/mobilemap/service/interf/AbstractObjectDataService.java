package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.entity.GeographicalObject;

import java.util.List;

public interface AbstractObjectDataService<T> extends AbstractService<T>{
    List<T> findAllByName(String name);
}
