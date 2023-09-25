package com.silvaindustries.calculatorbackend.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigInteger;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private BigInteger typeId;
    @Column(name = "type_name", nullable = false)
    private String typeName;

}
