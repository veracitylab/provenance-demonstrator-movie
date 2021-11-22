package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.ProvenanceData;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomSelect implements MovieRecommender {

    private static final Random RNG = new Random();


    @Override
    public void provideFeatures(Object features) {
        // Doesn't care about the features, randomly assigns recommendations
    }

    @Override
    public List<Integer> getRecommendations() {
        return getRecommendations(DEFAULT_NUM_RECOMMENDATIONS);
    }

    @Override
    public List<Integer> getRecommendations(int resultSize) {
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
