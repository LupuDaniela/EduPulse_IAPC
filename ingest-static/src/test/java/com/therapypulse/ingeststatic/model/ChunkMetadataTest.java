package com.therapypulse.ingeststatic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ChunkMetadataTest {

    private static final String CHECKSUM = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";

    @Test
    void mapRoundTripKeepsAllFields() {
        ChunkMetadata metadata = new ChunkMetadata("doc-1", 1, "guides/who.pdf", 3, 7, CHECKSUM);
        Map<String, Object> map = metadata.toMap();
        assertThat(map).containsKeys("doc_id", "version", "source", "page", "chunk_index", "checksum");
        assertThat(ChunkMetadata.fromMap(map)).isEqualTo(metadata);
    }

    @Test
    void fromMapAcceptsNumbersOfAnyType() {
        Map<String, Object> map = new HashMap<>(new ChunkMetadata("doc-1", 2, "a.pdf", 1, 0, CHECKSUM).toMap());
        map.put("version", 2L);
        map.put("page", 1.0);
        map.put("chunk_index", "0");
        assertThat(ChunkMetadata.fromMap(map).version()).isEqualTo(2);
    }

    @Test
    void missingFieldThrowsClearError() {
        Map<String, Object> map = new HashMap<>(new ChunkMetadata("doc-1", 1, "a.pdf", 1, 0, CHECKSUM).toMap());
        map.remove("checksum");
        assertThatThrownBy(() -> ChunkMetadata.fromMap(map))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("checksum");
    }

    @Test
    void invalidValuesAreRejected() {
        assertThatThrownBy(() -> new ChunkMetadata("doc-1", 0, "a.pdf", 1, 0, CHECKSUM))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ChunkMetadata("doc-1", 1, "a.pdf", 1, 0, "not-a-checksum"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}