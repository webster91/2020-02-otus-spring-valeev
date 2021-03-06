package ru.otus.valeev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@EnableCircuitBreaker
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
