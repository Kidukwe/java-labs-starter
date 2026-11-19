package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SampleFileLoader {
    private static final String HEADER = "id;label;source;featureCount;status";
    private static final String[] FIELD_NAMES = HEADER.split(";");

    public LoadResult load(Path path) {
        List<DataSample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (!HEADER.equals(header)) {
                errors.add(new ValidationError(1, "header", "Expected header: " + HEADER));
            } else {
                int lineNumber = 1;
                String line;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (!line.isBlank()) {
                        parseRow(line, lineNumber, samples, errors);
                    }
                }
            }
        } catch (IOException e) {
            throw new DataLoadingException("Cannot read " + path, e);
        }

        return new LoadResult(samples, errors);
    }

    private void parseRow(
            String line, int lineNumber, List<DataSample> samples, List<ValidationError> errors) {
        String[] fields;
        try {
            fields = splitFields(line);
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationError(lineNumber, "row", e.getMessage()));
            return;
        }
        if (fields.length != FIELD_NAMES.length) {
            errors.add(new ValidationError(lineNumber, "row", "Expected " + FIELD_NAMES.length + " fields"));
            return;
        }

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isBlank()) {
                errors.add(new ValidationError(lineNumber, FIELD_NAMES[i], "Field must not be blank"));
                return;
            }
        }

        int featureCount;
        try {
            featureCount = Integer.parseInt(fields[3]);
        } catch (NumberFormatException e) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Expected an integer"));
            return;
        }
        if (featureCount <= 0) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Must be positive"));
            return;
        }

        SampleStatus status;
        try {
            status = SampleStatus.valueOf(fields[4]);
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationError(lineNumber, "status", "Unknown status: " + fields[4]));
            return;
        }

        samples.add(new DataSample(fields[0], fields[1], fields[2], featureCount, status));
    }

    private String[] splitFields(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);
            if (current == '"') {
                if (inQuotes) {
                    inQuotes = false;
                    if (i + 1 < line.length() && line.charAt(i + 1) != ';') {
                        throw new IllegalArgumentException("Expected delimiter after closing quote");
                    }
                } else {
                    if (!field.isEmpty()) {
                        throw new IllegalArgumentException("Quote must start a field");
                    }
                    inQuotes = true;
                }
            } else if (current == ';' && !inQuotes) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(current);
            }
        }

        if (inQuotes) {
            throw new IllegalArgumentException("Unclosed quoted field");
        }
        fields.add(field.toString());
        return fields.toArray(new String[0]);
    }
}
