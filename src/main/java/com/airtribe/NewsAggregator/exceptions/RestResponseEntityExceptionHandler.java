package com.airtribe.NewsAggregator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler(UserPreferenceNotFoundException.class)
    public ErrorMessage handleUserPreferenceNotFound(UserPreferenceNotFoundException ex){
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getStackTrace());
    }

    @ExceptionHandler(NewsNotFoundException.class)
    public ErrorMessage handleNewsNotFound(NewsNotFoundException ex){
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getStackTrace());
    }
}
