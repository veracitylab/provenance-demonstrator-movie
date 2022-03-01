package nz.ac.canterbury.dataprovenancedemo;

import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;

import java.util.List;
import java.util.Map;

public interface MovieRecommender {
    /**
     * Obtains a list of recommendations keyed by the ID of the movie to recommend.
     * @param features The features needed to perform the recommendation. Can be nu
     * @param resultSize Number or recommendations to provide
     * @return List of movie IDs, in order of recommendation
     */
    List<Integer> getRecommendations(Map<String, Object> features, int resultSize);

    /**
     * Method stub to get the provenance data
     * @return Provenance data for the generated recommendation
     */
    ProvenanceData getProvenanceData();

    /**
     * Sets available movies for use by recommenders
     * @param movieIds Movie IDs to use
     */
    void setAvailableMovieIds(List<Integer> movieIds);
}
