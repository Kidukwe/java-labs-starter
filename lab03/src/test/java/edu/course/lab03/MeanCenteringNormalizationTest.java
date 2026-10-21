package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeanCenteringNormalizationTest {
    @Test
    void centersValuesAroundMean() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] values = {2.0, 4.0, 6.0};

        double[] result = strategy.normalize(values);

        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, result, 1e-9);
    }

    @Test
    void centersNegativeValues() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] values = {-6.0, -4.0, -2.0};

        double[] result = strategy.normalize(values);

        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, result, 1e-9);
    }

    @Test
    void returnsZerosForEqualValues() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] values = {5.0, 5.0, 5.0};

        double[] result = strategy.normalize(values);

        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, result, 1e-9);
    }

    @Test
    void returnsZeroForSingleValue() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] values = {7.0};

        double[] result = strategy.normalize(values);

        assertArrayEquals(new double[]{0.0}, result, 1e-9);
    }

    @Test
    void preservesOriginalArrayAndReturnsNewArray() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] values = {2.0, 4.0, 6.0};

        double[] result = strategy.normalize(values);

        assertArrayEquals(new double[]{2.0, 4.0, 6.0}, values, 1e-9);
        assertNotSame(values, result);
    }

    @Test
    void rejectsNullArray() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();

        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(null));
    }

    @Test
    void rejectsEmptyArray() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();

        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[]{}));
    }

    @Test
    void rejectsNonFiniteValues() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();

        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[]{1.0, Double.NaN}));
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[]{1.0, Double.POSITIVE_INFINITY}));
        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[]{1.0, Double.NEGATIVE_INFINITY}));
    }
}