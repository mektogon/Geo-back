package ru.dorofeev.mobilemap.service.interf;

import java.util.List;
import java.util.UUID;

public interface AbstractDataObjectService<T> extends AbstractService<T> {
    List<T> findAllByName(String name);

    UUID saveAndReturnId(T t);
}
