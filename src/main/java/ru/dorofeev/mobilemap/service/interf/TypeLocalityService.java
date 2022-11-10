package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.base.TypeLocality;

import java.util.List;

public interface TypeLocalityService extends AbstractService<TypeLocality> {
    TypeLocality getByName(String name);

    List<TypeLocality> getAllByName(String name);
}
