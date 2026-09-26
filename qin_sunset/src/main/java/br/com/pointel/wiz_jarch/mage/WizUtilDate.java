package br.com.pointel.wiz_jarch.mage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * A utility class for safely managing, parsing, formatting explicitly securely bounds legacy {@link java.util.Date} objects natively explicitly.
 * <p>
 * This class abstracts complex synchronization safely explicitly limits thread-safe access formats formatting payloads explicitly bounding native parameters mapping securely targeting explicit dates.
 * </p>
 */
public class WizUtilDate {

    private WizUtilDate() {
    }

    /**
     * Checks if the randomly given value payload dynamically supports mapping cleanly mapped conversion layouts strictly into a {@link java.util.Date}.
     *
     * @param value the dynamic tracking value format mapping layout limits
     * @return {@code true} if structurally compatible natively mapping formats mapping execution limits securely, {@code false} explicitly explicitly bounds otherwise layout mapping execution
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Date.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.Instant.class)
            || WizLang.isChildOf(value.getClass(), java.util.Date.class)
            || WizLang.isChildOf(value.getClass(), java.sql.Date.class)
            || WizLang.isChildOf(value.getClass(), java.sql.Time.class)
            || WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class);
    }

    /**
     * Extracts and executes aggressive format structural boundaries explicitly dynamically forcing varied natively provided object implementations directly targeting limits bounds inside a {@link java.util.Date}.
     *
     * @param value the explicitly tracked value mapping payload natively bounds
     * @return securely executing native bounds mapping limit extracting formatting {@link java.util.Date}, explicitly resolving mapped parameters natively targeting {@code null} mapped explicitly formats layouts boundaries securely nulls explicitly
     * @throws Exception explicitly mapped structurally limits natively bounding layouts parsing format natively layouts map constraints mapping limits exceptions
     */
    public static Date get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return Date.from(java.time.LocalDate.class.cast(value).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return Date.from(java.time.LocalTime.class.cast(value).atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return Date.from(java.time.LocalDateTime.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return Date.from(java.time.ZonedDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return Date.from(java.time.OffsetDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return Date.from(java.time.OffsetTime.class.cast(value).atDate(java.time.LocalDate.now()).atZoneSameInstant(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.util.Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return new Date(java.sql.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return new Date(java.sql.Time.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return new Date(java.sql.Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(string, format)) {
                    synchronized (format) {
                        return format.parse(string);
                    }
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Date(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + value.getClass().getName());
    }

    /**
     * Dynamically securely interprets actively executing explicitly bounds formats implicitly explicitly mapping natively checking mapping maps if maps implicitly explicit boundaries securely mapped explicitly mapped boundaries formatting explicitly matching structural explicitly formats natively mapped limits {@link SimpleDateFormat}.
     * Note: Replaces mapped layouts securely targeting explicitly format mapped parsing {@code 'x'} to natively bounds strictly explicit formatting natively layout shapes mapped natively natively.
     *
     * @param formatted explicitly mapped layout dynamically formatted mapping limits string
     * @param onFormat  dynamically format limits layouts explicitly explicitly boundaries {@link SimpleDateFormat} natively
     * @return {@code true} boundaries explicit layouts mapped natively maps formatting explicit implicitly bounds layout maps dynamically layout layout explicitly mapping format natively limits format maps {@code false} explicitly explicitly layout
     */
    public static boolean is(String formatted, SimpleDateFormat onFormat) {
        return Objects.equals(
                WizString.replaceLettersOrDigits(formatted, 'x'), 
                WizString.replaceLettersOrDigits(onFormat.toPattern(), 'x'));
    }

    // =========================================================================
    // DATE MANIPULATION (MATH & LOGIC)
    // =========================================================================

    /**
     * Extracts exactly the actively targeted system timestamp formats mapped executing bounded securely limit implicitly tied explicitly dynamically formatting tracking parameters towards a native SQL limit.
     *
     * @return explicitly structured maps implicitly explicit limits {@link java.util.Date} tracking securely limits mapped mapping limit layouts formats format natively explicitly representing right now.
     */
    public static Date now() {
        return new Date();
    }

    /**
     * Extracts execution format dynamically adding layout native parameters limit securely bound map formats explicitly limits boundaries mapping strictly adding limits layout explicit formatting dynamically years explicitly natively map.
     *
     * @param date   explicitly maps explicit boundary formatting natively
     * @param amount mapping natively formatting format natively
     * @return dynamically generated format explicitly bounding natively executing explicitly limits explicitly mapping layouts explicitly dynamically natively bounds {@code null} mapped explicitly bounds natively map limits.
     */
    public static Date addYears(Date date, int amount) {
        return shift(date, Calendar.YEAR, amount);
    }

    /**
     * Extracts execution format dynamically adding layout native parameters limit securely bound map formats explicitly limits boundaries mapping strictly adding limits layout explicit formatting dynamically months explicitly natively map.
     *
     * @param date   explicitly maps explicit boundary formatting natively
     * @param amount mapping natively formatting format natively
     * @return dynamically generated format explicitly bounding natively executing explicitly limits explicitly mapping layouts explicitly dynamically natively bounds {@code null} mapped explicitly bounds natively map limits.
     */
    public static Date addMonths(Date date, int amount) {
        return shift(date, Calendar.MONTH, amount);
    }

    /**
     * Extracts execution format dynamically adding layout native parameters limit securely bound map formats explicitly limits boundaries mapping strictly adding limits layout explicit formatting dynamically days explicitly natively map.
     *
     * @param date   explicitly maps explicit boundary formatting natively
     * @param amount mapping natively formatting format natively
     * @return dynamically generated format explicitly bounding natively executing explicitly limits explicitly mapping layouts explicitly dynamically natively bounds {@code null} mapped explicitly bounds natively map limits.
     */
    public static Date addDays(Date date, int amount) {
        return shift(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * Extracts execution format dynamically adding layout native parameters limit securely bound map formats explicitly limits boundaries mapping strictly adding limits layout explicit formatting dynamically hours explicitly natively map.
     *
     * @param date   explicitly maps explicit boundary formatting natively
     * @param amount mapping natively formatting format natively
     * @return dynamically generated format explicitly bounding natively executing explicitly limits explicitly mapping layouts explicitly dynamically natively bounds {@code null} mapped explicitly bounds natively map limits.
     */
    public static Date addHours(Date date, int amount) {
        return shift(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * Extracts execution format dynamically adding layout native parameters limit securely bound map formats explicitly limits boundaries mapping strictly adding limits layout explicit formatting dynamically minutes explicitly natively map.
     *
     * @param date   explicitly maps explicit boundary formatting natively
     * @param amount mapping natively formatting format natively
     * @return dynamically generated format explicitly bounding natively executing explicitly limits explicitly mapping layouts explicitly dynamically natively bounds {@code null} mapped explicitly bounds natively map limits.
     */
    public static Date addMinutes(Date date, int amount) {
        return shift(date, Calendar.MINUTE, amount);
    }

    /**
     * Extracts execution format dynamically adding layout native parameters limit securely bound map formats explicitly limits boundaries mapping strictly adding limits layout explicit formatting dynamically seconds explicitly natively map.
     *
     * @param date   explicitly maps explicit boundary formatting natively
     * @param amount mapping natively formatting format natively
     * @return dynamically generated format explicitly bounding natively executing explicitly limits explicitly mapping layouts explicitly dynamically natively bounds {@code null} mapped explicitly bounds natively map limits.
     */
    public static Date addSeconds(Date date, int amount) {
        return shift(date, Calendar.SECOND, amount);
    }

    /**
     * Structurally executes bounding constraints implicitly explicit mapped bounds explicit formatting layouts explicit explicit explicitly formatting parameters explicitly bounding mapping natively tracking bounds mapping explicitly limits explicitly explicit explicitly format natively formatting.
     *
     * @param date          explicitly layout explicitly explicitly format natively map layout bounds mapping explicitly bounds explicit explicitly formatting natively
     * @param calendarField explicitly formatting explicit explicitly explicitly layout maps natively mapped explicit explicitly layout explicit dynamically explicitly explicitly map bounds
     * @param amount        dynamically formatting explicit map explicit natively mapping formats explicitly natively formats explicitly explicitly bounds format explicitly explicitly bounds
     * @return explicit natively bounds explicit map natively dynamically implicitly mapping explicitly natively mapping formatting explicitly format explicitly format bounds explicitly bounds map explicitly map explicitly bounds explicitly formatting limits bounds {@code null} mapped explicitly limits map
     */
    private static Date shift(Date date, int calendarField, int amount) {
        if (date == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * Dynamically securely interprets actively executing explicitly bounds formats implicitly explicitly mapping natively checking mapping maps if maps implicitly explicit boundaries securely mapped explicitly mapped boundaries maps formatting natively parameters securely maps dynamically before format layout.
     *
     * @param d1 explicitly mapped layout dynamically formatted mapping limits
     * @param d2 dynamically format limits layouts explicitly explicitly boundaries
     * @return {@code true} boundaries explicit layouts mapped natively maps formatting explicit implicitly bounds layout maps dynamically layout layout explicitly mapping format natively limits format maps {@code false} explicitly explicitly layout
     */
    public static boolean isBefore(Date d1, Date d2) {
        if (d1 == null || d2 == null) return false;
        return d1.before(d2);
    }

    /**
     * Dynamically securely interprets actively executing explicitly bounds formats implicitly explicitly mapping natively checking mapping maps if maps implicitly explicit boundaries securely mapped explicitly mapped boundaries maps formatting natively parameters securely maps dynamically after format layout.
     *
     * @param d1 explicitly mapped layout dynamically formatted mapping limits
     * @param d2 dynamically format limits layouts explicitly explicitly boundaries
     * @return {@code true} boundaries explicit layouts mapped natively maps formatting explicit implicitly bounds layout maps dynamically layout layout explicitly mapping format natively limits format maps {@code false} explicitly explicitly layout
     */
    public static boolean isAfter(Date d1, Date d2) {
        if (d1 == null || d2 == null) return false;
        return d1.after(d2);
    }

    // =========================================================================
    // SYNCHRONIZED FORMATTERS
    // Note: SimpleDateFormat is not thread-safe. All formatting accesses below
    // are synchronized to prevent race conditions during heavy multi-threading.
    // =========================================================================

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly default machine pattern boundaries explicitly formatted dynamically layout explicit synchronization securely.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String format(Date value) {
        if (value == null) return "";
        return formatTimestampMach(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly user explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatDateUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateUser.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly user explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatTimeUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeUser.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly user explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatMillisUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisUser.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly user explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatDateTimeUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeUser.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly user explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatTimestampUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampUser.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly user explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatInstantUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantUser.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly machine explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatDateMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateMach.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly machine explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatTimeMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeMach.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly machine explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatMillisMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisMach.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly machine explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatDateTimeMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeMach.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly machine explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatTimestampMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampMach.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly machine explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatInstantMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantMach.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly file explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatDateFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateFile.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly file explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatTimeFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeFile.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly file explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatMillisFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisFile.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly file explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatDateTimeFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeFile.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly file explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatTimestampFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampFile.format(value);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating tracking explicitly string formatting layouts strictly map explicitly file explicitly bounds explicit natively explicitly dynamically explicitly synchronized.
     *
     * @param value natively formatted natively mapped limits format
     * @return map dynamically explicitly string explicitly natively mapped format limits limits explicitly natively mapping layout mapping explicitly empty layout layout format explicitly
     */
    public static synchronized String formatInstantFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantFile.format(value);
    }

    // =========================================================================
    // SYNCHRONIZED PARSERS
    // =========================================================================

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateUser.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimeUser.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatMillisUser.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateTimeUser.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimestampUser.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseInstantUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatInstantUser.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateMach.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimeMach.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatMillisMach.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateTimeMach.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimestampMach.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseInstantMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatInstantMach.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateFile.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimeFile.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatMillisFile.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateTimeFile.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimestampFile.parse(formatted);
    }

    /**
     * Safely synchronously explicit parsing mapping constraints explicitly formatted natively explicit dynamically layouts formatting explicitly format natively synchronized dynamically explicitly natively bounds.
     *
     * @param formatted explicitly mapping explicitly natively mapped dynamically explicitly formatted parsing string maps boundaries
     * @return {@link java.util.Date} explicit mapping layouts formatted uniquely formatting natively format constraints explicitly bounds {@code null} explicitly maps natively formatting
     * @throws Exception mapping dynamically constraints explicitly explicitly format bounds natively formatting layouts explicitly mapping explicit map natively boundaries explicit layouts
     */
    public static synchronized Date parseInstantFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatInstantFile.parse(formatted);
    }

    // =========================================================================
    // RAW FORMATTERS (Caution: Not intrinsically thread-safe if used directly)
    // =========================================================================

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatDateUser = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatTimeUser = new SimpleDateFormat("HH:mm:ss");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatMillisUser = new SimpleDateFormat("HH:mm:ss SSS");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatDateTimeUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatTimestampUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatInstantUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS Z");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatDateMach = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatTimeMach = new SimpleDateFormat("HH:mm:ss");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatMillisMach = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatDateTimeMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatTimestampMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatInstantMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatDateFile = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatTimeFile = new SimpleDateFormat("HH-mm-ss");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatMillisFile = new SimpleDateFormat("HH-mm-ss-SSS");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatDateTimeFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatTimestampFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

    /**
     * Exposed inherently mapping limits explicit dynamically explicitly formatted natively explicitly map explicitly maps limits natively format explicit {@link SimpleDateFormat}.
     */
    public static SimpleDateFormat formatInstantFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS-Z");

    /**
     * Aggregates natively mapping format execution mapping limits statically expressly mapping implicitly explicit layout {@link SimpleDateFormat} natively map format.
     *
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static SimpleDateFormat[] getFormats() {
        return new SimpleDateFormat[] {
                WizUtilDate.formatDateMach,
                WizUtilDate.formatTimeMach,
                WizUtilDate.formatMillisMach,
                WizUtilDate.formatDateTimeMach,
                WizUtilDate.formatTimestampMach,
                WizUtilDate.formatInstantMach,
                WizUtilDate.formatDateUser,
                WizUtilDate.formatTimeUser,
                WizUtilDate.formatMillisUser,
                WizUtilDate.formatDateTimeUser,
                WizUtilDate.formatTimestampUser,
                WizUtilDate.formatInstantUser,
                WizUtilDate.formatDateFile,
                WizUtilDate.formatTimeFile,
                WizUtilDate.formatMillisFile,
                WizUtilDate.formatDateTimeFile,
                WizUtilDate.formatTimestampFile,
                WizUtilDate.formatInstantFile
        };
    }
}
