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
 * A utility class providing safe manipulation, checking, and conversion operations for {@link OffsetTime} objects.
 * <p>
 * This class abstracts away common boundary conditions like null-checking while bridging conversions between legacy Time types, 
 * numeric timestamps, and java.time API constructs maintaining their regional zone offset metadata.
 * </p>
 */
public class WizOffsetTime {

    private WizOffsetTime() {
    }

    /**
     * Checks if the given value can be cleanly converted to an {@link OffsetTime}.
     *
     * @param value the value to check
     * @return {@code true} if the value is supported for conversion, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts various object types into an {@link OffsetTime}.
     * <p>
     * Extracts time information and applies the system default offset mapping if natively omitted from the source format.
     * </p>
     *
     * @param value the target value to convert
     * @return the resolved {@link OffsetTime}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the object type is not supported
     */
    public static OffsetTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
            return OffsetTime.of(LocalTime.class.cast(value), offset);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
            return OffsetTime.of(LocalDateTime.class.cast(value).toLocalTime(), offset);
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            String string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, OffsetTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        throw new Exception("Could not convert to an OffsetTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current system offset time.
     *
     * @return an {@link OffsetTime} representing the exact current moment factoring local zone offsets
     */
    public static OffsetTime now() {
        return OffsetTime.now();
    }

    /**
     * Adds hours securely to a given offset time.
     *
     * @param time  the initial time
     * @param hours the number of hours to add
     * @return the manipulated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime plusHours(OffsetTime time, long hours) {
        if (time == null) return null;
        return time.plusHours(hours);
    }

    /**
     * Subtracts hours securely from a given offset time.
     *
     * @param time  the initial time
     * @param hours the number of hours to subtract
     * @return the manipulated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime minusHours(OffsetTime time, long hours) {
        if (time == null) return null;
        return time.minusHours(hours);
    }

    /**
     * Adds minutes securely to a given offset time.
     *
     * @param time    the initial time
     * @param minutes the number of minutes to add
     * @return the manipulated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime plusMinutes(OffsetTime time, long minutes) {
        if (time == null) return null;
        return time.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely from a given offset time.
     *
     * @param time    the initial time
     * @param minutes the number of minutes to subtract
     * @return the manipulated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime minusMinutes(OffsetTime time, long minutes) {
        if (time == null) return null;
        return time.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely to a given offset time.
     *
     * @param time    the initial time
     * @param seconds the number of seconds to add
     * @return the manipulated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime plusSeconds(OffsetTime time, long seconds) {
        if (time == null) return null;
        return time.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely from a given offset time.
     *
     * @param time    the initial time
     * @param seconds the number of seconds to subtract
     * @return the manipulated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime minusSeconds(OffsetTime time, long seconds) {
        if (time == null) return null;
        return time.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     *
     * @param time the initial time
     * @return the truncated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime truncateToHours(OffsetTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     *
     * @param time the initial time
     * @return the truncated {@link OffsetTime}, or {@code null} if the input is null
     */
    public static OffsetTime truncateToMinutes(OffsetTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Safely evaluates if the first offset time strictly precedes the second.
     *
     * @param t1 the first {@link OffsetTime}
     * @param t2 the second {@link OffsetTime}
     * @return {@code true} if t1 is before t2; {@code false} if either is null or t1 >= t2
     */
    public static boolean isBefore(OffsetTime t1, OffsetTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isBefore(t2);
    }

    /**
     * Safely evaluates if the first offset time strictly follows the second.
     *
     * @param t1 the first {@link OffsetTime}
     * @param t2 the second {@link OffsetTime}
     * @return {@code true} if t1 is after t2; {@code false} if either is null or {@code t1 <= t2}
     */
    public static boolean isAfter(OffsetTime t1, OffsetTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isAfter(t2);
    }

    /**
     * Safely evaluates if the first offset time is chronologically equal to the second.
     *
     * @param t1 the first {@link OffsetTime}
     * @param t2 the second {@link OffsetTime}
     * @return {@code true} if both are exactly equal; {@code true} if both are null; {@code false} otherwise
     */
    public static boolean isEqual(OffsetTime t1, OffsetTime t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.isEqual(t2);
    }

    /**
     * Returns the exact {@link Duration} between two offset times.
     *
     * @param startInclusive the start {@link OffsetTime} (inclusive)
     * @param endExclusive   the end {@link OffsetTime} (exclusive)
     * @return the {@link Duration} between the two limits, or {@code null} if either is null
     */
    public static Duration between(OffsetTime startInclusive, OffsetTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Formats an {@link OffsetTime} via the machine-readable default standard {@code (HH:mm:ss)}.
     *
     * @param value the offset time to format
     * @return the formatted string, or an empty string if null
     */
    public static String format(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats an {@link OffsetTime} utilizing the strict machine pattern {@code (HH:mm:ss)}.
     *
     * @param value the offset time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatTimeMach(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats an {@link OffsetTime} utilizing the preferred user-readable pattern {@code (HH:mm:ss)}.
     *
     * @param value the offset time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatTimeUser(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value);
    }

    /**
     * Formats an {@link OffsetTime} utilizing a file-system compatible pattern {@code (HH-mm-ss)}.
     *
     * @param value the offset time to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatTimeFile(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value);
    }

}
