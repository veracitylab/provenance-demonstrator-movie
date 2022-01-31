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
        return Optional.of(description);
    }

    public String[] getSteps() {
        return steps;
    }

    public Optional<String[]> getAdditionalInfo() {
        return Optional.of(additionalInfo);
    }

    /**
     * Builder class for AlgorithmInfo
     */
    public static class AlgorithmInfoBuilder {
        private String name;
        private String description;
        private String[] steps;
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
