package nz.ac.canterbury.dataprovenancedemo.services;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final static int DEFAULT_PAGE_SIZE = 100;

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
        Page<Movie> res = movieRepository.findAll(PageRequest.of(0, DEFAULT_PAGE_SIZE));
        return res.toList();
    }

    /**
     * Function to obtain a page of movies with sorting enabled. Currently the default number of movies are selected.
     * @param pageNumber The page to begin at.
     * @param sortBy Field of Movie to sort by.
     * @return List of movies sorted by the provided sort field.
     */
    public List<Movie> getMoviePageWithSort(int pageNumber, String sortBy) {
        Pageable request = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE, Sort.by(sortBy));
        Page<Movie> result = movieRepository.findAll(request);
        return result.toList();
    }
}
