package nz.ac.canterbury.dataprovenancedemo.database.repositories;

import nz.ac.canterbury.dataprovenancedemo.database.model.Rating;
import org.springframework.data.repository.Repository;

/**
 * Responsible for handling the insertion of ratings into the database.
 */
public interface RatingRepository extends Repository<Rating, Integer> {

    /**
     * Inserts a given rating into the database. Rating contains username, movieId and numerical rating.
     * @param rating The rating object to save.
     */
    void save(Rating rating);
}
