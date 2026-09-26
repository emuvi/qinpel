package br.com.pointel.wiz_jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used for overriding the default mapping name of a field, method, or class.
 * Useful for ORM mappings or serialization boundaries.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MapName {
    /**
     * @return the explicit name override
     */
    String value();
}
