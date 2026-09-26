package br.com.pointel.wiz_jarch.mage;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A utility class for safe manipulation and conversion of {@link Float} objects.
 * <p>
 * This class provides null-safe operations for parsing, comparing, and math evaluation
 * on {@link Float} types.
 * </p>
 */
public class WizFloat {

    private WizFloat() {
    }

    /**
     * Checks if the given value can be converted to a {@link Float}.
     * <p>
     * Supported types include {@link Float}, {@link Number}, {@link Boolean}, and {@link String}.
     * </p>
     *
     * @param value the value to check
     * @return {@code true} if the value can be converted, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Float.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given object to a {@link Float}.
     * <p>
     * This method supports parsing from {@link Number}, {@link Boolean} 
     * ({@code true}=1.0f, {@code false}=0.0f), and {@link String}.
     * Blank strings return {@code null}.
     * </p>
     *
     * @param value the value to convert
     * @return the converted {@link Float}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the type is unsupported
     */
    public static Float get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Float.class)) {
            return Float.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).floatValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1.0f : 0.0f;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Float.parseFloat(string);
        }
        throw new Exception("Could not convert to a Float value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object to a {@link Float}, returning a default fallback if conversion fails.
     *
     * @param value     the value to convert
     * @param orDefault the fallback value
     * @return the converted {@link Float}, or {@code orDefault} if conversion fails
     */
    public static Float get(Object value, Float orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a {@link Float} as a string.
     *
     * @param value the {@link Float} to format
     * @return the string representation, or an empty string if null
     */
    public static String format(Float value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts a {@link Float} object to a primitive {@code float}.
     * <p>
     * Null values are treated as {@code 0.0f}.
     * </p>
     *
     * @param value the {@link Float} to convert
     * @return the primitive {@code float} value, or {@code 0.0f} if null
     */
    public static float toPrimitive(Float value) {
        return value != null ? value : 0.0f;
    }

    /**
     * Converts a {@link Float} object to a primitive {@code float} with a custom fallback for nulls.
     *
     * @param value         the {@link Float} to convert
     * @param defaultIfNull the fallback value
     * @return the primitive {@code float} value, or {@code defaultIfNull} if null
     */
    public static float toPrimitive(Float value, float defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two {@link Float} values.
     * <p>
     * A {@code null} value is considered less than any non-null value.
     * </p>
     *
     * @param f1 the first {@link Float}
     * @param f2 the second {@link Float}
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public static int compare(Float f1, Float f2) {
        if (f1 == null && f2 == null) return 0;
        if (f1 == null) return -1;
        if (f2 == null) return 1;
        return Float.compare(f1, f2);
    }

    /**
     * Checks if two {@link Float} values are exactly equal.
     *
     * @param f1 the first {@link Float}
     * @param f2 the second {@link Float}
     * @return {@code true} if equal, {@code false} otherwise (including if only one is null)
     */
    public static boolean isEqual(Float f1, Float f2) {
        return compare(f1, f2) == 0;
    }

    /**
     * Checks if a {@link Float} is exactly zero.
     *
     * @param value the {@link Float} to check
     * @return {@code true} if the value is {@code 0.0f} (or {@code -0.0f})
     */
    public static boolean isZero(Float value) {
        return value != null && value == 0.0f;
    }

    /**
     * Checks if a {@link Float} is positive (greater than zero).
     *
     * @param value the {@link Float} to check
     * @return {@code true} if positive, {@code false} if null or {@code <= 0}
     */
    public static boolean isPositive(Float value) {
        return value != null && value > 0.0f;
    }

    /**
     * Checks if a {@link Float} is negative (less than zero).
     *
     * @param value the {@link Float} to check
     * @return {@code true} if negative, {@code false} if null or {@code >= 0}
     */
    public static boolean isNegative(Float value) {
        return value != null && value < 0.0f;
    }

    /**
     * Checks if a {@link Float} is a finite floating-point value.
     *
     * @param value the {@link Float} to check
     * @return {@code true} if finite, {@code false} if null, NaN, or Infinite
     */
    public static boolean isFinite(Float value) {
        return value != null && Float.isFinite(value);
    }

    /**
     * Checks if a {@link Float} is Not-a-Number (NaN).
     *
     * @param value the {@link Float} to check
     * @return {@code true} if NaN, {@code false} if null or a valid number
     */
    public static boolean isNaN(Float value) {
        return value != null && Float.isNaN(value);
    }

    /**
     * Safely adds two {@link Float} values.
     * <p>
     * Null values are treated as {@code 0.0f}.
     * </p>
     *
     * @param f1 the first {@link Float}
     * @param f2 the second {@link Float}
     * @return the sum, or {@code null} if both inputs are null
     */
    public static Float sum(Float f1, Float f2) {
        if (f1 == null && f2 == null) return null;
        return toPrimitive(f1) + toPrimitive(f2);
    }

    /**
     * Returns the minimum of two {@link Float} values.
     * <p>
     * Nulls are ignored if one value is present. If both are null, returns {@code null}.
     * </p>
     *
     * @param f1 the first {@link Float}
     * @param f2 the second {@link Float}
     * @return the minimum value
     */
    public static Float min(Float f1, Float f2) {
        if (f1 == null) return f2;
        if (f2 == null) return f1;
        return Math.min(f1, f2);
    }

    /**
     * Returns the maximum of two {@link Float} values.
     * <p>
     * Nulls are ignored if one value is present. If both are null, returns {@code null}.
     * </p>
     *
     * @param f1 the first {@link Float}
     * @param f2 the second {@link Float}
     * @return the maximum value
     */
    public static Float max(Float f1, Float f2) {
        if (f1 == null) return f2;
        if (f2 == null) return f1;
        return Math.max(f1, f2);
    }

    /**
     * Rounds a {@link Float} to a specific number of decimal places.
     * <p>
     * Uses {@link RoundingMode#HALF_UP} rounding mode.
     * </p>
     *
     * @param value  the {@link Float} to round
     * @param places the number of decimal places
     * @return the rounded {@link Float}, or {@code null} if input is null or non-finite
     * @throws IllegalArgumentException if places is negative
     */
    public static Float round(Float value, int places) {
        if (value == null || !isFinite(value)) return value;
        if (places < 0) throw new IllegalArgumentException("Decimal places cannot be negative");
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
