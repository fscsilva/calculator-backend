package com.silvaindustries.calculatorbackend.service.dto.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

@Jacksonized
@Builder
@Getter
public class OperationDTO {

    @JsonProperty
    @NotBlank
    private final BigInteger operationId;
    @JsonProperty
    private final TypeEnum type;
    @JsonProperty
    private final Double cost;
}
