package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.ProvenanceData;

import java.util.List;

public class SimilarNeighbours implements MovieRecommender {
    @Override
    public List<Integer> getRecommendations(Object features) {
        return null;
    }

    @Override
    public List<Integer> getRecommendations(Object features, int resultSize) {
        return null;
    }

    @Override
    public ProvenanceData getProvenanceData() {
        return null;
    }
}
