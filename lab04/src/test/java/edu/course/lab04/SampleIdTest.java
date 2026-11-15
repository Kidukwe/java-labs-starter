package edu.course.lab04;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SampleIdTest {
    @Test
    void storesDifferentKeysWithSameHashCode() {
        SampleId firstId = new SampleId("Aa", 42);
        SampleId secondId = new SampleId("BB", 42);

        assertEquals(firstId.hashCode(), secondId.hashCode());
        assertNotEquals(firstId, secondId);

        HashMap<SampleId, String> samplesById = new HashMap<>();
        samplesById.put(firstId, "Первое");
        samplesById.put(secondId, "Второе");

        assertEquals(2, samplesById.size());
        assertEquals("Первое", samplesById.get(firstId));
        assertEquals("Второе", samplesById.get(secondId));
    }
    @Test
    void isNotEqualToNull() {
        SampleId id = new SampleId("train", 42);

        assertFalse(id.equals(null));
    }

    @Test
    void isNotEqualToObjectOfAnotherType() {
        SampleId id = new SampleId("train", 42);
        String other = "train";

        assertFalse(id.equals(other));
    }
}
