package ru.dorofeev.mobilemap.exception.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Violation {
    private final String className;
    private final String fieldName;
    private final String message;
}
