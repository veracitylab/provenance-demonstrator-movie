package nz.ac.canterbury.dataprovenancedemo;

import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.provenance.SelfReportedProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.utils.ProvenanceProcessor;

import java.util.List;

public abstract class AbstractMovieRecommender implements MovieRecommender {


    @Override
    public List<Integer> getRecommendations(Object features, int resultSize) {
        //TODO: This is where the introspection will occur. This is currently out of scope so
        //TODO: Will be done at a later date

        return algorithm(features, resultSize);
    }


    /**
     * Currently uses the provenance processor to get a generated report from source-code annotations
     * //TODO: Extend this to include introspection information
     * @return
     */
    @Override
    public Object getProvenanceData() {
        SelfReportedProvenanceData srd = ProvenanceProcessor.process(this);
        return new ProvenanceData(srd);
    }

    protected abstract List<Integer> algorithm(Object features, int resultSize);
}
