package br.com.pointel.wiz_jarch.mage;

/**
 * A null-safe utility class for evaluating, converting, and comparing {@link Boolean} and primitive {@code boolean} values.
 * <p>
 * {@code WizBoolean} provides static methods engineered to seamlessly parse strings and numbers into boolean values,
 * execute logical operations (AND, OR, XOR) over collections of booleans, and cleanly manage states while preventing
 * {@link NullPointerException}s when operating on uninitialized object instances.
 * </p>
 */
public class WizBoolean {

    private WizBoolean() {
    }

    /**
     * Determines whether the given object is natively compatible or actively translatable into a {@link Boolean}.
     * <p>
     * Supported mapping formats encompass active {@link Boolean} instances, {@link Number} formats (evaluated by value),
     * or standard {@link String} structures.
     * </p>
     *
     * @param value the target object to evaluate
     * @return {@code true} if the object can map directly into a {@link Boolean}; {@code false} otherwise (or if the input is null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts a supported, versatile object format structurally into a {@link Boolean}.
     * <p>
     * {@link Number} inputs evaluate to {@code true} strictly if they are greater than zero.
     * Textual string inputs evaluate utilizing standard {@link Boolean#parseBoolean(String)} logic.
     * Blank strings evaluate structurally to {@code null}.
     * </p>
     *
     * @param value the raw structural parameter aimed for translation
     * @return the successfully mapped {@link Boolean}, or {@code null} if the input is identically null or a blank string
     * @throws Exception if the targeted value falls outside supported types or fails translation
     */
    public static Boolean get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return Boolean.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).intValue() > 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Boolean.parseBoolean(string);
        }
        throw new Exception("Could not convert to an Boolean value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a {@link Boolean} object as a standard textual string.
     *
     * @param value the {@link Boolean} targeted for formatting
     * @return {@code "true"} or {@code "false"} based on the object state, or an empty string ({@code ""}) if the value is null
     */
    public static String format(Boolean value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts an object into a {@link Boolean}, providing a protective fallback in case of translation failure.
     * <p>
     * Any structural or formatting exception thrown during processing is suppressed, deferring back to the provided fallback.
     * </p>
     *
     * @param value     the raw parameter queued for translation
     * @param orDefault the default structured {@link Boolean} returned upon an aborted conversion attempt
     * @return the correctly generated {@link Boolean}, or the designated fallback default
     */
    public static Boolean get(Object value, Boolean orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Safely checks if a provided {@link Boolean} evaluates identically to {@link Boolean#TRUE}.
     *
     * @param value the {@link Boolean} to inspect
     * @return {@code true} if the object evaluates exactly to true; {@code false} if it evaluates to false or is null
     */
    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    /**
     * Safely checks if a provided {@link Boolean} does not evaluate to {@link Boolean#TRUE}.
     *
     * @param value the {@link Boolean} to inspect
     * @return {@code true} if the object evaluates to false or is identically null; {@code false} if the object evaluates strictly to true
     */
    public static boolean isNotTrue(Boolean value) {
        return !isTrue(value);
    }

    /**
     * Safely checks if a provided {@link Boolean} evaluates identically to {@link Boolean#FALSE}.
     *
     * @param value the {@link Boolean} to inspect
     * @return {@code true} if the object evaluates exactly to false; {@code false} if it evaluates to true or is null
     */
    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    /**
     * Safely checks if a provided {@link Boolean} does not evaluate to {@link Boolean#FALSE}.
     *
     * @param value the {@link Boolean} to inspect
     * @return {@code true} if the object evaluates to true or is identically null; {@code false} if the object evaluates strictly to false
     */
    public static boolean isNotFalse(Boolean value) {
        return !isFalse(value);
    }

    /**
     * Downgrades a structured {@link Boolean} object into a primitive {@code boolean}.
     * <p>
     * Standardizes unbound {@code null} parameters by safely mapping them strictly to {@code false}.
     * </p>
     *
     * @param value the {@link Boolean} to evaluate
     * @return the correctly primitive boolean extraction, or {@code false} if the value object evaluates to null
     */
    public static boolean toPrimitive(Boolean value) {
        return value != null ? value : false;
    }

    /**
     * Downgrades a structured {@link Boolean} object into a primitive {@code boolean}, permitting a defined fallback default.
     *
     * @param value         the {@link Boolean} to evaluate
     * @param defaultIfNull the explicit primitive fallback returned if the object parameter evaluates to null
     * @return the correctly primitive boolean extraction, or the fallback constraint
     */
    public static boolean toPrimitive(Boolean value, boolean defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Computes the absolute logical negation (inverse) of a structured {@link Boolean} object.
     * <p>
     * Bypasses internal object unboxing by checking for null states explicitly, which are returned structurally unaffected.
     * </p>
     *
     * @param value the parameter targeted for negation
     * @return a new {@link Boolean} charting the inverse, or {@code null} if the provided original parameter was identically null
     */
    public static Boolean negate(Boolean value) {
        if (value == null) return null;
        return !value;
    }

    /**
     * Evaluates a sequential array of primitive boolean segments via a comprehensive logical {@code AND} gate.
     *
     * @param values an array containing standard boolean states
     * @return {@code true} only if all array variables evaluate to true. Aborts parsing early upon detecting any {@code false} index. Yields {@code false} for empty arrays.
     */
    public static boolean and(boolean... values) {
        if (values == null || values.length == 0) return false;
        for (boolean value : values) {
            if (!value) return false;
        }
        return true;
    }

    /**
     * Evaluates a sequential array of primitive boolean segments via a comprehensive logical {@code OR} gate.
     *
     * @param values an array containing standard boolean states
     * @return {@code true} if any index evaluates internally to true. Aborts parsing early upon detecting an initial {@code true} state. Yields {@code false} for empty arrays.
     */
    public static boolean or(boolean... values) {
        if (values == null || values.length == 0) return false;
        for (boolean value : values) {
            if (value) return true;
        }
        return false;
    }

    /**
     * Evaluates a sequential array of primitive boolean segments via a comprehensive logical {@code XOR} (exclusive OR) gate.
     *
     * @param values an array containing standard boolean states
     * @return {@code true} if an odd culmination of internal values register as true; {@code false} otherwise (or for empty arrays)
     */
    public static boolean xor(boolean... values) {
        if (values == null || values.length == 0) return false;
        boolean result = false;
        for (boolean value : values) {
            result ^= value;
        }
        return result;
    }

    /**
     * Flexibly converts a structured {@link Boolean} into an integer footprint relying on custom bounds explicitly defined
     * per logical state configuration.
     *
     * @param value      the object scheduled for state analysis
     * @param trueValue  the integer generated if the parameter equates explicitly to true
     * @param falseValue the integer generated if the parameter equates explicitly to false
     * @param nullValue  the integer generated if the parameter natively registers as null
     * @return the appropriately mapped custom integer code
     */
    public static int toInteger(Boolean value, int trueValue, int falseValue, int nullValue) {
        if (value == null) return nullValue;
        return value ? trueValue : falseValue;
    }
    
    /**
     * Converts a structured {@link Boolean} into a standard binary-compliant integer footprint footprint.
     * <p>
     * Explicit mappings resolve {@code true} as {@code 1}, while both {@code false} and {@code null} coalesce functionally to {@code 0}.
     * </p>
     *
     * @param value the object scheduled for standard binary translation
     * @return {@code 1} if exactly true; {@code 0} if evaluated as false or entirely absent
     */
    public static int toInteger(Boolean value) {
        return toInteger(value, 1, 0, 0);
    }

    /**
     * Flexibly converts a structured {@link Boolean} into a custom text output relying on mapping bounds explicitly defined
     * per logical state configuration.
     *
     * @param value       the object scheduled for translation mapping
     * @param trueString  the mapped output utilized if the parameter equates explicitly to true
     * @param falseString the mapped output utilized if the parameter equates explicitly to false
     * @param nullString  the mapped output utilized if the parameter natively registers as null
     * @return the appropriately mapped textual framework
     */
    public static String toString(Boolean value, String trueString, String falseString, String nullString) {
        if (value == null) return nullString;
        return value ? trueString : falseString;
    }

}
