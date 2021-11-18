package nz.ac.canterbury.dataprovenancedemo.database.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Ratings")
@IdClass(RatingPK.class)
public class Rating {

    @Id
    @Column(name = "movie_id")
    private int movieId;

    @Id
    @Column(name = "user")
    private String username;

    @Column(name = "rating")
    private int rating;

    protected Rating() {}

    protected String getUsername() {
        return username;
    }

    public int getRating() {
        if (rating < 1) return 1;
        if (rating > 5) return 5;
        return rating;
    }
}

class RatingPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "user")
    private String username;
}
