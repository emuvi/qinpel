package br.com.pointel.wiz_jarch.mage;

/**
 * A utility class providing safe manipulation, checking, and conversion operations for {@link Long} objects.
 * <p>
 * This class abstracts away common boundary conditions like null-checking while providing mathematical comparison 
 * and fallback assignment logic for primitive interactions.
 * </p>
 */
public class WizLong {

    private WizLong() {
    }

    /**
     * Checks if the given value can be cleanly converted to a {@link Long}.
     * <p>
     * Supports evaluation across {@link Long}, {@link Number}, {@link Boolean}, and {@link String} classes.
     * </p>
     *
     * @param value the value to check
     * @return {@code true} if the value is supported for conversion, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Long.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a {@link Long}.
     * <p>
     * Supports extraction from {@link Long}, {@link Number}, {@link Boolean} ({@code true} = 1, {@code false} = 0), and {@link String} parsing.
     * Blank strings evaluate to {@code null}.
     * </p>
     *
     * @param value the target value to convert
     * @return the resolved {@link Long}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the object type is not supported
     */
    public static Long get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Long.class)) {
            return Long.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).longValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1L : 0L;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Long.parseLong(string.trim());
        }
        throw new Exception("Could not convert to a Long value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts a value to {@link Long}, gracefully returning a specified fallback default if conversion fails.
     *
     * @param value     the target value to convert
     * @param orDefault the fallback value
     * @return the converted {@link Long}, or {@code orDefault} if parsing encounters an exception
     */
    public static Long get(Object value, Long orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a {@link Long} into its representative string payload.
     *
     * @param value the {@link Long} to format
     * @return the string representation, or an empty string if null
     */
    public static String format(Long value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts a {@link Long} wrapper to its primitive {@code long} equivalent.
     * <p>
     * A {@code null} value evaluates as {@code 0L}.
     * </p>
     *
     * @param value the {@link Long} to convert
     * @return the primitive {@code long} value, or {@code 0L} if null
     */
    public static long toPrimitive(Long value) {
        return value != null ? value : 0L;
    }

    /**
     * Converts a {@link Long} wrapper to its primitive {@code long} equivalent mapping a custom fallback.
     *
     * @param value         the {@link Long} to convert
     * @param defaultIfNull the custom fallback value
     * @return the primitive {@code long} value, or {@code defaultIfNull} if null
     */
    public static long toPrimitive(Long value, long defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two {@link Long} values.
     * <p>
     * A {@code null} is inherently considered less than any non-null value.
     * </p>
     *
     * @param l1 the first {@link Long}
     * @param l2 the second {@link Long}
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public static int compare(Long l1, Long l2) {
        if (l1 == null && l2 == null) return 0;
        if (l1 == null) return -1;
        if (l2 == null) return 1;
        return Long.compare(l1, l2);
    }

    /**
     * Checks if two {@link Long} values are mathematically exactly equal.
     *
     * @param l1 the first {@link Long}
     * @param l2 the second {@link Long}
     * @return {@code true} if exactly equal, {@code false} otherwise (including if only one is null)
     */
    public static boolean isEqual(Long l1, Long l2) {
        return compare(l1, l2) == 0;
    }

    /**
     * Checks if a {@link Long} evaluates to exactly zero.
     *
     * @param value the {@link Long} to check
     * @return {@code true} if the value is {@code 0L}, {@code false} otherwise
     */
    public static boolean isZero(Long value) {
        return value != null && value == 0L;
    }

    /**
     * Checks if a {@link Long} is mathematically positive (greater than 0).
     *
     * @param value the {@link Long} to check
     * @return {@code true} if positive, {@code false} if null or {@code <= 0L}
     */
    public static boolean isPositive(Long value) {
        return value != null && value > 0L;
    }

    /**
     * Checks if a {@link Long} is mathematically negative (less than 0).
     *
     * @param value the {@link Long} to check
     * @return {@code true} if negative, {@code false} if null or {@code >= 0L}
     */
    public static boolean isNegative(Long value) {
        return value != null && value < 0L;
    }

    /**
     * Checks if a {@link Long} is mathematically even.
     *
     * @param value the {@link Long} to check
     * @return {@code true} if even, {@code false} if null or odd
     */
    public static boolean isEven(Long value) {
        return value != null && (value % 2 == 0L);
    }

    /**
     * Checks if a {@link Long} is mathematically odd.
     *
     * @param value the {@link Long} to check
     * @return {@code true} if odd, {@code false} if null or even
     */
    public static boolean isOdd(Long value) {
        return value != null && (value % 2 != 0L);
    }

    /**
     * Safely adds two {@link Long} values.
     * <p>
     * Null constraints are treated natively as {@code 0L}.
     * </p>
     *
     * @param l1 the first {@link Long}
     * @param l2 the second {@link Long}
     * @return the calculated sum, or {@code null} if both inputs are null
     */
    public static Long sum(Long l1, Long l2) {
        if (l1 == null && l2 == null) return null;
        return toPrimitive(l1) + toPrimitive(l2);
    }

    /**
     * Returns the minimum between two {@link Long} values.
     * <p>
     * Null properties are ignored if one value is present. If both are null, returns {@code null}.
     * </p>
     *
     * @param l1 the first {@link Long}
     * @param l2 the second {@link Long}
     * @return the absolute minimum value
     */
    public static Long min(Long l1, Long l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        return Math.min(l1, l2);
    }

    /**
     * Returns the maximum between two {@link Long} values.
     * <p>
     * Null properties are ignored if one value is present. If both are null, returns {@code null}.
     * </p>
     *
     * @param l1 the first {@link Long}
     * @param l2 the second {@link Long}
     * @return the absolute maximum value
     */
    public static Long max(Long l1, Long l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        return Math.max(l1, l2);
    }

}
