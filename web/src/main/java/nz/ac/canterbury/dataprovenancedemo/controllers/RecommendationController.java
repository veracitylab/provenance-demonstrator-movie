package nz.ac.canterbury.dataprovenancedemo.controllers;

import com.google.common.collect.Iterables;
import nz.ac.canterbury.dataprovenancedemo.database.model.Movie;
import nz.ac.canterbury.dataprovenancedemo.database.model.Recommendation;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;
import nz.ac.canterbury.dataprovenancedemo.services.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This controller is responsible for handling requests relating to retrieving recommendations and provenance data.
 * The endpoints specified in this controller are as follows:
 * <ul>
 *     <li>"/recommendations" This endpoint returns a rendered HTML element of recommended movies.</li>
 *     <li>"/recommendations.json" This endpoint returns JSON representation of recommended movies.</li>
 *     <li>
 *         "/provenance/{ID}" This endpoint returns JSON representation of the provenance information associated with a
 *         given ID.
 *     </li>
 * </ul>
 */
@Controller
public class RecommendationController {
    private final Logger logger = LoggerFactory.getLogger(RecommendationController.class);
    private final RecommendationService recommendationService;


    /**
     * Constructs the controller with DI recommendation service.
     * @param recommendationService The service object to be used for recommendations.
     */
    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    /**
     * Obtains a list of recommendations and formats them into a thymeleaf fragment for use
     * on the demo site.
     * @param model Spring MVC model.
     * @param request Contains user authentication for custom recommendations.
     * @return HTML content of the recommendation list.
     */
    @GetMapping("/recommendations")
    public String libraryRecommendationsPage(Model model, HttpServletRequest request) {
        // For customised recommendations
        Principal principal = request.getUserPrincipal();

        List<Recommendation> recommendations = recommendationService.getRecommendations();
        Iterable<List<Recommendation>> recommendationSubLists = Iterables.partition(recommendations, 5);
        model.addAttribute("recommendationGroups", recommendationSubLists);

        return "recommendations";
    }

    /**
     * Provides a list of recommendations represented as JSON.
     * @param request Contains user authentication for custom recommendations.
     * @return JSON array of recommendations.
     */
    @GetMapping("api/v1/recommendations")
    public ResponseEntity<List<Movie>> libraryRecommendations(HttpServletRequest request) {

        List<Recommendation> rec = recommendationService.getRecommendations();
        List<Movie> movies = rec.stream().map(Recommendation::getMovie).collect(Collectors.toList());
        String provId = !rec.isEmpty() ? rec.get(0).getId() : null;

        return ResponseEntity
                .ok()
                .header("Provenance-ID", provId)
                .body(movies);
    }

    /**
     * Handles requests for provenance data for a given ID.
     * @param id ID to obtain provenance data from.
     * @return String representation of the provenance information associated with a request.
     */
    @GetMapping(value = "api/v1/provenance/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> provenanceHandler(@PathVariable String id) {

        Optional<ProvenanceData> provenanceData = recommendationService.getProvenanceData(id);

        return provenanceData.map(o -> ResponseEntity.ok(o.toJSON(true)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
