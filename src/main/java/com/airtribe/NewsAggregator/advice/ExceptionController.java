package com.airtribe.NewsAggregator.advice;

import com.airtribe.NewsAggregator.exceptions.ErrorMessage;
import com.airtribe.NewsAggregator.exceptions.ResourceNotFoundException;
import com.airtribe.NewsAggregator.exceptions.PreferenceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(PreferenceNotFoundException.class)
    public ErrorMessage handlePreferenceNotFound(PreferenceNotFoundException ex){
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getStackTrace());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleResourceNotFound(ResourceNotFoundException ex){
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getStackTrace());
    }
}
