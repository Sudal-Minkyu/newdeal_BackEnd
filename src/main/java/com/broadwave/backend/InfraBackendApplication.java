package com.broadwave.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class InfraBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfraBackendApplication.class, args);
    }
}
