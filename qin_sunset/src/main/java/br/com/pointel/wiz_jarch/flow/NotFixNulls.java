package br.com.pointel.wiz_jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used as a marker to instruct {@link FixVals#fixNulls()}
 * to skip default value injection for the annotated field or parameter if it is null.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFixNulls {
}
