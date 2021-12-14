package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.MovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.utils.RecommenderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
        recommender = RecommenderConfiguration.getRecommender(recommendationMethod);
    }

    public List<Movie> getRecommendations() {
        List<Integer> recommendationIds =  recommender.getRecommendations(null, 5);
        ProvenanceData provenanceData = (ProvenanceData) recommender.getProvenanceData();
        return movieRepository.findMoviesByIds(recommendationIds);
    }
}
