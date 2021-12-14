package nz.ac.canterbury.dataprovenancedemo.provenance;

public class ProvenanceData {
    private SelfReportedProvenanceData selfReportedData;

    protected ProvenanceData() {}

    public ProvenanceData(SelfReportedProvenanceData data) {
        this.selfReportedData = data;
    }

    public String getSelfReportedString() {
        return selfReportedData.getReport();
    }
}