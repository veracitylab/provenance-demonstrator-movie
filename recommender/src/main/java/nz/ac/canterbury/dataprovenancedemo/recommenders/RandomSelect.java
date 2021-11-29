package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.ProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.annotations.Algorithm;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Algorithm(
        name = "Random Select",
        description = "Randomly selects n integers from a range or set of integers"
)
public class RandomSelect implements MovieRecommender {

    private static final Random RNG = new Random();

    @Override
    public List<Integer> getRecommendations(Object features) {
        return getRecommendations(features, DEFAULT_NUM_RECOMMENDATIONS);
    }

    @Override
    public List<Integer> getRecommendations(Object features, int resultSize) {
        Stream<Integer> randomSelection = Stream.generate(
                () -> RNG.nextInt(17000)
        ).limit(resultSize);

        return randomSelection.collect(Collectors.toList());
    }

    @Override
    public ProvenanceData getProvenanceData() {
        return new ProvenanceData();
    }
}