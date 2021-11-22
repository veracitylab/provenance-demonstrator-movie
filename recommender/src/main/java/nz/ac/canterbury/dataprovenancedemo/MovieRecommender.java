package nz.ac.canterbury.dataprovenancedemo;

import java.util.List;

public interface MovieRecommender {

    /**
     * Default number of recommendations
     */
    public static final int DEFAULT_NUM_RECOMMENDATIONS = 10;

    /**
     * Provides the features to the recommendation engine in order to produce a set of
     * @param features Object containing the required features.
     */
    public void provideFeatures(Object features);

    /**
     * Obtains a list of recommendations keyed by the ID of the movie to recommend. Gets
     * the default number of recommendations
     * @return List of movie IDs, in order of recommendation
     */
    public List<Integer> getRecommendations();

    /**
     * Obtains a list of recommendations keyed by the ID of the movie to recommend.
     * @param resultSize Number or recommendations to provide
     * @return List of movie IDs, in order of recommendation
     */
    public List<Integer> getRecommendations(int resultSize);

    /**
     * Method stub to get the provenance data
     * @return Provenance data for the generated recommendation
     */
    public ProvenanceData getProvenanceData();
}
