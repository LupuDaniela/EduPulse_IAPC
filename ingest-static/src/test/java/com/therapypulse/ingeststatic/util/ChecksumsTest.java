package com.therapypulse.ingeststatic.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ChecksumsTest {

    @TempDir
    Path dir;

    @Test
    void sameContentGivesSameChecksum() throws Exception {
        Path a = Files.writeString(dir.resolve("a.pdf"), "same content");
        Path b = Files.writeString(dir.resolve("b.pdf"), "same content");
        assertThat(Checksums.sha256(a)).isEqualTo(Checksums.sha256(b));
    }

    @Test
    void differentContentGivesDifferentChecksum() throws Exception {
        Path file = dir.resolve("doc.pdf");
        Files.writeString(file, "version one");
        String before = Checksums.sha256(file);
        Files.writeString(file, "version two");
        assertThat(Checksums.sha256(file)).isNotEqualTo(before);
    }

    @Test
    void checksumIsSha256Hex() throws Exception {
        Path file = Files.writeString(dir.resolve("doc.pdf"), "abc");
        assertThat(Checksums.sha256(file))
                .isEqualTo("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
    }

    @Test
    void missingFileThrowsClearError() {
        Path missing = dir.resolve("missing.pdf");
        assertThatThrownBy(() -> Checksums.sha256(missing))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("File not found");
    }
}