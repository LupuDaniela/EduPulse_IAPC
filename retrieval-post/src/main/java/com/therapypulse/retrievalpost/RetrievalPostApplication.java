package com.therapypulse.retrievalpost;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RetrievalPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrievalPostApplication.class, args);
    }


    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("service", "retrieval-post", "status", "ok");
    }
}