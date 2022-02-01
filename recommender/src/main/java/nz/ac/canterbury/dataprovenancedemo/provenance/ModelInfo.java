package nz.ac.canterbury.dataprovenancedemo.provenance;

import java.io.Serializable;
import java.util.Optional;

/**
 * A POJO class describing a generated model used by the recommendation algorithm
 */
public class ModelInfo implements Serializable {

    /**
     * A list of the steps used to generate the model
     */
    private final String[] steps;

    /**
     * An optional list of rules used when generating the model
     */
    private final String[] rules;

    /**
     * An optional itemized list of additional information used for generating the model
     */
    private final String[] additionalInfo;

    private ModelInfo(ModelInfoBuilder builder) {
        this.steps = builder.steps;
        this.rules = builder.rules;
        this.additionalInfo = builder.additionalInfo;
    }

    public String[] getSteps() {
        return steps;
    }

    public Optional<String[]> getRules() {
        return Optional.of(rules);
    }

    public Optional<String[]> getAdditionalInfo() {
        return Optional.of(additionalInfo);
    }

    /**
     * Builder class for the model information
     */
    public static class ModelInfoBuilder {
        private String[] steps;
        private String[] rules;
        private String[] additionalInfo;

        public ModelInfoBuilder(String[] steps) {
            this.steps = steps;
        }

        public ModelInfoBuilder rules(String[] rules) {
            this.rules = rules;
            return this;
        }

        public ModelInfoBuilder additionalInfo(String[] info) {
            this.additionalInfo = info;
            return this;
        }

        public ModelInfo build() {
            return new ModelInfo(this);
        }


    }



}
