package edu.course.lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleRegistryTest {
    @Test
    void findsSampleUsingEqualKey() {
        SampleRegistry registry = new SampleRegistry();
        SampleId storedId = new SampleId("train", 42);
        SampleId lookupId = new SampleId("train", 42);
        DataSample sample = new DataSample("cat");

        registry.put(storedId, sample);

        assertNotSame(storedId, lookupId);
        assertEquals(storedId, lookupId);
        assertSame(sample, registry.get(lookupId));
    }

    @Test
    void replacesSampleWithoutIncreasingSize() {
        SampleRegistry registry = new SampleRegistry();
        SampleId firstId = new SampleId("train", 42);
        SampleId secondId = new SampleId("train", 42);
        DataSample original = new DataSample("cat");
        DataSample replacement = new DataSample("dog");

        registry.put(firstId, original);
        assertEquals(1, registry.size());

        registry.put(secondId, replacement);

        assertEquals(1, registry.size());
        assertSame(replacement, registry.get(firstId));
    }

    @Test
    void removesSampleUsingEqualKey() {
        SampleRegistry registry = new SampleRegistry();
        SampleId storedId = new SampleId("train", 42);
        SampleId removalId = new SampleId("train", 42);
        DataSample sample = new DataSample("cat");

        registry.put(storedId, sample);

        assertTrue(registry.containsKey(removalId));
        assertSame(sample, registry.remove(removalId));
        assertFalse(registry.containsKey(storedId));
        assertNull(registry.get(storedId));
        assertEquals(0, registry.size());
    }
}