package com.example.people.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleMessageNotReadableException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Not a valid request format", new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}