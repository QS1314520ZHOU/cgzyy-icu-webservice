package com.digixmed.icu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IcuApplication {
    public static void main(String[] args) {
        SpringApplication.run(IcuApplication.class, args);
    }
}
