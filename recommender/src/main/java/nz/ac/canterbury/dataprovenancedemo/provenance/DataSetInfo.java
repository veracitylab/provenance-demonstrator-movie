package nz.ac.canterbury.dataprovenancedemo.provenance;

import java.io.Serializable;
import java.util.Optional;

/**
 * POJO containing information relating to the dataset used for model generation, returns optionals for each field
 */
public class DataSetInfo implements Serializable {

    static final long serialVersionUID  = 1L;

    /**
     * Name of a given data set used for recommendations
     */
    private final String name;

    /**
     * An optional link to the source of the described dataset
     */
    private final String url;

    /**
     * An optional description of the described dataset
     */
    private final String description;

    /**
     * Any additional information regarding the dataset, itemized
     */
    private final String[] additionalInfo;

    /**
     * Private constructor for only data access
     * @param builder DataSetInfoBuilder used to construct the object
     */
    private DataSetInfo(DataSetInfoBuilder builder) {
        this.name = builder.name;
        this.url = builder.url;
        this.description = builder.description;
        this.additionalInfo = builder.additionalInfo;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getUrl() {
        return Optional.of(url);
    }

    public Optional<String> getDescription() {
        return Optional.of(description);
    }

    public Optional<String[]> getAdditionalInfo() {
        return Optional.of(additionalInfo);
    }

    /**
     * Builder for the DataSetInfo class
     */
    public static class DataSetInfoBuilder {
        private String name;
        private String url;
        private String description;
        private String[] additionalInfo;

        public DataSetInfoBuilder(String name) {
            this.name = name;
        }

        public DataSetInfoBuilder url(String url) {
            this.url = url;
            return this;
        }

        public DataSetInfoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DataSetInfoBuilder additionalInfo(String[] info) {
            this.additionalInfo = info;
            return this;
        }

        public DataSetInfo build() {
            return new DataSetInfo(this);
        }
    }


}
