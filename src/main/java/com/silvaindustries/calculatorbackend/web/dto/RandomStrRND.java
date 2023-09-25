package com.silvaindustries.calculatorbackend.web.dto;

import lombok.Getter;

@Getter
public enum RandomStrRND {

    NEW("new"),
    ID_IDENTIFIER("id.identifier"),
    DATE_ISO("date.iso-date");
    private final String rnd;
    RandomStrRND(String rnd) {

        this.rnd = rnd;
    }
}

