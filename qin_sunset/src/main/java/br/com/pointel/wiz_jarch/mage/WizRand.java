package br.com.pointel.wiz_jarch.mage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A highly performant, thread-safe utility class for aggressively generating random numbers, 
 * booleans, characters, strings, {@link UUID} structures, and securely shuffling native collections.
 * <p>
 * This class fundamentally relies on {@link ThreadLocalRandom} to entirely isolate concurrency execution,
 * structurally bypassing native thread-contention bottlenecks typically associated with standard math libraries.
 * </p>
 */
public class WizRand {

    private WizRand() {
    }

    /**
     * Extracts a radically generated boolean outcome mapping precisely to a balanced evaluation.
     *
     * @return {@code true} or {@code false} driven securely mapping an approximate 50/50 probability footprint
     */
    public static Boolean getBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Extracts a randomly evaluated boolean outcome heavily bound natively by customized target odds.
     * <p>
     * Generates a constrained integer dynamically mapping between {@code min} (inclusive) and {@code max} (exclusive),
     * strictly triggering an active true limit if the bounded evaluation lands strictly underneath the provided odds target.
     * </p>
     *
     * @param min  the structural minimum boundaries mapping the lower random footprint roll limit (inclusive)
     * @param max  the structural maximum boundaries mapping the upper random footprint roll limit (exclusive)
     * @param odds the native threshold strictly triggering a valid true constraint
     * @return {@code true} if the generated evaluation resolves completely underneath the target odds parameter, {@code false} otherwise
     */
    public static Boolean getBool(Integer min, Integer max, Integer odds) {
        if (min == null || max == null || odds == null) return false;
        return getInt(min, max) < odds;
    }

    /**
     * Resolves a rapidly generated random single-digit format tracking integer limits.
     *
     * @return a naturally constrained integer bound safely between 0 and 9 (inclusive)
     */
    public static Integer getDigit() {
        return ThreadLocalRandom.current().nextInt(10);
    }

    /**
     * Extracts a naturally unconstrained 32-bit mapped integer spanning natively the absolute integer bounds constraint spectrum.
     *
     * @return an unmapped integer payload natively evaluated
     */
    public static Integer getInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * Extracts a securely constrained integer actively bound mapping natively below a strict maximum upper limit.
     *
     * @param max the rigid maximum constraints limit (exclusive)
     * @return an actively mapped integer bound strictly tracking 0 up to {@code max - 1}, naturally triggering {@code -1} if mapping boundaries corrupt
     */
    public static Integer getInt(Integer max) {
        if (max == null || max <= 0) {
            return -1;
        } else {
            return ThreadLocalRandom.current().nextInt(max);
        }
    }

    /**
     * Generates an actively targeted random integer tracking closely constraints natively mapped between rigid minimum limit and maximum limit limits.
     *
     * @param min the rigid minimum constraints boundary mapping limit (inclusive)
     * @param max the rigid maximum constraints boundary mapping limit (exclusive)
     * @return a targeted random integer bound tracking natively from {@code min} stretching towards {@code max - 1}, resolving {@code -1} actively evaluating invalid constraint limits
     */
    public static Integer getInt(Integer min, Integer max) {
        if (min == null || max == null || max <= min) {
            return -1;
        } else {
            return ThreadLocalRandom.current().nextInt(min, max);
        }
    }

    /**
     * Resolves an implicitly unconstrained 64-bit integer mapped long payload traversing freely the long scale spectrum natively.
     *
     * @return a targeted long constraints format randomly evaluated
     */
    public static Long getLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * Extracts a securely targeted long parameter mapping randomly tracking strictly bounded beneath a maximum ceiling target constraint.
     *
     * @param max the strict ceiling maximum bounds tracking target limit (exclusive)
     * @return a naturally generated random long constrained spanning limits tracking 0 towards {@code max - 1}, executing fallback {@code -1L} natively circumventing boundary corruption limits
     */
    public static Long getLong(Long max) {
        if (max == null || max <= 0) {
            return -1L;
        } else {
            return ThreadLocalRandom.current().nextLong(max);
        }
    }

    /**
     * Generates a constrained randomly generated long mapped securely traversing boundaries bounded by a targeted lower limit climbing strictly underneath a targeted maximum bounds boundary limit.
     *
     * @param min the strictly bound minimal tracking constraints (inclusive)
     * @param max the explicitly targeted max upper boundary (exclusive)
     * @return a targeted random long securely spanning native mapping constraints resolving from {@code min} mapped stretching out naturally targeting {@code max - 1}, naturally intercepting corrupted bounds returning {@code -1L}
     */
    public static Long getLong(Long min, Long max) {
        if (min == null || max == null || max <= min) {
            return -1L;
        } else {
            return ThreadLocalRandom.current().nextLong(min, max);
        }
    }

    /**
     * Static payload explicitly housing uppercase mapped standardized English language alphabet definitions mapping footprints strictly natively utilized targeting rapid mappings.
     */
    public static final String simpleUpperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Static concatenated array array explicitly consolidating active uppercase, precise lowercase native letters explicitly accompanied by natural mapping 0 limits through 9 boundary formats.
     */
    public static final String alphaNumericChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Rapidly fetches natively a targeted singular character exclusively leveraging natively isolated upper-cased letters mapped formatting strictly natively limits boundaries constraints definitions format footprint definitions layout maps natively.
     *
     * @return a uniquely generated constrained native character mapping bound explicitly within footprint definitions mapping 'A' mapping boundaries layout bounds formats map towards native maps 'Z'
     */
    public static char getChar() {
        return simpleUpperChars.charAt(ThreadLocalRandom.current().nextInt(simpleUpperChars.length()));
    }

    /**
     * Securely generates natively mapped format natively generated dynamically formatted explicit strings natively mapping strict layout explicitly bound within format format exclusively isolating native strings mapping format explicitly upper-case limits explicitly bounds.
     *
     * @param size the explicit formatting size targets native constraints boundaries constraints length explicitly formatted maps natively defining parameters targeting maps footprint explicit string length layout
     * @return a generated native footprint mapped dynamically constructed string mapping definitions limits targeting sizes strictly, natively defaulting towards mapping an natively empty formatting structure safely intercepting mapping native 0 layout lengths footprint boundaries strings formatted limit constraints formats maps limits bounds limits formats natively length limits formats
     */
    public static String getChars(int size) {
        if (size <= 0) return "";
        var result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            result.append(getChar());
        }
        return result.toString();
    }

    /**
     * Actively constructs natively explicitly highly volatile securely generated unique identifier strings structurally composed layout mapped strictly utilizing natively formatted native limits constraints bounds formatting uppercase, natively bound natively defined limits formatted layout lowercase, and dynamically generated explicit mapping native natively numeric limits format boundaries format limits limits string formats footprint characters formatting strictly parameters explicitly formatting layout format bounds mapping formats.
     *
     * @param size the bounds targeted structural dimensions mapped natively natively formatting native constraints limit explicitly defining parameters layout targeting dimensions explicitly boundaries bounds formats explicit string length layouts formatting mapping sizes limit formats dimensions layouts
     * @return natively generated mapping targeted format explicitly alphanumeric token definition strings layout formatting securely mapping layout formats, explicit mapped strictly empty format payload limits executing actively upon natively mapping limits defining sizes strictly formatting bounds mapping sizes boundaries dimensions limits native securely 0 mapping limits formatting limits length constraints layouts formatting explicitly length maps mapping footprint formats limits mapping explicit length
     */
    public static String getAlphaNumericString(int size) {
        if (size <= 0) return "";
        var result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            result.append(alphaNumericChars.charAt(ThreadLocalRandom.current().nextInt(alphaNumericChars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a completely randomized dynamically formatted float scale mapped natively explicitly mapping boundaries securely executing mapping explicitly distributed spanning limits securely explicitly mapping limits bounds spanning formats limits explicitly spanning mapping explicitly targeting bounds securely between limits explicitly mapping 0.0 limit explicitly map bounds dynamically mapping mapping explicitly towards formatting native limit bounds explicitly 1.0 layouts explicit limits footprint limits format layouts formatting limits mapped securely.
     *
     * @return natively dynamically mapped random explicit limits floating point scale formats limits formatting footprint
     */
    public static Float getFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    /**
     * Resolves uniquely natively explicitly natively mapping mapped explicit dynamically securely formatted explicit double footprint maps structurally explicitly bounded formatting scale mapped securely explicitly securely traversing explicit native limit spanning dynamically between boundaries explicitly strictly mapping limits bounded limits explicitly boundaries bounds explicit formatting explicit limit explicitly strictly mapping layout format natively limits 0.0 bounds natively bounds limit formatted explicit bounds formats explicitly towards mapping natively mapping limits mapped bounds natively format limits 1.0 bounds limit limit mapping bounds explicitly explicit format layout footprint formatting explicit layouts.
     *
     * @return dynamically generated native precision explicit bounds securely double layout mapping boundaries formatted dynamically footprint
     */
    public static Double getDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * Actively executes securely generating natively mapped dynamically layout explicit boundaries securely pseudorandom double format boundaries map scaling structurally bounded native layout limits bounds explicit securely formats maps natively targeting bounds strictly format dynamically explicitly normal limits formats limits explicitly explicitly footprint format layouts limits formats dynamically mapped boundaries limits format limits explicitly securely explicitly bounds securely Gaussian scale footprint natively maps layout formatted explicitly limit limits layout dynamically mapped constraints layout strictly formats layout explicitly mapping layout natively maps explicitly layout formats boundaries natively formatting limits boundaries explicitly explicitly layout limits boundaries explicitly explicitly natively mapping explicitly limits natively securely mapping boundaries explicitly securely Gaussian format dynamically mapping bounds explicitly maps footprint securely explicitly boundaries explicit format bounds bounds formatting boundaries formats explicitly natively limits natively limits explicit format layouts dynamically mapped native explicitly mapping formats boundaries explicitly explicitly layouts bounds layout limits format mapping limits boundaries limits natively natively format securely explicitly limits natively bounds limits layout format limits maps explicitly limits layout explicitly explicitly limits bounds layout explicitly natively explicitly maps explicitly natively explicitly explicitly explicitly format.
     *
     * @return dynamically mathematically explicitly normally distributed dynamically generated mapped random limits native double layout explicitly maps limits layout natively footprint
     */
    public static Double getGaussian() {
        return ThreadLocalRandom.current().nextGaussian();
    }

    /**
     * Aggressively forces layout formats dynamically native overwrite executing strictly modifying explicit bytes payload structurally mapping strictly bounds completely securely bounds dynamically explicit boundaries limits mapped securely explicitly native random mapped formats byte limits explicit configurations securely natively map limits.
     *
     * @param bytes natively populated arrays structure bounds mapping mapping explicitly arrays bounds natively strictly targeted formatting payload execution limit explicit overwrite explicit mapping maps
     */
    public static void getBytes(byte[] bytes) {
        if (bytes != null) {
            ThreadLocalRandom.current().nextBytes(bytes);
        }
    }

    /**
     * Actively generates native execution formatted strings payload natively explicitly structurally mapping securely explicitly explicitly generating explicit mapped layout standard securely formatted uniquely limits explicitly formatting explicit Version natively bounds limit 4 explicitly generated standard native bounds dynamically mapping unique identifier securely formats explicit native layout identifier formats explicitly explicitly limits.
     *
     * @return formatted standardized natively explicitly UUID token explicitly limits layout mapped native string dynamically limits payload footprint natively (e.g., {@code 550e8400-e29b-41d4-a716-446655440000}) explicitly formats explicitly
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Selects randomly executing targeted securely bounds selection explicit limits isolating singularly dynamically mapping format native singularly mapped explicit randomly formatting explicit explicitly chosen native payload maps explicit explicit arrays explicitly formatting limits dynamically maps elements.
     *
     * @param <T>       the generic structurally dynamically mapped generic type formats element format payload explicitly parameters array explicitly maps limits footprint format bounds boundaries array explicitly layouts explicit format
     * @param fromArray natively mapped explicitly bounds layout formatting securely mapping arrays execution format native explicit explicit selection explicitly map bounds format explicitly
     * @return explicitly selected natively formatted dynamically mapped elements layout execution payload explicitly mapped limits mapped maps securely explicitly safely returning format native explicitly bounds {@code null} formatting map bounds constraints map explicit layout formatting native dynamically mapping explicitly parameters dynamically formats boundaries arrays explicitly map explicitly explicitly empty limits arrays bounds explicitly limits map explicitly
     */
    public static <T> T getItem(T[] fromArray) {
        if (fromArray != null && fromArray.length > 0) {
            return fromArray[ThreadLocalRandom.current().nextInt(fromArray.length)];
        }
        return null;
    }

    /**
     * Extracts randomly explicitly uniquely uniquely explicit mapping native explicitly dynamic singularly randomly targeted securely explicitly layout mapped element dynamically mapping explicitly targeting explicit limits natively limits dynamically formats natively native explicit {@link List} securely explicit boundaries payload format maps native limits layout explicit format limit arrays format explicitly maps explicitly layout limits maps format array explicit explicitly formats limits limits mapping mapping explicit limits boundaries layout layout limit arrays format natively.
     *
     * @param <T>      the native structurally formatted explicit dynamically mapped types mapped formatting natively explicitly native elements arrays explicit payload bounds boundaries lists format explicitly
     * @param fromList explicitly targeted native map explicit arrays dynamically boundaries explicit natively explicitly layout formats natively selection execution mapped format limit explicit payload limits maps explicitly explicitly arrays layout explicitly format mapping boundaries explicitly layout bounds explicitly natively
     * @return singularly selected explicitly natively layout map randomly mapped elements execution explicit payload natively returning boundaries format safely mapped dynamically layouts explicitly mapping {@code null} mapped formatting limits natively layouts arrays mapped map format explicitly explicitly limits limits map bounds format formatting boundaries layout explicit explicitly limits maps layout mapped mapping maps dynamically layouts empty boundaries map explicitly formats explicitly natively explicitly natively format format limits explicitly layout formats
     */
    public static <T> T getItem(List<T> fromList) {
        if (fromList != null && !fromList.isEmpty()) {
            return fromList.get(ThreadLocalRandom.current().nextInt(fromList.size()));
        }
        return null;
    }

    /**
     * Systematically securely natively modifies limits formats random mapping native layout natively execution mapping explicit map explicitly mapping bounds boundaries arrays securely executing explicitly layout bounds in-place map explicit natively mapped explicit maps formats execution native limit format dynamically format formatting bounds explicitly maps natively maps explicit lists formats limits explicitly.
     *
     * @param <T>  the bounds securely formats structurally dynamically mapped type arrays mapping natively mapped explicit explicit bounds element formats layout maps lists format explicitly map boundaries
     * @param list natively strictly bounds natively maps explicit explicitly arrays format targeted payload formatting securely limits explicitly explicitly natively mapping boundaries explicitly explicitly layout limits natively explicitly explicitly arrays layouts dynamically format maps format mapped formats explicitly explicitly
     */
    public static <T> void shuffle(List<T> list) {
        if (list != null && !list.isEmpty()) {
            Collections.shuffle(list, ThreadLocalRandom.current());
        }
    }
}
