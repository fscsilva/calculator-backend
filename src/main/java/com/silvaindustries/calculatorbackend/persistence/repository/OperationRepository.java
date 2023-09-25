package com.silvaindustries.calculatorbackend.persistence.repository;

import com.silvaindustries.calculatorbackend.persistence.model.Operation;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@Hidden
public interface OperationRepository extends CrudRepository<Operation, BigInteger> {
}
