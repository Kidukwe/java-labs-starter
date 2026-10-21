package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceCalculatorTest {
    @Test
    void supportsDifferentPolicies() {
        PriceCalculator withoutDiscount = new PriceCalculator(new NoDiscount());

        PriceCalculator withDiscount = new PriceCalculator(new PercentDiscount(20.0));

        double originalResult = withoutDiscount.calculate(100.0);
        double discountedResult = withDiscount.calculate(100.0);

        assertEquals(100.0, originalResult, 1e-9);
        assertEquals(80.0, discountedResult, 1e-9);
    }

    @Test
    void rejectsNullPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new PriceCalculator(null));
    }

    @Test
    void rejectsNegativePriceWithEitherPolicy() {
        PriceCalculator withoutDiscount = new PriceCalculator(new NoDiscount());

        PriceCalculator withDiscount = new PriceCalculator(new PercentDiscount(20.0));

        assertThrows(IllegalArgumentException.class, () -> withoutDiscount.calculate(-1.0));
        assertThrows(IllegalArgumentException.class, () -> withDiscount.calculate(-1.0));
    }
}