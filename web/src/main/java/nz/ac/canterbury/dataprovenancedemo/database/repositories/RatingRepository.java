package nz.ac.canterbury.dataprovenancedemo.database.repositories;

import nz.ac.canterbury.dataprovenancedemo.database.model.Rating;
import org.springframework.data.repository.Repository;

public interface RatingRepository extends Repository<Rating, Integer> {
    void save(Rating rating);
}
