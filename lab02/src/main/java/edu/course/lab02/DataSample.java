package edu.course.lab02;

public class DataSample {
    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, SampleStatus status, double[] features) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank.");
        }

        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label cannot be null or blank.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features cannot be blank.");
        }  

        this.id = id;
        this.label = label;
        this.status = status;
        this.features = features.clone();
    }
              
        
    public void changeStatus(SampleStatus newStatus) {
        if (newStatus != null) {
            this.status = newStatus;
        }
        else {
            throw new IllegalArgumentException("Status cannot be null.");
        }
    }

    public boolean isReady() {
        return this.status == SampleStatus.READY;    
    }        

    public double averageFeatures() {
        double sumFeatures = 0;
        for (double f : this.features) {
            sumFeatures += f;
        }
        return sumFeatures / this.features.length;
    }

    public double[] getFeatures() {
        return this.features.clone();
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public SampleStatus getStatus() {
        return this.status;
    }
}
