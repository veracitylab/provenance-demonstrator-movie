package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;

import java.util.List;

public class CollabFilter extends AbstractMovieRecommender {
    @Override
    protected List<Integer> algorithm(Object features, int resultSize) {
        return null;
    }

    @Override
    public Object getProvenanceData() {
        return null;
    }
}
