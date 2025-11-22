package com.universidad.inscripcion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "repositories")
@ComponentScan(basePackages = {"com.universidad.inscripcion", "repositories", "services", "controllers"})
public class SistemaInscripcionUniversitariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaInscripcionUniversitariaApplication.class, args);
    }
}