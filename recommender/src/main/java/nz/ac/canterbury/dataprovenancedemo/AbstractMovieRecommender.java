package nz.ac.canterbury.dataprovenancedemo;

import nz.ac.canterbury.dataprovenancedemo.provenance.DataSetInfo;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.provenance.SelfReportedProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.utils.ProvenanceProcessor;

import java.util.List;

public abstract class AbstractMovieRecommender implements MovieRecommender {

    protected static final DataSetInfo dataSetInfo = new DataSetInfo.DataSetInfoBuilder("Netflix Prize Data")
            .url("https://www.kaggle.com/netflix-inc/netflix-prize-data")
            .build();


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
    public ProvenanceData getProvenanceData() {
        SelfReportedProvenanceData srd = ProvenanceProcessor.process(this);
//        return new ProvenanceData(srd);
        return null;
    }

    protected abstract List<Integer> algorithm(Object features, int resultSize);
}
