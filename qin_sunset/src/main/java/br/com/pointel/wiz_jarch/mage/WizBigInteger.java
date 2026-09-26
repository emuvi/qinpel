package br.com.pointel.wiz_jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;


/**
 * A null-safe utility class for operations involving {@link BigInteger} values.
 * <p>
 * {@code WizBigInteger} provides static methods for safely instantiating, formatting,
 * comparing, and calculating arithmetic operations on unbounded integer numbers.
 * </p>
 * <p>
 * It seamlessly handles {@code null} values across calculations, typically treating them as
 * {@link BigInteger#ZERO}, preventing common {@link NullPointerException}s during mathematical evaluations.
 * </p>
 */
public class WizBigInteger {

    private WizBigInteger() {
    }

    /**
     * Determines whether the given object is, or can be safely cast to, a {@link BigInteger}.
     * <p>
     * Supported formats include explicit {@link BigInteger} instances, {@link BigDecimal} instances,
     * any {@link Number} implementations, {@link Boolean} types (evaluated as 1 or 0), and standard {@link String} representations.
     * </p>
     *
     * @param value the object to evaluate
     * @return {@code true} if the object can be validly converted into a {@link BigInteger}; {@code false} otherwise (or if null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), BigInteger.class)
            || WizLang.isChildOf(value.getClass(), BigDecimal.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts a versatile set of objects into a strict {@link BigInteger} instance.
     * <p>
     * This conversion maps standard {@link Number} instances through their long integer values,
     * translates {@link BigDecimal} by truncation, evaluates {@code true} into {@link BigInteger#ONE}
     * and {@code false} into {@link BigInteger#ZERO}, and strictly parses {@link String} formats barring blank inputs.
     * </p>
     *
     * @param value the target value meant for conversion
     * @return a mapped {@link BigInteger} instance, or {@code null} if the input itself is null or a blank string
     * @throws Exception if the value falls out of the supported types or structurally fails to parse
     */
    public static BigInteger get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), BigInteger.class)) {
            return BigInteger.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), BigDecimal.class)) {
            return (BigDecimal.class.cast(value)).toBigInteger();
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return BigInteger.valueOf(Number.class.cast(value).longValue());
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? BigInteger.ONE : BigInteger.ZERO;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return new BigInteger(string);
        }
        throw new Exception("Could not convert to a BigInteger value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object to a {@link BigInteger}, defaulting to a fallback value upon any failure.
     * <p>
     * Suppresses any parsing exceptions triggered during the conversion pipeline.
     * </p>
     *
     * @param value     the raw value to convert
     * @param orDefault the default fallback if the conversion fails or the input is structurally incompatible
     * @return the successfully converted {@link BigInteger}, or the provided default
     */
    public static BigInteger get(Object value, BigInteger orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Returns the standardized string representation of a {@link BigInteger}.
     *
     * @param value the {@link BigInteger} to format
     * @return the string output natively provided by the class, or an empty string ({@code ""}) if the input is null
     */
    public static String format(BigInteger value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Coalesces a {@code null} reference to a safe zero equivalent.
     *
     * @param value the targeted {@link BigInteger} to inspect
     * @return the provided value, or {@link BigInteger#ZERO} if it is null
     */
    public static BigInteger nullToZero(BigInteger value) {
        return value == null ? BigInteger.ZERO : value;
    }

    /**
     * Coalesces a {@code null} reference to a specified fallback default.
     *
     * @param value        the targeted {@link BigInteger} to inspect
     * @param defaultValue the fallback returned if the targeted value proves null
     * @return the original value, or the configured fallback
     */
    public static BigInteger defaultIfNull(BigInteger value, BigInteger defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Checks whether the value strictly equals numerical zero.
     *
     * @param value the {@link BigInteger} to inspect
     * @return {@code true} if the value is instantiated and evaluates mathematically to zero; {@code false} otherwise
     */
    public static boolean isZero(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) == 0;
    }

    /**
     * Checks whether the value is strictly positive (greater than zero).
     *
     * @param value the {@link BigInteger} to inspect
     * @return {@code true} if the value is instantiated and evaluates greater than zero; {@code false} otherwise
     */
    public static boolean isPositive(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) > 0;
    }

    /**
     * Checks whether the value is strictly negative (less than zero).
     *
     * @param value the {@link BigInteger} to inspect
     * @return {@code true} if the value is instantiated and evaluates less than zero; {@code false} otherwise
     */
    public static boolean isNegative(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) < 0;
    }

    /**
     * Checks whether the value evaluates as zero or strictly positive.
     *
     * @param value the {@link BigInteger} to inspect
     * @return {@code true} if the value is instantiated and evaluates greater than or equal to zero; {@code false} otherwise
     */
    public static boolean isPositiveOrZero(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) >= 0;
    }

    /**
     * Checks whether the value evaluates as zero or strictly negative.
     *
     * @param value the {@link BigInteger} to inspect
     * @return {@code true} if the value is instantiated and evaluates less than or equal to zero; {@code false} otherwise
     */
    public static boolean isNegativeOrZero(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) <= 0;
    }

    /**
     * Safely compares two {@link BigInteger} instances for numerical equality.
     * <p>
     * Proper mathematical checks are done utilizing {@link BigInteger#compareTo(BigInteger)} rather than {@code equals()}.
     * </p>
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return {@code true} if both equal each other; {@code false} if their numeric values differ, or if exactly one of them is null
     */
    public static boolean isEqual(BigInteger v1, BigInteger v2) {
        if (v1 == v2) return true;
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) == 0;
    }

    /**
     * Safely establishes if the first instance holds a greater numerical value than the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is strictly larger; {@code false} otherwise (or if either is null)
     */
    public static boolean isGreaterThan(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) > 0;
    }

    /**
     * Safely establishes if the first instance holds a greater or equal numerical value compared to the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is larger or equal; {@code false} otherwise (or if either is null)
     */
    public static boolean isGreaterThanOrEqual(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) >= 0;
    }

    /**
     * Safely establishes if the first instance holds a lesser numerical value than the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is strictly smaller; {@code false} otherwise (or if either is null)
     */
    public static boolean isLessThan(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) < 0;
    }

    /**
     * Safely establishes if the first instance holds a lesser or equal numerical value compared to the second.
     *
     * @param v1 the baseline variable
     * @param v2 the target comparison variable
     * @return {@code true} if the first is smaller or equal; {@code false} otherwise (or if either is null)
     */
    public static boolean isLessThanOrEqual(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) <= 0;
    }

    /**
     * Identifies the smaller of two given variables, accommodating partial null states.
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return the mathematically smaller object. If one is null, the valid counterpart is returned.
     */
    public static BigInteger min(BigInteger v1, BigInteger v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) <= 0 ? v1 : v2;
    }

    /**
     * Identifies the larger of two given variables, accommodating partial null states.
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return the mathematically larger object. If one is null, the valid counterpart is returned.
     */
    public static BigInteger max(BigInteger v1, BigInteger v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) >= 0 ? v1 : v2;
    }

    /**
     * Safely calculates the addition (sum) of two values.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow.
     * </p>
     *
     * @param v1 the first addend
     * @param v2 the second addend
     * @return a new {@link BigInteger} charting the sum of both numbers
     */
    public static BigInteger add(BigInteger v1, BigInteger v2) {
        return nullToZero(v1).add(nullToZero(v2));
    }

    /**
     * Safely calculates the subtraction (difference) of the second value from the first.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow.
     * </p>
     *
     * @param v1 the minuend
     * @param v2 the subtrahend
     * @return a new {@link BigInteger} mapping the resultant difference
     */
    public static BigInteger subtract(BigInteger v1, BigInteger v2) {
        return nullToZero(v1).subtract(nullToZero(v2));
    }

    /**
     * Safely calculates the multiplication (product) of two values.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow.
     * </p>
     *
     * @param v1 the first multiplier
     * @param v2 the second multiplier
     * @return a new {@link BigInteger} equating to the generated product
     */
    public static BigInteger multiply(BigInteger v1, BigInteger v2) {
        return nullToZero(v1).multiply(nullToZero(v2));
    }

    /**
     * Safely calculates the division (quotient) of the first value by the second.
     * <p>
     * This operation performs strict integer division, truncating any residual fractions. 
     * Null variables are structurally coalesced into zeros.
     * </p>
     *
     * @param v1 the baseline dividend
     * @param v2 the functional divisor
     * @return a new {@link BigInteger} denoting the whole integer division result
     * @throws ArithmeticException if the target divisor evaluates strictly to zero
     */
    public static BigInteger divide(BigInteger v1, BigInteger v2) {
        BigInteger num = nullToZero(v1);
        BigInteger den = nullToZero(v2);
        if (den.compareTo(BigInteger.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num.divide(den);
    }

    /**
     * Safely calculates the mathematical remainder (modulo) resulting from dividing the first value by the second.
     * <p>
     * Null variables are structurally coalesced into zeros before calculation.
     * </p>
     *
     * @param v1 the baseline dividend
     * @param v2 the functional divisor
     * @return a new {@link BigInteger} resolving to the remainder of the division
     * @throws ArithmeticException if the target divisor evaluates strictly to zero
     */
    public static BigInteger remainder(BigInteger v1, BigInteger v2) {
        BigInteger num = nullToZero(v1);
        BigInteger den = nullToZero(v2);
        if (den.compareTo(BigInteger.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num.remainder(den);
    }

    /**
     * Calculates the cumulative total of an array of variable numbers.
     * <p>
     * Automatically purges null blocks during calculation instead of failing.
     * </p>
     *
     * @param values a continuous array chain of numeric values
     * @return the cumulative total as a {@link BigInteger}, scaling down to zero for completely empty or null segments
     */
    public static BigInteger sum(BigInteger... values) {
        if (values == null || values.length == 0) return BigInteger.ZERO;
        BigInteger total = BigInteger.ZERO;
        for (BigInteger value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

    /**
     * Calculates the cumulative total across a collection tree of numeric variables.
     * <p>
     * Automatically ignores nested null segments internally to streamline bulk totals.
     * </p>
     *
     * @param values the structured {@link Collection} of numeric variables to ingest
     * @return the overarching sum evaluated as a {@link BigInteger}
     */
    public static BigInteger sum(Collection<BigInteger> values) {
        if (values == null || values.isEmpty()) return BigInteger.ZERO;
        BigInteger total = BigInteger.ZERO;
        for (BigInteger value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

}
