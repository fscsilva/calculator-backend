package com.silvaindustries.calculatorbackend.service.exception;

import jakarta.validation.ValidationException;

public class UserNotFoundException extends ValidationException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
