package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Recommendation;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import nz.ac.canterbury.dataprovenancedemo.utils.RecommenderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecommendationService {

    @Value("${movie.recommender.method}")
    private String recommendationMethod;

    private MovieRecommender recommender;

    private final MovieRepository movieRepository;

    private static Map<String, Object> provenance = new HashMap<>();

    @Autowired
    public RecommendationService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    private void setRecommender() {
        recommender = RecommenderConfiguration.getRecommender(recommendationMethod);
    }

    public Recommendation getRecommendations() {

        String id = UUID.randomUUID().toString();
        List<Integer> recommendationIds = recommender.getRecommendations(null, 5);
        List<Movie> movies = movieRepository.findMoviesByIds(recommendationIds);
        Object provenanceData = recommender.getProvenanceData();

        provenance.put(id, provenanceData);

        return new Recommendation(id, movies);
    }

    public Optional<Object> getProvenanceData(String id) {
        if (provenance.containsKey(id)) {
            return Optional.of(provenance.get(id));
        }
        return Optional.empty();
    }
}
