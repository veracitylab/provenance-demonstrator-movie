package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Rating;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsible for handling actions relating to the retrieval of movies and
 * modification of movie ratings.
 */
@Service
public class LibraryService {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;


    /**
     * Constructs the service using DI for the movie repository and the rating repository.
     * @param movieRepository Repository object used for retrieving movies.
     * @param ratingRepository Repository object used for interacting with ratings.
     */
    @Autowired
    public LibraryService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }


    /**
     * Gets a page of movies at the specified page number.
     * @param pageNum Page number to get movies from.
     * @return A page of unsorted movies.
     */
    public Page<Movie> getMovies(int pageNum) {
        return movieRepository.findAll(PageRequest.of(pageNum, DEFAULT_PAGE_SIZE));
    }

    /**
     * Gets a movie with a specific ID.
     * @param id ID of the movie to get
     * @return The movie if the ID is present, empty optional if not.
     */
    public Optional<Movie> getMovie(int id) {
        return movieRepository.findMovieById(id);
    }

    /**
     * Searches the movie database, including ability to paginate.
     * @param pageNum Page number to query.
     * @param title Title to search by, the case is ignored.
     * @return A Page of movies filtered by their title.
     */
    public Page<Movie> getMoviesByTitle(int pageNum, String title) {
        return movieRepository.findMovieByTitleContainingIgnoreCase(title, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE));
    }

    /**
     * Inserts a given rating into the database.
     * @param rating The rating with relevant information to be inserted.
     */
    public void rateMovie(Rating rating) {
        ratingRepository.save(rating);
    }
}
