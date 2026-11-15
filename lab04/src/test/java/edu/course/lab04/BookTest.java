package edu.course.lab04;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookTest {
    @Test
    void findsValueUsingAnotherBookWithSameIsbn() {
        Book storedBook = new Book("123", "Java Basics");
        Book lookupBook = new Book("123", "Java Fundamentals");
        HashMap<Book, String> booksByIsbn = new HashMap<>();

        booksByIsbn.put(storedBook, "На полке");
        String result = booksByIsbn.get(lookupBook);

        assertEquals("На полке", result);
    }

    @Test
    void booksWithDifferentIsbnsAreNotEqual() {
        Book firstBook = new Book("123", "Java Basics");
        Book secondBook = new Book("456", "Java Basics");

        assertNotEquals(firstBook, secondBook);
    }
}
