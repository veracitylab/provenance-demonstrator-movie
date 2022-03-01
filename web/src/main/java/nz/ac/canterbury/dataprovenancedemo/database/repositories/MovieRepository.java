package nz.ac.canterbury.dataprovenancedemo.database.repositories;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Responsible for handling the interactions between services and the configured data source for movies.
 */
public interface MovieRepository extends Repository<Movie, Integer> {

    /**
     * Gets a page of movies in the database according to the page description.
     * @param pageable Page description to use when retrieving movies, contains parameters such as number or movies to
     *                 retrieve and what page number to retrieve.
     * @return A page of movies matching the description provided by pageable
     */
    Page<Movie> findAll(Pageable pageable);

    /**
     * Gets a specific movie with a given ID.
     * @param id ID of the movie to find.
     * @return Optional containing the movie if the ID is present, null otherwise.
     */
    Optional<Movie> findMovieById(int id);

    /**
     * Finds movies in the database by title and according to the page description.
     * @param titleSearch Title to search by.
     * @param pageable Page description to use when retrieving movies, contains parameters such as number or movies to
     *                 retrieve and what page number to retrieve.
     * @return A page of movies matching the description provided by titleSearch and pageable.
     */
    Page<Movie> findMovieByTitleContainingIgnoreCase(String titleSearch, Pageable pageable);

    /**
     * Custom query to find all movies given a collection of ID's.
     * @param movieIds List of movie IDs to find
     * @return List of movies who's IDs match the provided list.
     */
    @Query("Select m from Movie m where m.id in :ids")
    List<Movie> findMoviesByIds(@Param("ids") List<Integer> movieIds);

    /**
     * Custom query to find all movie ID's
     * @return List of Movie ID's
     */
    @Query("Select m.id from Movie m")
    List<Integer> findAllMovieIds();
}
