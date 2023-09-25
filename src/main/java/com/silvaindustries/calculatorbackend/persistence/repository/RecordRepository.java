package com.silvaindustries.calculatorbackend.persistence.repository;

import com.silvaindustries.calculatorbackend.persistence.model.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(path = "record")
public interface RecordRepository extends PagingAndSortingRepository<Record, BigInteger>, CrudRepository<Record, BigInteger>  {
}
