package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.base.TypeLocality;

public interface TypeLocalityService extends AbstractService<TypeLocality> {
    TypeLocality getByName(String name);
}
