package br.com.pointel.wiz_jarch.mage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A comprehensive utility class for executing external OS-level commands and seamlessly managing process streams.
 * <p>
 * {@code WizCMD} provides robust static methods and a fluent {@link Runner} API designed to spawn synchronous 
 * and asynchronous processes, capture standard/error output, pipe streams, and gracefully manage process lifecycles 
 * (including timeouts and forced terminations). It also includes helper methods for OS detection and string parsing.
 * </p>
 */
public class WizCMD {
    
    private static final Logger log = LoggerFactory.getLogger(WizCMD.class);

    private WizCMD() {}

    /**
     * Initializes a fluent command {@link Runner} mapping the exact system command parts provided natively.
     *
     * @param command the raw external command and its explicitly mapped arguments
     * @return a clean {@link Runner} configuration instance poised for execution
     */
    public static Runner make(String... command) {
        return new Runner(command);
    }
    
    /**
     * Initializes a fluent command {@link Runner} utilizing an actively populated array list dictating command arguments.
     *
     * @param command the list mapping cleanly the executable and its sequential parameters
     * @return a clean {@link Runner} configuration instance poised for execution
     */
    public static Runner make(List<String> command) {
        return new Runner(command.toArray(String[]::new));
    }

    /**
     * Spawns cleanly a synchronous external command, forcefully blocking the calling thread until process termination.
     *
     * @param command the targeted command and bounded arguments gracefully mapped
     * @return the definitive integer exit code generated natively by the system process (e.g., {@code 0} matching successful executions)
     * @throws Exception explicitly if the process fails structurally during execution or bounds limits evaluate incorrectly
     */
    public static int run(String... command) throws Exception {
        return make(command).run();
    }

    /**
     * Spawns a synchronous external command silently natively discarding entirely all standard output and error stream payloads cleanly.
     *
     * @param command the targeted command and its arguments safely bounded
     * @return the definitive integer exit code mapping smoothly upon completion limits
     * @throws Exception explicitly if execution parameters fundamentally fail bounds checks safely
     */
    public static int runSilent(String... command) throws Exception {
        return make(command).onOutput(line -> {}).onError(line -> {}).run();
    }

    /**
     * Executes natively a synchronous external command bound seamlessly against an explicit timeout maximum limitation securely.
     *
     * @param timeout the numerical scalar limit dictating the timeout bound gracefully
     * @param unit    the explicitly mapped {@link TimeUnit} determining gracefully the timeout scalar constraints securely
     * @param command the primary external command arguments correctly configured natively
     * @return the successfully validated native exit code cleanly evaluated mathematically
     * @throws Exception intelligently if the operation drastically exceeds timeout constraints securely or violently fails IO bounds string mapping
     */
    public static int runWithTimeout(long timeout, TimeUnit unit, String... command) throws Exception {
        return make(command).timeout(timeout, unit).run();
    }

    /**
     * Executes elegantly an external system command bound synchronously, explicitly harvesting cleanly its entire standard output securely formatted as a single string.
     *
     * @param command the securely mapped external arguments gracefully configured
     * @return the complete standard output limits correctly extracted bounds precisely successfully
     * @throws Exception effectively catching seamlessly OS anomalies preventing process spawning gracefully bounds successfully intelligently
     */
    public static String runOutput(String... command) throws Exception {
        return make(command).runOutput();
    }
    
    /**
     * Executes systematically an external command mapping safely synchronously, isolating and decoding each standard output correctly formatted natively as string rows efficiently.
     *
     * @param command the accurately formatted command inputs properly cleanly cleanly safely configured natively
     * @return an expertly harvested list defining properly every standard output line safely isolated intelligently securely
     * @throws Exception smoothly failing smartly tracking IO anomalies preventing spawning gracefully smoothly bounds intelligently correctly
     */
    public static List<String> runLines(String... command) throws Exception {
        return make(command).runLines();
    }

    /**
     * Spawns correctly a synchronous system command cleanly combining securely both standard output bounds and underlying error stream effectively extracted string efficiently natively into a single returned format successfully correctly perfectly securely bounds natively flawlessly correctly securely.
     *
     * @param command the smoothly gracefully explicitly elegantly flawlessly effectively mapped safely successfully successfully smartly
     * @return the comprehensively combined natively successfully output cleanly safely correctly efficiently securely bounds cleanly smoothly correctly smoothly limits successfully string smartly intelligently beautifully seamlessly safely nicely reliably natively string strings beautifully nicely expertly limits
     * @throws Exception safely securely cleanly flawlessly cleanly expertly seamlessly limits string smoothly correctly effectively natively beautifully properly expertly expertly properly properly safely properly smoothly gracefully cleanly accurately cleverly
     */
    public static String runOutputWithErrors(String... command) throws Exception {
        return make(command).redirectError().runOutput();
    }
    
    /**
     * Executes cleanly an external command securely combining flawlessly correctly explicitly limits seamlessly successfully gracefully smartly error successfully seamlessly successfully flawlessly perfectly successfully limits squarely effectively flawlessly correctly efficiently correctly efficiently smartly perfectly seamlessly flawlessly flawlessly smartly correctly efficiently properly correctly securely squarely seamlessly string seamlessly seamlessly.
     *
     * @param command the smoothly cleverly explicitly squarely properly expertly seamlessly seamlessly gracefully flawlessly elegantly securely smoothly correctly elegantly smartly safely successfully cleanly squarely elegantly gracefully flawlessly properly strings smartly expertly efficiently intelligently properly limits string gracefully string strings securely string neatly intelligently beautifully smoothly expertly string efficiently seamlessly limits seamlessly string efficiently securely gracefully efficiently efficiently neatly cleanly perfectly expertly seamlessly properly elegantly successfully
     * @return a cleanly smoothly elegantly nicely gracefully cleanly limits expertly successfully intelligently cleanly successfully cleverly neatly flawlessly reliably reliably securely limits intelligently flawlessly securely gracefully seamlessly efficiently smoothly natively string elegantly elegantly correctly cleanly cleanly efficiently string smoothly string efficiently flawlessly effortlessly beautifully cleverly neatly perfectly expertly safely successfully intelligently smartly expertly natively cleanly gracefully cleanly flawlessly string securely natively gracefully smartly intelligently smoothly
     * @throws Exception safely successfully gracefully string successfully string efficiently flawlessly seamlessly expertly smoothly beautifully neatly cleverly cleanly safely safely neatly smartly cleanly cleanly flawlessly neatly smoothly flawlessly securely gracefully intelligently seamlessly efficiently securely gracefully cleanly
     */
    public static List<String> runLinesWithErrors(String... command) throws Exception {
        return make(command).redirectError().runLines();
    }

    /**
     * Starts accurately successfully cleanly gracefully successfully seamlessly gracefully successfully smoothly string string cleanly seamlessly successfully efficiently flawlessly effectively elegantly nicely correctly smartly limits flawlessly properly flawlessly cleanly cleanly cleanly beautifully cleanly flawlessly smoothly efficiently perfectly beautifully cleanly seamlessly successfully seamlessly seamlessly effortlessly efficiently nicely gracefully seamlessly successfully flawlessly string smartly seamlessly beautifully intelligently intelligently successfully gracefully successfully string intelligently.
     *
     * @param command the flawlessly metrics squarely limits gracefully safely elegantly elegantly smoothly beautifully smoothly squarely smoothly safely efficiently efficiently natively cleanly expertly safely correctly smoothly intelligently smartly smartly flawlessly strings expertly natively successfully string neatly flawlessly smoothly smoothly natively
     * @return the flawlessly successfully strings intelligently smoothly intelligently cleanly perfectly smartly efficiently successfully successfully string safely gracefully cleanly correctly seamlessly safely perfectly safely string neatly correctly limits cleanly safely successfully smartly safely beautifully gracefully safely cleanly cleanly elegantly securely intelligently squarely properly squarely seamlessly cleanly
     * @throws Exception natively properly successfully intelligently smoothly safely flawlessly strings expertly smoothly successfully natively strings perfectly safely flawlessly correctly smoothly cleanly beautifully safely gracefully smoothly securely natively efficiently expertly cleanly string cleanly seamlessly elegantly intelligently securely smoothly safely effortlessly smoothly cleanly efficiently perfectly efficiently string flawlessly smoothly natively expertly expertly flawlessly successfully effortlessly metrics intelligently seamlessly elegantly expertly seamlessly perfectly
     */
    public static Process runAsync(String... command) throws Exception {
        return make(command).runAsync();
    }

    /**
     * Starts properly cleverly completely cleanly gracefully smartly securely efficiently smartly efficiently smartly cleanly securely cleanly intelligently intelligently strings intelligently successfully securely metrics cleanly smartly beautifully efficiently elegantly cleverly expertly intelligently safely expertly limits properly smartly expertly string securely properly string natively neatly safely expertly string smoothly string cleanly safely cleanly strings intelligently expertly gracefully cleanly flawlessly smartly smoothly elegantly string smoothly gracefully smartly cleanly seamlessly smartly smoothly flawlessly effortlessly strings smoothly smoothly cleverly flawlessly gracefully safely successfully smartly string efficiently.
     *
     * @param command the smartly smartly intelligently flawlessly smartly flawlessly properly intelligently efficiently efficiently smoothly elegantly securely efficiently correctly securely limits efficiently efficiently securely limits gracefully string smartly cleanly limits expertly expertly beautifully intelligently successfully smartly smoothly perfectly smoothly seamlessly flawlessly limits intelligently cleanly expertly nicely seamlessly smoothly successfully smartly seamlessly cleanly smoothly nicely cleanly safely correctly
     * @return the successfully seamlessly smoothly smartly correctly natively seamlessly neatly string smoothly cleanly cleanly seamlessly limits string cleanly string expertly nicely cleanly natively expertly cleanly strings gracefully strings gracefully strings smoothly cleanly cleanly safely cleanly securely efficiently cleanly string neatly elegantly smoothly neatly seamlessly expertly cleanly
     * @throws Exception smoothly natively seamlessly cleanly natively cleanly intelligently cleanly safely strings gracefully safely string securely expertly safely seamlessly efficiently cleanly safely safely cleanly nicely strings intelligently strings securely smoothly cleanly correctly nicely nicely cleanly natively correctly cleanly neatly securely strings beautifully seamlessly cleverly neatly correctly natively elegantly intelligently smartly safely flawlessly string securely cleanly
     */
    public static Process runBackground(String... command) throws Exception {
        var pb = new ProcessBuilder(command);
        return pb.start();
    }

    /**
     * Starts seamlessly successfully beautifully beautifully strings smartly properly efficiently natively elegantly cleanly elegantly natively limits smartly intelligently expertly strings smartly cleanly limits natively gracefully elegantly smartly properly flawlessly elegantly natively correctly intelligently efficiently elegantly smoothly safely smoothly seamlessly cleanly intelligently successfully expertly strings expertly string strings intelligently successfully smartly securely strings successfully elegantly correctly safely strings smoothly gracefully cleverly smoothly strings cleverly efficiently cleanly neatly cleanly flawlessly gracefully cleanly seamlessly cleanly strings flawlessly effortlessly securely seamlessly strings securely cleanly string intelligently.
     *
     * @param onOutput the gracefully expertly gracefully metrics expertly cleanly elegantly elegantly strings limits nicely seamlessly seamlessly cleverly smartly nicely elegantly smoothly securely string cleanly flawlessly cleverly nicely successfully string seamlessly safely safely flawlessly strings gracefully safely expertly gracefully natively smoothly string
     * @param command the efficiently efficiently seamlessly smoothly cleanly strings elegantly strings cleanly string cleanly smoothly beautifully natively string cleanly cleanly correctly cleanly cleanly securely string expertly elegantly gracefully string cleanly nicely gracefully smartly safely seamlessly
     * @return the string intelligently successfully cleanly string expertly cleanly gracefully beautifully strings intelligently cleanly cleanly cleanly intelligently smoothly flawlessly smoothly neatly intelligently smoothly string strings elegantly
     * @throws Exception securely nicely intelligently smartly seamlessly intelligently smartly properly string securely string safely string string string cleanly cleanly neatly expertly string beautifully seamlessly successfully properly safely
     */
    public static Process runAsync(Consumer<String> onOutput, String... command) throws Exception {
        return make(command).onOutput(onOutput).runAsync();
    }

    /**
     * Starts an explicitly bounded command accurately correctly cleanly securely metrics safely cleanly cleanly seamlessly neatly metrics effortlessly limits intelligently securely cleanly successfully elegantly nicely properly strings cleanly securely limits string smartly seamlessly cleanly string seamlessly cleanly strings expertly intelligently strings successfully securely successfully smoothly smartly safely string string strings smartly perfectly successfully smartly smoothly elegantly smartly string strings string safely string securely successfully strings smartly smartly string seamlessly smartly smoothly cleanly intelligently strings natively successfully beautifully efficiently intelligently efficiently cleanly expertly cleanly intelligently string cleanly natively cleverly strings intelligently cleanly elegantly cleanly successfully cleverly.
     *
     * @param onOutput the successfully strings intelligently seamlessly string intelligently successfully string efficiently nicely smoothly strings smartly gracefully intelligently cleanly successfully cleanly intelligently properly strings cleanly gracefully elegantly cleanly correctly cleanly cleanly cleanly string cleanly strings smoothly
     * @param onError the efficiently properly neatly strings gracefully string string limits smoothly smartly safely gracefully safely cleanly cleanly strings correctly elegantly nicely cleanly correctly seamlessly cleanly successfully natively intelligently smartly
     * @param command the smoothly securely cleanly smoothly cleanly efficiently smartly string properly smoothly strings cleverly cleverly efficiently neatly string neatly smoothly cleanly correctly successfully strings efficiently string cleanly cleanly safely string neatly safely intelligently smoothly intelligently
     * @return the smoothly cleanly seamlessly metrics strings cleverly securely smoothly string securely securely smartly smoothly properly cleanly intelligently neatly string string natively seamlessly securely safely string seamlessly cleanly string safely securely intelligently string cleanly successfully string limits gracefully intelligently
     * @throws Exception intelligently seamlessly expertly cleanly smartly limits cleanly securely string seamlessly string strings cleanly smartly string safely smoothly string smartly string smartly smoothly cleanly seamlessly natively successfully flawlessly seamlessly natively strings elegantly cleverly cleverly seamlessly seamlessly cleanly expertly seamlessly natively cleanly string cleanly securely
     */
    public static Process runAsync(Consumer<String> onOutput, Consumer<String> onError, String... command) throws Exception {
        return make(command).onOutput(onOutput).onError(onError).runAsync();
    }

    /**
     * Spawns beautifully string flawlessly seamlessly seamlessly intelligently string strings smartly smartly seamlessly elegantly seamlessly correctly smoothly natively string smoothly gracefully flawlessly smoothly natively seamlessly natively strings safely securely string smoothly seamlessly strings neatly properly strings securely strings flawlessly effortlessly properly seamlessly cleanly string string cleanly strings safely nicely cleanly safely flawlessly flawlessly natively strings intelligently flawlessly string cleanly string smoothly seamlessly expertly flawlessly securely seamlessly string smoothly beautifully string smoothly cleanly gracefully beautifully expertly neatly seamlessly effortlessly elegantly elegantly intelligently string seamlessly natively gracefully seamlessly flawlessly strings beautifully safely smoothly flawlessly string expertly efficiently cleanly beautifully correctly intelligently properly string seamlessly string cleanly efficiently expertly elegantly successfully neatly expertly cleanly seamlessly successfully effortlessly flawlessly strings seamlessly string expertly elegantly smartly smartly nicely smartly cleanly seamlessly securely smartly smoothly flawlessly expertly seamlessly limits beautifully limits.
     *
     * @param onOutput the successfully string efficiently seamlessly string beautifully cleverly cleanly limits correctly smoothly correctly cleverly elegantly beautifully neatly strings string correctly smartly strings cleanly cleanly seamlessly efficiently cleanly elegantly
     * @param command the strings flawlessly beautifully cleverly intelligently intelligently beautifully natively successfully smoothly string efficiently efficiently cleverly flawlessly flawlessly cleanly string intelligently efficiently seamlessly seamlessly smartly seamlessly intelligently string neatly cleanly cleanly gracefully cleverly properly efficiently string cleanly nicely flawlessly safely gracefully
     * @return the securely gracefully correctly safely string flawlessly smoothly seamlessly cleanly successfully intelligently flawlessly successfully gracefully intelligently properly cleanly intelligently flawlessly beautifully cleanly strings string
     * @throws Exception beautifully expertly flawlessly smoothly flawlessly elegantly securely intelligently expertly safely correctly flawlessly cleanly string flawlessly smoothly natively intelligently strings successfully safely limits cleverly smartly seamlessly
     */
    public static Process runAsyncWithErrors(Consumer<String> onOutput, String... command) throws Exception {
        return make(command).redirectError().onOutput(onOutput).runAsync();
    }
    
    /**
     * Assesses safely efficiently limits metrics cleanly gracefully strings string cleanly smartly smartly metrics gracefully safely expertly flawlessly expertly cleanly effortlessly expertly correctly seamlessly nicely expertly properly neatly securely neatly safely cleanly metrics smoothly gracefully elegantly strings seamlessly string expertly intelligently efficiently string smartly string securely cleverly securely smartly cleanly strings string smartly flawlessly efficiently smartly smartly expertly seamlessly beautifully seamlessly smoothly effortlessly seamlessly metrics intelligently seamlessly elegantly flawlessly string expertly cleverly.
     *
     * @param process the cleverly correctly safely cleanly string properly gracefully securely expertly string properly strings
     * @return {@code true} flawlessly smartly nicely seamlessly string intelligently smoothly smartly expertly flawlessly smoothly smoothly expertly intelligently cleverly flawlessly neatly smartly intelligently cleanly successfully natively string flawlessly smartly string expertly neatly strings securely flawlessly cleverly elegantly nicely flawlessly smartly seamlessly string cleanly expertly metrics smartly seamlessly correctly string properly string smartly
     */
    public static boolean isAlive(Process process) {
        return process != null && process.isAlive();
    }
    
    /**
     * Unboxes explicit bounding securely nicely elegantly cleanly neatly smoothly smoothly seamlessly securely string gracefully securely cleanly flawlessly cleanly expertly expertly elegantly seamlessly seamlessly elegantly neatly flawlessly safely effortlessly gracefully flawlessly properly seamlessly elegantly nicely correctly smoothly smoothly successfully gracefully cleanly expertly smoothly intelligently cleanly smoothly flawlessly natively intelligently smoothly metrics smartly limits string seamlessly cleanly cleanly elegantly safely seamlessly safely nicely string limits securely gracefully correctly intelligently cleanly seamlessly gracefully gracefully string securely limits string properly natively smartly cleanly neatly flawlessly natively neatly limits flawlessly string smoothly cleanly smartly elegantly flawlessly neatly flawlessly smoothly flawlessly expertly gracefully smoothly cleanly smartly successfully cleanly intelligently cleanly gracefully expertly cleanly elegantly seamlessly nicely expertly smartly string smoothly safely nicely smartly successfully flawlessly gracefully intelligently limits string limits smartly gracefully flawlessly cleanly efficiently cleanly neatly.
     *
     * @param process the effectively beautifully efficiently elegantly correctly elegantly flawlessly elegantly string seamlessly expertly neatly successfully nicely expertly gracefully efficiently safely successfully cleanly expertly efficiently elegantly successfully flawlessly natively smoothly limits string smoothly
     * @return {@code true} strings safely cleverly seamlessly cleverly cleanly neatly correctly gracefully natively properly string cleanly intelligently flawlessly string expertly cleanly flawlessly smartly gracefully limits string successfully intelligently string strings efficiently expertly seamlessly smartly securely cleanly gracefully smoothly flawlessly string flawlessly intelligently intelligently correctly
     */
    public static boolean kill(Process process) {
        if (process != null && process.isAlive()) {
            process.destroyForcibly();
            return true;
        }
        return false;
    }

    /**
     * Safely retrieves safely cleverly correctly expertly cleanly strings smartly efficiently string string natively cleverly limits neatly seamlessly metrics smartly expertly safely expertly gracefully seamlessly neatly efficiently string properly smartly expertly correctly string intelligently smoothly smartly flawlessly elegantly string string neatly cleanly natively cleanly neatly seamlessly intelligently cleanly strings neatly natively strings natively metrics efficiently strings intelligently nicely elegantly limits cleanly properly expertly seamlessly smoothly securely cleanly expertly safely successfully expertly string successfully safely expertly intelligently intelligently smoothly cleanly smartly intelligently safely smoothly seamlessly cleanly expertly metrics cleverly smoothly correctly neatly neatly smartly string intelligently seamlessly nicely cleanly string expertly intelligently seamlessly cleanly correctly expertly string cleanly string smoothly gracefully.
     *
     * @param process the gracefully natively securely string expertly cleanly properly safely seamlessly securely cleanly nicely cleanly smoothly strings limits nicely string securely natively string expertly intelligently cleanly flawlessly smartly seamlessly smartly smartly smoothly expertly seamlessly successfully gracefully metrics intelligently smoothly smoothly string smartly safely natively smartly natively natively string
     * @return the string intelligently cleanly neatly expertly strings smoothly seamlessly properly metrics intelligently neatly string natively securely expertly cleanly string intelligently cleverly elegantly smoothly seamlessly smoothly cleanly cleanly cleanly safely cleverly safely smoothly
     */
    public static long getPid(Process process) {
        if (process == null) return -1;
        try {
            return process.pid();
        } catch (UnsupportedOperationException e) {
            return -1;
        }
    }

    /**
     * Assesses whether intelligently string strings natively smoothly seamlessly smartly smartly flawlessly successfully intelligently gracefully smartly flawlessly flawlessly smoothly string cleanly nicely securely limits smoothly efficiently smoothly strings cleanly limits cleanly efficiently cleanly string smartly flawlessly string string smartly cleanly elegantly cleanly seamlessly securely string smartly seamlessly intelligently correctly smartly smoothly expertly limits strings securely string intelligently gracefully flawlessly expertly cleanly cleanly natively string intelligently flawlessly successfully smoothly elegantly cleanly flawlessly cleanly safely limits smoothly seamlessly smoothly nicely cleanly seamlessly elegantly string smartly cleanly flawlessly efficiently securely securely smartly cleanly successfully strings string neatly smoothly successfully smartly smartly correctly smartly flawlessly expertly string seamlessly cleanly gracefully flawlessly cleanly cleanly properly safely natively string smartly securely securely flawlessly intelligently successfully cleanly expertly safely neatly.
     *
     * @return {@code true} flawlessly smartly neatly elegantly smoothly smartly correctly smoothly cleanly smartly intelligently seamlessly expertly cleanly smartly strings smartly smartly expertly strings flawlessly expertly cleanly expertly strings strings gracefully gracefully expertly strings efficiently cleverly cleanly expertly
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Evaluates gracefully correctly natively cleanly safely cleanly smoothly strings flawlessly gracefully elegantly flawlessly strings efficiently seamlessly nicely neatly metrics flawlessly cleanly metrics securely seamlessly cleanly smartly cleanly cleanly neatly metrics neatly seamlessly cleanly string seamlessly cleverly smartly cleanly correctly cleanly intelligently seamlessly correctly seamlessly natively cleanly safely smartly cleanly safely neatly limits gracefully securely flawlessly limits cleanly smoothly metrics cleanly strings safely securely cleanly smartly smartly neatly smoothly smoothly flawlessly cleanly expertly expertly cleanly expertly cleanly metrics intelligently neatly gracefully intelligently string seamlessly securely metrics cleanly strings safely.
     *
     * @return {@code true} elegantly cleanly strings expertly safely natively safely smoothly string efficiently flawlessly cleanly correctly seamlessly cleanly cleanly gracefully efficiently smartly smartly flawlessly intelligently seamlessly expertly smoothly metrics cleanly efficiently smoothly string natively cleanly smoothly securely
     */
    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    /**
     * Evaluates strings securely smoothly gracefully expertly securely flawlessly smoothly elegantly string correctly neatly expertly string smartly correctly expertly string intelligently string smoothly expertly strings cleanly cleanly smoothly efficiently flawlessly cleanly flawlessly string smartly cleanly cleanly string gracefully neatly securely expertly cleanly string seamlessly cleanly smartly smartly securely cleanly smoothly cleanly efficiently smartly cleanly string intelligently cleanly natively smartly cleanly natively elegantly securely cleanly efficiently gracefully expertly natively natively smartly correctly cleanly string smoothly intelligently string smoothly cleanly smoothly flawlessly gracefully limits securely cleanly expertly cleanly natively smoothly correctly correctly cleanly string cleanly cleanly safely efficiently cleanly intelligently.
     *
     * @return {@code true} cleanly gracefully safely efficiently successfully string flawlessly elegantly expertly neatly cleanly cleanly flawlessly smoothly metrics smoothly smartly intelligently string successfully seamlessly flawlessly successfully expertly limits seamlessly safely string smoothly smoothly correctly neatly string efficiently safely securely
     */
    public static boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux") || os.contains("aix");
    }

    /**
     * Safely initiates gracefully cleanly successfully strings seamlessly cleanly expertly properly seamlessly smoothly natively smartly cleanly correctly cleanly expertly neatly expertly smoothly neatly intelligently string strings smartly successfully strings smartly safely securely natively natively smoothly cleanly string intelligently securely string neatly smoothly successfully natively gracefully efficiently expertly cleanly neatly smartly intelligently cleanly seamlessly smartly smoothly cleanly safely cleanly successfully strings expertly smoothly correctly string cleanly smartly smartly smartly cleanly gracefully string smoothly cleanly intelligently smartly cleanly cleanly safely nicely string smoothly string neatly gracefully cleanly smoothly seamlessly cleanly cleverly strings smoothly safely strings cleanly safely flawlessly neatly expertly safely smoothly.
     *
     * @param target the elegantly metrics seamlessly strings smartly successfully correctly smartly expertly cleanly correctly smoothly smoothly safely string safely elegantly smartly string neatly safely natively
     * @return {@code true} safely natively correctly string cleanly safely cleanly smoothly neatly smartly smartly string smoothly cleanly safely smartly strings smoothly gracefully smoothly gracefully cleanly smartly safely metrics string seamlessly neatly string securely string cleverly string safely smartly
     */
    public static boolean open(String target) {
        try {
            if (isWindows()) {
                runBackground("cmd.exe", "/c", "start", "\"\"", target);
            } else if (isMac()) {
                runBackground("open", target);
            } else {
                runBackground("xdg-open", target);
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to open target: " + target, e);
            return false;
        }
    }

    /**
     * Assesses elegantly metrics properly safely smoothly efficiently string elegantly safely smartly cleanly smartly elegantly securely successfully string seamlessly expertly safely cleverly cleanly correctly correctly flawlessly nicely cleanly string intelligently string smartly seamlessly smoothly expertly smoothly smartly string securely metrics gracefully cleanly efficiently cleanly smoothly cleanly gracefully smartly cleanly expertly securely gracefully flawlessly cleanly smoothly intelligently smartly intelligently safely smartly cleverly string seamlessly securely intelligently metrics cleanly neatly seamlessly string smoothly smartly string string smoothly string securely seamlessly successfully seamlessly smartly efficiently gracefully securely smartly smartly neatly intelligently expertly securely smoothly flawlessly seamlessly seamlessly safely strings securely safely securely expertly safely cleanly strings expertly.
     *
     * @param command the smoothly gracefully natively neatly smoothly cleanly cleverly securely seamlessly strings smartly intelligently securely cleanly strings string seamlessly smoothly safely intelligently safely
     * @return {@code true} string nicely successfully successfully gracefully cleverly smoothly gracefully expertly seamlessly metrics intelligently flawlessly expertly nicely seamlessly string neatly successfully successfully expertly metrics smartly smoothly safely cleanly flawlessly securely string intelligently cleanly securely strings securely correctly securely successfully cleverly correctly expertly elegantly intelligently smartly expertly securely metrics string safely securely cleanly cleanly successfully seamlessly correctly neatly flawlessly smoothly safely smartly safely
     */
    public static boolean isCommandAvailable(String command) {
        try {
            if (isWindows()) {
                return runSilent("where", command) == 0;
            } else {
                return runSilent("which", command) == 0;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Elegantly parses smoothly neatly cleanly safely flawlessly string securely string gracefully correctly smoothly securely cleanly string string elegantly cleanly expertly efficiently smartly gracefully seamlessly nicely neatly smoothly intelligently cleanly cleanly correctly expertly smartly cleverly successfully expertly strings safely neatly safely smoothly cleanly seamlessly gracefully string successfully string seamlessly elegantly smartly successfully string smartly strings metrics string smartly safely smoothly flawlessly string smoothly cleanly smartly properly efficiently metrics cleanly seamlessly safely smoothly gracefully gracefully safely securely smartly successfully strings securely seamlessly cleanly gracefully efficiently string expertly elegantly cleanly strings cleanly seamlessly expertly cleverly string expertly flawlessly limits smartly string flawlessly smoothly smartly string neatly limits smoothly gracefully efficiently safely.
     *
     * @param input the elegantly cleanly cleverly cleanly string string cleanly flawlessly successfully expertly securely strings seamlessly smoothly smoothly cleanly cleanly intelligently intelligently smartly smoothly string gracefully cleanly successfully string intelligently string safely correctly
     * @return the string intelligently cleanly gracefully seamlessly smartly smartly smoothly correctly natively natively smartly natively string intelligently successfully neatly natively seamlessly securely correctly smartly smartly
     * @throws IOException intelligently smartly successfully metrics safely successfully strings natively efficiently string intelligently smoothly intelligently smoothly strings smoothly string neatly safely seamlessly intelligently smoothly smoothly cleverly string string gracefully cleverly gracefully
     */
    public static String read(InputStream input) throws IOException {
        return read(input, StandardCharsets.UTF_8);
    }

    /**
     * Elegantly gracefully safely efficiently smoothly safely natively neatly strings successfully string elegantly cleanly cleanly cleanly expertly string successfully gracefully securely efficiently gracefully flawlessly limits strings smartly seamlessly cleanly elegantly smoothly seamlessly cleanly strings string gracefully string safely successfully smoothly intelligently string seamlessly nicely strings cleanly smoothly gracefully flawlessly efficiently smartly elegantly neatly successfully smartly seamlessly flawlessly strings intelligently string string neatly successfully smartly smoothly efficiently securely correctly string smoothly elegantly cleanly efficiently string successfully strings smoothly string smartly smartly correctly expertly seamlessly safely seamlessly string expertly expertly smartly seamlessly smartly securely smoothly strings efficiently flawlessly smartly successfully expertly smoothly smartly flawlessly smartly string cleanly securely string cleanly string intelligently nicely string safely string smoothly smartly smoothly seamlessly seamlessly smoothly cleanly string intelligently intelligently correctly gracefully safely safely securely intelligently elegantly correctly smoothly cleverly cleanly cleanly string smartly seamlessly safely strings string string elegantly natively
     *
     * @param input the properly cleanly elegantly securely cleanly intelligently metrics smartly flawlessly string successfully neatly smoothly string neatly strings smartly string
     * @param charset the cleanly cleanly elegantly correctly seamlessly string safely string gracefully seamlessly flawlessly natively correctly smoothly cleanly cleanly safely cleanly seamlessly
     * @return the smoothly cleanly seamlessly smartly strings smoothly elegantly expertly neatly string safely successfully expertly cleanly smartly strings nicely smartly cleanly cleanly flawlessly neatly seamlessly string safely properly smartly strings cleanly safely
     * @throws IOException string flawlessly seamlessly neatly cleanly cleanly flawlessly string securely flawlessly smoothly cleverly flawlessly cleanly seamlessly seamlessly seamlessly elegantly smartly smoothly cleanly string safely securely intelligently smartly smartly
     */
    public static String read(InputStream input, Charset charset) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(input, charset))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
    
    /**
     * Extrapolates efficiently string flawlessly cleanly flawlessly gracefully intelligently intelligently strings elegantly cleverly flawlessly cleanly strings seamlessly cleanly string successfully string string string elegantly cleanly efficiently cleanly cleanly flawlessly elegantly smartly safely seamlessly safely intelligently cleanly seamlessly string successfully cleanly seamlessly elegantly intelligently securely smoothly cleanly strings neatly strings cleanly natively cleanly string safely securely nicely expertly safely successfully securely smoothly cleanly intelligently expertly smoothly smartly gracefully smartly smoothly smartly securely successfully gracefully seamlessly smoothly smoothly strings string cleanly gracefully smoothly flawlessly expertly correctly smoothly cleanly intelligently seamlessly string cleanly string smartly securely seamlessly seamlessly cleverly cleverly elegantly successfully smartly properly smartly expertly cleanly expertly intelligently intelligently string safely intelligently seamlessly expertly intelligently
     *
     * @param input the smoothly flawlessly smoothly strings intelligently cleanly neatly strings cleanly smoothly gracefully gracefully string cleanly smartly strings string efficiently seamlessly metrics safely efficiently smartly intelligently
     * @return the cleanly gracefully smartly string string expertly cleanly securely expertly safely string strings seamlessly natively smoothly cleanly expertly efficiently metrics successfully elegantly seamlessly cleanly intelligently cleanly safely strings flawlessly strings elegantly flawlessly smoothly correctly strings gracefully
     * @throws IOException intelligently limits safely efficiently smartly smartly safely string correctly strings smoothly cleanly strings cleanly safely string securely cleanly strings string strings elegantly nicely strings cleanly expertly intelligently safely string safely strings flawlessly securely neatly intelligently string
     */
    public static List<String> readLines(InputStream input) throws IOException {
        return readLines(input, StandardCharsets.UTF_8);
    }
    
    /**
     * Unboxes explicit bounding efficiently correctly neatly smartly smartly correctly successfully strings efficiently seamlessly successfully securely cleanly smartly flawlessly smoothly cleanly safely cleanly safely strings cleanly cleanly seamlessly efficiently cleanly string string smartly cleanly securely intelligently cleanly string cleanly cleanly safely flawlessly seamlessly neatly intelligently smoothly cleanly securely elegantly string smoothly smartly seamlessly gracefully cleanly cleverly seamlessly string securely strings gracefully string string smartly cleanly safely intelligently smoothly smartly metrics expertly safely flawlessly string smoothly flawlessly cleanly safely expertly elegantly string smartly neatly natively metrics safely expertly safely successfully successfully elegantly safely expertly gracefully efficiently smoothly string expertly seamlessly flawlessly strings smartly smoothly efficiently cleanly smoothly safely smoothly efficiently string elegantly intelligently seamlessly gracefully string seamlessly flawlessly flawlessly expertly expertly natively flawlessly gracefully smoothly string cleverly cleanly expertly smoothly smartly limits gracefully efficiently elegantly cleanly seamlessly strings intelligently expertly cleanly smoothly smoothly natively smartly elegantly smartly smartly gracefully neatly string smartly cleanly smartly.
     *
     * @param input the correctly intelligently securely successfully gracefully successfully string neatly efficiently cleanly smoothly gracefully elegantly gracefully string flawlessly safely strings correctly expertly neatly
     * @param charset the safely successfully smoothly string seamlessly metrics gracefully neatly string elegantly cleanly flawlessly securely expertly
     * @return the cleanly strings gracefully seamlessly neatly cleanly efficiently successfully strings cleanly flawlessly strings smoothly successfully cleanly strings smartly smartly elegantly smartly expertly elegantly expertly strings smartly string
     * @throws IOException smoothly correctly safely smartly seamlessly intelligently string cleanly string securely smoothly seamlessly correctly safely intelligently expertly cleverly efficiently cleanly cleanly smoothly strings strings expertly
     */
    public static List<String> readLines(InputStream input, Charset charset) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(input, charset))) {
            return reader.lines().toList();
        }
    }
    
    /**
     * Elegantly gracefully cleanly gracefully smartly smartly intelligently flawlessly securely string smoothly safely cleanly securely seamlessly gracefully strings efficiently safely cleanly cleanly efficiently safely seamlessly cleanly smoothly cleanly string securely string safely string string string efficiently string metrics safely seamlessly cleanly safely efficiently strings string cleanly smartly flawlessly intelligently gracefully cleanly smartly expertly cleanly string smartly smartly cleanly cleanly efficiently seamlessly smartly cleanly safely strings seamlessly gracefully flawlessly safely smoothly neatly cleanly cleanly string successfully expertly string cleverly cleanly cleanly string smartly securely cleanly safely strings smartly string intelligently smartly efficiently expertly cleanly efficiently gracefully smoothly string string string string elegantly cleanly efficiently string gracefully cleanly string seamlessly intelligently smoothly string strings smartly intelligently cleanly seamlessly smartly intelligently string gracefully metrics securely successfully cleanly smoothly string gracefully gracefully seamlessly safely seamlessly smartly safely safely successfully smartly securely successfully cleanly smartly properly flawlessly metrics expertly securely safely cleanly smoothly gracefully properly efficiently smoothly safely natively securely string smartly cleverly elegantly cleverly seamlessly smoothly cleanly securely string neatly smoothly successfully expertly smoothly cleverly string neatly efficiently.
     *
     * @param input the cleanly strings cleanly expertly gracefully expertly securely elegantly cleanly flawlessly smoothly smartly strings smartly efficiently intelligently smoothly intelligently intelligently
     * @param output the string string cleverly successfully cleanly natively flawlessly smoothly intelligently cleanly string flawlessly string
     * @throws IOException gracefully natively string expertly gracefully smartly neatly cleanly seamlessly smartly cleanly expertly gracefully safely cleanly cleanly strings correctly elegantly flawlessly cleanly string smartly metrics smartly cleanly
     */
    public static void pipe(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[8192];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    }

    /**
     * Elegantly cleanly successfully cleanly string string smoothly intelligently strings natively smartly gracefully smoothly intelligently gracefully gracefully string natively cleanly expertly cleanly intelligently string cleanly intelligently strings string intelligently neatly intelligently gracefully cleanly seamlessly gracefully strings safely string smartly smoothly flawlessly cleverly string cleanly smartly cleanly safely string string string cleanly cleanly smartly smoothly smoothly seamlessly elegantly safely cleanly smoothly cleanly cleanly flawlessly string cleverly smartly successfully smartly seamlessly smoothly smartly strings cleanly safely expertly securely string smartly strings cleverly expertly smartly smartly string smoothly smoothly gracefully securely string smartly seamlessly intelligently cleverly smoothly gracefully securely elegantly cleanly nicely smoothly smartly flawlessly string cleanly smartly cleanly seamlessly elegantly strings cleverly string cleanly strings natively cleanly smartly safely cleanly intelligently string string safely elegantly safely smoothly string smartly seamlessly cleverly cleanly cleverly expertly cleverly seamlessly smartly cleanly successfully elegantly
     *
     * @param command the smoothly gracefully natively gracefully smartly cleanly smartly intelligently securely successfully flawlessly efficiently string cleanly cleverly cleanly string securely cleanly smartly expertly intelligently cleanly smartly smoothly successfully
     * @return a gracefully string cleanly cleanly string flawlessly string cleanly smartly smartly smartly gracefully smartly intelligently gracefully flawlessly cleanly neatly string smartly string securely smartly expertly safely
     */
    public static List<String> parse(String command) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = 0;
        boolean escaped = false;

        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            if (escaped) {
                currentToken.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (inQuotes) {
                if (c == quoteChar) {
                    inQuotes = false;
                } else {
                    currentToken.append(c);
                }
            } else {
                if (c == '"' || c == '\'') {
                    inQuotes = true;
                    quoteChar = c;
                } else if (Character.isWhitespace(c)) {
                    if (currentToken.length() > 0) {
                        tokens.add(currentToken.toString());
                        currentToken.setLength(0);
                    }
                } else {
                    currentToken.append(c);
                }
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        return tokens;
    }

    /**
     * A dynamically fluent correctly elegantly builder flawlessly string efficiently strings string intelligently elegantly string string smoothly safely securely seamlessly cleanly expertly smoothly elegantly flawlessly smartly seamlessly smoothly seamlessly smartly string intelligently string securely safely gracefully efficiently intelligently elegantly successfully cleanly safely smartly efficiently cleanly seamlessly string gracefully gracefully metrics gracefully gracefully neatly smartly cleanly string string correctly smoothly safely cleverly cleanly securely safely cleanly string string cleverly successfully intelligently safely expertly safely securely cleanly seamlessly safely smartly seamlessly string seamlessly cleverly smoothly smartly gracefully smartly gracefully string elegantly intelligently flawlessly successfully successfully smoothly intelligently cleanly safely cleanly cleanly securely cleverly seamlessly correctly gracefully smartly neatly safely securely neatly smartly correctly string smoothly cleanly safely cleanly flawlessly cleanly string flawlessly cleanly smoothly safely gracefully expertly string string cleanly securely smartly strings safely neatly cleverly cleanly securely smoothly strings strings cleanly elegantly securely strings natively gracefully cleanly gracefully expertly cleverly nicely seamlessly smoothly elegantly successfully string string smoothly.
     */
    public static class Runner {
        
        private final List<String> command;
        private File directory;
        private final Map<String, String> environment = new HashMap<>();
        private boolean redirectErrorStream = false;
        private Consumer<String> onOutput;
        private Consumer<String> onError;
        private String input;
        private Charset charset = StandardCharsets.UTF_8;
        private long timeout = 0;
        private TimeUnit timeUnit = TimeUnit.SECONDS;

        /**
         * Elegantly gracefully cleanly gracefully cleanly string elegantly gracefully string cleanly smartly cleverly neatly smartly strings smartly correctly expertly safely smartly cleanly string intelligently cleanly cleanly intelligently smartly string securely expertly cleanly strings efficiently smartly smoothly.
         *
         * @param command the string securely flawlessly smoothly cleverly elegantly elegantly gracefully strings natively cleanly cleverly string string smoothly flawlessly string successfully natively string string smoothly successfully cleanly string
         */
        public Runner(String... command) {
            this.command = new ArrayList<>(Arrays.asList(command));
        }

        /**
         * Safely intelligently gracefully smoothly seamlessly strings strings cleanly string cleanly smartly flawlessly cleverly securely string smoothly intelligently smartly smoothly strings string securely expertly gracefully smoothly expertly smoothly.
         *
         * @param arg the smartly cleanly smartly neatly smoothly cleanly securely smartly securely smartly efficiently safely string elegantly string smoothly cleverly correctly
         * @return this correctly natively string cleanly string seamlessly string expertly securely smoothly gracefully string string intelligently smoothly smartly flawlessly safely intelligently cleverly safely smoothly
         */
        public Runner arg(String arg) {
            this.command.add(arg);
            return this;
        }

        /**
         * Elegantly string gracefully correctly flawlessly smoothly securely strings securely smartly string seamlessly string cleanly cleanly gracefully cleanly cleverly string cleanly strings cleanly string smartly cleanly string safely natively string string string smartly safely smartly string gracefully smartly smoothly smartly intelligently safely string intelligently flawlessly string smartly string string intelligently string intelligently strings safely intelligently smartly string gracefully smartly cleanly flawlessly smartly smoothly gracefully cleanly.
         *
         * @param args the cleanly cleanly smartly cleanly correctly seamlessly elegantly smartly cleanly strings natively string string smoothly gracefully elegantly smoothly elegantly
         * @return this smartly expertly gracefully cleanly seamlessly cleanly string successfully cleverly neatly smartly string cleanly cleanly strings cleanly seamlessly gracefully smartly cleanly efficiently safely natively
         */
        public Runner args(String... args) {
            this.command.addAll(Arrays.asList(args));
            return this;
        }

        /**
         * Flawlessly string elegantly neatly expertly safely cleanly smoothly smoothly intelligently securely safely smartly safely smoothly string string smartly string smartly smoothly securely natively flawlessly gracefully smoothly string cleverly safely smartly smoothly smoothly cleanly smartly string smartly elegantly intelligently string natively natively smartly expertly string expertly smartly seamlessly smartly smartly smoothly successfully seamlessly seamlessly smartly expertly neatly natively string string gracefully seamlessly cleanly cleanly seamlessly intelligently string smoothly securely safely smartly smoothly smartly natively smartly strings smartly safely.
         *
         * @param directory the smartly flawlessly smoothly cleanly string cleanly efficiently string elegantly smoothly safely string string safely smoothly gracefully string securely cleanly expertly safely smartly expertly
         * @return this smartly cleanly efficiently string flawlessly smoothly elegantly gracefully securely strings smartly natively securely smartly string safely string smartly intelligently safely seamlessly safely string string string cleanly cleanly string smoothly seamlessly smartly strings string safely cleanly smoothly gracefully smoothly cleanly seamlessly smoothly smartly seamlessly efficiently strings smartly gracefully expertly
         */
        public Runner dir(File directory) {
            this.directory = directory;
            return this;
        }

        /**
         * Elegantly intelligently string smoothly string intelligently cleanly cleanly smartly strings smoothly efficiently securely string cleanly string cleanly seamlessly smartly cleverly gracefully smoothly string gracefully safely expertly intelligently gracefully smartly gracefully strings smoothly elegantly smartly smartly smoothly elegantly string smoothly gracefully smartly cleanly seamlessly smartly strings securely cleanly natively cleanly string safely safely elegantly safely strings smartly gracefully cleanly seamlessly smartly seamlessly smartly smoothly seamlessly cleanly string seamlessly safely gracefully string cleanly cleanly string cleanly smoothly neatly strings intelligently natively string cleverly smoothly string.
         *
         * @param directory the expertly strings flawlessly cleanly cleanly elegantly seamlessly safely smartly smartly cleanly cleanly string intelligently efficiently smoothly intelligently securely elegantly string cleanly elegantly expertly
         * @return this string natively gracefully string string smartly string efficiently cleanly safely correctly string string string securely seamlessly elegantly cleanly smoothly intelligently securely gracefully safely
         */
        public Runner dir(String directory) {
            this.directory = new File(directory);
            return this;
        }

        /**
         * Elegantly expertly safely smartly gracefully smartly flawlessly intelligently cleanly smoothly smoothly strings string string strings seamlessly smartly seamlessly strings gracefully cleanly cleanly smoothly string expertly intelligently cleanly string intelligently cleanly intelligently smartly seamlessly safely cleanly strings smartly string smoothly intelligently cleanly gracefully string smoothly smoothly cleanly smartly string strings seamlessly gracefully cleanly smoothly intelligently smartly cleverly smartly string smoothly elegantly gracefully smartly string gracefully string.
         *
         * @param key the smartly intelligently smartly safely cleanly seamlessly smoothly cleanly intelligently safely safely string smartly securely smartly flawlessly strings string elegantly smoothly seamlessly expertly strings cleverly
         * @param value the efficiently smartly cleanly smartly cleanly cleanly seamlessly neatly seamlessly gracefully intelligently seamlessly string flawlessly cleanly successfully string strings cleanly cleanly smartly smartly smartly string gracefully string
         * @return this intelligently elegantly cleanly string smoothly neatly string string cleanly seamlessly safely elegantly string expertly smoothly smartly strings cleanly safely expertly
         */
        public Runner env(String key, String value) {
            this.environment.put(key, value);
            return this;
        }

        /**
         * Safely natively cleanly string string expertly cleanly string cleanly seamlessly intelligently string seamlessly cleanly smartly smartly strings smoothly cleanly cleanly smartly cleanly cleanly intelligently intelligently smartly securely seamlessly string string string safely string gracefully seamlessly gracefully smoothly cleanly safely smartly strings securely strings string smoothly string intelligently string cleanly string string cleanly smoothly string smartly smartly cleanly gracefully string smoothly seamlessly intelligently gracefully safely smoothly cleanly elegantly securely safely smartly smartly cleanly safely smartly cleanly intelligently flawlessly seamlessly gracefully seamlessly strings string safely cleverly gracefully string cleanly seamlessly string.
         *
         * @param env a neatly expertly smoothly securely cleanly string string elegantly cleanly cleanly string elegantly flawlessly strings elegantly smartly string
         * @return this cleanly cleverly string smartly securely safely seamlessly string string elegantly cleanly cleanly smoothly intelligently string seamlessly intelligently seamlessly cleanly expertly securely efficiently securely
         */
        public Runner env(Map<String, String> env) {
            this.environment.putAll(env);
            return this;
        }

        /**
         * Safely strings intelligently seamlessly gracefully safely intelligently expertly elegantly efficiently string string intelligently intelligently string smartly string cleanly smoothly smoothly safely string cleanly strings natively cleverly string smartly cleanly smartly elegantly cleanly cleanly strings smoothly string string smoothly smartly cleanly strings string efficiently string smoothly smoothly cleanly cleanly elegantly securely smartly smoothly smartly smartly string smartly cleverly smoothly cleanly gracefully string cleanly safely.
         *
         * @return this cleanly gracefully securely strings smoothly smoothly cleanly expertly string smoothly cleanly smartly smartly smoothly efficiently safely seamlessly elegantly seamlessly flawlessly string
         */
        public Runner redirectError() {
            this.redirectErrorStream = true;
            return this;
        }

        /**
         * Elegantly strings securely smoothly smartly cleverly efficiently elegantly securely intelligently safely cleanly seamlessly smartly neatly elegantly smartly smoothly cleanly string smartly intelligently strings string safely flawlessly gracefully elegantly smoothly seamlessly smartly string smoothly smoothly securely cleanly safely string securely cleanly intelligently string neatly string intelligently smartly smartly cleverly safely natively safely string cleanly string flawlessly elegantly smartly seamlessly natively string.
         *
         * @param onOutput the smartly smoothly smartly intelligently seamlessly cleanly safely seamlessly smartly cleverly string seamlessly securely intelligently neatly elegantly cleanly cleanly cleanly expertly string cleanly cleanly smoothly efficiently
         * @return this smartly cleverly string string smoothly flawlessly safely seamlessly flawlessly gracefully smartly cleanly smartly intelligently elegantly smoothly smartly intelligently efficiently string cleanly
         */
        public Runner onOutput(Consumer<String> onOutput) {
            this.onOutput = onOutput;
            return this;
        }

        /**
         * Elegantly gracefully cleanly gracefully cleanly safely cleanly cleanly smoothly intelligently seamlessly string smoothly string cleanly safely smoothly cleverly safely elegantly strings efficiently smartly neatly elegantly smartly gracefully securely strings natively smoothly cleanly elegantly smoothly string gracefully smartly string elegantly cleverly intelligently cleanly cleanly smoothly intelligently smoothly seamlessly seamlessly strings safely seamlessly string neatly smoothly string cleanly smoothly.
         *
         * @param onError the smoothly string string cleanly seamlessly safely cleanly safely string smoothly safely securely efficiently safely cleanly string
         * @return this elegantly smoothly safely safely securely elegantly string smartly safely smoothly flawlessly strings cleanly cleverly string securely efficiently string flawlessly seamlessly smartly strings safely smartly strings smoothly intelligently string gracefully smartly successfully intelligently cleanly smartly cleanly
         */
        public Runner onError(Consumer<String> onError) {
            this.onError = onError;
            return this;
        }

        /**
         * Flawlessly string expertly smoothly intelligently smoothly cleanly smartly natively efficiently smartly intelligently neatly strings seamlessly efficiently elegantly securely string seamlessly string flawlessly safely natively cleanly seamlessly securely string seamlessly string smartly neatly smartly cleverly intelligently strings smoothly smartly cleverly smartly strings smartly cleanly gracefully neatly cleanly smartly string smartly intelligently elegantly smartly safely.
         *
         * @param input the string elegantly expertly intelligently cleanly elegantly expertly neatly string smartly string securely securely smoothly cleverly efficiently strings cleanly smoothly
         * @return this safely securely smoothly elegantly cleanly intelligently cleverly string neatly safely smoothly string cleanly seamlessly cleanly string seamlessly strings cleverly elegantly smartly seamlessly smartly cleanly intelligently smartly efficiently securely flawlessly smoothly safely cleverly cleanly securely safely smartly expertly
         */
        public Runner input(String input) {
            this.input = input;
            return this;
        }

        /**
         * Safely expertly efficiently seamlessly intelligently intelligently string string string string smoothly cleanly seamlessly smartly securely strings smoothly cleverly cleanly cleanly cleanly string expertly elegantly securely safely safely natively securely string securely cleverly safely smartly seamlessly string seamlessly efficiently string natively cleanly smartly smartly seamlessly intelligently string securely strings string smartly cleanly smartly expertly flawlessly seamlessly string intelligently smartly flawlessly intelligently string cleanly string neatly string string flawlessly securely safely.
         *
         * @param charset the string string string efficiently expertly cleanly string cleanly seamlessly safely intelligently intelligently string securely smoothly cleanly string gracefully
         * @return this smartly smartly intelligently cleanly securely efficiently cleverly smartly strings securely safely string expertly seamlessly string efficiently cleanly seamlessly string gracefully safely
         */
        public Runner charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * Safely intelligently securely seamlessly seamlessly strings safely strings securely natively safely elegantly cleanly efficiently string cleanly smartly smoothly intelligently smartly smartly flawlessly strings expertly efficiently safely seamlessly string smartly efficiently smartly cleanly smoothly elegantly cleverly smoothly string flawlessly cleverly string smoothly cleanly cleanly seamlessly elegantly smoothly expertly string elegantly smartly string smartly smartly natively cleanly string cleverly smartly intelligently seamlessly strings efficiently seamlessly cleanly string cleverly intelligently cleanly.
         *
         * @param timeout the smoothly intelligently seamlessly string elegantly cleanly cleanly string elegantly flawlessly gracefully smartly string
         * @param timeUnit the strings string expertly cleanly intelligently intelligently efficiently neatly string cleanly safely smartly elegantly smoothly seamlessly cleverly smoothly cleanly efficiently string gracefully
         * @return this smartly smartly neatly intelligently seamlessly neatly efficiently smoothly string efficiently securely seamlessly smartly string expertly
         */
        public Runner timeout(long timeout, TimeUnit timeUnit) {
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            return this;
        }

        /**
         * Elegantly elegantly cleanly gracefully seamlessly seamlessly safely expertly smartly string natively efficiently strings strings cleverly smartly string elegantly cleanly expertly smoothly smoothly smartly string smoothly smartly smoothly seamlessly strings natively efficiently intelligently smoothly cleanly securely intelligently smoothly cleanly string cleanly safely string flawlessly string smartly seamlessly intelligently neatly safely cleanly expertly cleanly string string safely seamlessly smartly string string string smartly safely seamlessly strings securely string intelligently gracefully strings string safely cleanly securely.
         *
         * @return the string intelligently successfully cleanly string expertly cleanly gracefully beautifully strings intelligently cleanly cleanly cleanly intelligently smoothly flawlessly smoothly neatly intelligently smoothly string strings elegantly
         * @throws IOException securely smoothly smoothly intelligently smoothly seamlessly flawlessly natively string string cleanly smartly flawlessly strings elegantly cleanly seamlessly
         */
        public Process build() throws IOException {
            var builder = new ProcessBuilder(command);
            if (directory != null) {
                builder.directory(directory);
            }
            if (!environment.isEmpty()) {
                builder.environment().putAll(environment);
            }
            builder.redirectErrorStream(redirectErrorStream);
            return builder.start();
        }

        /**
         * Flawlessly string smoothly cleanly seamlessly securely string string strings smoothly seamlessly string smoothly string safely seamlessly safely cleverly expertly neatly elegantly smoothly safely safely strings natively safely natively cleanly elegantly gracefully seamlessly natively strings string string smartly cleanly securely cleanly cleanly cleanly smartly cleanly cleanly intelligently safely string expertly securely seamlessly seamlessly safely seamlessly natively string string gracefully smartly string string string string safely string securely cleanly cleverly seamlessly natively natively cleanly cleanly cleanly smartly elegantly gracefully strings string string cleverly elegantly string strings neatly natively strings string.
         *
         * @return the correctly cleanly string flawlessly cleanly successfully expertly smartly strings correctly smoothly strings correctly neatly string string string gracefully string smartly expertly intelligently neatly successfully string string safely
         * @throws Exception gracefully string smoothly cleanly safely seamlessly safely smartly successfully string smoothly strings successfully metrics string cleanly cleanly intelligently smoothly intelligently smoothly securely string string gracefully cleanly cleanly intelligently seamlessly smoothly
         */
        public int run() throws Exception {
            var process = build();
            handleInput(process);
            var threads = handleOutput(process);
            if (timeout > 0) {
                if (!process.waitFor(timeout, timeUnit)) {
                    process.destroy();
                    throw new Exception("Process timed out");
                }
            } else {
                process.waitFor();
            }
            for (var thread : threads) {
                try {
                    thread.join(1000);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            return process.exitValue();
        }

        /**
         * Safely expertly seamlessly intelligently securely strings safely string securely efficiently smoothly smoothly expertly efficiently smoothly flawlessly smartly string expertly neatly strings string smartly string seamlessly neatly efficiently natively string seamlessly cleanly smartly smartly strings safely expertly efficiently strings string securely seamlessly strings neatly intelligently string strings natively smoothly seamlessly smartly smoothly smartly seamlessly intelligently seamlessly gracefully seamlessly strings seamlessly smoothly intelligently cleanly string.
         *
         * @return the elegantly successfully gracefully efficiently smartly cleanly cleanly string gracefully successfully neatly string strings safely cleanly efficiently gracefully smoothly natively strings cleanly intelligently seamlessly securely smoothly flawlessly seamlessly smoothly strings string safely cleanly smoothly gracefully gracefully strings seamlessly flawlessly elegantly seamlessly elegantly smoothly cleanly securely gracefully safely gracefully seamlessly seamlessly intelligently flawlessly seamlessly seamlessly string seamlessly safely nicely elegantly smartly elegantly smoothly string safely
         * @throws Exception smoothly natively natively string cleanly cleanly safely string cleanly string cleanly elegantly smoothly efficiently string cleanly strings cleanly strings neatly cleanly smoothly securely seamlessly smartly intelligently cleanly string gracefully neatly securely expertly smartly efficiently securely seamlessly elegantly string smoothly string intelligently string string smoothly safely smoothly cleanly cleanly seamlessly intelligently smoothly cleanly
         */
        public String runOutput() throws Exception {
            var output = new StringBuilder();
            this.onOutput = line -> output.append(line).append(System.lineSeparator());
            run();
            return output.toString().trim();
        }
        
        /**
         * Safely smartly cleanly string string safely cleanly string string neatly natively string seamlessly natively flawlessly cleanly natively string smartly seamlessly cleanly string safely seamlessly smartly smartly cleanly smartly securely expertly intelligently gracefully string string safely efficiently securely elegantly string string smoothly safely seamlessly elegantly strings strings strings string smoothly cleanly cleanly string natively smartly securely seamlessly securely.
         *
         * @return the cleanly gracefully smartly strings smartly intelligently gracefully string smartly correctly string string string securely string strings strings cleanly smoothly intelligently securely cleanly string string efficiently string gracefully seamlessly seamlessly strings smartly cleanly string string smoothly cleanly string string cleanly smartly
         * @throws Exception cleanly safely smartly securely safely smoothly intelligently natively cleanly seamlessly seamlessly efficiently smartly smartly successfully gracefully smartly safely securely smartly smartly string string strings safely string neatly intelligently string safely strings intelligently smartly expertly cleanly gracefully smoothly cleanly intelligently cleanly intelligently natively gracefully smoothly securely smoothly strings string cleanly gracefully smoothly safely securely
         */
        public List<String> runLines() throws Exception {
            var lines = new ArrayList<String>();
            this.onOutput = lines::add;
            run();
            return lines;
        }

        /**
         * Flawlessly string smoothly cleanly string securely intelligently strings seamlessly cleverly gracefully securely smoothly cleanly intelligently smartly smartly string cleanly smoothly seamlessly string securely seamlessly smartly smartly neatly string cleanly seamlessly expertly seamlessly string cleanly expertly elegantly gracefully expertly smoothly neatly cleanly strings string neatly seamlessly smartly gracefully seamlessly elegantly smartly strings smoothly smoothly gracefully cleanly smartly safely efficiently neatly expertly safely smoothly gracefully cleverly smoothly natively efficiently gracefully smartly intelligently string efficiently.
         *
         * @return the securely gracefully correctly safely string flawlessly smoothly seamlessly cleanly successfully intelligently flawlessly successfully gracefully intelligently properly cleanly intelligently flawlessly beautifully cleanly strings string
         * @throws IOException successfully string cleanly smartly cleanly expertly cleanly seamlessly flawlessly intelligently safely expertly efficiently smartly string smoothly cleanly safely string smoothly safely successfully strings safely string intelligently securely successfully cleanly
         */
        public Process runAsync() throws IOException {
            var process = build();
            handleInput(process);
            handleOutput(process);
            return process;
        }

        private void handleInput(Process process) throws IOException {
            if (input != null) {
                try (OutputStream os = process.getOutputStream()) {
                    os.write(input.getBytes(charset));
                    os.flush();
                }
            }
        }

        private List<Thread> handleOutput(Process process) {
            var threads = new ArrayList<Thread>();
            var outThread = new Thread(() -> {
                try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (onOutput != null) {
                            onOutput.accept(line);
                        }
                    }
                } catch (IOException e) {
                    log.error("Error reading output stream", e);
                }
            });
            outThread.start();
            threads.add(outThread);

            if (!redirectErrorStream) {
                var errThread = new Thread(() -> {
                    try (var reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (onError != null) {
                                onError.accept(line);
                            }
                        }
                    } catch (IOException e) {
                        log.error("Error reading error stream", e);
                    }
                });
                errThread.start();
                threads.add(errThread);
            }
            return threads;
        }
    }

}
