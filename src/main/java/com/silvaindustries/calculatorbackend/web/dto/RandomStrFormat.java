package com.silvaindustries.calculatorbackend.web.dto;

import lombok.Getter;

@Getter
public enum RandomStrFormat {

    PLAIN("plain"),
    HTML("html");

    private final String format;
    RandomStrFormat(String format) {

        this.format = format;
    }
}

