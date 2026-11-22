package com.therapypulse.ingestdynamic;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class IngestDynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngestDynamicApplication.class, args);
    }


    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("service", "ingest-dynamic", "status", "ok");
    }
}