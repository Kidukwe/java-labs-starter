package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }

    @Test
    void returnsTrueForZero() {
        boolean result = CourseToolkit.isEven(0);

        assertTrue(result);
    }

    @Test
    void returnsTrueForNegativeEvenNumber() {
        boolean result = CourseToolkit.isEven(-8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForNumberLessThanTwo() {
        boolean result = CourseToolkit.isPrime(1);

        assertFalse(result);
    }

    @Test 
    void returnsFalseForZero() {
        boolean result = CourseToolkit.isPrime(0);

        assertFalse(result);
    }

    @Test 
    void returnsFalseForNegativeNumber() {
        boolean result = CourseToolkit.isPrime(-5);

        assertFalse(result);
    }

    @Test
    void returnsTrueForTwo() {
        boolean result = CourseToolkit.isPrime(2);

        assertTrue(result);
    }

    @Test 
    void returnsFalseForCompositeNumber() {
        boolean result = CourseToolkit.isPrime(15);

        assertFalse(result);
    }

    @Test 
    void returnsFalseForSquareOfPrimeNumber() {
        boolean result = CourseToolkit.isPrime(49);

        assertFalse(result);
    }

    @Test 
    void returnsTrueForPalindrome() {
        boolean result = CourseToolkit.isPalindrome("abba");

        assertTrue(result);
    }

    @Test 
    void returnsFalseForNotPalindrome() {
        boolean result = CourseToolkit.isPalindrome("hello");

        assertFalse(result);
    }

    @Test 
    void returnsFalseForRegister() {
        boolean result = CourseToolkit.isPalindrome("Radar");

        assertFalse(result);
    }

    @Test 
    void returnsFalseForSpace() {
        boolean result = CourseToolkit.isPalindrome("nan ");

        assertFalse(result);
    }

    @Test
    void throwsIllegalArgumentExceptionForNullText() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
    }

    @Test 
    void returnsCorrectAverageForPositiveNumbers() {
        double result = CourseToolkit.average(new int[] {1, 2});

        assertEquals(1.5, result, 1e-9);
    }

    @Test 
    void returnsCorrectAverageForNegativeNumbers() {
        double result = CourseToolkit.average(new int[] {-2, -4, -6});

        assertEquals(-4.0, result, 1e-9);
    }

    @Test 
    void throwsIllegalArgumentExceptionForNullArray() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
    }

    @Test 
    void throwsIllegalArgumentExceptionForEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(new int[]{}));
    }

    @Test
    void doesNotMutateInputArray() {
    int[] original = {10, 20, 30};
    CourseToolkit.average(original);

    assertArrayEquals(new int[]{10, 20, 30}, original);
    }

    @Test 
    void returnsCorrectMinAndMax() {
        int[] array = {3, 1, 5, 2};
        assertEquals(1, CourseToolkit.min(array));
        assertEquals(5, CourseToolkit.max(array));
    }
    
    @Test 
    void returnsCorrectMinAndMaxForNegativeNumbers() {
        int[] array = {-10, -5, -20, -1};
        assertEquals(-20, CourseToolkit.min(array));
        assertEquals(-1, CourseToolkit.max(array));
    }

    @Test 
    void returnsElementForSingleElementArray() {
        int[] array = {42};
        assertEquals(42, CourseToolkit.min(array));
        assertEquals(42, CourseToolkit.max(array));
    }

    @Test
    void throwsIllegalArgumentExceptionForNullOrEmptyInMinAndMax() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.min(null));
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.min(new int[]{}));
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.max(null));
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.max(new int[]{}));
    }
}
