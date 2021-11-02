package nz.ac.canterbury.dataprovenancedemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {

    @GetMapping("/library")
    public String libraryLanding() {
        return "";
    }
}
