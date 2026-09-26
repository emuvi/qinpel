package br.com.pointel.wiz_jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used as a marker to instruct {@link FixVals#fixEnvs()}
 * to skip environment variable substitution for the annotated field or parameter.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFixEnvs {
}
