package com.therapypulse.ingeststatic.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DocumentIdsTest {

    @TempDir
    Path resources;

    @Test
    void samePathGivesSameDocId() {
        Path file = resources.resolve("guides/who-guide.pdf");
        assertThat(DocumentIds.docId(resources, file)).isEqualTo(DocumentIds.docId(resources, file));
    }

    @Test
    void differentPathsGiveDifferentDocIds() {
        String first = DocumentIds.docId(resources, resources.resolve("a.pdf"));
        String second = DocumentIds.docId(resources, resources.resolve("b.pdf"));
        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void changedContentKeepsSameDocId() throws Exception {
        Path file = resources.resolve("doc.pdf");
        Files.writeString(file, "version one");
        String before = DocumentIds.docId(resources, file);
        Files.writeString(file, "version two");
        assertThat(DocumentIds.docId(resources, file)).isEqualTo(before);
    }

    @Test
    void sourceUsesForwardSlashes() {
        Path file = resources.resolve("guides").resolve("who-guide.pdf");
        assertThat(DocumentIds.relativeSource(resources, file)).isEqualTo("guides/who-guide.pdf");
    }

    @Test
    void fileOutsideResourcesIsRejected() {
        Path outside = resources.resolve("../other.pdf");
        assertThatThrownBy(() -> DocumentIds.docId(resources, outside))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("outside the resources folder");
    }
}