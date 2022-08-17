package ru.dorofeev.mobilemap.service.interf;

import ru.dorofeev.mobilemap.model.base.TypeObject;

public interface TypeObjectService extends AbstractDataObjectService<TypeObject> {
    TypeObject getTypeObjectByName(String typeName);
}
