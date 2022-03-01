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
        recommender.setAvailableMovieIds(this.movieRepository.findAllMovieIds());
    }

    /**
     * Gets a list of movie recommendations from the recommender, also get the provenance information associated with
     * those recommendations. Currently provenance objects encapsulate all recommendations
     * @return List of recommendations
     */
    public List<Recommendation> getRecommendations() {

        String id = UUID.randomUUID().toString();

        // THIS IS A TEST!!!!
        Map<String, Object> testData = Map.of(
                "user_age_t", "0",
                "user_region_t", "1",
                "user_sex_t", "0",
                "user_relStat_t", "2"
        );

        List<Integer> recommendationIds = recommender.getRecommendations(testData, 5);
        List<Movie> movies = movieRepository.findMoviesByIds(recommendationIds);
        ProvenanceData provenanceData = recommender.getProvenanceData();

        provenance.put(id, provenanceData);

        return movies.stream().map(m -> new Recommendation(id, m)).collect(Collectors.toList());
    }

    /**
     * Gets provenance information for a given recommendation ID
     * @param id ID of the recommendation to retrieve provenance data for
     * @return Provenance object if it exists for that ID, empty Optional otherwise
     */
    public Optional<ProvenanceData> getProvenanceData(String id) {
        if (provenance.containsKey(id)) {
            return Optional.of(provenance.get(id));
        }
        return Optional.empty();
    }
}
