package edu.leicester.co2103.part1s2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Part1s2Application {

    public static void main(String[] args) {
        SpringApplication.run(Part1s2Application.class, args);
    }

}
