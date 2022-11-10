package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.base.TypeObject;

import java.util.List;

public interface TypeObjectService extends AbstractService<TypeObject> {
    TypeObject getByName(String typeName);

    List<TypeObject> getAllByName(String name);
}
