package nz.ac.canterbury.dataprovenancedemo.database.model;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Movies")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String title;

    @Column(name = "release_year")
    private Date releaseYear;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "genres")
    private String genres;

    @OneToMany
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private List<Rating> ratings;

    @Transient
    private Optional<Integer> populatedRating;

    protected Movie() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public String getReleaseYearString() {
        final String format = "yyyy";
        final SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter.format(releaseYear);
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getGenres() {
        return genres;
    }

    public Optional<Integer> getPopulatedRating() {
        return populatedRating;
    }

    public Rating getUserRating(String username) {
        return this.ratings.stream()
                .filter(rating -> rating.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void populateRating(String username) {
        this.ratings.stream()
                .filter(rating -> rating.getUsername().equals(username))
                .findFirst()
                .ifPresent(r -> this.populatedRating = Optional.of(r.getRating()));
    }
}
