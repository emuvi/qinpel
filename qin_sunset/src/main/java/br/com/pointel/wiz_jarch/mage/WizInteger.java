package br.com.pointel.wiz_jarch.mage;

/**
 * A utility class for safe manipulation and conversion of {@link Integer} objects.
 * <p>
 * This class provides null-safe operations for parsing, comparing, and math evaluation
 * on {@link Integer} types.
 * </p>
 */
public class WizInteger {
    
    private WizInteger() {
    }

    /**
     * Checks if the given value can be converted to an {@link Integer}.
     * <p>
     * Supported types include {@link Integer}, {@link Number}, {@link Boolean}, and {@link String}.
     * </p>
     *
     * @param value the value to check
     * @return {@code true} if the value can be converted, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Integer.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given object to an {@link Integer}.
     * <p>
     * This method supports parsing from {@link Number}, {@link Boolean} 
     * ({@code true}=1, {@code false}=0), and {@link String}.
     * Blank strings return {@code null}.
     * </p>
     *
     * @param value the value to convert
     * @return the converted {@link Integer}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the type is unsupported
     */
    public static Integer get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Integer.class)) {
            return Integer.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).intValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1 : 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Integer.parseInt(string.trim());
        }
        throw new Exception("Could not convert to an Integer value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object to an {@link Integer}, returning a default fallback if conversion fails.
     *
     * @param value     the value to convert
     * @param orDefault the fallback value
     * @return the converted {@link Integer}, or {@code orDefault} if conversion fails
     */
    public static Integer get(Object value, Integer orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats an {@link Integer} as a string.
     *
     * @param value the {@link Integer} to format
     * @return the string representation, or an empty string if null
     */
    public static String format(Integer value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts an {@link Integer} object to a primitive {@code int}.
     * <p>
     * Null values are treated as {@code 0}.
     * </p>
     *
     * @param value the {@link Integer} to convert
     * @return the primitive {@code int} value, or {@code 0} if null
     */
    public static int toPrimitive(Integer value) {
        return value != null ? value : 0;
    }

    /**
     * Converts an {@link Integer} object to a primitive {@code int} with a custom fallback for nulls.
     *
     * @param value         the {@link Integer} to convert
     * @param defaultIfNull the fallback value
     * @return the primitive {@code int} value, or {@code defaultIfNull} if null
     */
    public static int toPrimitive(Integer value, int defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two {@link Integer} values.
     * <p>
     * A {@code null} value is considered less than any non-null value.
     * </p>
     *
     * @param i1 the first {@link Integer}
     * @param i2 the second {@link Integer}
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public static int compare(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return 0;
        if (i1 == null) return -1;
        if (i2 == null) return 1;
        return Integer.compare(i1, i2);
    }

    /**
     * Checks if two {@link Integer} values are exactly equal.
     *
     * @param i1 the first {@link Integer}
     * @param i2 the second {@link Integer}
     * @return {@code true} if equal, {@code false} otherwise (including if only one is null)
     */
    public static boolean isEqual(Integer i1, Integer i2) {
        return compare(i1, i2) == 0;
    }

    /**
     * Checks if an {@link Integer} is exactly zero.
     *
     * @param value the {@link Integer} to check
     * @return {@code true} if the value is {@code 0}, {@code false} otherwise
     */
    public static boolean isZero(Integer value) {
        return value != null && value == 0;
    }

    /**
     * Checks if an {@link Integer} is positive (greater than zero).
     *
     * @param value the {@link Integer} to check
     * @return {@code true} if positive, {@code false} if null or {@code <= 0}
     */
    public static boolean isPositive(Integer value) {
        return value != null && value > 0;
    }

    /**
     * Checks if an {@link Integer} is negative (less than zero).
     *
     * @param value the {@link Integer} to check
     * @return {@code true} if negative, {@code false} if null or {@code >= 0}
     */
    public static boolean isNegative(Integer value) {
        return value != null && value < 0;
    }

    /**
     * Checks if an {@link Integer} is even.
     *
     * @param value the {@link Integer} to check
     * @return {@code true} if even, {@code false} if null or odd
     */
    public static boolean isEven(Integer value) {
        return value != null && (value % 2 == 0);
    }

    /**
     * Checks if an {@link Integer} is odd.
     *
     * @param value the {@link Integer} to check
     * @return {@code true} if odd, {@code false} if null or even
     */
    public static boolean isOdd(Integer value) {
        return value != null && (value % 2 != 0);
    }

    /**
     * Safely adds two {@link Integer} values.
     * <p>
     * Null values are treated as {@code 0}.
     * </p>
     *
     * @param i1 the first {@link Integer}
     * @param i2 the second {@link Integer}
     * @return the sum, or {@code null} if both inputs are null
     */
    public static Integer sum(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return null;
        return toPrimitive(i1) + toPrimitive(i2);
    }

    /**
     * Returns the minimum of two {@link Integer} values.
     * <p>
     * Nulls are ignored if one value is present. If both are null, returns {@code null}.
     * </p>
     *
     * @param i1 the first {@link Integer}
     * @param i2 the second {@link Integer}
     * @return the minimum value
     */
    public static Integer min(Integer i1, Integer i2) {
        if (i1 == null) return i2;
        if (i2 == null) return i1;
        return Math.min(i1, i2);
    }

    /**
     * Returns the maximum of two {@link Integer} values.
     * <p>
     * Nulls are ignored if one value is present. If both are null, returns {@code null}.
     * </p>
     *
     * @param i1 the first {@link Integer}
     * @param i2 the second {@link Integer}
     * @return the maximum value
     */
    public static Integer max(Integer i1, Integer i2) {
        if (i1 == null) return i2;
        if (i2 == null) return i1;
        return Math.max(i1, i2);
    }

}
