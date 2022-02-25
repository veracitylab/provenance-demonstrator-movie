package nz.ac.canterbury.dataprovenancedemo.provenance;


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

    @Override
    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Data provenance report:\n");
//
//        // Dataset information
//        sb.append("\nDataset information:\n");
//        sb.append(String.format("Name: %s%n", dataSetDetails.name));
//        sb.append(String.format("Url: %s%n", dataSetDetails.url != null ? dataSetDetails.url : "None provided"));
//
//        // Personal information used
//        sb.append("\nPersonal information:\n");
//        sb.append("Not yet implemented\n");
//
//        //Algorithmic steps
//        sb.append("\nAlgorithmic Steps:\n");
//
//        ListIterator<String> it = algorithmicSteps.listIterator();
//
//        while(it.hasNext()) {
//            sb.append(String.format("%s. %s%n", it.nextIndex() + 1, it.next()));
//        }
//
//        sb.append("\nAdditional information:\n");
//        for(String info: additionalInfo) {
//            sb.append(String.format("%s%n", info));
//        }
//
//        return sb.toString();
        return dataSetDetails.toString();
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