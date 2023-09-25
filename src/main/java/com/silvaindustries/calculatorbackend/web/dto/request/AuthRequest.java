package com.silvaindustries.calculatorbackend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
public class AuthRequest {

    @JsonProperty
    private final String username;
    @JsonProperty
    private final String password;

}

