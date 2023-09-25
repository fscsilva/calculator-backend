package com.silvaindustries.calculatorbackend.service.exception;

import jakarta.validation.ValidationException;

public class UserException extends ValidationException {

    public UserException(String message) {
        super(message);
    }
}
