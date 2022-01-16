package nz.ac.canterbury.dataprovenancedemo.controllers;

import com.google.common.collect.Iterables;
import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Rating;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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

        if (titleSearch.isPresent()) {
            model.addAttribute("searchTerm", titleSearch.get());
            moviePage = libraryService.getMoviesByTitle(currPage, titleSearch.get());
        } else {
            moviePage = libraryService.getMovies(currPage);
        }

        Iterable<List<Movie>> movieSubLists = Iterables.partition(moviePage, 5);

        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("currentPage", moviePage.getNumber());
        model.addAttribute("movieSubLists", movieSubLists);

        return "library";
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Movie> movieDetail(HttpServletRequest request, @PathVariable(value="movieId") int id) {
        Optional<Movie> movie = libraryService.getMovie(id);
        Principal principal = request.getUserPrincipal();

        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Movie movieDetail = movie.get();

        if (principal != null) {
            movieDetail.populateRating(principal.getName());
        }

        return ResponseEntity.ok().body(movieDetail);
    }

    @PutMapping(value = "/movie/rate")
    public ResponseEntity<String> rateMovie(HttpServletRequest request, @RequestBody String data) {
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        // JSON coverter was busted for some reason, enjoy this tasty member here.
        String[] d = data.split("&");
        int movieId = Integer.parseInt(d[0].split("=")[1]);
        int stars = Integer.parseInt(d[1].split("=")[1]);

        Rating rating = new Rating(principal.getName(), movieId, stars);

        libraryService.rateMovie(rating);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recommendations")
    public String libraryRecommendationPage(HttpServletRequest request) {
        // For customised recommendations
        Principal principal = request.getUserPrincipal();

        List<Recommendation> rec = recommendationService.getRecommendations();

        return "recommendations";
    }

//    @GetMapping("/json/recommendations")
//    ResponseEntity<String> libraryRecommendations() {
//
//        List<Recommendation> rec = recommendationService.getRecommendations();
//        try {
//            return ResponseEntity
//                    .ok()
//                    .header("Provenance-ID", rec.getId())
//                    .body(rec.getMoviesAsJson());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.internalServerError().build();
//    }

    /**
     * Handles requests for provenance data for a given ID
     * @param id ID to obtain provenance data from
     * @return
     */
    @GetMapping("/provenance/{id}")
    ResponseEntity<String> provenanceHandler(@PathVariable String id) {

        Optional<?> provenanceData = recommendationService.getProvenanceData(id);

        return provenanceData.map(o -> ResponseEntity.ok(o.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
