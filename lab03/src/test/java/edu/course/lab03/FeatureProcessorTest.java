package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FeatureProcessorTest {
    @Test
    void supportsDifferentStrategies() {
        FeatureProcessor minMaxProcessor = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor meanCenteringProcessor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] values = {2.0, 4.0, 6.0};

        double[] minMaxResult = minMaxProcessor.process(values);
        double[] meanCenteringResult = meanCenteringProcessor.process(values);

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, minMaxResult, 1e-9);
        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, meanCenteringResult, 1e-9);
    }

    @Test
    void preservesOriginalArrayWithMinMaxStrategy() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] values = {2.0, 4.0, 6.0};

        double[] result = processor.process(values);

        assertArrayEquals(new double[]{2.0, 4.0, 6.0}, values, 1e-9);
        assertNotSame(values, result);
    }

    @Test
    void preservesOriginalArrayWithMeanCenteringStrategy() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] values = {2.0, 4.0, 6.0};

        double[] result = processor.process(values);

        assertArrayEquals(new double[]{2.0, 4.0, 6.0}, values, 1e-9);
        assertNotSame(values, result);
    }

    @Test
    void rejectsNullStrategy() {
        assertThrows(IllegalArgumentException.class,
                () -> new FeatureProcessor(null));
    }
}