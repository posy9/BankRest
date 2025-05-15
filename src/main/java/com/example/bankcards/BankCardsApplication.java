package com.example.bankcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.bankcards.entity")
public class BankCardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankCardsApplication.class, args);
    }
}
