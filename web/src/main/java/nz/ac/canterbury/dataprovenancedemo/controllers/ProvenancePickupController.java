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
import org.springframework.http.HttpStatus;
import nz.ac.wgtn.veracity.provenance.injector.tracker.ProvenanceTracker;
import nz.ac.wgtn.veracity.provenance.injector.model.Invocation;
import nz.ac.wgtn.veracity.provenance.injector.instrumentation.ProvenanceAgent;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProvenancePickupController {
    private static final Logger logger = LoggerFactory.getLogger(ProvenancePickupController.class);

    protected final ProvenanceTracker<Invocation> tracker = ProvenanceAgent.getProvenanceTracker(); // Populated earlier by the Java agent

    @GetMapping("/prov/{id}")
//    public String pickUpProvenance(HttpServletRequest request) {
    //        return "<html><body>RAW HTML!</body></html>";
    //    }
    //    public ResponseEntity<String> pickUpProvenance(HttpServletRequest request, @PathVariable(value="provId") int id) {
//    public ResponseEntity<String> pickUpProvenance(HttpServletRequest request, @PathVariable(value="id") String id) {
    public ResponseEntity<List<Invocation>> pickUpProvenance(HttpServletRequest request, @PathVariable(value="id") String id) {
        //        return new ResponseEntity<>("Hello Provenance!", HttpStatus.OK);
        //        return new ResponseEntity<>("Hello, <b>provenance</b>!", HttpStatus.OK);
//        JsonProvenanceInfoPickupServlet jsonPickUpServlet = new JsonProvenanceInfoPickupServlet(null);  //TODO: Use something better than null
//        ProvenanceTracker<Invocation> tracker = null;       //TODO
//        ProvenanceTracker<Invocation> tracker = ProvenanceAgent.getProvenanceTracker();
//        return new ResponseEntity<>("Hello, <b>proovenance</b>!", HttpStatus.OK);
//        return new ResponseEntity<>("Hello, the <i>real</i> <b>proovenance</b> endpoint for ID " + id + "! tracker field=" + tracker + ".", HttpStatus.OK);
        System.out.println("BEFORE");   //DEBUG
        List<Invocation> data = tracker.pickup(id);
        System.out.println("AFTER");   //DEBUG
        tracker.cull(id);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/testprov")
    public ResponseEntity<String> testProv() {
//        System.out.println("BEFORE calling java.sql.DriverManager.getConnection()");    //DEBUG
//        try {
//            java.sql.Connection connection = java.sql.DriverManager.getConnection("bogus:database/url");
//        } catch (java.sql.SQLException e) {
//            System.out.println("Caught exception while trying to run java.sql.DriverManager.getConnection(): " + e);
//        }
//        System.out.println("AFTER calling java.sql.DriverManager.getConnection()");    //DEBUG
        System.out.println("BEFORE calling dummyStaticMethod()");    //DEBUG
        int result = dummyStaticMethod(42);
        System.out.println("AFTER calling dummyStaticMethod()");    //DEBUG

        return ResponseEntity.ok().body("Hello, wurld from the provenance testing endpoint!");      //HACK
    }

    public static int dummyStaticMethod(int x) {
        int retVal = x * 2;
        System.out.println("dummyStaticMethod(x=" + x + ") called, will return " + retVal + "!");
        return retVal;
    }
}
