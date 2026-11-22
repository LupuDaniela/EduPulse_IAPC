package com.therapypulse.retrievalpre;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RetrievalPreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrievalPreApplication.class, args);
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("service", "retrieval-pre", "status", "ok");
    }
}