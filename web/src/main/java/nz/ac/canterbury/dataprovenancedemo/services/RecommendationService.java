package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Recommendation;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.utils.RecommenderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Value("${movie.recommender.method}")
    private String recommendationMethod;

    private MovieRecommender recommender;

    private final MovieRepository movieRepository;

    private static Map<String, ProvenanceData> provenance = new HashMap<>();

    @Autowired
    public RecommendationService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    private void setRecommender() {
        recommender = RecommenderConfiguration.getRecommender(recommendationMethod);
    }

    public List<Recommendation> getRecommendations() {

        String id = UUID.randomUUID().toString();
        List<Integer> recommendationIds = recommender.getRecommendations(null, 5);
        List<Movie> movies = movieRepository.findMoviesByIds(recommendationIds);
        ProvenanceData provenanceData = recommender.getProvenanceData();

        provenance.put(id, provenanceData);

        return movies.stream().map(m -> new Recommendation(id, m)).collect(Collectors.toList());
    }

    public Optional<ProvenanceData> getProvenanceData(String id) {
        if (provenance.containsKey(id)) {
            return Optional.of(provenance.get(id));
        }
        return Optional.empty();
    }
}
