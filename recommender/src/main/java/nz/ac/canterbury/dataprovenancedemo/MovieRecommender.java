package nz.ac.canterbury.dataprovenancedemo;

import java.util.List;

public interface MovieRecommender {

    /**
     * Default number of recommendations
     */
    public static final int DEFAULT_NUM_RECOMMENDATIONS = 10;

    /**
     * Gets recommendations with provided features
     * @param features The features needed to perform the recommendation
     * @return A list of movie
     */
    public List<Integer> getRecommendations(Object features);

    /**
     * Obtains a list of recommendations keyed by the ID of the movie to recommend.
     * @param features The features needed to perform the recommendation. Can be nu
     * @param resultSize Number or recommendations to provide
     * @return List of movie IDs, in order of recommendation
     */
    public List<Integer> getRecommendations(Object features, int resultSize);

    /**
     * Method stub to get the provenance data
     * @return Provenance data for the generated recommendation
     */
    public ProvenanceData getProvenanceData();
}
