package com.mb.application.exception;

public class RecipeAlreadyExistsException extends RuntimeException {
    public RecipeAlreadyExistsException(String message) {
        super(message);
    }
}
