package com.therapypulse.ingeststatic;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class IngestStaticApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngestStaticApplication.class, args);
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("service", "ingest-static", "status", "ok");
    }
}