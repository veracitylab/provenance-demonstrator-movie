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
        return Optional.ofNullable(rules);
    }

    public Optional<String[]> getAdditionalInfo() {
        return Optional.ofNullable(additionalInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Model information: %n"));

        sb.append(String.format("Generation steps:%n"));
        for(int i = 0; i < steps.length; i++) {
            sb.append(String.format("\t%s. %s%n", i, steps[i]));
        }

        if(rules != null) {
            sb.append(String.format("Model rules:%n"));
            for(int i = 0; i < rules.length; i++) {
                sb.append(String.format("%s. %s%n", i, rules[i]));
            }
        }

        if(additionalInfo != null) {
            sb.append(String.format("Additional information:%n"));

            for(int i = 0; i < additionalInfo.length; i++) {
                sb.append(String.format("\t%s. %s%n", i, additionalInfo[i]));
            }
        }

        return sb.toString();
    }

    /**
     * Builder class for the model information
     */
    public static class ModelInfoBuilder {
        private final String[] steps;
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
