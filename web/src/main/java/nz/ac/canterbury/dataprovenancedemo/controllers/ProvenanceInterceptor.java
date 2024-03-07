package nz.ac.canterbury.dataprovenancedemo.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
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
public class ProvenanceInterceptor implements HandlerInterceptor {

    private static final boolean LOG = true;

    protected final ProvenanceTracker<Invocation> tracker = ProvenanceAgent.getProvenanceTracker(); // Populated earlier by the Java agent

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (LOG) {
            request.getServletContext().log("pre handle: " + request.getServletPath());
        }

        String path = request.getServletPath();
        boolean isApplicationRequest = !path.contains("/prov");     //HACK
        if (isApplicationRequest) {
            if (tracker != null) {
                String ID = tracker.start();
                response.addHeader("provenance-id", ID);
            } else {
                //TODO: Should throw an exception here, currently not doing so for debugging
                request.getServletContext().log("pre handle: tracker is null, has the provenance agent been installed with -javaagent:.../provenance-agent.jar?");
                response.addHeader("provenance-id", "PROVENANCE-AGENT-IS-MISSING");
            }
        } else {
            request.getServletContext().log("pre handle: ignoring non-application URL " + path);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (LOG) {
            request.getServletContext().log("post handle: " + request.getServletPath());
        }

        String path = request.getServletPath();
        boolean isApplicationRequest = !path.contains("/prov");
        if (isApplicationRequest) {
            if (tracker != null) {
                tracker.finish();
            } else {
                //TODO: Should throw an exception here, currently not doing so for debugging
                request.getServletContext().log("post handle: tracker is null, has the provenance agent been installed with -javaagent:.../provenance-agent.jar?");
            }
        }
    }
}
