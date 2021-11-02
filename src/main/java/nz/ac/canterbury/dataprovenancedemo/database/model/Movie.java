package nz.ac.canterbury.dataprovenancedemo.database.model;

import javax.persistence.*;
import java.sql.Date;

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

    private String genres;

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

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getGenres() {
        return genres;
    }
}
