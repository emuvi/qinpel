package br.com.pointel.wiz_jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used to inject or enforce a fixed object instantiation
 * based on a specific class type (and optional string value) on a field
 * during mapping or configuration.
 *
 * <p>This annotation works heavily in conjunction with the {@link FixVals} interface.
 * When a class implements {@link FixVals}, fields annotated with {@code @FixObject}
 * will be automatically instantiated and injected during the null-resolution phase,
 * invoked via {@link FixVals#fixNulls()} or {@link FixVals#fixNullsAndEnvs()}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FixObject {
    String value();
    Class<?> type();
}
