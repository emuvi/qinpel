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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * A utility class providing safe manipulation, checking, and conversion operations for {@link OffsetDateTime} objects.
 * <p>
 * This class abstracts away common boundary conditions like null-checking while bridging conversions between legacy Date types, 
 * numeric timestamps, and java.time API constructs maintaining their regional zone offset metadata.
 * </p>
 */
public class WizOffsetDateTime {

    private WizOffsetDateTime() {
    }

    /**
     * Checks if the given value can be cleanly converted to an {@link OffsetDateTime}.
     *
     * @param value the value to check
     * @return {@code true} if the value is supported for conversion, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
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
     * Converts various object types into an {@link OffsetDateTime}.
     *
     * @param value the target value to convert
     * @return the resolved {@link OffsetDateTime}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the object type is not supported
     */
    public static OffsetDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            OffsetTime offsetTime = OffsetTime.class.cast(value);
            ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
            return OffsetDateTime.of(LocalDate.now(), offsetTime.toLocalTime(), offset);
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, OffsetDateTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        throw new Exception("Could not convert to an OffsetDateTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current system offset date and time.
     *
     * @return an {@link OffsetDateTime} representing the exact current moment factoring local zone offsets
     */
    public static OffsetDateTime now() {
        return OffsetDateTime.now();
    }

    /**
     * Adds days securely to a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param days     the number of days to add
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime plusDays(OffsetDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.plusDays(days);
    }

    /**
     * Subtracts days securely from a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param days     the number of days to subtract
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime minusDays(OffsetDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.minusDays(days);
    }

    /**
     * Adds hours securely to a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param hours    the number of hours to add
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime plusHours(OffsetDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.plusHours(hours);
    }

    /**
     * Subtracts hours securely from a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param hours    the number of hours to subtract
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime minusHours(OffsetDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.minusHours(hours);
    }

    /**
     * Adds minutes securely to a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param minutes  the number of minutes to add
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime plusMinutes(OffsetDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely from a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param minutes  the number of minutes to subtract
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime minusMinutes(OffsetDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely to a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param seconds  the number of seconds to add
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime plusSeconds(OffsetDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely from a given offset date-time.
     *
     * @param dateTime the initial date-time
     * @param seconds  the number of seconds to subtract
     * @return the manipulated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime minusSeconds(OffsetDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     *
     * @param dateTime the initial date-time
     * @return the truncated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime truncateToHours(OffsetDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     *
     * @param dateTime the initial date-time
     * @return the truncated {@link OffsetDateTime}, or {@code null} if the input is null
     */
    public static OffsetDateTime truncateToMinutes(OffsetDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Safely evaluates if the first offset date-time strictly precedes the second.
     *
     * @param d1 the first {@link OffsetDateTime}
     * @param d2 the second {@link OffsetDateTime}
     * @return {@code true} if d1 is before d2; {@code false} if either is null or d1 >= d2
     */
    public static boolean isBefore(OffsetDateTime d1, OffsetDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isBefore(d2);
    }

    /**
     * Safely evaluates if the first offset date-time strictly follows the second.
     *
     * @param d1 the first {@link OffsetDateTime}
     * @param d2 the second {@link OffsetDateTime}
     * @return {@code true} if d1 is after d2; {@code false} if either is null or {@code d1 <= d2}
     */
    public static boolean isAfter(OffsetDateTime d1, OffsetDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isAfter(d2);
    }

    /**
     * Safely evaluates if the first offset date-time is chronologically equal to the second.
     *
     * @param d1 the first {@link OffsetDateTime}
     * @param d2 the second {@link OffsetDateTime}
     * @return {@code true} if both are exactly equal; {@code true} if both are null; {@code false} otherwise
     */
    public static boolean isEqual(OffsetDateTime d1, OffsetDateTime d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.isEqual(d2);
    }

    /**
     * Returns the exact {@link Duration} between two offset date-times.
     *
     * @param startInclusive the start {@link OffsetDateTime} (inclusive)
     * @param endExclusive   the end {@link OffsetDateTime} (exclusive)
     * @return the {@link Duration} between the two limits, or {@code null} if either is null
     */
    public static Duration between(OffsetDateTime startInclusive, OffsetDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * Calculates the exact number of days between two offset date-times.
     *
     * @param startInclusive the start {@link OffsetDateTime} (inclusive)
     * @param endExclusive   the end {@link OffsetDateTime} (exclusive)
     * @return the number of days separating the two dates, or {@code null} if either is null
     */
    public static Long daysBetween(OffsetDateTime startInclusive, OffsetDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Formats an {@link OffsetDateTime} via the machine-readable default standard {@code (yyyy-MM-dd HH:mm:ss)}.
     *
     * @param value the offset date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String format(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats an {@link OffsetDateTime} utilizing the strict machine pattern {@code (yyyy-MM-dd HH:mm:ss)}.
     *
     * @param value the offset date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateTimeMach(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats an {@link OffsetDateTime} utilizing the preferred user-readable pattern {@code (dd/MM/yyyy HH:mm:ss)}.
     *
     * @param value the offset date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateTimeUser(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value);
    }

    /**
     * Formats an {@link OffsetDateTime} utilizing a file-system compatible pattern {@code (yyyy-MM-dd-HH-mm-ss)}.
     *
     * @param value the offset date-time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateTimeFile(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value);
    }

}
