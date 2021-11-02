package nz.ac.canterbury.dataprovenancedemo.database.model;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

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
}
