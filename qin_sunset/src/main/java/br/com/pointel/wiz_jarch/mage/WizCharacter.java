package br.com.pointel.wiz_jarch.mage;

/**
 * A null-safe utility class for evaluating, converting, and analyzing {@link Character} objects.
 * <p>
 * {@code WizCharacter} provides static methods engineered to safely parse multiple data types (such as numbers and strings)
 * strictly into characters, execute robust character inspections (like checking for ASCII limits, whitespaces, or casing), 
 * and manage fallback states. It manages {@code null} parameters uniformly to avert standard {@link NullPointerException}s.
 * </p>
 */
public class WizCharacter {

    private WizCharacter() {
    }

    /**
     * Determines whether the given object is natively compatible or actively translatable into a {@link Character}.
     * <p>
     * Supported mapping formats encompass active {@link Character} instances, any {@link Number} formats 
     * (interpreting integer values as char codes), and standard {@link String} representations.
     * </p>
     *
     * @param value the target object to evaluate
     * @return {@code true} if the object can map directly into a {@link Character}; {@code false} otherwise (or if the input is null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Character.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts a supported, versatile object format structurally into a {@link Character}.
     * <p>
     * {@link Number} inputs translate using their explicitly mapped {@code intValue()} as standard character codes. 
     * Textual string inputs evaluate solely against their initial mapped index component (position {@code 0}). 
     * Blank strings evaluate structurally strictly to {@code null}.
     * </p>
     *
     * @param value the raw structural parameter aimed for translation
     * @return the successfully mapped {@link Character}, or {@code null} if the input is identically null or a blank string
     * @throws Exception if the targeted value falls outside supported types or fails translation
     */
    public static Character get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Character.class)) {
            return Character.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return (char) Number.class.cast(value).intValue();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return string.charAt(0);
        }
        throw new Exception("Could not convert to a Character value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a {@link Character} object as a standard textual string.
     *
     * @param value the {@link Character} targeted for formatting
     * @return the natively converted textual string, or an empty string ({@code ""}) if the value is null
     */
    public static String format(Character value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts an object into a {@link Character}, providing a protective fallback in case of translation failure.
     * <p>
     * Any structural or formatting exception thrown during processing is suppressed, deferring back to the provided fallback.
     * </p>
     *
     * @param value     the raw parameter queued for translation
     * @param orDefault the default structured {@link Character} returned upon an aborted conversion attempt
     * @return the correctly generated {@link Character}, or the designated fallback default
     */
    public static Character get(Object value, Character orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Downgrades a structured {@link Character} object into a primitive {@code char}.
     * <p>
     * Standardizes unbound {@code null} parameters by safely mapping them strictly to the fundamental null character ({@code '\u0000'}).
     * </p>
     *
     * @param value the {@link Character} to evaluate
     * @return the correctly primitive character extraction, or {@code '\u0000'} if the value object evaluates to null
     */
    public static char toPrimitive(Character value) {
        return value != null ? value : '\u0000';
    }

    /**
     * Downgrades a structured {@link Character} object into a primitive {@code char}, permitting a defined fallback default.
     *
     * @param value         the {@link Character} to evaluate
     * @param defaultIfNull the explicit primitive fallback returned if the object parameter evaluates to null
     * @return the correctly primitive character extraction, or the fallback constraint
     */
    public static char toPrimitive(Character value, char defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two {@link Character} instances for exact equality.
     *
     * @param c1 the first variable
     * @param c2 the second variable
     * @return {@code true} if both equal each other; {@code false} if their numeric values differ, or if exactly one of them is null
     */
    public static boolean isEqual(Character c1, Character c2) {
        if (c1 == c2) return true;
        if (c1 == null || c2 == null) return false;
        return c1.equals(c2);
    }

    /**
     * Assesses whether a specific character safely fits within the baseline ASCII framework bounds (mapped under limit {@code 128}).
     *
     * @param value the object actively undergoing constraint verification
     * @return {@code true} provided the evaluated character respects ASCII constraints strictly; {@code false} otherwise or assuming null
     */
    public static boolean isAscii(Character value) {
        return value != null && value < 128;
    }

    /**
     * Assesses whether a specific character safely fits within standard printable ASCII format bounds (inclusive numeric mapping {@code 32-126}).
     *
     * @param value the object actively undergoing constraint verification
     * @return {@code true} provided the evaluated character rests exactly within visible ASCII domains; {@code false} otherwise or assuming null
     */
    public static boolean isAsciiPrintable(Character value) {
        return value != null && value >= 32 && value < 127;
    }

    /**
     * Determines whether a given character maps natively as a standard textual letter configuration (e.g. {@code a-z}, {@code A-Z}, or Unicode letter constraints).
     *
     * @param value the target constraint undergoing measurement
     * @return {@code true} resolving successfully matching recognized native lettering formats; {@code false} natively rejecting non-letters or handling a null bound
     */
    public static boolean isLetter(Character value) {
        return value != null && Character.isLetter(value);
    }

    /**
     * Determines whether a given character maps natively as a standard textual digit configuration (e.g. {@code 0-9}).
     *
     * @param value the target constraint undergoing measurement
     * @return {@code true} resolving successfully matching recognized numeric mappings; {@code false} natively rejecting non-numeric tokens or handling a null bound
     */
    public static boolean isDigit(Character value) {
        return value != null && Character.isDigit(value);
    }
    
    /**
     * Determines whether a given character maps natively as either a standard textual letter configuration or a mathematical digit.
     *
     * @param value the target constraint undergoing measurement
     * @return {@code true} if mapping constraints cover textual letters or explicitly valid digit patterns; {@code false} rejecting symbols or uninstantiated targets
     */
    public static boolean isLetterOrDigit(Character value) {
        return value != null && Character.isLetterOrDigit(value);
    }

    /**
     * Determines whether a given character evaluates inherently as whitespace formats (e.g., standard spaces, structural tabs, or native newlines).
     *
     * @param value the target constraint undergoing evaluation
     * @return {@code true} acknowledging valid whitespace structures; {@code false} dismissing other valid characters or managing null constraints cleanly
     */
    public static boolean isWhitespace(Character value) {
        return value != null && Character.isWhitespace(value);
    }

    /**
     * Determines whether a textual character is explicitly mapped against standard uppercase casing rules.
     *
     * @param value the target character checking bounds limit
     * @return {@code true} provided the instance verifies uppercase mapping bounds strictly; {@code false} handling null mappings or alternate cases
     */
    public static boolean isUpperCase(Character value) {
        return value != null && Character.isUpperCase(value);
    }

    /**
     * Determines whether a textual character is explicitly mapped against standard lowercase casing rules.
     *
     * @param value the target character checking bounds limit
     * @return {@code true} provided the instance verifies lowercase mapping bounds strictly; {@code false} handling null mappings or alternate cases
     */
    public static boolean isLowerCase(Character value) {
        return value != null && Character.isLowerCase(value);
    }

    /**
     * Converts a baseline textual parameter dynamically forcing bounds towards its exact standardized uppercase structure limit.
     *
     * @param value the character undergoing structural reconfiguration
     * @return the properly forced mapping instance mirroring standard uppercase mappings, or {@code null} managing missing elements dynamically
     */
    public static Character toUpperCase(Character value) {
        if (value == null) return null;
        return Character.toUpperCase(value);
    }

    /**
     * Converts a baseline textual parameter dynamically forcing bounds towards its exact standardized lowercase structure limit.
     *
     * @param value the character undergoing structural reconfiguration
     * @return the properly forced mapping instance mirroring standard lowercase mappings, or {@code null} managing missing elements dynamically
     */
    public static Character toLowerCase(Character value) {
        if (value == null) return null;
        return Character.toLowerCase(value);
    }
    
    /**
     * Compares two structural Character arrays lexicographically mapping mathematically exactly via standard character translation boundaries dynamically.
     * <p>
     * Uninstantiated null bindings natively structure systematically translating as implicitly 'lesser' constraints relative to initialized sequences.
     * </p>
     *
     * @param c1 the baseline structural leading bound sequence mapping systematically
     * @param c2 the bounding parameter component tracking exact sequential bounds offsets
     * @return a definitively negative constraint mapping resolving {@code c1 < c2}, zero matching symmetrically {@code c1 == c2}, or identically positive bounding tracking logically {@code c1 > c2}
     */
    public static int compare(Character c1, Character c2) {
        if (c1 == c2) return 0;
        if (c1 == null) return -1;
        if (c2 == null) return 1;
        return c1.compareTo(c2);
    }

}
