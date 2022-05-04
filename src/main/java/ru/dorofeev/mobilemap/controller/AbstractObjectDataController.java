package ru.dorofeev.mobilemap.controller;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AbstractObjectDataController<T> extends AbstractController<T>{
    List<T> getById(String name);
}
