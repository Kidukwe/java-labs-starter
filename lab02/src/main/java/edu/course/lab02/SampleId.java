package edu.course.lab02;

public record SampleId(String value) {
    public SampleId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("SampleId value cannot be null or blank.");
        }
    }
}
