package com.mb.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = { ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
                request.getDescription(false)),HttpStatus.NOT_FOUND) ;
    }

    @ExceptionHandler(value = { RecipeAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> recipeAlreadyExistsException(RecipeAlreadyExistsException ex, WebRequest request) {

        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
                request.getDescription(false)),HttpStatus.BAD_REQUEST) ;
    }
}
