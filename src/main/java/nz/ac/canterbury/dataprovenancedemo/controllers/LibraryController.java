package nz.ac.canterbury.dataprovenancedemo.controllers;

import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.services.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LibraryController {
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);
    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @GetMapping("/library")
    public String libraryLanding(Model model) {
        List<Movie> displayMovies = service.getAllMovies();
        logger.debug(String.format("Loaded %s movies", displayMovies.size()));
        model.addAttribute("movies", displayMovies);
        return "library";
    }
}
