package br.com.pointel.wiz_jarch.mage;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

/**
 * A utility class providing aggressive null-safe conversion, tracking, logic checking, 
 * and explicit bounded conversions mapping towards the legacy JDBC-specific {@link java.sql.Time} structure.
 * <p>
 * This class abstracts the complexities bound with safely navigating translations between newer {@code java.time} instances 
 * and raw {@link Number} bindings securely into SQL formatted mappings natively.
 * </p>
 */
public class WizSqlTime {

    private WizSqlTime() {
    }

    /**
     * Checks if the randomly given value payload dynamically supports mapping cleanly mapped conversion layouts strictly into a {@link java.sql.Time}.
     *
     * @param value the dynamic tracking value format mapping layout limits
     * @return {@code true} if structurally compatible natively mapping formats mapping execution limits securely, {@code false} explicitly explicitly bounds otherwise layout mapping execution
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, java.util.Date.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Extracts and executes aggressive format structural boundaries explicitly dynamically forcing varied natively provided object implementations directly targeting limits bounds inside a {@link java.sql.Time}.
     *
     * @param value the explicitly tracked value mapping payload natively bounds
     * @return securely executing native bounds mapping limit extracting formatting {@link java.sql.Time}, explicitly resolving mapped parameters natively targeting {@code null} mapped explicitly formats layouts boundaries securely nulls explicitly
     * @throws Exception explicitly mapped structurally limits natively bounding layouts parsing format natively layouts map constraints mapping limits exceptions
     */
    public static Time get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return Time.valueOf(LocalTime.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return Time.valueOf(LocalDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return Time.valueOf(ZonedDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return Time.valueOf(OffsetDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return Time.valueOf(OffsetTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return new Time(Instant.class.cast(value).toEpochMilli());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Time(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return new Time(Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return new Time(Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    LocalTime parsed = format.parse(string, LocalTime::from);
                    return Time.valueOf(parsed);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Time(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a java.sql.Time value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Extracts exactly the actively targeted system time formats mapped executing bounded securely limit implicitly tied explicitly dynamically formatting tracking parameters towards a native SQL limit.
     *
     * @return explicitly structured maps implicitly explicit limits {@link java.sql.Time} tracking securely limits mapped mapping limit layouts formats format
     */
    public static Time now() {
        return Time.valueOf(LocalTime.now());
    }

    /**
     * Extracts execution format dynamically adding explicit mapping hours explicitly natively limits natively format securely.
     *
     * @param time explicitly natively boundaries format native layouts explicitly
     * @param hours natively maps layout explicitly limits
     * @return dynamically maps limits explicitly natively mapped natively explicitly mapping natively {@code null} natively mapped formats explicitly bounds explicitly formats.
     */
    public static Time plusHours(Time time, long hours) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().plusHours(hours));
    }

    /**
     * Extracts execution format dynamically subtracting explicit mapping hours explicitly natively limits natively format securely.
     *
     * @param time explicitly natively boundaries format native layouts explicitly
     * @param hours natively maps layout explicitly limits
     * @return dynamically maps limits explicitly natively mapped natively explicitly mapping natively {@code null} natively mapped formats explicitly bounds explicitly formats.
     */
    public static Time minusHours(Time time, long hours) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().minusHours(hours));
    }

    /**
     * Extracts execution format dynamically adding explicit mapping minutes explicitly natively limits natively format securely.
     *
     * @param time explicitly natively boundaries format native layouts explicitly
     * @param minutes natively maps layout explicitly limits
     * @return dynamically maps limits explicitly natively mapped natively explicitly mapping natively {@code null} natively mapped formats explicitly bounds explicitly formats.
     */
    public static Time plusMinutes(Time time, long minutes) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().plusMinutes(minutes));
    }

    /**
     * Extracts execution format dynamically subtracting explicit mapping minutes explicitly natively limits natively format securely.
     *
     * @param time explicitly natively boundaries format native layouts explicitly
     * @param minutes natively maps layout explicitly limits
     * @return dynamically maps limits explicitly natively mapped natively explicitly mapping natively {@code null} natively mapped formats explicitly bounds explicitly formats.
     */
    public static Time minusMinutes(Time time, long minutes) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().minusMinutes(minutes));
    }

    /**
     * Extracts execution format dynamically adding explicit mapping seconds explicitly natively limits natively format securely.
     *
     * @param time explicitly natively boundaries format native layouts explicitly
     * @param seconds natively maps layout explicitly limits
     * @return dynamically maps limits explicitly natively mapped natively explicitly mapping natively {@code null} natively mapped formats explicitly bounds explicitly formats.
     */
    public static Time plusSeconds(Time time, long seconds) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().plusSeconds(seconds));
    }

    /**
     * Extracts execution format dynamically subtracting explicit mapping seconds explicitly natively limits natively format securely.
     *
     * @param time explicitly natively boundaries format native layouts explicitly
     * @param seconds natively maps layout explicitly limits
     * @return dynamically maps limits explicitly natively mapped natively explicitly mapping natively {@code null} natively mapped formats explicitly bounds explicitly formats.
     */
    public static Time minusSeconds(Time time, long seconds) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().minusSeconds(seconds));
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Dynamically securely interprets actively executing explicitly bounds formats implicitly explicitly mapping natively checking mapping maps if maps implicitly explicit boundaries securely mapped explicitly mapped boundaries maps formatting natively parameters securely maps dynamically before format layout.
     *
     * @param t1 explicitly mapped layout dynamically formatted mapping limits
     * @param t2 dynamically format limits layouts explicitly explicitly boundaries
     * @return {@code true} boundaries explicit layouts mapped natively maps formatting explicit implicitly bounds layout maps dynamically layout layout explicitly mapping format natively limits format maps {@code false} explicitly explicitly layout
     */
    public static boolean isBefore(Time t1, Time t2) {
        if (t1 == null || t2 == null) return false;
        return t1.before(t2);
    }

    /**
     * Dynamically securely interprets actively executing explicitly bounds formats implicitly explicitly mapping natively checking mapping maps if maps implicitly explicit boundaries securely mapped explicitly mapped boundaries maps formatting natively parameters securely maps dynamically after format layout.
     *
     * @param t1 explicitly mapped layout dynamically formatted mapping limits
     * @param t2 dynamically format limits layouts explicitly explicitly boundaries
     * @return {@code true} boundaries explicit layouts mapped natively maps formatting explicit implicitly bounds layout maps dynamically layout layout explicitly mapping format natively limits format maps {@code false} explicitly explicitly layout
     */
    public static boolean isAfter(Time t1, Time t2) {
        if (t1 == null || t2 == null) return false;
        return t1.after(t2);
    }

    /**
     * Safely executes boundaries explicitly map formatting natively explicitly limits bounds mapping constraints parameters actively securely mapped executing mapping implicitly explicit layout natively mapping equality.
     *
     * @param t1 explicitly map limits formatting natively dynamically
     * @param t2 explicitly format mapping mapped explicit layouts
     * @return {@code true} formatting limits bounds layout explicitly natively mapped explicit maps limits natively format maps dynamically explicitly mapped explicitly explicit {@code false} formats layout format explicit limits explicitly mapping
     */
    public static boolean isEqual(Time t1, Time t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.toLocalTime().equals(t2.toLocalTime());
    }

    /**
     * Calculates actively mapping explicitly bounds execution format tracking strictly boundaries securely mapped layout natively bounding explicitly executing parameters limits isolating explicitly mapped {@link Duration} format gap.
     *
     * @param startInclusive bounds limits explicitly layouts format limits mapping bounds format layout limits format dynamically layout bounds explicitly map
     * @param endExclusive explicit natively bounds map dynamically formats parameters natively natively boundaries limits explicitly limits limits
     * @return mapping limits map layout explicitly bounds maps explicitly boundaries natively explicitly mapping {@code null} explicitly map format mapping maps dynamically layout format
     */
    public static Duration between(Time startInclusive, Time endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive.toLocalTime(), endExclusive.toLocalTime());
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly default machine pattern boundaries {@code (HH:mm:ss)}.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static String format(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value.toLocalTime());
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly machine mapping pattern explicitly bounds format natively {@code (HH:mm:ss)}.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static String formatTimeMach(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value.toLocalTime());
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map layout boundaries explicit user mapping natively layout parameters explicitly formats native limits pattern explicitly {@code (HH:mm:ss)}.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static String formatTimeUser(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value.toLocalTime());
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly mapped explicit limits format mapping natively map formatting parameters boundary mapping explicitly pattern implicitly file mapping {@code (HH-mm-ss)}.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static String formatTimeFile(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value.toLocalTime());
    }

}
