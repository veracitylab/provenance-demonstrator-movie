package nz.ac.canterbury.dataprovenancedemo.utils;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.annotations.Algorithm;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmProperty;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmStep;
import nz.ac.canterbury.dataprovenancedemo.provenance.SelfReportedProvenanceData;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This utility class contains processors for producing self-reported provenance data from annotations in a
 * recommender's source code.
 */
public class ProvenanceProcessor {

    protected ProvenanceProcessor() {}

    public static SelfReportedProvenanceData process(MovieRecommender target) {
        Algorithm[] algoNames = target.getClass().getAnnotationsByType(Algorithm.class);
        AlgorithmStep[] algoSteps = target.getClass().getAnnotationsByType(AlgorithmStep.class);
        AlgorithmProperty[] algoProps = target.getClass().getAnnotationsByType(AlgorithmProperty.class);

        // Tasty validations. Note validation 2 is disgusting and shouldn't be done
        assert algoNames.length == 1;
        assert Arrays.stream(algoSteps).map(AlgorithmStep::order).collect(Collectors.toSet()).size() == algoSteps.length;

        String algorithmName = algoNames[0].name();
        String algoDescription = algoNames[0].description();

        SelfReportedProvenanceData data = new SelfReportedProvenanceData.Builder()
                .algorithmName(algorithmName)
//                .algorithmSteps(Arrays.asList(algoSteps))
                .algorithmProperties(Arrays.asList(algoProps))
                .build();

        //        Class<?> clazz = target.getClass();
//
//        System.out.printf("Algorithm information for class: %s%n", clazz.getSimpleName());
//        System.out.printf("\tName: %s%n", algorithmName);
//        System.out.printf("\tDescription: %s%n", algoDescription);

//        System.out.println(data.getReport());
        return data;
    }

}