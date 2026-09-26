package br.com.pointel.wiz_jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;


/**
 * A null-safe utility class for operations involving {@link BigDecimal} values.
 * <p>
 * {@code WizBigDecimal} provides static methods for safely instantiating, formatting,
 * comparing, and calculating arithmetic operations on decimal numbers.
 * </p>
 * <p>
 * It seamlessly handles {@code null} values across calculations, often treating them as
 * {@link BigDecimal#ZERO}, preventing typical {@link NullPointerException}s during mathematical evaluations.
 * </p>
 */
public class WizBigDecimal {

    private WizBigDecimal() {
    }

    /**
     * Determines whether the given object is, or can be safely cast to, a {@link BigDecimal}.
     * <p>
     * Supported formats include explicit {@link BigDecimal} instances, any {@link Number} implementations,
     * {@link Boolean} types (evaluated as 1 or 0), and standard {@link String} representations.
     * </p>
     *
     * @param value the object to evaluate
     * @return {@code true} if the object can be validly converted into a {@link BigDecimal}; {@code false} otherwise (or if null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), BigDecimal.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts a versatile set of objects into a strict {@link BigDecimal} instance.
     * <p>
     * This conversion maps standard {@link Number} instances through their double-precision values,
     * translates {@code true} into {@link BigDecimal#ONE} and {@code false} into {@link BigDecimal#ZERO},
     * and strictly parses {@link String} formats barring blank inputs.
     * </p>
     *
     * @param value the target value meant for conversion
     * @return a mapped {@link BigDecimal} instance, or {@code null} if the input itself is null or a blank string
     * @throws Exception if the value falls out of the supported types or structurally fails to parse
     */
    public static BigDecimal get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), BigDecimal.class)) {
            return BigDecimal.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), BigInteger.class)) {
            return new BigDecimal(BigInteger.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return BigDecimal.valueOf(Number.class.cast(value).doubleValue());
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? BigDecimal.ONE : BigDecimal.ZERO;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return new BigDecimal(string);
        }
        throw new Exception("Could not convert to a BigDecimal value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object to a {@link BigDecimal}, defaulting to a fallback value upon any failure.
     * <p>
     * Suppresses any parsing exceptions triggered during the conversion pipeline.
     * </p>
     *
     * @param value     the raw value to convert
     * @param orDefault the default fallback if the conversion fails or the input is structurally incompatible
     * @return the successfully converted {@link BigDecimal}, or the provided default
     */
    public static BigDecimal get(Object value, BigDecimal orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Returns the standardized string representation of a {@link BigDecimal}.
     *
     * @param value the {@link BigDecimal} to format
     * @return the string output natively provided by the class, or an empty string ({@code ""}) if the input is null
     */
    public static String format(BigDecimal value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Coalesces a {@code null} reference to a safe zero equivalent.
     *
     * @param value the targeted {@link BigDecimal} to inspect
     * @return the provided value, or {@link BigDecimal#ZERO} if it is null
     */
    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * Coalesces a {@code null} reference to a specified fallback default.
     *
     * @param value        the targeted {@link BigDecimal} to inspect
     * @param defaultValue the fallback returned if the targeted value proves null
     * @return the original value, or the configured fallback
     */
    public static BigDecimal defaultIfNull(BigDecimal value, BigDecimal defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Checks whether the value strictly equals numerical zero.
     *
     * @param value the {@link BigDecimal} to inspect
     * @return {@code true} if the value is instantiated and evaluates mathematically to zero; {@code false} otherwise
     */
    public static boolean isZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Checks whether the value is strictly positive (greater than zero).
     *
     * @param value the {@link BigDecimal} to inspect
     * @return {@code true} if the value is instantiated and evaluates greater than zero; {@code false} otherwise
     */
    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Checks whether the value is strictly negative (less than zero).
     *
     * @param value the {@link BigDecimal} to inspect
     * @return {@code true} if the value is instantiated and evaluates less than zero; {@code false} otherwise
     */
    public static boolean isNegative(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Checks whether the value evaluates as zero or strictly positive.
     *
     * @param value the {@link BigDecimal} to inspect
     * @return {@code true} if the value is instantiated and evaluates greater than or equal to zero; {@code false} otherwise
     */
    public static boolean isPositiveOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Checks whether the value evaluates as zero or strictly negative.
     *
     * @param value the {@link BigDecimal} to inspect
     * @return {@code true} if the value is instantiated and evaluates less than or equal to zero; {@code false} otherwise
     */
    public static boolean isNegativeOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * Safely compares two {@link BigDecimal} instances for numerical equality.
     * <p>
     * Proper mathematical checks are done utilizing {@link BigDecimal#compareTo(BigDecimal)} rather than {@code equals()},
     * bridging differences in scalar definitions.
     * </p>
     *
     * @param v1 the first variable
     * @param v2 the second variable
     * @return {@code true} if both equal each other; {@code false} if their numeric values differ, or if exactly one of them is null
     */
    public static boolean isEqual(BigDecimal v1, BigDecimal v2) {
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
    public static boolean isGreaterThan(BigDecimal v1, BigDecimal v2) {
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
    public static boolean isGreaterThanOrEqual(BigDecimal v1, BigDecimal v2) {
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
    public static boolean isLessThan(BigDecimal v1, BigDecimal v2) {
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
    public static boolean isLessThanOrEqual(BigDecimal v1, BigDecimal v2) {
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
    public static BigDecimal min(BigDecimal v1, BigDecimal v2) {
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
    public static BigDecimal max(BigDecimal v1, BigDecimal v2) {
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
     * @return a new {@link BigDecimal} charting the sum of both numbers
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
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
     * @return a new {@link BigDecimal} mapping the resultant difference
     */
    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
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
     * @return a new {@link BigDecimal} equating to the generated product
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        return nullToZero(v1).multiply(nullToZero(v2));
    }

    /**
     * Safely calculates the division (quotient) of the first value by the second.
     * <p>
     * Default settings employ a fixed scalar length of 2 utilizing {@link RoundingMode#HALF_UP}
     * rounding behavior. Nulls equate mathematically to zero.
     * </p>
     *
     * @param v1 the baseline dividend
     * @param v2 the functional divisor
     * @return a new {@link BigDecimal} denoting the formatted division result
     * @throws ArithmeticException if the target divisor evaluates strictly to zero
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return divide(v1, v2, 2, RoundingMode.HALF_UP);
    }

    /**
     * Safely calculates the division (quotient) of the first value by the second, allowing strict
     * scaling rules and rounding policies.
     * <p>
     * Null variables are structurally coalesced into zeros to preserve mathematical flow.
     * </p>
     *
     * @param v1           the baseline dividend
     * @param v2           the functional divisor
     * @param scale        the volume of allowed decimal trailing slots
     * @param roundingMode the policy outlining the mathematical boundary truncation algorithm
     * @return a new {@link BigDecimal} denoting the structured division result
     * @throws ArithmeticException if the target divisor evaluates strictly to zero
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
        BigDecimal num = nullToZero(v1);
        BigDecimal den = nullToZero(v2);
        if (den.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num.divide(den, scale, roundingMode);
    }

    /**
     * Calculates the cumulative total of an array of variable numbers.
     * <p>
     * Automatically purges null blocks during calculation instead of failing.
     * </p>
     *
     * @param values a continuous array chain of numeric values
     * @return the cumulative total as a {@link BigDecimal}, scaling down to zero for completely empty or null segments
     */
    public static BigDecimal sum(BigDecimal... values) {
        if (values == null || values.length == 0) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
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
     * @return the overarching sum evaluated as a {@link BigDecimal}
     */
    public static BigDecimal sum(Collection<BigDecimal> values) {
        if (values == null || values.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

    /**
     * Calculates the statistical numeric mean of a collection array while binding exact
     * decimals constraints alongside designated rounding boundaries.
     *
     * @param values       the structured {@link Collection} of numeric parameters to ingest
     * @param scale        the finite constraint on allowed trailing decimal components
     * @param roundingMode the mathematical rule deployed when reducing surplus digits
     * @return the generated average encoded into a correctly bounded {@link BigDecimal}
     */
    public static BigDecimal average(Collection<BigDecimal> values, int scale, RoundingMode roundingMode) {
        if (values == null || values.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = sum(values);
        return total.divide(BigDecimal.valueOf(values.size()), scale, roundingMode);
    }

    /**
     * Calculates the statistical numeric mean of a collection leveraging fixed baseline scaling formats.
     * <p>
     * Employs standard formatting yielding exactly 2 decimal slots alongside a {@link RoundingMode#HALF_UP} standard format.
     * </p>
     *
     * @param values the structured {@link Collection} of parameters
     * @return the formatted average mapped squarely into a {@link BigDecimal} object
     */
    public static BigDecimal average(Collection<BigDecimal> values) {
        return average(values, 2, RoundingMode.HALF_UP);
    }

    /**
     * Enforces strict trailing decimal scalar boundaries against a numeric target and maps the resulting structure
     * securely into standard textual format.
     * <p>
     * By default, limits are solved via {@link RoundingMode#HALF_UP} algorithms.
     * </p>
     *
     * @param value the target parameter needing string resolution
     * @param scale the trailing boundaries of the target limit
     * @return the textually mapped output form, or empty format ({@code ""}) given a fully null state
     */
    public static String format(BigDecimal value, int scale) {
        return format(value, scale, RoundingMode.HALF_UP);
    }

    /**
     * Converts a baseline decimal parameter dynamically into a properly confined text output string matching custom formatting.
     *
     * @param value        the original mapping component parameter
     * @param scale        the maximum decimal constraint boundary
     * @param roundingMode the mathematical algorithm defining precision boundary loss mapping
     * @return the textually mapped output string, collapsing cleanly into an empty structure ({@code ""}) if completely null
     */
    public static String format(BigDecimal value, int scale, RoundingMode roundingMode) {
        return value == null ? "" : value.setScale(scale, roundingMode).toString();
    }

}
