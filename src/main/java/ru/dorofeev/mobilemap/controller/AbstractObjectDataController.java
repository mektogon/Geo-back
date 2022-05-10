package ru.dorofeev.mobilemap.controller;

import java.util.List;

public interface AbstractObjectDataController<T> extends AbstractController<T>{
    List<T> getById(String name);
}
