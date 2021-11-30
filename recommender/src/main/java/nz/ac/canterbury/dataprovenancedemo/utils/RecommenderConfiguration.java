package nz.ac.canterbury.dataprovenancedemo.utils;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RecommenderConfiguration {

    protected RecommenderConfiguration() {}

    public static MovieRecommender getRecommender(String recommenderMethod) throws IllegalArgumentException {
        final String cname = "nz.ac.canterbury.dataprovenancedemo.recommenders.%s";

        try {
            Class<?> clazz = Class.forName(String.format(cname, recommenderMethod));
            Constructor<?> constructor = clazz.getDeclaredConstructor();

            return (MovieRecommender) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Invalid recommender method supplied");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Could not create recommender");
        }
    }
}
