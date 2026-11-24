package com.therapypulse.ingeststatic.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IngestionPropertiesTest {

    @Test
    void validConfigIsAccepted() {
        IngestionProperties properties = new IngestionProperties(800, 100, "nomic-embed-text", "./resources");
        assertThat(properties.chunkOverlap()).isLessThan(properties.chunkSize());
    }

    @Test
    void overlapBiggerThanChunkIsRejected() {
        assertThatThrownBy(() -> new IngestionProperties(100, 100, "nomic-embed-text", "./resources"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("chunk-overlap");
    }
}