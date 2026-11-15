package edu.course.lab04;

import java.util.Objects;

public final class SampleId {
    private final String datasetName;
    private final int observationNumber;

    public SampleId(String datasetName, int observationNumber) {
        this.datasetName = Objects.requireNonNull(datasetName, "Dataset name cannot be null");
        this.observationNumber = observationNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SampleId)) {
            return false;
        }

        SampleId otherId = (SampleId) other;
        return observationNumber == otherId.observationNumber
                && datasetName.equals(otherId.datasetName);
    }

    @Override
    public int hashCode() {
        return 31 * datasetName.hashCode() + Integer.hashCode(observationNumber);
    }
}