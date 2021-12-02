package nz.ac.canterbury.dataprovenancedemo.utils;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.SelfReportedProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.annotations.Algorithm;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmProperty;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmStep;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProvenanceProcessor {

    protected ProvenanceProcessor() {}

    public static void process(MovieRecommender target) {
        Algorithm[] algoNames = target.getClass().getAnnotationsByType(Algorithm.class);
        AlgorithmStep[] algoSteps = target.getClass().getAnnotationsByType(AlgorithmStep.class);
        AlgorithmProperty[] algoProps = target.getClass().getAnnotationsByType(AlgorithmProperty.class);

        // Tasty validations. Note validation 2 is disgusting and shouldn't be done
        assert algoNames.length == 1;
        assert Arrays.stream(algoSteps).map(AlgorithmStep::order).collect(Collectors.toSet()).size() == algoSteps.length;

        String algorithmName = algoNames[0].name();
        String algoDescription = algoNames[0].description();

        List<AlgorithmStep> sortedSteps = Arrays.stream(algoSteps)
                .sorted(Comparator.comparingInt(AlgorithmStep::order))
                .collect(Collectors.toList());

        SelfReportedProvenanceData data = new SelfReportedProvenanceData.Builder()
                .algorithmName(algorithmName)
                .algorithmSteps(sortedSteps)
                .build();

        System.out.println(data.getReport());

        Class<?> clazz = target.getClass();

        System.out.printf("Algorithm information for class: %s%n", clazz.getSimpleName());
        System.out.printf("\tName: %s%n", algorithmName);
        System.out.printf("\tDescription: %s%n", algoDescription);
    }

}