package nz.ac.canterbury.dataprovenancedemo;

import nz.ac.canterbury.dataprovenancedemo.provenance.DataSetInfo;

import java.util.List;
import java.util.Map;

public abstract class AbstractMovieRecommender implements MovieRecommender {

    /**
     * DataSet information, this can be static for use in ProvenanceData objects as only one dataset is used.
     */
    protected static final DataSetInfo dataSetInfo = new DataSetInfo.DataSetInfoBuilder("Netflix Prize Data")
            .url("https://www.kaggle.com/netflix-inc/netflix-prize-data")
            .build();

    /**
     * List of Movie IDs to be used by recommenders.
     */
    protected List<Integer> movieIds;


    @Override
    public List<Integer> getRecommendations(Map<String, Object> features, int resultSize) throws IllegalStateException {
        if (movieIds == null) {
            throw new IllegalStateException("Available movie ID's have not been set");
        }
        //TODO: This is where the introspection will occur. This is currently out of scope so
        //TODO: Will be done at a later date

        return algorithm(features, resultSize);
    }

    /**
     * Sets the available movie IDs for this recommender and subclasses of this recommender.
     * @param movieIds Movie IDs to use
     */
    @Override
    public void setAvailableMovieIds(List<Integer> movieIds) {
        this.movieIds = movieIds;
    }

    /**
     * Algorithm to be implemented by a movie recommender. Must accept a JSON object containing features needed by the
     * recommender in order to perform a recommendation.
     * @param features Features object to use for recommendation
     * @param resultSize Number of recommendations to provide
     * @return
     */
    protected abstract List<Integer> algorithm(Map<String, Object> features, int resultSize);
}
