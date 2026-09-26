package br.com.pointel.wiz_jarch.mage;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A utility class for handling Double values.
 * <p>
 * This class provides null-safe methods for parsing, formatting, and comparing Double objects.
 * </p>
 */
public class WizDouble {

    private WizDouble() {
    }

    /**
     * Checks if the given object can be converted to a Double.
     *
     * @param value the object to check
     * @return {@code true} if it can be converted, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Double.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts an object to a Double.
     *
     * @param value the object to convert
     * @return the Double value, or {@code null} if blank or null
     * @throws Exception if the conversion fails
     */
    public static Double get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Double.class)) {
            return Double.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).doubleValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1.0 : 0.0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Double.parseDouble(string);
        }
        throw new Exception("Could not convert to a Double value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object to a Double with a default fallback.
     *
     * @param value     the object to convert
     * @param orDefault the fallback value
     * @return the converted Double, or the fallback
     */
    public static Double get(Object value, Double orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a Double to a string.
     *
     * @param value the Double to format
     * @return the string representation
     */
    public static String format(Double value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts a Double to a primitive double.
     *
     * @param value the Double to convert
     * @return the primitive value, or 0.0 if null
     */
    public static double toPrimitive(Double value) {
        return value != null ? value : 0.0;
    }

    /**
     * Converts a Double to a primitive double with a custom fallback.
     *
     * @param value         the Double to convert
     * @param defaultIfNull the fallback value
     * @return the primitive value
     */
    public static double toPrimitive(Double value, double defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Compares two Double values.
     *
     * @param d1 the first value
     * @param d2 the second value
     * @return negative if d1 is less than d2, zero if equal, positive if d1 is greater
     */
    public static int compare(Double d1, Double d2) {
        if (d1 == null && d2 == null) return 0;
        if (d1 == null) return -1;
        if (d2 == null) return 1;
        return Double.compare(d1, d2);
    }

    /**
     * Checks if two Double values are equal.
     *
     * @param d1 the first value
     * @param d2 the second value
     * @return {@code true} if equal, {@code false} otherwise
     */
    public static boolean isEqual(Double d1, Double d2) {
        return compare(d1, d2) == 0;
    }

    /**
     * Checks if a Double is zero.
     *
     * @param value the Double to check
     * @return {@code true} if zero
     */
    public static boolean isZero(Double value) {
        return value != null && value == 0.0;
    }

    /**
     * Checks if a Double is positive.
     *
     * @param value the Double to check
     * @return {@code true} if positive
     */
    public static boolean isPositive(Double value) {
        return value != null && value > 0.0;
    }

    /**
     * Checks if a Double is negative.
     *
     * @param value the Double to check
     * @return {@code true} if negative
     */
    public static boolean isNegative(Double value) {
        return value != null && value < 0.0;
    }

    /**
     * Checks if a Double is finite.
     *
     * @param value the Double to check
     * @return {@code true} if finite
     */
    public static boolean isFinite(Double value) {
        return value != null && Double.isFinite(value);
    }

    /**
     * Checks if a Double is NaN.
     *
     * @param value the Double to check
     * @return {@code true} if NaN
     */
    public static boolean isNaN(Double value) {
        return value != null && Double.isNaN(value);
    }

    /**
     * Adds two Double values.
     *
     * @param d1 the first value
     * @param d2 the second value
     * @return the sum
     */
    public static Double sum(Double d1, Double d2) {
        if (d1 == null && d2 == null) return null;
        return toPrimitive(d1) + toPrimitive(d2);
    }

    /**
     * Returns the minimum of two Double values.
     *
     * @param d1 the first value
     * @param d2 the second value
     * @return the minimum value
     */
    public static Double min(Double d1, Double d2) {
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return Math.min(d1, d2);
    }

    /**
     * Returns the maximum of two Double values.
     *
     * @param d1 the first value
     * @param d2 the second value
     * @return the maximum value
     */
    public static Double max(Double d1, Double d2) {
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return Math.max(d1, d2);
    }

    /**
     * Rounds a Double to the specified decimal places.
     *
     * @param value  the Double to round
     * @param places the decimal places
     * @return the rounded value
     */
    public static Double round(Double value, int places) {
        if (value == null || !isFinite(value)) return value;
        if (places < 0) throw new IllegalArgumentException("Decimal places cannot be negative");
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
