package com.therapypulse.ingeststatic.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class IngestionConfigLogger {

    private static final Logger log = LoggerFactory.getLogger(IngestionConfigLogger.class);

    private final IngestionProperties properties;

    public IngestionConfigLogger(IngestionProperties properties) {
        this.properties = properties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logConfig() {
        log.info("Ingestion config: chunkSize={}, chunkOverlap={}, embeddingModel={}, resourcesDir={}",
                properties.chunkSize(),
                properties.chunkOverlap(),
                properties.embeddingModel(),
                properties.resourcesDir());
    }
}