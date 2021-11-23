package nz.ac.canterbury.dataprovenancedemo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation set will be used to trace how an algorithm performs some action
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Trace {
}
