package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;

import java.util.List;
import java.util.Map;

public class CollabFilter extends AbstractMovieRecommender {
    @Override
    protected List<Integer> algorithm(Map<String, Object> features, int resultSize) {
        return null;
    }

    @Override
    public ProvenanceData getProvenanceData() {
        return null;
    }
}
