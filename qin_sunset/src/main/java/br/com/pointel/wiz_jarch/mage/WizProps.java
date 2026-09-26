package br.com.pointel.wiz_jarch.mage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A robust properties manager utility that handles reading, writing, parsing, 
 * and converting configuration properties data securely.
 * <p>
 * This class abstracts away the {@link Properties} API, granting auto-save capabilities, 
 * silent background execution, generic conversion, and direct classpath extraction.
 * </p>
 */
public class WizProps {

    private WizProps() {
    }

    private static final Logger logger = LoggerFactory.getLogger(WizProps.class);

    private static final Properties props = new Properties();

    private static volatile File folder = new File(".");

    /**
     * Retrieves the active base directory actively hosting property definitions.
     *
     * @return the localized {@link File} directory reference
     */
    public static File getFolder() {
        return folder;
    }

    /**
     * Installs a new base directory configuration and actively attempts to reload the core default properties footprint.
     *
     * @param folder the targeted base {@link File} folder
     */
    public static void setFolder(File folder) {
        WizProps.folder = folder;
        tryLoad();
    }

    /**
     * Installs a new base directory configuration and actively attempts to reload a targeted properties footprint.
     *
     * @param folder the targeted base {@link File} folder
     * @param name   the exact name mapping of the properties file (without the {@code .ini} extension) to load
     */
    public static void setFolder(File folder, String name) {
        WizProps.folder = folder;
        tryLoad(name);
    }

    static {
        tryLoad();
    }

    /**
     * Executing a silent background reload mapped securely to the default properties configuration (derived via {@link WizLang#getName()}).
     * <p>
     * Active Exceptions are intercepted and securely logged as errors via SLF4J, completely suppressing execution interrupts.
     * </p>
     */
    public static void tryLoad() {
        try {
            load();
        } catch (Exception ex) {
            logger.error("Error loading properties", ex);
        }
    }

    /**
     * Executing a silent background reload targeting a uniquely named properties configuration mapping.
     * <p>
     * Active Exceptions are intercepted and securely logged as errors via SLF4J, completely suppressing execution interrupts.
     * </p>
     *
     * @param name the precise properties mapping filename (without the {@code .ini} extension)
     */
    public static void tryLoad(String name) {
        try {
            load(name);
        } catch (Exception ex) {
            logger.error("Error loading properties", ex);
        }
    }

    /**
     * Securely forces a reload routine mapped structurally to the default properties context footprint.
     *
     * @throws Exception if physical file system I/O errors structurally interrupt execution flow
     */
    public static void load() throws Exception {
        load(WizLang.getName());
    }

    /**
     * Securely forces a reload routine strictly mapped to a tailored properties file located within the default footprint directory.
     * <p>
     * The file payload physically requires an {@code .ini} mapping configuration.
     * </p>
     *
     * @param name the precise properties mapping filename (without the {@code .ini} extension)
     * @throws Exception if physical file system I/O errors structurally interrupt execution flow
     */
    public static void load(String name) throws Exception {
        File file = new File(folder, name + ".ini");
        if (file.exists()) {
            try (FileReader input = new FileReader(file)) {
                props.load(input);
            }
        }
    }

    /**
     * Loads a secure properties mapping configuration structurally bypassing local files, extracting data natively through the Java Classpath footprint.
     *
     * @param path the internal absolute path resolving the targeted resource bundle
     * @throws Exception if the targeted resource stream actively rejects mapping
     */
    public static void loadFromClasspath(String path) throws Exception {
        try (InputStream in = WizProps.class.getClassLoader().getResourceAsStream(path)) {
            if (in != null) {
                props.load(in);
            }
        }
    }

    /**
     * Executing a silent background file save tracking the actively modified configuration state context.
     * <p>
     * Active Exceptions are intercepted and securely logged as errors via SLF4J, completely suppressing execution interrupts.
     * </p>
     */
    public static void trySave() {
        try {
            save();
        } catch (Exception ex) {
            logger.error("Error saving properties", ex);
        }
    }

    /**
     * Forcefully writes the active properties lifecycle tracking configuration mapping out into physical file storage footprints.
     *
     * @throws Exception if physical file system I/O errors structurally interrupt execution flow
     */
    public static void save() throws Exception {
        save(WizLang.getName());
    }

    /**
     * Forcefully writes the active properties lifecycle tracking configuration securely out into a specific target mapping payload.
     *
     * @param name the precise properties mapping filename (without the {@code .ini} extension)
     * @throws Exception if physical file system I/O errors structurally interrupt execution flow
     */
    public static void save(String name) throws Exception {
        File file = new File(folder, name + ".ini");
        try (FileWriter output = new FileWriter(file)) {
            props.store(output, name + " properties");
        }
    }

    // =========================================================================
    // PROPERTY ACCESS (GETTERS)
    // =========================================================================

    /**
     * Evaluates if a structurally defined property tracking key dynamically exists inside the active configuration footprint.
     *
     * @param key the property mapping tracker key
     * @return {@code true} if mapping is actively registered, {@code false} otherwise
     */
    public static boolean containsKey(String key) {
        return props.containsKey(key);
    }

    /**
     * Securely retrieves a mapped String configuration mapping payload, actively falling back to a mapped default parameter.
     *
     * @param key          the property mapping tracker key
     * @param defaultValue the localized default parameter payload
     * @return the structurally linked string configuration, or {@code defaultValue} if mapping rejected
     */
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    /**
     * Securely retrieves a mapped {@link Boolean} configuration mapping payload, actively falling back to a mapped default parameter.
     *
     * @param key          the property mapping tracker key
     * @param defaultValue the localized default parameter payload
     * @return the structurally parsed {@link Boolean} configuration
     */
    public static Boolean get(String key, Boolean defaultValue) {
        return Boolean.valueOf(get(key, defaultValue.toString()));
    }

    /**
     * Securely retrieves a mapped {@link Integer} configuration mapping payload, actively falling back to a mapped default parameter upon parsing exceptions.
     *
     * @param key          the property mapping tracker key
     * @param defaultValue the localized default parameter payload
     * @return the structurally parsed {@link Integer} configuration
     */
    public static Integer get(String key, Integer defaultValue) {
        try {
            return Integer.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Securely retrieves a mapped {@link Long} configuration mapping payload, actively falling back to a mapped default parameter upon parsing exceptions.
     *
     * @param key          the property mapping tracker key
     * @param defaultValue the localized default parameter payload
     * @return the structurally parsed {@link Long} configuration
     */
    public static Long get(String key, Long defaultValue) {
        try {
            return Long.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Securely retrieves a mapped {@link Double} configuration mapping payload, actively falling back to a mapped default parameter upon parsing exceptions.
     *
     * @param key          the property mapping tracker key
     * @param defaultValue the localized default parameter payload
     * @return the structurally parsed {@link Double} configuration
     */
    public static Double get(String key, Double defaultValue) {
        try {
            return Double.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Securely retrieves a mapped {@link Float} configuration mapping payload, actively falling back to a mapped default parameter upon parsing exceptions.
     *
     * @param key          the property mapping tracker key
     * @param defaultValue the localized default parameter payload
     * @return the structurally parsed {@link Float} configuration
     */
    public static Float get(String key, Float defaultValue) {
        try {
            return Float.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves an isolated deep copy mapping extracting all active property payload constructs via a standardized {@link Map} footprint.
     * <p>
     * Modifications actively applied to the generated map context do not implicitly taint the localized active property manager mapping.
     * </p>
     *
     * @return a fully populated {@link LinkedHashMap} mapping housing the properties
     */
    public static Map<String, String> getAll() {
        Map<String, String> map = new LinkedHashMap<>();
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.getProperty(key));
        }
        return map;
    }

    // =========================================================================
    // PROPERTY MODIFICATION (SETTERS)
    // =========================================================================

    /**
     * Installs a direct mapped string payload tracking configuration, executing a background mapped physical file save immediately.
     *
     * @param key   the property mapping tracker key
     * @param value the target updated string value footprint
     */
    public static void set(String key, String value) {
        props.setProperty(key, value);
        trySave();
    }

    /**
     * Installs a mapped {@link Boolean} property, converting constraints mapping internally towards text while executing an automatic physical save limit.
     *
     * @param key   the property mapping tracker key
     * @param value the structural {@link Boolean} value footprint
     */
    public static void set(String key, Boolean value) {
        set(key, value.toString());
    }

    /**
     * Installs a mapped {@link Integer} property, converting constraints mapping internally towards text while executing an automatic physical save limit.
     *
     * @param key   the property mapping tracker key
     * @param value the structural {@link Integer} value footprint
     */
    public static void set(String key, Integer value) {
        set(key, value.toString());
    }

    /**
     * Installs a mapped {@link Long} property, converting constraints mapping internally towards text while executing an automatic physical save limit.
     *
     * @param key   the property mapping tracker key
     * @param value the structural {@link Long} value footprint
     */
    public static void set(String key, Long value) {
        set(key, value.toString());
    }

    /**
     * Installs a mapped {@link Double} property, converting constraints mapping internally towards text while executing an automatic physical save limit.
     *
     * @param key   the property mapping tracker key
     * @param value the structural {@link Double} value footprint
     */
    public static void set(String key, Double value) {
        set(key, value.toString());
    }

    /**
     * Installs a mapped {@link Float} property, converting constraints mapping internally towards text while executing an automatic physical save limit.
     *
     * @param key   the property mapping tracker key
     * @param value the structural {@link Float} value footprint
     */
    public static void set(String key, Float value) {
        set(key, value.toString());
    }

    /**
     * Erases a specified configuration property tracker from active mappings natively executing an active background file update.
     *
     * @param key the property mapping tracker key linked to removal
     */
    public static void remove(String key) {
        props.remove(key);
        trySave();
    }

    /**
     * Radically clears all currently loaded mapped properties mapping entirely, resetting state constraints via physical save mapping executions.
     */
    public static void clear() {
        props.clear();
        trySave();
    }

    /**
     * Actively maps a monolithic {@link Map} payload collection structurally writing all target pair nodes implicitly followed by a localized save format.
     *
     * @param map the target payload definitions block
     */
    public static void putAll(Map<String, String> map) {
        if (map == null) return;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue());
        }
        trySave();
    }

    // =========================================================================
    // UTILS (MAP -> STRING)
    // =========================================================================

    /**
     * Interprets a structured text payload formatting mapped delimiter data constraints logically mapping the extracted data natively towards a target map pattern block mapping {@code =} defaults.
     *
     * @param source the target raw property text mapping block source
     * @return a mapped structured {@link Map} implementation configuration housing extracted constraints
     */
    public static Map<String, String> fromSource(String source) {
        return fromSource(source, "=");
    }

    /**
     * Interprets a structured text payload block resolving key-value mappings bounded strictly mapping a targeted text payload separator.
     * <p>
     * Active lines physically mapping the leading character {@code #} are structurally dismissed as internal unparsed comment parameters.
     * </p>
     *
     * @param source    the active raw property mapping string definitions source block
     * @param separator the defined String constraints separator token format
     * @return a compiled {@link Map} construct defining extracted configurations
     */
    public static Map<String, String> fromSource(String source, String separator) {
        var result = new LinkedHashMap<String, String>();
        if (WizString.isEmpty(source)) {
            return result;
        }
        var lines = WizString.getLines(source);
        for (var line : lines) {
            if (line.isBlank() || line.startsWith("#")) {
                continue;
            }
            int pos = line.indexOf(separator);
            if (pos > -1) {
                result.put(line.substring(0, pos).trim(), line.substring(pos + separator.length()).trim());
            }
        }
        return result;
    }

    /**
     * Translates a robust {@link Map} representation out towards mapped text serialization bounded by standard formatted constraints parameters.
     *
     * @param props the Map collection mapping constraints targeting dump behaviors
     * @return a formatted raw payload output mapped strictly for file integrations
     */
    public static String toSource(Map<String, String> props) {
        return toSource(props, "=");
    }

    /**
     * Serializes a complex properties mapping structure structurally targeting mapped raw formatted output text boundaries leveraging targeted delimiters.
     *
     * @param props     the localized active parameters Map configuration footprint
     * @param separator the delimiter format mapped natively between string keys and limits
     * @return the serialized mapping text payload output
     */
    public static String toSource(Map<String, String> props, String separator) {
        return toSource(props, separator, getPropsDefaultComments());
    }

    /**
     * Serializes an exhaustive native configuration mapping block translating mappings accurately while enforcing static header comment mapping declarations.
     *
     * @param props     the target configuration Map mapping properties data layout
     * @param separator the targeted delimited boundary mapping footprint
     * @param comments  mapped comment headers injected naturally atop generated footprints mapping {@code #} standard tags
     * @return a serialized multi-lined configuration mappings text model payload
     */
    public static String toSource(Map<String, String> props, String separator, String[] comments) {
        var result = new StringBuilder();
        for (var comment : comments) {
            if (comment == null || comment.isBlank()) {
                continue;
            }
            if (!comment.startsWith("#")) {
                result.append("# ");
            }
            result.append(comment);
            result.append("\n");
        }
        for (var key : props.keySet()) {
            result.append(key);
            result.append(separator);
            result.append(props.get(key));
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Generates active dynamically structured timestamp headers explicitly documenting configuration mapping payloads limits context parameters format.
     *
     * @return an implicitly structured textual string mapped tracking array configuration block mappings
     */
    public static String[] getPropsDefaultComments() {
        return new String[] {
                "# Generated by: " + WizLang.getName(),
                "# Created on: " + WizInstant.formatDateTimeFile.format(Instant.now())
        };
    }

}
