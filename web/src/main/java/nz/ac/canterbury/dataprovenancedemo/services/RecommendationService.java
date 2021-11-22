package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import nz.ac.canterbury.dataprovenancedemo.utils.RecommenderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RecommendationService {

    @Value("${movie.recommender.method}")
    private String recommendationMethod;

    private MovieRecommender recommender;

    private final MovieRepository movieRepository;

    @Autowired
    public RecommendationService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    private void setRecommender() {
        this.recommender = RecommenderConfiguration.getRecommender(recommendationMethod);
    }

    public String recommendation() {
        return recommendationMethod;
    }
}
