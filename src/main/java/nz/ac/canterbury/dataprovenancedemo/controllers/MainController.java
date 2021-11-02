package nz.ac.canterbury.dataprovenancedemo.controllers;


import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    private MovieRepository repository;

    @GetMapping("/")
    public String index() {
        List<Movie> res = repository.findMovieByTitleContaining("Night at ");
        res.forEach(item -> System.out.println(item.getTitle()));
        return "index";
    }
}
