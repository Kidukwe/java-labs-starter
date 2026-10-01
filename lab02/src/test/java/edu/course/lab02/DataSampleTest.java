package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DataSampleTest {
    @Test
    void createsCorrectDataSampleAndReadsFields() {
        DataSample sample = new DataSample("s1", "cat", SampleStatus.RAW, new double[]{1.0, 3.0});
        assertEquals("s1", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals(SampleStatus.RAW, sample.getStatus());
        assertArrayEquals(new double[]{1.0, 3.0}, sample.getFeatures());
    }

    @Test
    void correctBehavior() {
        DataSample sample = new DataSample("s2", "dog", SampleStatus.RAW, new double[]{1.0, 3.0, 5.0});
        assertFalse(sample.isReady());

        sample.changeStatus(SampleStatus.READY);
        assertTrue(sample.isReady());
        assertEquals(SampleStatus.READY, sample.getStatus());
        assertEquals(3.0, sample.averageFeatures(), 1e-9);
    }

    @Test
    void defensiveCopying() {
        double[] original = {1.0, 2.0};
        DataSample sample = new DataSample("s1", "cat", SampleStatus.RAW, original);
        original[0] = 999.0;
        assertEquals(1.0, sample.getFeatures()[0]);

        double[] leaked = sample.getFeatures();
        leaked[0] = 888.0;
        assertEquals(1.0, sample.getFeatures()[0]);
    }

    @Test
    void mistakeChecking() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample(null, "dog", SampleStatus.RAW, new double[]{1.0, 3.0, 5.0}));
        assertThrows(IllegalArgumentException.class, () -> new DataSample(" ", "dog", SampleStatus.RAW, new double[]{1.0, 3.0, 5.0}));
        
        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", null, SampleStatus.RAW, new double[]{1.0}));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "   ", SampleStatus.RAW, new double[]{1.0}));

        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "dog", null, new double[]{1.0}));

        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "dog", SampleStatus.RAW, null));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "dog", SampleStatus.RAW, new double[]{}));

        DataSample sample = new DataSample("s1", "dog", SampleStatus.RAW, new double[]{1.0});
        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
    }

    @Test
    void sampleIdValidatesCorrectly() {
        SampleId id = new SampleId("id-123");
        assertEquals("id-123", id.value());

        assertThrows(IllegalArgumentException.class, () -> new SampleId(null));
        assertThrows(IllegalArgumentException.class, () -> new SampleId("   "));
    }

}
