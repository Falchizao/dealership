package com.utfpr.concessionaria.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFound ex){

        ErrorMessage error = new ErrorMessage(ex.getMessage(), "Not Found", HttpStatus.NOT_FOUND.value()); //Personalized Message

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}