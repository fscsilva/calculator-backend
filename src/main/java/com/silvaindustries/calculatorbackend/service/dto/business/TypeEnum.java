package com.silvaindustries.calculatorbackend.service.dto.business;

import jakarta.validation.ValidationException;
import lombok.Getter;

@Getter
public enum TypeEnum {

    ADDITION(1),
    SUBTRACTION(2),
    MULTIPLICATION(3),
    DIVISION(4),
    SQRT(5),
    RANDOM_STR(6);

    private final int id;
    TypeEnum(int id) {

        this.id = id;
    }
    public static TypeEnum getTypeById(int id) {
        switch (id) {
            case 1 : return ADDITION;
            case 2 : return SUBTRACTION;
            case 3 : return MULTIPLICATION;
            case 4 : return DIVISION;
            case 5 : return SQRT;
            case 6 : return RANDOM_STR;
        }
        throw new ValidationException("Type id not valid");
    }
}

