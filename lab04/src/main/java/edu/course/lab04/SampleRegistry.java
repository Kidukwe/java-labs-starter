package edu.course.lab04;

import java.util.HashMap;

public final class SampleRegistry {
    private final HashMap<SampleId, DataSample> samplesById = new HashMap<>();

    public void put(SampleId id, DataSample sample) {
        samplesById.put(id, sample);
    }

    public DataSample get(SampleId id) {
        return samplesById.get(id);
    }

    public DataSample remove(SampleId id) {
        return samplesById.remove(id);
    }

    public boolean containsKey(SampleId id) {
        return samplesById.containsKey(id);
    }

    public int size() {
        return samplesById.size();
    }
}