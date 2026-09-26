package br.com.pointel.wiz_jarch.mage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * A utility class for safely accessing environment variables and system properties.
 * <p>
 * This class provides convenient methods to retrieve configuration values, system directories,
 * and OS-specific information. It handles parsing and default values automatically.
 * </p>
 */
public class WizEnv {

    private WizEnv() {
    }

    // =========================================================================
    // ENVIRONMENT VARIABLES (System.getenv)
    // =========================================================================

    /**
     * Gets an environment variable by its key.
     *
     * @param key the name of the environment variable
     * @return the value, or {@code null} if the variable is missing or blank
     */
    public static String get(String key) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? null : value;
    }

    /**
     * Gets an environment variable, returning a fallback value if it is missing or blank.
     *
     * @param key          the name of the environment variable
     * @param defaultValue the fallback value to return
     * @return the value, or the fallback if missing or blank
     */
    public static String get(String key, String defaultValue) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    /**
     * Gets an environment variable as an Integer, returning a fallback if missing or invalid.
     *
     * @param key          the name of the environment variable
     * @param defaultValue the fallback value
     * @return the parsed Integer, or the fallback
     */
    public static Integer getInt(String key, Integer defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets an environment variable as a Long, returning a fallback if missing or invalid.
     *
     * @param key          the name of the environment variable
     * @param defaultValue the fallback value
     * @return the parsed Long, or the fallback
     */
    public static Long getLong(String key, Long defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets an environment variable as a Double, returning a fallback if missing or invalid.
     *
     * @param key          the name of the environment variable
     * @param defaultValue the fallback value
     * @return the parsed Double, or the fallback
     */
    public static Double getDouble(String key, Double defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets an environment variable as a Boolean, returning a fallback if missing or blank.
     * <p>
     * The strings "true", "1", "yes", and "on" (case-insensitive) map to {@code true}.
     * </p>
     *
     * @param key          the name of the environment variable
     * @param defaultValue the fallback value
     * @return the parsed Boolean, or the fallback
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        String val = value.trim().toLowerCase();
        return val.equals("true") || val.equals("1") || val.equals("yes") || val.equals("on");
    }

    /**
     * Gets an environment variable, throwing an exception if it is missing or blank.
     *
     * @param key the name of the environment variable
     * @return the value of the environment variable
     * @throws IllegalStateException if the variable is missing or blank
     */
    public static String require(String key) {
        var value = get(key);
        if (value == null) {
            throw new IllegalStateException("Required environment variable is missing: " + key);
        }
        return value;
    }

    /**
     * Returns a map of all current system environment variables.
     *
     * @return a map containing all environment variables
     */
    public static Map<String, String> getAll() {
        return System.getenv();
    }

    // =========================================================================
    // SYSTEM PROPERTIES (System.getProperty)
    // =========================================================================

    /**
     * Gets a system property by its key.
     *
     * @param key the name of the system property
     * @return the value, or {@code null} if missing or blank
     */
    public static String getProperty(String key) {
        var value = System.getProperty(key);
        return value == null || value.isBlank() ? null : value;
    }

    /**
     * Gets a system property, returning a fallback value if missing or blank.
     *
     * @param key          the name of the system property
     * @param defaultValue the fallback value
     * @return the value, or the fallback if missing or blank
     */
    public static String getProperty(String key, String defaultValue) {
        var value = System.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    /**
     * Gets a system property as a Boolean, returning a fallback if missing or blank.
     * <p>
     * The strings "true", "1", "yes", and "on" (case-insensitive) map to {@code true}.
     * </p>
     *
     * @param key          the name of the system property
     * @param defaultValue the fallback value
     * @return the parsed Boolean, or the fallback
     */
    public static Boolean getPropertyBoolean(String key, Boolean defaultValue) {
        var value = System.getProperty(key);
        if (value == null || value.isBlank()) return defaultValue;
        String val = value.trim().toLowerCase();
        return val.equals("true") || val.equals("1") || val.equals("yes") || val.equals("on");
    }

    /**
     * Gets a system property, throwing an exception if it is missing or blank.
     *
     * @param key the name of the system property
     * @return the value of the system property
     * @throws IllegalStateException if the property is missing or blank
     */
    public static String requireProperty(String key) {
        var value = getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required system property is missing: " + key);
        }
        return value;
    }

    /**
     * Returns all current system properties.
     *
     * @return a {@link Properties} object containing all system properties
     */
    public static Properties getProperties() {
        return System.getProperties();
    }

    // =========================================================================
    // COMMON SYSTEM DIRECTORIES & INFO
    // =========================================================================

    /**
     * Gets the system temporary directory path.
     *
     * @return the temporary directory path
     */
    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Gets the system temporary directory as a {@link File}.
     *
     * @return the temporary directory file
     */
    public static File getTempDirFile() {
        return new File(getTempDir());
    }

    /**
     * Gets the current user's home directory path.
     *
     * @return the user's home directory path
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Gets the current user's home directory as a {@link Path}.
     *
     * @return the user's home directory path object
     */
    public static Path getUserHomePath() {
        return Paths.get(getUserHome());
    }

    /**
     * Gets the current working directory path.
     *
     * @return the current working directory path
     */
    public static String getWorkDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Gets the current working directory as a {@link File}.
     *
     * @return the current working directory file
     */
    public static File getWorkDirFile() {
        return new File(getWorkDir());
    }

    /**
     * Gets the current operating system user name.
     *
     * @return the OS user name
     */
    public static String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * Gets the operating system name (e.g., Windows 10, Linux, Mac OS X).
     *
     * @return the OS name
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Gets the operating system version.
     *
     * @return the OS version
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Gets the Java runtime version.
     *
     * @return the Java version string
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Gets the file separator for the current OS (e.g., "\" on Windows, "/" on Unix).
     *
     * @return the file separator string
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }
    
    /**
     * Gets the path separator for the current OS (e.g., ";" on Windows, ":" on Unix).
     *
     * @return the path separator string
     */
    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    /**
     * Gets the line separator for the current OS.
     *
     * @return the line separator string
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
    }

}
