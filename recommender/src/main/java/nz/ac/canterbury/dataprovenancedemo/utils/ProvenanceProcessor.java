package nz.ac.canterbury.dataprovenancedemo.utils;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.annotations.Algorithm;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmProperty;
import nz.ac.canterbury.dataprovenancedemo.annotations.AlgorithmStep;

public class ProvenanceProcessor {

    protected ProvenanceProcessor() {}

    public static void process(MovieRecommender target) {
        Algorithm[] algoNames = target.getClass().getAnnotationsByType(Algorithm.class);
        AlgorithmProperty[] algoProps = target.getClass().getAnnotationsByType(AlgorithmProperty.class);
        AlgorithmStep[] algoSteps = target.getClass().getAnnotationsByType(AlgorithmStep.class);

        assert algoNames.length == 1;

        Class<?> clazz = target.getClass();

        System.out.printf("Algorithm information for class: %s%n", clazz.getSimpleName());
        System.out.printf("\tName: %s%n", algoNames[0].name());
        System.out.printf("\tDescription: %s%n", algoNames[0].description());
    }

}