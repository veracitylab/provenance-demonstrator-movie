package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private final MovieRepository movieRepository;

    public LibraryService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
}
