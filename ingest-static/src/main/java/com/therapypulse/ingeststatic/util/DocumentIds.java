package com.therapypulse.ingeststatic.util;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.UUID;

public final class DocumentIds {

    private DocumentIds() {
    }

    public static String relativeSource(Path resourcesDir, Path file) {
        Path base = resourcesDir.toAbsolutePath().normalize();
        Path target = file.toAbsolutePath().normalize();
        if (!target.startsWith(base)) {
            throw new IllegalArgumentException("File " + file + " is outside the resources folder " + resourcesDir);
        }
        return base.relativize(target).toString().replace('\\', '/');
    }

    public static String docId(Path resourcesDir, Path file) {
        String relative = relativeSource(resourcesDir, file);
        return UUID.nameUUIDFromBytes(relative.getBytes(StandardCharsets.UTF_8)).toString();
    }
}