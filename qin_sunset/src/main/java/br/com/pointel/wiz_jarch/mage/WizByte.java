package br.com.pointel.wiz_jarch.mage;

/**
 * A null-safe utility class for evaluating, converting, and performing arithmetic/bitwise operations on {@link Byte} objects.
 * <p>
 * {@code WizByte} provides static methods engineered to safely parse multiple data types (like numbers, strings, and booleans)
 * into bytes, perform fundamental bitwise operations, and execute mathematics. Crucially, it manages {@code null} parameters
 * uniformly, treating them as {@code 0} to avert standard {@link NullPointerException}s during sequential logic.
 * </p>
 */
public class WizByte {

    private WizByte() {
    }

    /**
     * Determines whether the given object is natively compatible or actively translatable into a {@link Byte}.
     * <p>
     * Supported mapping formats encompass active {@link Byte} instances, any {@link Number} formats,
     * {@link Boolean} types (resolving to 1 or 0), and standard {@link String} representations.
     * </p>
     *
     * @param value the target object to evaluate
     * @return {@code true} if the object can map directly into a {@link Byte}; {@code false} otherwise (or if the input is null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Byte.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts a supported, versatile object format structurally into a {@link Byte}.
     * <p>
     * {@link Number} inputs are mapped directly via their explicit {@code byteValue()}.
     * {@link Boolean} inputs resolve {@code true} to {@code 1} and {@code false} to {@code 0}.
     * Textual string inputs evaluate utilizing standard {@link Byte#parseByte(String)} logic.
     * Blank strings evaluate structurally to {@code null}.
     * </p>
     *
     * @param value the raw structural parameter aimed for translation
     * @return the successfully mapped {@link Byte}, or {@code null} if the input is identically null or a blank string
     * @throws Exception if the targeted value falls outside supported types or structurally fails translation bounds
     */
    public static Byte get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Byte.class)) {
            return Byte.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).byteValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? (byte) 1 : (byte) 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Byte.parseByte(string);
        }
        throw new Exception("Could not convert to an Byte value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object into a {@link Byte}, providing a protective fallback in case of translation failure.
     * <p>
     * Any structural or formatting exception thrown during processing is suppressed, deferring back to the provided fallback.
     * </p>
     *
     * @param value     the raw parameter queued for translation
     * @param orDefault the default structured {@link Byte} returned upon an aborted conversion attempt
     * @return the correctly generated {@link Byte}, or the designated fallback default
     */
    public static Byte get(Object value, Byte orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a {@link Byte} object as a standard textual string.
     *
     * @param value the {@link Byte} targeted for formatting
     * @return the natively converted textual string, or an empty string ({@code ""}) if the value is null
     */
    public static String format(Byte value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Coalesces a {@code null} reference to a safe zero equivalent.
     *
     * @param value the targeted {@link Byte} to inspect
     * @return the provided value, or {@code (byte) 0} if it is null
     */
    public static Byte nullToZero(Byte value) {
        return value == null ? (byte) 0 : value;
    }

    /**
     * Coalesces a {@code null} reference to a specified fallback default.
     *
     * @param value        the targeted {@link Byte} to inspect
     * @param defaultValue the fallback returned if the targeted value proves null
     * @return the original value, or the configured fallback
     */
    public static Byte defaultIfNull(Byte value, Byte defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Downgrades a structured {@link Byte} object into a primitive {@code byte}.
     * <p>
     * Standardizes unbound {@code null} parameters by safely mapping them strictly to {@code 0}.
     * </p>
     *
     * @param value the {@link Byte} to evaluate
     * @return the correctly primitive byte extraction, or {@code 0} if the value object evaluates to null
     */
    public static byte toPrimitive(Byte value) {
        return value != null ? value : 0;
    }

    /**
     * Downgrades a structured {@link Byte} object into a primitive {@code byte}, permitting a defined fallback default.
     *
     * @param value         the {@link Byte} to evaluate
     * @param defaultIfNull the explicit primitive fallback returned if the object parameter evaluates to null
     * @return the correctly primitive byte extraction, or the fallback constraint
     */
    public static byte toPrimitive(Byte value, byte defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two {@link Byte} instances for exact numerical equality.
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return {@code true} if both equal each other; {@code false} if their numeric values differ, or if exactly one of them is null
     */
    public static boolean isEqual(Byte v1, Byte v2) {
        if (v1 == v2) return true;
        if (v1 == null || v2 == null) return false;
        return v1.equals(v2);
    }

    /**
     * Safely establishes if the first instance holds a greater numerical value than the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is strictly larger; {@code false} otherwise (or if either is null)
     */
    public static boolean isGreaterThan(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 > v2;
    }

    /**
     * Safely establishes if the first instance holds a greater or equal numerical value compared to the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is larger or equal; {@code false} otherwise (or if either is null)
     */
    public static boolean isGreaterThanOrEqual(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 >= v2;
    }

    /**
     * Safely establishes if the first instance holds a lesser numerical value than the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is strictly smaller; {@code false} otherwise (or if either is null)
     */
    public static boolean isLessThan(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 < v2;
    }

    /**
     * Safely establishes if the first instance holds a lesser or equal numerical value compared to the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is smaller or equal; {@code false} otherwise (or if either is null)
     */
    public static boolean isLessThanOrEqual(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 <= v2;
    }

    /**
     * Identifies the smaller of two given variables, accommodating partial null states.
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return the mathematically smaller object. If one is null, the valid counterpart is returned.
     */
    public static Byte min(Byte v1, Byte v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1 <= v2 ? v1 : v2;
    }

    /**
     * Identifies the larger of two given variables, accommodating partial null states.
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return the mathematically larger object. If one is null, the valid counterpart is returned.
     */
    public static Byte max(Byte v1, Byte v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1 >= v2 ? v1 : v2;
    }

    /**
     * Retrieves the absolute minimum primitive bound across an arbitrary series of input bytes.
     *
     * @param values a continuous array chain of primitive byte values
     * @return the mathematically smallest byte discovered, or {@code 0} if the array is null or empty
     */
    public static byte min(byte... values) {
        if (values == null || values.length == 0) return 0;
        byte min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return min;
    }

    /**
     * Retrieves the absolute maximum primitive bound across an arbitrary series of input bytes.
     *
     * @param values a continuous array chain of primitive byte values
     * @return the mathematically largest byte discovered, or {@code 0} if the array is null or empty
     */
    public static byte max(byte... values) {
        if (values == null || values.length == 0) return 0;
        byte max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }

    /**
     * Safely calculates the addition (sum) of two values.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow. 
     * Due to primitive boundary casting, results crossing byte limitations will overflow organically.
     * </p>
     *
     * @param v1 the first addend
     * @param v2 the second addend
     * @return a new {@link Byte} charting the sum of both numbers
     */
    public static Byte add(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) + toPrimitive(v2));
    }

    /**
     * Safely calculates the subtraction (difference) of the second value from the first.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow.
     * Due to primitive boundary casting, results crossing byte limitations will overflow organically.
     * </p>
     *
     * @param v1 the minuend
     * @param v2 the subtrahend
     * @return a new {@link Byte} mapping the resultant difference
     */
    public static Byte subtract(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) - toPrimitive(v2));
    }

    /**
     * Safely calculates the multiplication (product) of two values.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow.
     * Due to primitive boundary casting, results crossing byte limitations will overflow organically.
     * </p>
     *
     * @param v1 the first multiplier
     * @param v2 the second multiplier
     * @return a new {@link Byte} equating to the generated product
     */
    public static Byte multiply(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) * toPrimitive(v2));
    }

    /**
     * Safely calculates the integer division (quotient) of the first value by the second.
     * <p>
     * Null variables are structurally coalesced into zeros.
     * </p>
     *
     * @param v1 the baseline dividend
     * @param v2 the functional divisor
     * @return a new {@link Byte} denoting the formatted division result
     * @throws ArithmeticException if the target divisor evaluates strictly to zero
     */
    public static Byte divide(Byte v1, Byte v2) {
        byte den = toPrimitive(v2);
        if (den == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (byte) (toPrimitive(v1) / den);
    }
    
    /**
     * Safely calculates the mathematical remainder (modulo) resulting from dividing the first value by the second.
     * <p>
     * Null variables are structurally coalesced into zeros before calculation.
     * </p>
     *
     * @param v1 the baseline dividend
     * @param v2 the functional divisor
     * @return a new {@link Byte} resolving to the remainder of the division
     * @throws ArithmeticException if the target divisor evaluates strictly to zero
     */
    public static Byte remainder(Byte v1, Byte v2) {
        byte den = toPrimitive(v2);
        if (den == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (byte) (toPrimitive(v1) % den);
    }

    /**
     * Computes the bitwise {@code AND} operation spanning two defined bytes.
     * <p>
     * {@code null} references are treated functionally as zero bounds ({@code 0x00}).
     * </p>
     *
     * @param v1 the first parameter
     * @param v2 the second parameter
     * @return a newly evaluated {@link Byte} matching the calculated boolean intersect
     */
    public static Byte and(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) & toPrimitive(v2));
    }

    /**
     * Computes the bitwise {@code OR} operation spanning two defined bytes.
     * <p>
     * {@code null} references are treated functionally as zero bounds ({@code 0x00}).
     * </p>
     *
     * @param v1 the first parameter
     * @param v2 the second parameter
     * @return a newly evaluated {@link Byte} matching the calculated boolean aggregate
     */
    public static Byte or(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) | toPrimitive(v2));
    }

    /**
     * Computes the bitwise {@code XOR} (exclusive OR) operation spanning two defined bytes.
     * <p>
     * {@code null} references are treated functionally as zero bounds ({@code 0x00}).
     * </p>
     *
     * @param v1 the first parameter
     * @param v2 the second parameter
     * @return a newly evaluated {@link Byte} matching the calculated exclusive boolean separation
     */
    public static Byte xor(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) ^ toPrimitive(v2));
    }

    /**
     * Computes the bitwise {@code NOT} (complement) inversion covering a singular byte.
     * <p>
     * {@code null} references natively resolve via zero inversion (yields {@code -1}).
     * </p>
     *
     * @param value the variable undergoing inversion
     * @return the cleanly inverted {@link Byte} variable
     */
    public static Byte not(Byte value) {
        return (byte) (~toPrimitive(value));
    }
    
    /**
     * Structurally modifies a targeted byte parameter by performing a bitwise left-shift operation.
     * <p>
     * Value overflow outside standard byte constraints terminates via natural truncation. 
     * Null targets equate intrinsically to zero.
     * </p>
     *
     * @param value the operand undergoing shifting
     * @param shift the integer magnitude governing the distance of shifted offset
     * @return the manipulated shift output structurally formatted into a {@link Byte}
     */
    public static Byte shiftLeft(Byte value, int shift) {
        return (byte) (toPrimitive(value) << shift);
    }
    
    /**
     * Structurally modifies a targeted byte parameter by performing a signed bitwise right-shift operation.
     * <p>
     * The upper bits shifted downward will preserve their existing signed marker (arithmetic shift).
     * Null targets equate intrinsically to zero.
     * </p>
     *
     * @param value the operand undergoing shifting
     * @param shift the integer magnitude governing the distance of shifted offset
     * @return the manipulated shift output structurally formatted into a {@link Byte}
     */
    public static Byte shiftRight(Byte value, int shift) {
        return (byte) (toPrimitive(value) >> shift);
    }
    
    /**
     * Structurally modifies a targeted byte parameter by performing an unsigned bitwise right-shift operation.
     * <p>
     * The shift operates mathematically identically across the full {@code 0xFF} structure array mask,
     * stripping preceding bits strictly with zeros (logical shift). Null targets equate intrinsically to zero.
     * </p>
     *
     * @param value the operand undergoing shifting
     * @param shift the integer magnitude governing the distance of shifted offset
     * @return the manipulated shift output structurally formatted into a {@link Byte}
     */
    public static Byte unsignedShiftRight(Byte value, int shift) {
        return (byte) ((toPrimitive(value) & 0xFF) >>> shift);
    }

}
