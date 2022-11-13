package ru.dorofeev.mobilemap.exception.generalerror;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorGeneralResponseHandler {

    @ExceptionHandler(GeneralErrorException.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ErrorGeneralResponse(status.value(), ex.getMessage()), status);
    }
}
