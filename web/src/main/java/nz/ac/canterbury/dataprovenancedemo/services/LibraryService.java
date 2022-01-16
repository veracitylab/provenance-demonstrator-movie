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

@Service
public class LibraryService {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public LibraryService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     * Gets movies with optional pagination
     * @param pageNum Page number to go to
     * @return A page of unsorted movies
     */
    public Page<Movie> getMovies(int pageNum) {
        return movieRepository.findAll(PageRequest.of(pageNum, DEFAULT_PAGE_SIZE));
    }

    public Optional<Movie> getMovie(int id) {
        return movieRepository.findMovieById(id);
    }

    /**
     * Searches the movie database, including ability to paginate
     * @param pageNum Page number to query
     * @param title Title to search by, the case is ignored
     * @return A Page of movies filtered by their title
     */
    public Page<Movie> getMoviesByTitle(int pageNum, String title) {
        return movieRepository.findMovieByTitleContainingIgnoreCase(title, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE));
    }

    /**
     * Inserts a given rating into the database
     * @param rating The rating with relevant information to be inserted
     */
    public void rateMovie(Rating rating) {
        ratingRepository.save(rating);
    }
}
