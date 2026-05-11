package com.barberpoint.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.barberpoint.bff")
public class BFFApplication {

    public static void main(String[] args) {
        SpringApplication.run(BFFApplication.class, args);
    }
}
