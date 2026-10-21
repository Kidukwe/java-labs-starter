package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PercentDiscountTest {
    @Test
    void appliesTwentyPercentDiscount() {
        PercentDiscount policy = new PercentDiscount(20.0);

        double result = policy.apply(100.0);

        assertEquals(80.0, result, 1e-9);
    }

    @Test
    void zeroPercentPreservesPrice() {
        PercentDiscount policy = new PercentDiscount(0.0);

        double result = policy.apply(150.0);

        assertEquals(150.0, result, 1e-9);
    }

    @Test
    void hundredPercentReturnsZero() {
        PercentDiscount policy = new PercentDiscount(100.0);

        double result = policy.apply(150.0);

        assertEquals(0.0, result, 1e-9);
    }

    @Test
    void acceptsZeroPrice() {
        PercentDiscount policy = new PercentDiscount(20.0);

        double result = policy.apply(0.0);

        assertEquals(0.0, result, 1e-9);
    }

    @Test
    void rejectsNegativePrice() {
        PercentDiscount policy = new PercentDiscount(20.0);

        assertThrows(IllegalArgumentException.class, () -> policy.apply(-1.0));
    }

    @Test
    void rejectsInvalidPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-1.0));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(101.0));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(Double.NEGATIVE_INFINITY));
    }
}