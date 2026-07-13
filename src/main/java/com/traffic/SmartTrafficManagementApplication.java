package com.traffic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot Application entry point
 * Smart Traffic Management System
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.traffic"})
public class SmartTrafficManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartTrafficManagementApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Smart Traffic Management System Started");
        System.out.println("Server running on: http://localhost:8080");
        System.out.println("=======================================");
    }

}
