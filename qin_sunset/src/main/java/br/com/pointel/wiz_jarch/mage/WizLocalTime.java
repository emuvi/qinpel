package br.com.pointel.wiz_jarch.mage;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * A utility class providing safe manipulation, checking, and conversion operations for {@link LocalTime} objects.
 * <p>
 * This class abstracts away common boundary conditions like null-checking while bridging conversions between legacy Time types, 
 * numeric timestamps, and java.time API constructs.
 * </p>
 */
public class WizLocalTime {

    private WizLocalTime() {
    }

    /**
     * Checks if the given value can be converted to a {@link LocalTime}.
     *
     * @param value the value to check
     * @return {@code true} if the value is supported for conversion, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts various object types into a {@link LocalTime}.
     *
     * @param value the target value to convert
     * @return the resolved {@link LocalTime}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the object type is not supported
     */
    public static LocalTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toLocalDate().atStartOfDay().toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toLocalDateTime().toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, LocalTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        throw new Exception("Could not convert to a LocalTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current system local time.
     *
     * @return a {@link LocalTime} representing the exact current moment
     */
    public static LocalTime now() {
        return LocalTime.now();
    }

    /**
     * Adds hours securely to a given time.
     *
     * @param time  the initial time
     * @param hours the number of hours to add
     * @return the manipulated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime plusHours(LocalTime time, long hours) {
        if (time == null) return null;
        return time.plusHours(hours);
    }

    /**
     * Subtracts hours securely from a given time.
     *
     * @param time  the initial time
     * @param hours the number of hours to subtract
     * @return the manipulated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime minusHours(LocalTime time, long hours) {
        if (time == null) return null;
        return time.minusHours(hours);
    }

    /**
     * Adds minutes securely to a given time.
     *
     * @param time    the initial time
     * @param minutes the number of minutes to add
     * @return the manipulated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime plusMinutes(LocalTime time, long minutes) {
        if (time == null) return null;
        return time.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely from a given time.
     *
     * @param time    the initial time
     * @param minutes the number of minutes to subtract
     * @return the manipulated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime minusMinutes(LocalTime time, long minutes) {
        if (time == null) return null;
        return time.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely to a given time.
     *
     * @param time    the initial time
     * @param seconds the number of seconds to add
     * @return the manipulated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime plusSeconds(LocalTime time, long seconds) {
        if (time == null) return null;
        return time.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely from a given time.
     *
     * @param time    the initial time
     * @param seconds the number of seconds to subtract
     * @return the manipulated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime minusSeconds(LocalTime time, long seconds) {
        if (time == null) return null;
        return time.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     *
     * @param time the initial time
     * @return the truncated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime truncateToHours(LocalTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     *
     * @param time the initial time
     * @return the truncated {@link LocalTime}, or {@code null} if the input time is null
     */
    public static LocalTime truncateToMinutes(LocalTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Safely evaluates if the first time strictly precedes the second.
     *
     * @param t1 the first {@link LocalTime}
     * @param t2 the second {@link LocalTime}
     * @return {@code true} if t1 is before t2; {@code false} if either is null or t1 >= t2
     */
    public static boolean isBefore(LocalTime t1, LocalTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isBefore(t2);
    }

    /**
     * Safely evaluates if the first time strictly follows the second.
     *
     * @param t1 the first {@link LocalTime}
     * @param t2 the second {@link LocalTime}
     * @return {@code true} if t1 is after t2; {@code false} if either is null or {@code t1 <= t2}
     */
    public static boolean isAfter(LocalTime t1, LocalTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isAfter(t2);
    }

    /**
     * Safely evaluates if the first time is chronologically equal to the second.
     *
     * @param t1 the first {@link LocalTime}
     * @param t2 the second {@link LocalTime}
     * @return {@code true} if both are exactly equal; {@code true} if both are null; {@code false} otherwise
     */
    public static boolean isEqual(LocalTime t1, LocalTime t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.equals(t2);
    }

    /**
     * Returns the exact {@link Duration} between two times.
     *
     * @param startInclusive the start {@link LocalTime} (inclusive)
     * @param endExclusive   the end {@link LocalTime} (exclusive)
     * @return the {@link Duration} between the two limits, or {@code null} if either is null
     */
    public static Duration between(LocalTime startInclusive, LocalTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Formats a {@link LocalTime} via the machine-readable default standard {@code (HH:mm:ss)}.
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if null
     */
    public static String format(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats a {@link LocalTime} utilizing the strict machine pattern {@code (HH:mm:ss)}.
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatTimeMach(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats a {@link LocalTime} utilizing the preferred user-readable pattern {@code (HH:mm:ss)}.
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatTimeUser(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value);
    }

    /**
     * Formats a {@link LocalTime} utilizing a file-system compatible pattern {@code (HH-mm-ss)}.
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatTimeFile(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value);
    }

}
