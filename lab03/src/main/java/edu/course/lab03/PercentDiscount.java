package edu.course.lab03;

public class PercentDiscount implements DiscountPolicy {
    private final double percent;

    public PercentDiscount(double percent) {
        if (!Double.isFinite(percent) || percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
        this.percent = percent;
    }

    @Override
    public double apply(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Initial price cannot be negative.");
        }
        return price * (1 - percent / 100);
    }
}
