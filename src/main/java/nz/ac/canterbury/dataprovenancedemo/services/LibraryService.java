package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final MovieRepository movieRepository;

    @Autowired
    public LibraryService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> searchMovieTitle(String title) {
        List<Movie> result = movieRepository.findMovieByTitleContaining(title);

        return null;
    }

    public List<Movie> getAllMovies() {
        Page<Movie> res = movieRepository.findAll(PageRequest.of(0, 100));
        return res.toList();
    }
}
