package nz.ac.canterbury.dataprovenancedemo.controllers;

import com.google.common.collect.Iterables;
import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Rating;
import nz.ac.canterbury.dataprovenancedemo.services.LibraryService;
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
import java.util.Map;
import java.util.Optional;
import java.io.*;
import java.net.*;

/**
 * This controller is responsible for handling requests relating to the retrieval of library pages and movie information.
 * The endpoints specified in this controller are as follows:
 * <ul>
 *     <li>"/library" This endpoint returns the rendered HTML page of the library.</li>
 *     <li>"/library.json" This endpoint returns the JSON representation of the library.</li>
 *     <li>"/movie/{id}" This endpoint returns JSON representation of a selected movie.</li>
 *     <li>"/movie/rate" This endpoint is used to rate a movie, if the request is authenticated.</li>
 * </ul>
 */
@Controller
public class LibraryController {
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);
    private final LibraryService libraryService;


    /**
     * Constructs the controller with DI for the library service.
     * @param libraryService The service object to be used for library related operations.
     */
    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    /**
     * Returns the library landing page rendered using thymeleaf fragments. This endpoint allows searching of movies by
     * title as well as pagination. Default page size is 20. Pagination and search are not exclusive functions.
     * @param model Spring MVC model.
     * @param pageNum Page number to go to.
     * @param titleSearch Movie title to search by.
     * @return Rendered HTML representation of the requested library page.
     */
    @GetMapping("/library")
    public String libraryPage(Model model,
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

    /**
     * Returns JSON representation of a page of the library. This endpoint will always return an object. Supports
     * pagination as well as search. These functions are not mutually exclusive.
     * @param pageNum Page number to go to.
     * @param titleSearch Movie title to search by.
     * @return JSON representation of the library page.
     */
    @GetMapping("/api/v1/library")
    public ResponseEntity<Page<Movie>> library(@RequestParam("page") Optional<Integer> pageNum,
                                               @RequestParam("title") Optional<String> titleSearch)
    {
        int currPage = pageNum.orElse(1) - 1;
        Page<Movie> moviePage;

        if (titleSearch.isPresent()) {
            moviePage = libraryService.getMoviesByTitle(currPage, titleSearch.get());
        } else {
            moviePage = libraryService.getMovies(currPage);
        }

        return ResponseEntity.ok().body(moviePage);
    }

    /**
     * Returns more details of a movie in JSON format, including ratings if the request has authentication data
     * attached.
     * @param request Request object containing authentication data.
     * @param id ID of the movie to find.
     * @return JSON representation of a movie, if present in the DB. 404 otherwise.
     */
    @GetMapping("api/v1/movie/{movieId}")
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

    /**
     * Endpoint responsible for adding a rating to a movie for a given user, if the request is authenticated.
     * @param request Request object containing authentication data.
     * @param data Body of the PUT request, containing the movie ID and the given rating.
     * @return 200 if the rating was successful, 401 if not authenticated.
     */
    @PutMapping(value = "api/v1/movie/rate")
    public ResponseEntity<String> rateMovie(HttpServletRequest request, @RequestBody Map<String, Integer> data) {
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        if (!data.containsKey("movieId") || !data.containsKey("rating")) {
            return ResponseEntity.badRequest().build();
        }

        int movieId = data.get("movieId");
        int stars = data.get("rating");

        if (stars < 1 || stars > 5) {
            return ResponseEntity.badRequest().build();
        }

        Rating rating = new Rating(principal.getName(), movieId, stars);

        libraryService.rateMovie(rating);

        // Test capture of outgoing server-side HTTP requests
        String outgoingHttpUrl = "https://app.veracity.homes/omar-notifications-main-menu.html?name=" + principal.getName() + "&movieId=" + movieId + "&stars=" + stars;
        logger.info("Server will send outgoing HTTP request to " + outgoingHttpUrl + " -- let's see if it's picked up.");
        String result = getHTML(outgoingHttpUrl);
        logger.info("Server sent outgoing HTTP request to " + outgoingHttpUrl + " and got response '" + result + "'.");

        return ResponseEntity.ok().build();
    }

    public static String getHTML(String urlToRead) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            return result.toString();
        } catch (Exception e) {
            logger.error("Yikes, an exception occurred! " + e);
            return "EXCEPTION_OCCURRED";        //HACK
        }
    }
}
