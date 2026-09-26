package br.com.pointel.wiz_jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used to inject or enforce a fixed {@code double} value
 * on a field during mapping or configuration.
 *
 * <p>This annotation works heavily in conjunction with the {@link FixVals} interface.
 * When a class implements {@link FixVals}, fields annotated with {@code @FixDouble}
 * will have this fixed value automatically resolved and injected during the
 * null-resolution phase, invoked via {@link FixVals#fixNulls()} or {@link FixVals#fixNullsAndEnvs()}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FixDouble {
    /**
     * @return the fixed double value
     */
    double value();
}
