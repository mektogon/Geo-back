package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.base.TypeObject;

public interface TypeObjectService extends AbstractService<TypeObject> {
    TypeObject getByName(String typeName);
}
