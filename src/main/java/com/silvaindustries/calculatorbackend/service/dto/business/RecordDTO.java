package com.silvaindustries.calculatorbackend.service.dto.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.silvaindustries.calculatorbackend.persistence.model.Operation;
import com.silvaindustries.calculatorbackend.persistence.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Jacksonized
@Builder
@Getter
public class RecordDTO {

    @JsonProperty
    private final BigInteger recordId;
    @JsonProperty
    private final UserDTO user;
    @JsonProperty
    private final OperationDTO operation;
    @JsonProperty
    private final Double amount;
    @JsonProperty
    private final Double userBalance;
    @JsonProperty
    private final String operationResponse;
    @JsonProperty
    private final LocalDateTime date;

}
