package nz.ac.canterbury.dataprovenancedemo.controllers;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.wgtn.veracity.provenance.injector.tracker.ProvenanceTracker;
import nz.ac.wgtn.veracity.provenance.injector.model.Invocation;
import nz.ac.wgtn.veracity.provenance.injector.instrumentation.ProvenanceAgent;

/**
 * Intercept requests in order to append a header with a unique id that can be used by a provenance-collecting client
 * to pick up information gathered via instrumentation.
 */

@Component
public class ProvenanceTrackerFilter implements Filter {
    public static final String X_CLACKS_OVERHEAD = "X-Clacks-Overhead";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("ProvenanceTrackerFilter.doFilter() called!");   //DEBUG
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader(X_CLACKS_OVERHEAD, "GNU Terry Pratchett");
        chain.doFilter(req, res);
        System.out.println("ProvenanceTrackerFilter.doFilter() returning!");   //DEBUG
    }
}
