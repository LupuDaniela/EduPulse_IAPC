package com.therapypulse.ingeststatic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.ingest")
public record IngestionProperties(
        int chunkSize,
        int chunkOverlap,
        String embeddingModel,
        String resourcesDir) {

    public IngestionProperties {
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("app.ingest.chunk-size must be greater than 0");
        }
        if (chunkOverlap < 0 || chunkOverlap >= chunkSize) {
            throw new IllegalArgumentException("app.ingest.chunk-overlap must be between 0 and chunk-size - 1");
        }
        if (embeddingModel == null || embeddingModel.isBlank()) {
            throw new IllegalArgumentException("app.ingest.embedding-model must be set");
        }
        if (resourcesDir == null || resourcesDir.isBlank()) {
            throw new IllegalArgumentException("app.ingest.resources-dir must be set");
        }
    }
}