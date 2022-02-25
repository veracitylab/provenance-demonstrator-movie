package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.annotations.Algorithm;
import nz.ac.canterbury.dataprovenancedemo.provenance.AlgorithmInfo;
import nz.ac.canterbury.dataprovenancedemo.provenance.DataSetInfo;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Algorithm(
        name = "Random Select",
        description = "Randomly selects n integers from a range of generated integers"
)
public class RandomSelect extends AbstractMovieRecommender {

    private static final Random RNG = new Random();
    private static final int RANGE = 100;

    private final AlgorithmInfo algorithmInfo;

    public RandomSelect() {
        String[] steps = new String[] {
                "Select, randomly a Movie ID from the list of movie IDs"
        };
        algorithmInfo = new AlgorithmInfo.AlgorithmInfoBuilder("Random select", steps).build();
    }


    @Override
    protected List<Integer> algorithm(Object features, int resultSize) {
        Stream<Integer> randomSelection = Stream.generate(
                () -> RNG.nextInt(RANGE)
        ).limit(resultSize);

        return randomSelection.collect(Collectors.toList());
    }

    @Override
    public ProvenanceData getProvenanceData() {


        ProvenanceData provenanceData = new ProvenanceData.ProvenanceDataBuilder()
                .dataSet(dataSetInfo)
                .algorithmInfo(algorithmInfo)
                .build();
        return provenanceData;
//        DataSet ds = new DataSet("Netflix Prize Data", "https://www.kaggle.com/netflix-inc/netflix-prize-data");
//        PersonalInfo pi = new PersonalInfo("None used");
//
//        List<String> steps = Collections.singletonList(
//                String.format("Generate random number between 0 and %s", RANGE)
//        );
//
//        List<String> ai = Collections.singletonList(
//                "Numbers correspond to a movie ID in the database"
//        );
//
//        return new ProvenanceData(ds, pi, steps, ai);
    }
}
