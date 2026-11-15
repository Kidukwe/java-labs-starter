package edu.course.lab04;

import java.util.Objects;

public final class ChainedHashTable {
    private static final int BUCKET_COUNT = 16;

    private final Entry[] buckets = new Entry[BUCKET_COUNT];
    private int size;

    public void put(SampleId id, DataSample sample) {
        int index = bucketIndex(id);
        Entry existing = findEntry(index, id);
        if (existing != null) {
            existing.sample = sample;
            return;
        }

        buckets[index] = new Entry(id, sample, buckets[index]);
        size++;
    }

    public DataSample get(SampleId id) {
        Entry entry = findEntry(bucketIndex(id), id);
        return entry == null ? null : entry.sample;
    }

    public boolean containsKey(SampleId id) {
        return findEntry(bucketIndex(id), id) != null;
    }

    public DataSample remove(SampleId id) {
        int index = bucketIndex(id);
        Entry previous = null;

        for (Entry current = buckets[index]; current != null; current = current.next) {
            if (current.id.equals(id)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.sample;
            }
            previous = current;
        }
        return null;
    }

    public int size() {
        return size;
    }

    private int bucketIndex(SampleId id) {
        Objects.requireNonNull(id, "Sample id cannot be null");
        return Math.floorMod(id.hashCode(), buckets.length);
    }

    private Entry findEntry(int index, SampleId id) {
        for (Entry current = buckets[index]; current != null; current = current.next) {
            if (current.id.equals(id)) {
                return current;
            }
        }
        return null;
    }

    private static final class Entry {
        private final SampleId id;
        private DataSample sample;
        private Entry next;

        private Entry(SampleId id, DataSample sample, Entry next) {
            this.id = id;
            this.sample = sample;
            this.next = next;
        }
    }
}
