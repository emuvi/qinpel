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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

/**
 * A utility class for safe manipulation and conversion of {@link Instant} objects.
 * <p>
 * This class provides null-safe operations for parsing, comparing, formatting, 
 * and transforming temporal data across multiple Date/Time architectures, 
 * including legacy SQL dates, raw timestamps, and java.time structures.
 * </p>
 */
public class WizInstant {

    private WizInstant() {
    }

    /**
     * Checks if the given value can be converted to an {@link Instant}.
     * <p>
     * Supports checking against {@link Instant}, standard date/time types, 
     * {@link Date}, {@link Timestamp}, {@link String}, and {@link Number}.
     * </p>
     *
     * @param value the value to check
     * @return {@code true} if the value can be converted, {@code false} otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts the given object to an {@link Instant}.
     * <p>
     * Handles normalization from multiple temporal sources including {@link LocalDate}, 
     * {@link LocalTime}, {@link LocalDateTime}, {@link ZonedDateTime}, {@link OffsetDateTime}, 
     * {@link OffsetTime}, {@link Date}, {@link java.sql.Date}, {@link Time}, {@link Timestamp}, 
     * formatted {@link String} components, and {@link Number} instances acting as epoch millis.
     * </p>
     *
     * @param value the value to convert
     * @return the converted {@link Instant}, or {@code null} if the value is null
     * @throws Exception if the value cannot be converted or is an unsupported type
     */
    public static Instant get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value).atDate(LocalDate.now()).atZoneSameInstant(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, Instant::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to an Instant value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current time as an {@link Instant}.
     *
     * @return the current {@link Instant}
     */
    public static Instant now() {
        return Instant.now();
    }

    /**
     * Adds a specified time amount to an {@link Instant}.
     *
     * @param instant the {@link Instant} to add to (null-safe)
     * @param amount  the amount to add
     * @param unit    the {@link TemporalUnit} (e.g., {@link ChronoUnit#DAYS})
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant plus(Instant instant, long amount, TemporalUnit unit) {
        if (instant == null) return null;
        return instant.plus(amount, unit);
    }

    /**
     * Subtracts a specified time amount from an {@link Instant}.
     *
     * @param instant the {@link Instant} to subtract from (null-safe)
     * @param amount  the amount to subtract
     * @param unit    the {@link TemporalUnit} (e.g., {@link ChronoUnit#DAYS})
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant minus(Instant instant, long amount, TemporalUnit unit) {
        if (instant == null) return null;
        return instant.minus(amount, unit);
    }

    /**
     * Adds days to an {@link Instant}.
     *
     * @param instant the {@link Instant} to add to (null-safe)
     * @param days    the number of days to add
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant plusDays(Instant instant, long days) {
        return plus(instant, days, ChronoUnit.DAYS);
    }

    /**
     * Subtracts days from an {@link Instant}.
     *
     * @param instant the {@link Instant} to subtract from (null-safe)
     * @param days    the number of days to subtract
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant minusDays(Instant instant, long days) {
        return minus(instant, days, ChronoUnit.DAYS);
    }

    /**
     * Adds hours to an {@link Instant}.
     *
     * @param instant the {@link Instant} to add to (null-safe)
     * @param hours   the number of hours to add
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant plusHours(Instant instant, long hours) {
        return plus(instant, hours, ChronoUnit.HOURS);
    }

    /**
     * Subtracts hours from an {@link Instant}.
     *
     * @param instant the {@link Instant} to subtract from (null-safe)
     * @param hours   the number of hours to subtract
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant minusHours(Instant instant, long hours) {
        return minus(instant, hours, ChronoUnit.HOURS);
    }

    /**
     * Adds minutes to an {@link Instant}.
     *
     * @param instant the {@link Instant} to add to (null-safe)
     * @param minutes the number of minutes to add
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant plusMinutes(Instant instant, long minutes) {
        return plus(instant, minutes, ChronoUnit.MINUTES);
    }

    /**
     * Subtracts minutes from an {@link Instant}.
     *
     * @param instant the {@link Instant} to subtract from (null-safe)
     * @param minutes the number of minutes to subtract
     * @return the modified {@link Instant}, or {@code null} if the input is null
     */
    public static Instant minusMinutes(Instant instant, long minutes) {
        return minus(instant, minutes, ChronoUnit.MINUTES);
    }

    /**
     * Truncates an {@link Instant} to days, effectively removing the time portion.
     *
     * @param instant the {@link Instant} to truncate (null-safe)
     * @return the truncated {@link Instant}, or {@code null} if the input is null
     */
    public static Instant truncateToDays(Instant instant) {
        if (instant == null) return null;
        return instant.truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * Truncates an {@link Instant} to hours, removing minutes, seconds, and nanoseconds.
     *
     * @param instant the {@link Instant} to truncate (null-safe)
     * @return the truncated {@link Instant}, or {@code null} if the input is null
     */
    public static Instant truncateToHours(Instant instant) {
        if (instant == null) return null;
        return instant.truncatedTo(ChronoUnit.HOURS);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if the first {@link Instant} strictly precedes the second.
     *
     * @param i1 the first {@link Instant}
     * @param i2 the second {@link Instant}
     * @return {@code true} if i1 is before i2; {@code false} if either is null
     */
    public static boolean isBefore(Instant i1, Instant i2) {
        if (i1 == null || i2 == null) return false;
        return i1.isBefore(i2);
    }

    /**
     * Checks if the first {@link Instant} strictly follows the second.
     *
     * @param i1 the first {@link Instant}
     * @param i2 the second {@link Instant}
     * @return {@code true} if i1 is after i2; {@code false} if either is null
     */
    public static boolean isAfter(Instant i1, Instant i2) {
        if (i1 == null || i2 == null) return false;
        return i1.isAfter(i2);
    }

    /**
     * Checks if two {@link Instant} objects represent the same point on the timeline.
     *
     * @param i1 the first {@link Instant}
     * @param i2 the second {@link Instant}
     * @return {@code true} if exactly equal; if both are null, evaluates to {@code true}
     */
    public static boolean isEqual(Instant i1, Instant i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null || i2 == null) return false;
        return i1.equals(i2);
    }

    /**
     * Calculates the {@link Duration} between two {@link Instant} objects.
     *
     * @param startInclusive the start {@link Instant} (inclusive)
     * @param endExclusive   the end {@link Instant} (exclusive)
     * @return the {@link Duration} between them, or {@code null} if either is null
     */
    public static Duration between(Instant startInclusive, Instant endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * Calculates the exact number of days between two {@link Instant} objects.
     *
     * @param startInclusive the start {@link Instant} (inclusive)
     * @param endExclusive   the end {@link Instant} (exclusive)
     * @return the number of days between them, or {@code null} if either is null
     */
    public static Long daysBetween(Instant startInclusive, Instant endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Checks if a string payload correctly matches a {@link DateTimeFormatter} pattern.
     *
     * @param formatted the formatted payload string
     * @param onFormat  the {@link DateTimeFormatter} to test against
     * @return {@code true} if the payload cleanly matches the defined pattern
     */
    public static boolean is(String formatted, DateTimeFormatter onFormat) {
        return Objects.equals(
                WizString.replaceLettersOrDigits(formatted, 'x'),
                WizString.replaceLettersOrDigits(onFormat.toString(), 'x'));
    }

    /**
     * Formats an {@link Instant} into a machine-readable default string form.
     *
     * @param value the {@link Instant} to format
     * @return the formatted string, or an empty string if null
     */
    public static String format(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantMach(value);
    }

    /**
     * Formats an {@link Instant} into a user-friendly date string {@code (dd/MM/yyyy)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted date string, or an empty string if null
     */
    public static String formatDateUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateUser.format(value);
    }

    /**
     * Formats an {@link Instant} into a user-friendly time string {@code (HH:mm:ss)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted time string, or an empty string if null
     */
    public static String formatTimeUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value);
    }

    /**
     * Formats an {@link Instant} into a user-friendly time string capturing milliseconds {@code (HH:mm:ss SSS)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted time string, or an empty string if null
     */
    public static String formatMillisUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatMillisUser.format(value);
    }

    /**
     * Formats an {@link Instant} into a user-friendly complete date-time string {@code (dd/MM/yyyy HH:mm:ss)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted date-time string, or an empty string if null
     */
    public static String formatDateTimeUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value);
    }

    /**
     * Formats an {@link Instant} into a user-friendly full timestamp {@code (dd/MM/yyyy HH:mm:ss SSS)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted timestamp string, or an empty string if null
     */
    public static String formatTimestampUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimestampUser.format(value);
    }

    /**
     * Formats an {@link Instant} reflecting timezone offsets {@code (dd/MM/yyyy HH:mm:ss SSS Z)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted instant string, or an empty string if null
     */
    public static String formatInstantUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantUser.format(value);
    }

    /**
     * Formats an {@link Instant} into a machine-readable date string {@code (yyyy-MM-dd)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted date string, or an empty string if null
     */
    public static String formatDateMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value);
    }

    /**
     * Formats an {@link Instant} into a machine-readable time string {@code (HH:mm:ss)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted time string, or an empty string if null
     */
    public static String formatTimeMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats an {@link Instant} into a machine-readable time string handling millis {@code (HH:mm:ss.SSS)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted time string, or an empty string if null
     */
    public static String formatMillisMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatMillisMach.format(value);
    }

    /**
     * Formats an {@link Instant} into a machine-readable date-time string {@code (yyyy-MM-dd HH:mm:ss)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted date-time string, or an empty string if null
     */
    public static String formatDateTimeMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats an {@link Instant} into a machine-readable timestamp {@code (yyyy-MM-dd HH:mm:ss.SSS)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted timestamp string, or an empty string if null
     */
    public static String formatTimestampMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimestampMach.format(value);
    }

    /**
     * Formats an {@link Instant} with standard zone offsetting {@code (yyyy-MM-dd HH:mm:ss.SSS Z)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted instant string, or an empty string if null
     */
    public static String formatInstantMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantMach.format(value);
    }

    /**
     * Formats an {@link Instant} safely for file systems {@code (yyyy-MM-dd)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted date string, or an empty string if null
     */
    public static String formatDateFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateFile.format(value);
    }

    /**
     * Formats an {@link Instant} safely for file systems {@code (HH-mm-ss)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted time string, or an empty string if null
     */
    public static String formatTimeFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value);
    }

    /**
     * Formats an {@link Instant} safely for file systems encapsulating milliseconds {@code (HH-mm-ss-SSS)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted time string, or an empty string if null
     */
    public static String formatMillisFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatMillisFile.format(value);
    }

    /**
     * Formats an {@link Instant} safely for file systems capturing date and time {@code (yyyy-MM-dd-HH-mm-ss)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted date-time string, or an empty string if null
     */
    public static String formatDateTimeFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value);
    }

    /**
     * Formats an {@link Instant} safely for file systems encompassing timestamp data {@code (yyyy-MM-dd-HH-mm-ss-SSS)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted timestamp string, or an empty string if null
     */
    public static String formatTimestampFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimestampFile.format(value);
    }

    /**
     * Formats an {@link Instant} safely for file systems factoring zone offsets {@code (yyyy-MM-dd-HH-mm-ss-SSS-Z)}.
     *
     * @param value the {@link Instant} to format
     * @return the formatted instant string, or an empty string if null
     */
    public static String formatInstantFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantFile.format(value);
    }

    /**
     * Parses a user-readable date string {@code (dd/MM/yyyy)} into an {@link Instant}.
     *
     * @param formatted the formatted date string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable time string {@code (HH:mm:ss)} into an {@link Instant}.
     *
     * @param formatted the formatted time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable time string incorporating milliseconds {@code (HH:mm:ss SSS)} into an {@link Instant}.
     *
     * @param formatted the formatted time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable date-time string {@code (dd/MM/yyyy HH:mm:ss)} into an {@link Instant}.
     *
     * @param formatted the formatted date-time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable timestamp string {@code (dd/MM/yyyy HH:mm:ss SSS)} into an {@link Instant}.
     *
     * @param formatted the formatted timestamp string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable instant string acknowledging zone offsets {@code (dd/MM/yyyy HH:mm:ss SSS Z)} into an {@link Instant}.
     *
     * @param formatted the formatted instant string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseInstantUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable date string {@code (yyyy-MM-dd)} into an {@link Instant}.
     *
     * @param formatted the formatted date string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable time string {@code (HH:mm:ss)} into an {@link Instant}.
     *
     * @param formatted the formatted time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable time string containing milliseconds {@code (HH:mm:ss.SSS)} into an {@link Instant}.
     *
     * @param formatted the formatted time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable date-time string {@code (yyyy-MM-dd HH:mm:ss)} into an {@link Instant}.
     *
     * @param formatted the formatted date-time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable timestamp string {@code (yyyy-MM-dd HH:mm:ss.SSS)} into an {@link Instant}.
     *
     * @param formatted the formatted timestamp string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable instant string inclusive of zones {@code (yyyy-MM-dd HH:mm:ss.SSS Z)} into an {@link Instant}.
     *
     * @param formatted the formatted instant string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseInstantMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible date string {@code (yyyy-MM-dd)} into an {@link Instant}.
     *
     * @param formatted the formatted date string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible time string {@code (HH-mm-ss)} into an {@link Instant}.
     *
     * @param formatted the formatted time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible time string incorporating milliseconds {@code (HH-mm-ss-SSS)} into an {@link Instant}.
     *
     * @param formatted the formatted time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible date-time string {@code (yyyy-MM-dd-HH-mm-ss)} into an {@link Instant}.
     *
     * @param formatted the formatted date-time string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible timestamp string {@code (yyyy-MM-dd-HH-mm-ss-SSS)} into an {@link Instant}.
     *
     * @param formatted the formatted timestamp string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible instant string retaining zones {@code (yyyy-MM-dd-HH-mm-ss-SSS-Z)} into an {@link Instant}.
     *
     * @param formatted the formatted instant string
     * @return the parsed {@link Instant}, or {@code null} if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseInstantFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantFile.parse(formatted, Instant::from);
    }

    // Formatter constants for user-readable formats
    
    /**
     * {@link DateTimeFormatter} template for user-readable dates {@code (dd/MM/yyyy)}.
     */
    public static final DateTimeFormatter formatDateUser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * {@link DateTimeFormatter} template for user-readable times {@code (HH:mm:ss)}.
     */
    public static final DateTimeFormatter formatTimeUser = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * {@link DateTimeFormatter} template for user-readable times encompassing milliseconds {@code (HH:mm:ss SSS)}.
     */
    public static final DateTimeFormatter formatMillisUser = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
    
    /**
     * {@link DateTimeFormatter} template for user-readable date-time pairs {@code (dd/MM/yyyy HH:mm:ss)}.
     */
    public static final DateTimeFormatter formatDateTimeUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    /**
     * {@link DateTimeFormatter} template for user-readable timestamp fragments mapping milliseconds {@code (dd/MM/yyyy HH:mm:ss SSS)}.
     */
    public static final DateTimeFormatter formatTimestampUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss SSS");
    
    /**
     * {@link DateTimeFormatter} template for user-readable instances mapping offsets {@code (dd/MM/yyyy HH:mm:ss SSS Z)}.
     */
    public static final DateTimeFormatter formatInstantUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss SSS Z");

    // Formatter constants for machine-readable formats
    
    /**
     * {@link DateTimeFormatter} template for automated machine-readable dates {@code (yyyy-MM-dd)}.
     */
    public static final DateTimeFormatter formatDateMach = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * {@link DateTimeFormatter} template for automated machine-readable times {@code (HH:mm:ss)}.
     */
    public static final DateTimeFormatter formatTimeMach = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * {@link DateTimeFormatter} template for automated machine-readable times addressing milliseconds {@code (HH:mm:ss.SSS)}.
     */
    public static final DateTimeFormatter formatMillisMach = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    /**
     * {@link DateTimeFormatter} template for automated machine-readable date-time combinations {@code (yyyy-MM-dd HH:mm:ss)}.
     */
    public static final DateTimeFormatter formatDateTimeMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * {@link DateTimeFormatter} template for automated machine-readable timestamps accounting for milliseconds {@code (yyyy-MM-dd HH:mm:ss.SSS)}.
     */
    public static final DateTimeFormatter formatTimestampMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    /**
     * {@link DateTimeFormatter} template for automated machine-readable payload mapping zones {@code (yyyy-MM-dd HH:mm:ss.SSS Z)}.
     */
    public static final DateTimeFormatter formatInstantMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");

    // Formatter constants for file-compatible formats
    
    /**
     * {@link DateTimeFormatter} template for structural file-compatible dates {@code (yyyy-MM-dd)}.
     */
    public static final DateTimeFormatter formatDateFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * {@link DateTimeFormatter} template for structural file-compatible times {@code (HH-mm-ss)}.
     */
    public static final DateTimeFormatter formatTimeFile = DateTimeFormatter.ofPattern("HH-mm-ss");
    
    /**
     * {@link DateTimeFormatter} template for structural file-compatible times targeting milliseconds {@code (HH-mm-ss-SSS)}.
     */
    public static final DateTimeFormatter formatMillisFile = DateTimeFormatter.ofPattern("HH-mm-ss-SSS");
    
    /**
     * {@link DateTimeFormatter} template for structural file-compatible date and time representations {@code (yyyy-MM-dd-HH-mm-ss)}.
     */
    public static final DateTimeFormatter formatDateTimeFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    
    /**
     * {@link DateTimeFormatter} template for structural file-compatible timestamps encapsulating milliseconds {@code (yyyy-MM-dd-HH-mm-ss-SSS)}.
     */
    public static final DateTimeFormatter formatTimestampFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
    
    /**
     * {@link DateTimeFormatter} template for structural file-compatible definitions mapped to zone offsets {@code (yyyy-MM-dd-HH-mm-ss-SSS-Z)}.
     */
    public static final DateTimeFormatter formatInstantFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS-Z");

    /**
     * Gathers all supported {@link DateTimeFormatter} permutations for brute-force matching.
     * <p>
     * Yields an array encapsulating user-readable, machine-readable, and file-compatible formatting strategies.
     * </p>
     *
     * @return an array mapping available {@link DateTimeFormatter} instances
     */
    public static DateTimeFormatter[] getFormats() {
        return new DateTimeFormatter[] {
                WizInstant.formatDateMach,
                WizInstant.formatTimeMach,
                WizInstant.formatMillisMach,
                WizInstant.formatDateTimeMach,
                WizInstant.formatTimestampMach,
                WizInstant.formatInstantMach,
                WizInstant.formatDateUser,
                WizInstant.formatTimeUser,
                WizInstant.formatMillisUser,
                WizInstant.formatDateTimeUser,
                WizInstant.formatTimestampUser,
                WizInstant.formatInstantUser,
                WizInstant.formatDateFile,
                WizInstant.formatTimeFile,
                WizInstant.formatMillisFile,
                WizInstant.formatDateTimeFile,
                WizInstant.formatTimestampFile,
                WizInstant.formatInstantFile
        };
    }

}
