package ru.dorofeev.mobilemap.exception.address;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorAddressResponseHandler {

    @ExceptionHandler(AddressFieldNotFoundException.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ErrorAddressResponse(status.value(), ex.getMessage()), status);
    }
}
