package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleFileLoaderTest {
    private static final String HEADER = "id;label;source;featureCount;status";

    @TempDir
    Path tempDir;

    private final SampleFileLoader loader = new SampleFileLoader();

    @Test
    void loadsValidSample() throws IOException {
        Path file = writeSamples("S-101;cat;camera-1;128;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(1, result.getSamples().size());
        DataSample sample = result.getSamples().getFirst();
        assertEquals("S-101", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals("camera-1", sample.getSource());
        assertEquals(128, sample.getFeatureCount());
        assertEquals(SampleStatus.READY, sample.getStatus());
    }

    @Test
    void preservesUnicodeText() throws IOException {
        Path file = writeSamples("S-101;кошка;камера-1;128;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(1, result.getSamples().size());
        DataSample sample = result.getSamples().getFirst();
        assertEquals("кошка", sample.getLabel());
        assertEquals("камера-1", sample.getSource());
    }

    @Test
    void keepsSemicolonInsideQuotedField() throws IOException {
        Path file = writeSamples("S-101;\"кошка;серая\";\"camera-1\";128;\"READY\"");

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(1, result.getSamples().size());
        DataSample sample = result.getSamples().getFirst();
        assertEquals("кошка;серая", sample.getLabel());
        assertEquals("camera-1", sample.getSource());
        assertEquals(SampleStatus.READY, sample.getStatus());
    }

    @Test
    void rejectsEmptyQuotedFieldAsFieldError() throws IOException {
        Path file = writeSamples("S-101;\"\";camera-1;128;READY");

        assertRejectedRow(loader.load(file), "label");
    }

    @Test
    void continuesAfterUnclosedQuotedField() throws IOException {
        Path file = writeSamples(
                "S-101;\"cat;camera-1;128;READY",
                "S-102;dog;camera-1;64;RAW");

        LoadResult result = loader.load(file);

        assertError(result, 2, "row");
        assertEquals(1, result.getSamples().size());
        assertEquals("S-102", result.getSamples().getFirst().getId());
    }

    @Test
    void rejectsTextAfterClosingQuote() throws IOException {
        Path file = writeSamples("S-101;\"cat\"extra;camera-1;128;READY");

        assertRejectedRow(loader.load(file), "row");
    }

    @Test
    void reportsEmptyLastFieldAsStatusError() throws IOException {
        Path file = writeSamples("S-101;cat;camera-1;128;");

        assertRejectedRow(loader.load(file), "status");
    }

    @Test
    void rejectsNonNumericFeatureCount() throws IOException {
        Path file = writeSamples("S-101;cat;camera-1;bad;READY");

        assertRejectedRow(loader.load(file), "featureCount");
    }

    @Test
    void rejectsZeroFeatureCount() throws IOException {
        Path file = writeSamples("S-101;cat;camera-1;0;READY");

        assertRejectedRow(loader.load(file), "featureCount");
    }

    @Test
    void rejectsUnknownStatus() throws IOException {
        Path file = writeSamples("S-101;cat;camera-1;128;UNKNOWN");

        assertRejectedRow(loader.load(file), "status");
    }

    @Test
    void rejectsExtraColumn() throws IOException {
        Path file = writeSamples("S-101;cat;camera-1;128;READY;extra");

        assertRejectedRow(loader.load(file), "row");
    }

    @Test
    void keepsValidRowsAndReportsPhysicalLineNumber() throws IOException {
        Path file = writeSamples(
                "S-101;cat;camera-1;128;READY",
                "",
                "S-102;dog;camera-1;bad;RAW",
                "S-103;fox;camera-2;64;PROCESSED");

        LoadResult result = loader.load(file);

        assertEquals(2, result.getSamples().size());
        assertEquals("S-101", result.getSamples().get(0).getId());
        assertEquals("S-103", result.getSamples().get(1).getId());
        assertError(result, 4, "featureCount");
    }

    @Test
    void reportsInvalidHeaderBeforeReadingRecords() throws IOException {
        Path file = tempDir.resolve("wrong-header.txt");
        Files.write(file, List.of("wrong header", "S-101;cat;camera-1;128;READY"), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertError(result, 1, "header");
    }

    @Test
    void preservesCauseWhenFileIsMissing() {
        Path file = tempDir.resolve("missing.txt");

        DataLoadingException error = assertThrows(DataLoadingException.class, () -> loader.load(file));

        assertTrue(error.getMessage().contains(file.toString()));
        assertInstanceOf(NoSuchFileException.class, error.getCause());
    }

    private Path writeSamples(String... rows) throws IOException {
        Path file = tempDir.resolve("samples.txt");
        return Files.writeString(file, HEADER + "\n" + String.join("\n", rows), StandardCharsets.UTF_8);
    }

    private void assertRejectedRow(LoadResult result, String field) {
        assertTrue(result.getSamples().isEmpty());
        assertError(result, 2, field);
    }

    private void assertError(LoadResult result, int lineNumber, String field) {
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().getFirst();
        assertEquals(lineNumber, error.getLineNumber());
        assertEquals(field, error.getField());
        assertTrue(!error.getMessage().isBlank());
    }
}
