package com.silvaindustries.calculatorbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("com.silvaindustries.calculatorbackend.persistence.model")
@EnableJpaRepositories(basePackages = "com.silvaindustries.calculatorbackend.persistence.repository")
@EnableAsync
public class CalculatorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorBackendApplication.class, args);
    }

}
