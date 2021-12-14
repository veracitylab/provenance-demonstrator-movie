package nz.ac.canterbury.dataprovenancedemo;

import java.util.List;

public interface MovieRecommender {


    /**
     * Obtains a list of recommendations keyed by the ID of the movie to recommend.
     * @param features The features needed to perform the recommendation. Can be nu
     * @param resultSize Number or recommendations to provide
     * @return List of movie IDs, in order of recommendation
     */
    List<Integer> getRecommendations(Object features, int resultSize);

    /**
     * Method stub to get the provenance data
     * @return Provenance data for the generated recommendation
     */
    Object getProvenanceData();
}
