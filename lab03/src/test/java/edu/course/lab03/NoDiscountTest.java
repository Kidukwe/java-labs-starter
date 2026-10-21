package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NoDiscountTest {
    @Test
    void returnsOriginalPrice() {
        NoDiscount policy = new NoDiscount();

        double result = policy.apply(150.0);

        assertEquals(150.0, result, 1e-9);
    }

    @Test
    void acceptsZeroPrice() {
        NoDiscount policy = new NoDiscount();

        double result = policy.apply(0.0);

        assertEquals(0.0, result, 1e-9);
    }

    @Test
    void rejectsNegativePrice() {
        NoDiscount policy = new NoDiscount();

        assertThrows(IllegalArgumentException.class,() -> policy.apply(-1.0));
    }
}