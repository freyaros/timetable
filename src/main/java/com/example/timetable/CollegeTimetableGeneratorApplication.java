package com.example.timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the College Timetable Generator.
 * Initialises the Spring Boot application and configures auto-wiring.
 */
@SpringBootApplication
public class CollegeTimetableGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeTimetableGeneratorApplication.class, args);
    }

}
