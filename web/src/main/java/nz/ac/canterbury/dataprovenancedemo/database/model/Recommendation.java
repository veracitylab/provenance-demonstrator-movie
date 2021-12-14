package nz.ac.canterbury.dataprovenancedemo.database.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * POJO object to return to client for JSON parsing
 */
public class Recommendation {

    private final String id;
    private final List<Movie> movies;

    public Recommendation(String id, List<Movie> movies) {
        this.id = id;
        this.movies = movies;
    }

    public String getId() {
        return this.id;
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    /**
     * Converts the object to JSON using ObjectMapper serialization
     * @return String containing the JSON representation of the data
     * @throws IOException If there is an issue serializing the object
     */
    public String asJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
