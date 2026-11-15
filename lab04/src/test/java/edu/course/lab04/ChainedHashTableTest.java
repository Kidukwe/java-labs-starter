package edu.course.lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChainedHashTableTest {
    @Test
    void replacesSampleUsingEqualKey() {
        ChainedHashTable table = new ChainedHashTable();
        SampleId originalId = new SampleId("train", 42);
        SampleId equalId = new SampleId("train", 42);
        DataSample original = new DataSample("cat");
        DataSample replacement = new DataSample("dog");

        table.put(originalId, original);
        assertSame(original, table.get(equalId));
        table.put(equalId, replacement);

        assertSame(replacement, table.get(originalId));
        assertEquals(1, table.size());
    }

    @Test
    void removesEntriesFromHeadAndMiddleOfCollisionChain() {
        ChainedHashTable table = new ChainedHashTable();
        SampleId tailId = new SampleId("AaAa", 42);
        SampleId middleId = new SampleId("AaBB", 42);
        SampleId headId = new SampleId("BBBB", 42);
        DataSample tail = new DataSample("tail");
        DataSample middle = new DataSample("middle");
        DataSample head = new DataSample("head");
        assertEquals(tailId.hashCode(), middleId.hashCode());
        assertEquals(tailId.hashCode(), headId.hashCode());

        table.put(tailId, tail);
        table.put(middleId, middle);
        table.put(headId, head);

        assertEquals(3, table.size());
        assertSame(middle, table.remove(new SampleId("AaBB", 42)));
        assertFalse(table.containsKey(middleId));
        assertSame(head, table.get(headId));
        assertSame(tail, table.get(tailId));
        assertSame(head, table.remove(headId));
        assertSame(tail, table.get(tailId));
        assertEquals(1, table.size());
        assertNull(table.remove(middleId));
        assertEquals(1, table.size());
    }

    @Test
    void handlesNegativeHashAndDistinguishesNullValueFromMissingKey() {
        ChainedHashTable table = new ChainedHashTable();
        SampleId id = new SampleId("A", Integer.MIN_VALUE);
        assertTrue(id.hashCode() < 0);

        table.put(id, null);

        assertTrue(table.containsKey(id));
        assertNull(table.get(id));
        assertNull(table.remove(id));
        assertFalse(table.containsKey(id));
        assertEquals(0, table.size());
    }
}
