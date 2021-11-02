package nz.ac.canterbury.dataprovenancedemo.database.repositories;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MovieRepository extends Repository<Movie, Integer> {

    List<Movie> findMovieByTitleContaining(String titleSearch);
}
