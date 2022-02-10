package nz.ac.canterbury.dataprovenancedemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main controller of the application. Only responsible for handling the landing page.
 */
@Controller
public class MainController {

    /**
     * Handles requests for the index page of the website.
     * @return Rendered HTML page using thymeleaf template.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
