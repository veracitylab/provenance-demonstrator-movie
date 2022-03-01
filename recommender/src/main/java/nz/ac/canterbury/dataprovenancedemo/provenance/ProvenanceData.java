package nz.ac.canterbury.dataprovenancedemo.provenance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.util.Optional;

/**
 * Wrapper class for provenance information. The information contained includes:
 * <ol>
 *     <li>Information about datasets used for model generation or used in predictions, if applicable</li>
 *     <li>Information about an algorithm that was used in generating predictions, if applicable</li>
 *     <li>Information about a model that may have been used in generating predictions, if applicable</li>
 *     <li>Any personal information from a user that has been used in generating predictions</li>
 *     <li>Any additional information that may be useful to an end user</li>
 * </ol>
 */
public class ProvenanceData {
    private final DataSetInfo dataSetDetails;
    private final AlgorithmInfo algorithmDetails;
    private final ModelInfo modelDetails;
    private final String[] personalInfo;

    private final String[] additionalInfo;

    private ProvenanceData(ProvenanceDataBuilder builder) {
        this.dataSetDetails = builder.dataSet;
        this.algorithmDetails = builder.algorithmInfo;
        this.modelDetails = builder.modelInfo;
        this.personalInfo = builder.personalInfo;
        this.additionalInfo = builder.additionalInfo;
    }

    public Optional<DataSetInfo> getDataSetDetails() {
        return Optional.ofNullable(dataSetDetails);
    }

    public Optional<AlgorithmInfo> getAlgorithmDetails() {
        return Optional.ofNullable(algorithmDetails);
    }

    public Optional<ModelInfo> getModelDetails() {
        return Optional.ofNullable(modelDetails);
    }

    public Optional<String[]> getPersonalInfo() {
        return Optional.ofNullable(personalInfo);
    }

    public Optional<String[]> getAdditionalInfo() {
        return Optional.ofNullable(additionalInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Provenance data report: %n%n"));

        if(dataSetDetails != null) {
            sb.append(dataSetDetails.toString());
            sb.append(String.format("%n"));
        }

        if(algorithmDetails != null) {
            sb.append(algorithmDetails.toString());
            sb.append(String.format("%n"));
        }

        if(modelDetails != null) {
            sb.append(modelDetails.toString());
            sb.append(String.format("%n"));
        }

        if(personalInfo != null) {
            sb.append(String.format("Personal information: %n"));
            for(int i = 0; i < personalInfo.length; i++) {
                sb.append(String.format("\t%s. %s%n", i, personalInfo[i]));
            }
            sb.append(String.format("%n"));
        }

        if(additionalInfo != null) {
            sb.append(String.format("Additional information: %n"));
            for(int i = 0; i < additionalInfo.length; i++) {
                sb.append(String.format("\t%s. %s%n", i, additionalInfo[i]));
            }
            sb.append(String.format("%n"));
        }


        return sb.toString();
    }

    /**
     * Converts this object to JSON, also adds a pretty print representation if needed (Used for TXT display)
     * @param prettyPrint True to add pretty print representation to the JSON node
     * @return JSON string
     */
    public String toJSON(boolean prettyPrint) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        JsonNode node = mapper.convertValue(this, JsonNode.class);

        if(prettyPrint) {
            ObjectNode newNode = ((ObjectNode) node).put("prettyPrint", this.toString());
            return newNode.toString();
        }

        return node.toString();
    }


    public static class ProvenanceDataBuilder {
        private DataSetInfo dataSet;
        private AlgorithmInfo algorithmInfo;
        private ModelInfo modelInfo;

        private String[] personalInfo;
        private String[] additionalInfo;

        public ProvenanceDataBuilder dataSet(DataSetInfo data) {
            this.dataSet = data;
            return this;
        }

        public ProvenanceDataBuilder algorithmInfo(AlgorithmInfo info) {
            this.algorithmInfo = info;
            return this;
        }

        public ProvenanceDataBuilder modelInfo(ModelInfo info) {
            this.modelInfo = info;
            return this;
        }

        public ProvenanceDataBuilder personalInfo(String[] info) {
            this.personalInfo = info;
            return this;
        }

        public ProvenanceDataBuilder additionalInfo(String[] info) {
            this.additionalInfo = info;
            return this;
        }


        /**
         * Builds the ProvenanceData object from the builder
         * @return new instance of ProvenanceData
         */
        public ProvenanceData build() {
            return new ProvenanceData(this);
        }
    }
}