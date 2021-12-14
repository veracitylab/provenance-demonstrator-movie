package nz.ac.canterbury.dataprovenancedemo.controllers;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Recommendation;
import nz.ac.canterbury.dataprovenancedemo.services.LibraryService;
import nz.ac.canterbury.dataprovenancedemo.services.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class LibraryController {
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);
    private final LibraryService libraryService;
    private final RecommendationService recommendationService;

    @Autowired
    public LibraryController(LibraryService libraryService, RecommendationService recommendationService) {
        this.libraryService = libraryService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/library")
    public String libraryLanding(Model model,
                                 @RequestParam("page") Optional<Integer> pageNum,
                                 @RequestParam("title") Optional<String> titleSearch)
    {
        int currPage = pageNum.orElse(1) - 1;
        Page<Movie> moviePage;
        List<Movie> tempMoveRecommendations = recommendationService.getRecommendations().getMovies();

        if (titleSearch.isPresent()) {
            model.addAttribute("searchTerm", titleSearch.get());
            moviePage = libraryService.getMoviesByTitle(currPage, titleSearch.get());
        } else {
            moviePage = libraryService.getMovies(currPage);
        }

        model.addAttribute("recommendations", tempMoveRecommendations);
        model.addAttribute("movies", moviePage);

        return "library";
    }

    @GetMapping("/recommendations")
    ResponseEntity<String> libraryRecommendations() {

        Recommendation rec = recommendationService.getRecommendations();
        try {
            return ResponseEntity
                    .ok()
                    .header("Provenance-ID", rec.getId())
                    .body(rec.asJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/provenance/{id}")
    ResponseEntity<String> provenanceHandler() {
        return null;
    }
}
