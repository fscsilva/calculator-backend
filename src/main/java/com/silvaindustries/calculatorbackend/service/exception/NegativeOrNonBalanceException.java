package com.silvaindustries.calculatorbackend.service.exception;

import jakarta.validation.ValidationException;

public class NegativeOrNonBalanceException extends ValidationException {

    public NegativeOrNonBalanceException(String message) {
        super(message);
    }
}
