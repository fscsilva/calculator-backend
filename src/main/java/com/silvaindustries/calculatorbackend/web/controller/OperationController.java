package com.silvaindustries.calculatorbackend.web.controller;

import com.silvaindustries.calculatorbackend.service.dto.business.RecordDTO;
import com.silvaindustries.calculatorbackend.service.dto.business.UserDTO;
import com.silvaindustries.calculatorbackend.service.v1.OperationService;
import com.silvaindustries.calculatorbackend.service.v2.OperationServiceV2;
import com.silvaindustries.calculatorbackend.web.api.OperationAPI;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrFormat;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrRND;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperationController implements OperationAPI {

    private final OperationService operationService;
    private final OperationServiceV2 operationServiceV2;
    
    @Override
    public ResponseEntity<RecordDTO> addition(String quantity) {
        return ResponseEntity.ok(operationService.addition(quantity).join());
    }

    @Override
    public ResponseEntity<RecordDTO> subtraction(String quantity) {
        return ResponseEntity.ok(operationService.subtraction(quantity).join());
    }

    @Override
    public ResponseEntity<RecordDTO> multiplication(String quantity) {
        return ResponseEntity.ok(operationService.multiplication(quantity).join());
    }

    @Override
    public ResponseEntity<RecordDTO> division(String quantity) {
        return ResponseEntity.ok(operationService.division(quantity).join());
    }

    @Override
    public ResponseEntity<RecordDTO> squareRoot() {
        return ResponseEntity.ok(operationService.sqrt().join());
    }

    @Override
    public ResponseEntity<RecordDTO> randomString(Integer num, Integer len, Boolean digits,
        Boolean upperAlpha, Boolean lowerAlpha, Boolean unique, RandomStrFormat format, RandomStrRND rnd) {
        return ResponseEntity.ok(
            operationService.randomStr(num, len, digits, upperAlpha, lowerAlpha, unique, format, rnd).join());
    }

    @Override
    public ResponseEntity<RecordDTO> randomStringV2(Integer num, Integer len, Boolean digits,
        Boolean upperAlpha, Boolean lowerAlpha, Boolean unique, RandomStrFormat format, RandomStrRND rnd) {
        var optionalUpper = Optional.ofNullable(upperAlpha);
        var optionalLower = Optional.ofNullable(lowerAlpha);
        if (optionalUpper.equals(optionalLower) && optionalUpper.equals(Optional.of(false))) {
            throw new ValidationException("LowerAlpha and UpperAlpha can't be false");
        }
        return ResponseEntity.ok(
            operationServiceV2.randomStrV2(num, len, digits, upperAlpha, lowerAlpha, unique, format, rnd).join());
    }
}
