package nz.ac.canterbury.dataprovenancedemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Registration of the feedback interceptor.
 * @author jens dietrich
 */

@Component
public class ProvenanceInterceptorAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    ProvenanceInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).order(Ordered.HIGHEST_PRECEDENCE);
    }
}
