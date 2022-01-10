package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.annotations.Algorithm;

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


    @Override
    protected List<Integer> algorithm(Object features, int resultSize) {
        Stream<Integer> randomSelection = Stream.generate(
                () -> RNG.nextInt(100)
        ).limit(resultSize);

        return randomSelection.collect(Collectors.toList());
    }

    @Override
    public Object getProvenanceData() {
        return "Test of the provenance data return";
    }
}
