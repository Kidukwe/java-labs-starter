package edu.course.lab01;

public final class CourseToolkit {

    private CourseToolkit() {
        // Утилитарный класс не должен иметь экземпляров.
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        } 
        for (int divisor = 2; divisor * divisor <= number; divisor++){
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(String text) {
        if (text == null){
            throw new IllegalArgumentException("Text cannot be null.");
        }

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static double average(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }

        long valuesSum = 0;

        for (int val : values) {
            valuesSum += val;
        }

        return (double) valuesSum / values.length;
    }

    public static int min(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }
        
        int currentMin = values[0];
        
        for (int val : values){
            if (val < currentMin) {
                currentMin = val;
            }
        }

        return currentMin;
    }

    public static int max(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }

        int currentMax = values[0];
        
        for (int val : values){
            if (val > currentMax) {
                currentMax = val;
            }
        }
        
        return currentMax;
    }

}
