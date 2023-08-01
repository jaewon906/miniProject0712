package com.project0712;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication()
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Project0712Application {

    public static void main(String[] args) {
        SpringApplication.run(Project0712Application.class, args);
    }

}
