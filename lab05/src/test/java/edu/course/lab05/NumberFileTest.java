package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberFileTest {
    @TempDir
    Path tempDir;

    @Test
    void readsAllLinesFromUtf8File() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        List<String> expected = List.of("10", "bad", "20");
        Files.write(file, expected, StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        assertEquals(expected, lines);
    }

    @Test
    void rejectsNonNumericLineWhenParsing() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        assertThrows(NumberFormatException.class, () -> {
            for (String line : lines) {
                Integer.parseInt(line);
            }
        });
    }
}
