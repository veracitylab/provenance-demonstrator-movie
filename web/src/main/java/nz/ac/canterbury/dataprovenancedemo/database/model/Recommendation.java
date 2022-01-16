package nz.ac.canterbury.dataprovenancedemo.database.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * POJO object to return to client for JSON parsing
 */
public class Recommendation {

    private final String id;
    private final Movie movie;

    public Recommendation(String id, Movie movies) {
        this.id = id;
        this.movie = movies;
    }

    public String getId() {
        return this.id;
    }

    public Movie getMovie() {
        return this.movie;
    }

    /**
     * Converts the object to JSON using ObjectMapper serialization
     * @return String containing the JSON representation of the data
     * @throws IOException If there is an issue serializing the object
     */
    public String getMovieAsJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this.movie);
    }
}
