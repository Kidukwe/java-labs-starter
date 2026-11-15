package edu.course.lab04;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MutableKeyTest {
    @Test
    void changingKeyAfterInsertionMakesEntryUnreachable() {
        HashMap<MutableKey, String> samples = new HashMap<>();
        MutableKey key = new MutableKey(1);
        samples.put(key, "cat");
        assertEquals("cat", samples.get(key));

        key.observationNumber = 2;

        assertNull(samples.get(key));
        assertEquals(1, samples.size());
    }

    private static final class MutableKey {
        private int observationNumber;

        private MutableKey(int observationNumber) {
            this.observationNumber = observationNumber;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof MutableKey key
                    && observationNumber == key.observationNumber;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(observationNumber);
        }
    }
}
