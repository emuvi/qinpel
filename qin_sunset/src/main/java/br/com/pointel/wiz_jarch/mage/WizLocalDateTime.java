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
 * A utility class providing safe manipulation, checking, and conversion operations for {@link LocalDateTime} objects.
 * <p>
 * This class abstracts away common boundary conditions like null-checking while bridging conversions between legacy Date types, 
 * numeric timestamps, and java.time API constructs.
 * </p>
 */
public class WizLocalDateTime {

    private WizLocalDateTime() {
    }

    /**
     * Checks if the given value can be converted to a {@link LocalDateTime}.
     *
     * @param value the value to check
     * @return {@code true} if the value is supported for conversion, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
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
     * Converts various object types into a {@link LocalDateTime}.
     *
     * @param value the target value to convert
     * @return the resolved {@link LocalDateTime}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the object type is not supported
     */
    public static LocalDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            var offsetTime = OffsetTime.class.cast(value);
            return offsetTime.atDate(LocalDate.now()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, LocalDateTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        throw new Exception("Could not convert to a LocalDateTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current system local date and time.
     *
     * @return a {@link LocalDateTime} representing the exact current moment
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Adds days securely to a given date-time.
     *
     * @param dateTime the initial date-time
     * @param days     the number of days to add
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime plusDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.plusDays(days);
    }

    /**
     * Subtracts days securely from a given date-time.
     *
     * @param dateTime the initial date-time
     * @param days     the number of days to subtract
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime minusDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.minusDays(days);
    }

    /**
     * Adds hours securely to a given date-time.
     *
     * @param dateTime the initial date-time
     * @param hours    the number of hours to add
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.plusHours(hours);
    }

    /**
     * Subtracts hours securely from a given date-time.
     *
     * @param dateTime the initial date-time
     * @param hours    the number of hours to subtract
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime minusHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.minusHours(hours);
    }

    /**
     * Adds minutes securely to a given date-time.
     *
     * @param dateTime the initial date-time
     * @param minutes  the number of minutes to add
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime plusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely from a given date-time.
     *
     * @param dateTime the initial date-time
     * @param minutes  the number of minutes to subtract
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime minusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely to a given date-time.
     *
     * @param dateTime the initial date-time
     * @param seconds  the number of seconds to add
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime plusSeconds(LocalDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely from a given date-time.
     *
     * @param dateTime the initial date-time
     * @param seconds  the number of seconds to subtract
     * @return the manipulated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime minusSeconds(LocalDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     *
     * @param dateTime the initial date-time
     * @return the truncated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime truncateToHours(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     *
     * @param dateTime the initial date-time
     * @return the truncated {@link LocalDateTime}, or {@code null} if the input date-time is null
     */
    public static LocalDateTime truncateToMinutes(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Safely evaluates if the first date-time strictly precedes the second.
     *
     * @param d1 the first {@link LocalDateTime}
     * @param d2 the second {@link LocalDateTime}
     * @return {@code true} if d1 is before d2; {@code false} if either is null or d1 >= d2
     */
    public static boolean isBefore(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isBefore(d2);
    }

    /**
     * Safely evaluates if the first date-time strictly follows the second.
     *
     * @param d1 the first {@link LocalDateTime}
     * @param d2 the second {@link LocalDateTime}
     * @return {@code true} if d1 is after d2; {@code false} if either is null or {@code d1 <= d2}
     */
    public static boolean isAfter(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isAfter(d2);
    }

    /**
     * Safely evaluates if the first date-time is chronologically equal to the second.
     *
     * @param d1 the first {@link LocalDateTime}
     * @param d2 the second {@link LocalDateTime}
     * @return {@code true} if both are exactly equal; {@code true} if both are null; {@code false} otherwise
     */
    public static boolean isEqual(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.isEqual(d2);
    }

    /**
     * Returns the exact {@link Duration} between two date-times.
     *
     * @param startInclusive the start {@link LocalDateTime} (inclusive)
     * @param endExclusive   the end {@link LocalDateTime} (exclusive)
     * @return the {@link Duration} between the two limits, or {@code null} if either is null
     */
    public static Duration between(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * Calculates the exact number of days between two date-times.
     *
     * @param startInclusive the start {@link LocalDateTime} (inclusive)
     * @param endExclusive   the end {@link LocalDateTime} (exclusive)
     * @return the number of days separating the two dates, or {@code null} if either is null
     */
    public static Long daysBetween(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Formats a {@link LocalDateTime} via the machine-readable default standard {@code (yyyy-MM-dd HH:mm:ss)}.
     *
     * @param value the date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String format(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats a {@link LocalDateTime} utilizing the strict machine pattern {@code (yyyy-MM-dd HH:mm:ss)}.
     *
     * @param value the date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateTimeMach(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats a {@link LocalDateTime} utilizing the preferred user-readable pattern {@code (dd/MM/yyyy HH:mm:ss)}.
     *
     * @param value the date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateTimeUser(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value);
    }

    /**
     * Formats a {@link LocalDateTime} utilizing a file-system compatible pattern {@code (yyyy-MM-dd-HH-mm-ss)}.
     *
     * @param value the date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateTimeFile(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value);
    }

}
