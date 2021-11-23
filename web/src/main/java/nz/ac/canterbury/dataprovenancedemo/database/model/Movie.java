package nz.ac.canterbury.dataprovenancedemo.database.model;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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

    public Rating getUserRating(String username) {
        return this.ratings.stream()
                .filter(rating -> rating.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
