package nz.ac.canterbury.dataprovenancedemo.provenance;

import java.io.Serializable;
import java.util.Optional;

/**
 * A POJO object used to describe the algorithm used in the recommendation process
 */
public class AlgorithmInfo implements Serializable {

    static final long serialVersionUID  = 1L;

    /**
     * The name of the algorithm used for recommendation
     */
    private final String name;

    /**
     * An optional description of the algorithm used for recommendation
     */
    private final String description;

    /**
     * A list of the algorithmic steps used for producing a recommendation.
     */
    private final String[] steps;

    /**
     * Optional itemized additional information used in the recommendation algorithm
     */
    private final String[] additionalInfo;


    private AlgorithmInfo(AlgorithmInfoBuilder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.steps = builder.steps;
        this.additionalInfo = builder.additionalInfo;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public String[] getSteps() {
        return steps;
    }

    public Optional<String[]> getAdditionalInfo() {
        return Optional.ofNullable(additionalInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Algorithm information:%n"));
        sb.append(String.format("Name: %s%n", name));

        if(description != null) {
            sb.append(String.format("Description: %s%n", description));
        }

        sb.append(String.format("Steps:%n"));
        for(int i = 0; i < steps.length; i++) {
            sb.append(String.format("\t%s. %s%n", i, steps[i]));
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
     * Builder class for AlgorithmInfo
     */
    public static class AlgorithmInfoBuilder {
        private final String name;
        private String description;
        private final String[] steps;
        private String[] additionalInfo;


        public AlgorithmInfoBuilder(String name, String[] steps) {
            this.name = name;
            this.steps = steps;
        }

        public AlgorithmInfoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AlgorithmInfoBuilder additionalInfo(String[] info) {
            this.additionalInfo = info;
            return this;
        }

        public AlgorithmInfo build() {
            return new AlgorithmInfo(this);
        }
    }
}
