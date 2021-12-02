package nz.ac.canterbury.dataprovenancedemo;

public class ProvenanceData {
    private SelfReportedProvenanceData selfReportedData;

    protected ProvenanceData() {}

    public ProvenanceData(SelfReportedProvenanceData data) {
        this.selfReportedData = data;
    }
}