package nz.ac.canterbury.dataprovenancedemo;

import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmProperty;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmStep;

import java.util.List;

public class SelfReportedProvenanceData {
    private static final String NO_SPEC = "Not specified";

    private final String name;
    private final List<AlgorithmStep> steps;
    private final List<AlgorithmProperty> properties;

    private SelfReportedProvenanceData(Builder builder) {
        this.name = builder.selfRepAlgo;
        this.steps = builder.selfRepSteps;
        this.properties = builder.selfRepProps;
    }

//    public long getDataId() {
//        return this.dataId;
//    }
//
//    public String getSelfReportedAlgorithmName() {
//        return this.name;
//    }
//
//    public List<AlgorithmStep> getSelfReportedAlgorithmSteps() {
//        return this.steps;
//    }
//
//    public List<AlgorithmProperty> getSElfReportedAlgorithmProperties() {
//        return this.properties;
//    }

    public String getReport() {
        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("Self reported provenance data:\n");
        reportBuilder.append(String.format("  Algorithm name: %s%n", this.name != null ? this.name : NO_SPEC));
        reportBuilder.append("  Algorithm description: TODO\n\n");
        reportBuilder.append(String.format("  Properties (%s): %n", this.properties != null ? this.properties.size() : NO_SPEC));

        reportBuilder.append(String.format("  Steps (%s):%n", this.steps != null ? steps.size() : NO_SPEC));

        return reportBuilder.toString();
    }

    /**
     * Builder class for self-reported provenance data. Uses the information provided by annotations as inputs
     */
    public static class Builder {

        private String selfRepAlgo;
        private List<AlgorithmStep> selfRepSteps;
        private List<AlgorithmProperty> selfRepProps;

        public Builder algorithmName(String name) {
            this.selfRepAlgo = name;
            return this;
        }

        public Builder algorithmSteps(List<AlgorithmStep> steps) {
            this.selfRepSteps = steps;
            return this;
        }

        public Builder algorithmProperties(List<AlgorithmProperty> props) {
            this.selfRepProps = props;
            return this;
        }

        /**
         * Builds the provenance data object
         * @return A new provenance data object that is immutable
         */
        public SelfReportedProvenanceData build() {
            //TODO: Validation?
            return new SelfReportedProvenanceData(this);
        }
    }
}