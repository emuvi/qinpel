package br.com.pointel.wiz_jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used as a marker to exclude a specific field or parameter
 * from automatic mapping or configuration injection.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFix {
}
