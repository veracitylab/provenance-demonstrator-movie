package nz.ac.canterbury.dataprovenancedemo.database.repositories;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MovieRepository extends Repository<Movie, Integer> {

    Page<Movie> findAll(Pageable pageable);
    Optional<Movie> findMovieById(int id);
    Page<Movie> findMovieByTitleContainingIgnoreCase(String titleSearch, Pageable pageable);
}
