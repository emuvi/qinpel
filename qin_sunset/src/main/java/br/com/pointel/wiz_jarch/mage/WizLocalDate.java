package br.com.pointel.wiz_jarch.mage;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
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
 * A utility class providing safe manipulation, checking, and conversion operations for {@link LocalDate} objects.
 * <p>
 * This class abstracts away common boundary conditions like null-checking while bridging conversions between legacy Date types, 
 * numeric timestamps, and java.time API constructs.
 * </p>
 */
public class WizLocalDate {

    private WizLocalDate() {
    }

    /**
     * Checks if the given value can be converted to a {@link LocalDate}.
     *
     * @param value the value to check
     * @return {@code true} if the value is supported for conversion, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
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
     * Converts various object types into a {@link LocalDate}.
     *
     * @param value the target value to convert
     * @return the resolved {@link LocalDate}, or {@code null} if the input is null or a blank string
     * @throws Exception if the conversion fails or the object type is not supported
     */
    public static LocalDate get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value).atDate(LocalDate.now()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, LocalDate::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        throw new Exception("Could not convert to a LocalDate value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current system date.
     *
     * @return a {@link LocalDate} representing today
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * Adds days securely to a given date.
     *
     * @param date the initial date
     * @param days the number of days to add
     * @return the manipulated {@link LocalDate}, or {@code null} if the input date is null
     */
    public static LocalDate plusDays(LocalDate date, long days) {
        if (date == null) return null;
        return date.plusDays(days);
    }

    /**
     * Subtracts days securely from a given date.
     *
     * @param date the initial date
     * @param days the number of days to subtract
     * @return the manipulated {@link LocalDate}, or {@code null} if the input date is null
     */
    public static LocalDate minusDays(LocalDate date, long days) {
        if (date == null) return null;
        return date.minusDays(days);
    }

    /**
     * Adds months securely to a given date.
     *
     * @param date   the initial date
     * @param months the number of months to add
     * @return the manipulated {@link LocalDate}, or {@code null} if the input date is null
     */
    public static LocalDate plusMonths(LocalDate date, long months) {
        if (date == null) return null;
        return date.plusMonths(months);
    }

    /**
     * Subtracts months securely from a given date.
     *
     * @param date   the initial date
     * @param months the number of months to subtract
     * @return the manipulated {@link LocalDate}, or {@code null} if the input date is null
     */
    public static LocalDate minusMonths(LocalDate date, long months) {
        if (date == null) return null;
        return date.minusMonths(months);
    }

    /**
     * Adds years securely to a given date.
     *
     * @param date  the initial date
     * @param years the number of years to add
     * @return the manipulated {@link LocalDate}, or {@code null} if the input date is null
     */
    public static LocalDate plusYears(LocalDate date, long years) {
        if (date == null) return null;
        return date.plusYears(years);
    }

    /**
     * Subtracts years securely from a given date.
     *
     * @param date  the initial date
     * @param years the number of years to subtract
     * @return the manipulated {@link LocalDate}, or {@code null} if the input date is null
     */
    public static LocalDate minusYears(LocalDate date, long years) {
        if (date == null) return null;
        return date.minusYears(years);
    }

    // =========================================================================
    // COMPARISONS & VALIDATIONS
    // =========================================================================

    /**
     * Safely evaluates if the first date strictly precedes the second.
     *
     * @param d1 the first {@link LocalDate}
     * @param d2 the second {@link LocalDate}
     * @return {@code true} if d1 is before d2; {@code false} if either is null or d1 >= d2
     */
    public static boolean isBefore(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isBefore(d2);
    }

    /**
     * Safely evaluates if the first date strictly follows the second.
     *
     * @param d1 the first {@link LocalDate}
     * @param d2 the second {@link LocalDate}
     * @return {@code true} if d1 is after d2; {@code false} if either is null or {@code d1 <= d2}
     */
    public static boolean isAfter(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isAfter(d2);
    }

    /**
     * Safely evaluates if the first date is chronologically equal to the second.
     *
     * @param d1 the first {@link LocalDate}
     * @param d2 the second {@link LocalDate}
     * @return {@code true} if dates are exactly equal; {@code true} if both are null; {@code false} otherwise
     */
    public static boolean isEqual(LocalDate d1, LocalDate d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.isEqual(d2);
    }

    /**
     * Checks if the given date represents "today" based on the system clock.
     *
     * @param date the date to check
     * @return {@code true} if the date matches today; {@code false} if null or otherwise
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) return false;
        return date.isEqual(now());
    }

    /**
     * Checks if the given date falls on a weekend (Saturday or Sunday).
     *
     * @param date the date to check
     * @return {@code true} if the date is a weekend day, {@code false} if null or a weekday
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) return false;
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * Checks if the given date resides within a leap year.
     *
     * @param date the date to check
     * @return {@code true} if it is a leap year; {@code false} if null or not a leap year
     */
    public static boolean isLeapYear(LocalDate date) {
        if (date == null) return false;
        return date.isLeapYear();
    }

    /**
     * Calculates the exact number of days between two dates.
     *
     * @param startInclusive the start {@link LocalDate} (inclusive)
     * @param endExclusive   the end {@link LocalDate} (exclusive)
     * @return the number of days separating the two dates, or {@code null} if either is null
     */
    public static Long daysBetween(LocalDate startInclusive, LocalDate endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Formats a {@link LocalDate} via the machine-readable default standard {@code (yyyy-MM-dd)}.
     *
     * @param value the date to format
     * @return the formatted string, or an empty string if null
     */
    public static String format(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value);
    }

    /**
     * Formats a {@link LocalDate} utilizing the strict machine pattern {@code (yyyy-MM-dd)}.
     *
     * @param value the date to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateMach(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value);
    }

    /**
     * Formats a {@link LocalDate} utilizing the preferred user-readable pattern {@code (dd/MM/yyyy)}.
     *
     * @param value the date to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateUser(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateUser.format(value);
    }

    /**
     * Formats a {@link LocalDate} utilizing a file-system compatible pattern {@code (yyyy-MM-dd)}.
     *
     * @param value the date to format
     * @return the formatted string, or an empty string if null
     */
    public static String formatDateFile(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateFile.format(value);
    }

}
