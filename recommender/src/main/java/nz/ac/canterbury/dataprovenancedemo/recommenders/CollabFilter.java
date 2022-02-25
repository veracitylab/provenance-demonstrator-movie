package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;

import java.util.List;

public class CollabFilter extends AbstractMovieRecommender {
    @Override
    protected List<Integer> algorithm(Object features, int resultSize) {
        return null;
    }

    @Override
    public ProvenanceData getProvenanceData() {
        return null;
    }
}
