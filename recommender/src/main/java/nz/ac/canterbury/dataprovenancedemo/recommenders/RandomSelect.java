package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.provenance.AlgorithmInfo;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class RandomSelect extends AbstractMovieRecommender {
    private final AlgorithmInfo algorithmInfo;

    public RandomSelect() {
        String[] steps = new String[] {
                "Select, randomly a Movie ID from the list of movie IDs"
        };
        algorithmInfo = new AlgorithmInfo.AlgorithmInfoBuilder("Random select", steps).build();
    }

    /**
     * Randomly selects n elements from a shuffled version of the movieID list.
     * @param features Features object to use for recommendation
     * @param resultSize Number of recommendations to provide
     * @return List of recommendations
     */
    @Override
    protected List<Integer> algorithm(Map<String, Object> features, int resultSize) {
        Collections.shuffle(movieIds);
        return movieIds.subList(0, Math.min(resultSize, movieIds.size()));
    }

    @Override
    public ProvenanceData getProvenanceData() {
        return new ProvenanceData.ProvenanceDataBuilder()
                .dataSet(dataSetInfo)
                .algorithmInfo(algorithmInfo)
                .build();
    }
}
