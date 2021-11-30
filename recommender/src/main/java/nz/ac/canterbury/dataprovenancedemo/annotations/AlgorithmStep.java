package nz.ac.canterbury.dataprovenancedemo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface AlgorithmStep {

    String name();
    int order() default 0;
}
