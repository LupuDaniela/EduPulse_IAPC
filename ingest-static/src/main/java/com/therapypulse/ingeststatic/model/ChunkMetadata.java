package com.therapypulse.ingeststatic.model;

import java.util.Map;
import java.util.regex.Pattern;

public record ChunkMetadata(
        String docId,
        int version,
        String source,
        int page,
        int chunkIndex,
        String checksum) {

    public static final String DOC_ID = "doc_id";
    public static final String VERSION = "version";
    public static final String SOURCE = "source";
    public static final String PAGE = "page";
    public static final String CHUNK_INDEX = "chunk_index";
    public static final String CHECKSUM = "checksum";

    private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");

    public ChunkMetadata {
        if (docId == null || docId.isBlank()) {
            throw new IllegalArgumentException("doc_id must not be empty");
        }
        if (version < 1) {
            throw new IllegalArgumentException("version must be 1 or higher");
        }
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("source must not be empty");
        }
        if (page < 1) {
            throw new IllegalArgumentException("page must be 1 or higher");
        }
        if (chunkIndex < 0) {
            throw new IllegalArgumentException("chunk_index must be 0 or higher");
        }
        if (checksum == null || !SHA256.matcher(checksum).matches()) {
            throw new IllegalArgumentException("checksum must be a SHA-256 hex string");
        }
    }

    public Map<String, Object> toMap() {
        return Map.of(
                DOC_ID, docId,
                VERSION, version,
                SOURCE, source,
                PAGE, page,
                CHUNK_INDEX, chunkIndex,
                CHECKSUM, checksum);
    }

    public static ChunkMetadata fromMap(Map<String, ?> map) {
        return new ChunkMetadata(
                required(map, DOC_ID).toString(),
                toInt(required(map, VERSION), VERSION),
                required(map, SOURCE).toString(),
                toInt(required(map, PAGE), PAGE),
                toInt(required(map, CHUNK_INDEX), CHUNK_INDEX),
                required(map, CHECKSUM).toString());
    }

    private static Object required(Map<String, ?> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing metadata field: " + key);
        }
        return value;
    }

    private static int toInt(Object value, String key) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Metadata field " + key + " is not a number: " + value);
        }
    }
}