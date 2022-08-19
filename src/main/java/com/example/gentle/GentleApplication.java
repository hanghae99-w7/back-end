package com.example.gentle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GentleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GentleApplication.class, args);
    }

}
