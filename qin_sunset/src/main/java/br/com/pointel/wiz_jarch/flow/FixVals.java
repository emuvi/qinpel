package br.com.pointel.wiz_jarch.flow;

import java.io.Serializable;

import br.com.pointel.wiz_jarch.mage.WizArray;
import br.com.pointel.wiz_jarch.mage.WizLang;

/**
 * An interface that grants classes the ability to automatically resolve
 * null values and evaluate environment variables across all their member fields.
 * Works heavily in conjunction with {@link WizLang} reflection utilities and
 * the {@code @Fix} family of annotations (such as {@link FixBool}, {@link FixInt},
 * {@link FixChars}, {@link FixObject}, etc.) to inject configured fixed values
 * or evaluate environmental overrides.
 */
public interface FixVals extends Serializable {

    /**
     * Executes both null resolution and environment variable substitution 
     * across the object's fields.
     *
     * @throws Exception if reflection or substitution fails
     */
    public default void fixNullsAndEnvs() throws Exception {
        fixNulls();
        fixEnvs();
    }

    /**
     * Scans the object's fields and injects configured default values into any fields
     * that are currently null. Respects {@code @NotFixNulls} annotations.
     * If the object is an array or collection, it propagates the fix to its elements.
     *
     * @throws Exception if reflection fails
     */
    public default void fixNulls() throws Exception {
        if (WizArray.is(this)) {
            for (var item : WizArray.get(this)) {
                if (item instanceof FixVals fixable) {
                    fixable.fixNulls();
                }
            }
        } else {
            WizLang.fixNullsOnMembers(this);
        }
        fixDefaults();
    }

    /**
     * A lifecycle hook called immediately after nulls are fixed.
     * Implementing classes can override this to perform custom programmatic
     * default assignments that cannot be handled via annotations.
     *
     * @throws Exception if custom logic fails
     */
    public default void fixDefaults() throws Exception {}

    /**
     * Scans the object's fields and resolves any strings matching environment variable
     * patterns (e.g. ${ENV_VAR}) into their actual system environment values.
     * Respects {@code @NotFixEnvs} annotations.
     *
     * @throws Exception if reflection or environment extraction fails
     */
    public default void fixEnvs() throws Exception {
        if (WizArray.is(this)) {
            for (var item : WizArray.get(this)) {
                if (item instanceof FixVals fixable) {
                    fixable.fixEnvs();
                }
            }
        } else {
            WizLang.fixEnvsOnMembers(this);
        }
    }

}
