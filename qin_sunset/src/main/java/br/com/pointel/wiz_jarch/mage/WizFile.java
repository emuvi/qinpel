package br.com.pointel.wiz_jarch.mage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A utility class for file and path manipulation, including file dialogs.
 * <p>
 * {@code WizFile} provides static methods for resolving absolute paths, manipulating
 * file extensions, performing file system operations, and launching Swing-based file
 * choosers (with fallback to terminal prompts).
 * </p>
 */
public class WizFile {

    /**
     * Cleans a file path by fixing separators and resolving it to an absolute path.
     *
     * @param path the path to clean
     * @return the cleaned absolute path
     */
    public static String clean(String path) {
        return WizFile.getAbsolute(WizFile.fixSeparators(path));
    }

    /**
     * Resolves a path to its absolute form, handling relative prefixes like {@code "."}, {@code ".."}, and {@code "~"}.
     *
     * @param path the path to resolve
     * @return the absolute path string
     */
    public static String getAbsolute(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        final var samePrefix = "." + File.separator;
        final var upperPrefix = ".." + File.separator;
        final var homePrefix = "~" + File.separator;
        if (path.startsWith(samePrefix) || path.startsWith(upperPrefix)) {
            var workingDir = new File(System.getProperty("user.dir"));
            while (path.startsWith(samePrefix) || path.startsWith(upperPrefix)) {
                if (path.startsWith(samePrefix)) {
                    path = path.substring(samePrefix.length());
                } else {
                    workingDir = workingDir.getParentFile();
                    path = path.substring(upperPrefix.length());
                }
            }
            return WizFile.sum(workingDir.getAbsolutePath(), path);
        }
        if (path.startsWith(homePrefix)) {
            var homeDir = new File(System.getProperty("user.home"));
            return WizFile.sum(homeDir.getAbsolutePath(), path);
        }
        return path;
    }

    /**
     * Fixes file separators in a path string to match the system's separator.
     *
     * @param path the path to fix
     * @return the path with fixed separators
     */
    public static String fixSeparators(String path) {
        if (WizString.isEmpty(path)) {
            return path;
        }
        if (path.contains("\\") && "/".equals(File.separator)) {
            path = path.replaceAll("\\\\", "/");
        } else if (path.contains("/") && "\\".equals(File.separator)) {
            path = path.replaceAll("/", "\\\\");
        }
        return path;
    }

    /**
     * Joins a parent path and a child path, handling separators automatically.
     *
     * @param path  the parent path
     * @param child the child path
     * @return the joined path string
     */
    public static String sum(String path, String child) {
        if (!WizString.isNotEmpty(path) || !WizString.isNotEmpty(child)) {
            return WizString.getFirstNonEmpty(path, child);
        }
        if (path.endsWith(File.separator) && child.startsWith(File.separator)) {
            return path + child.substring(File.separator.length());
        } else if (path.endsWith(File.separator) || child.startsWith(File.separator)) {
            return path + child;
        } else {
            return path + File.separator + child;
        }
    }

    /**
     * Joins a parent path and multiple child path segments.
     *
     * @param path     the parent path
     * @param children the child path segments
     * @return the joined path string
     */
    public static String sum(String path, String... children) {
        var result = path;
        if (children != null) {
            for (String filho : children) {
                result = WizFile.sum(result, filho);
            }
        }
        return result;
    }

    /**
     * Joins a parent {@link File} and multiple child path segments.
     *
     * @param path     the parent {@link File}
     * @param children the child path segments
     * @return the resulting {@link File}
     */
    public static File sum(File path, String... children) {
        var result = path;
        if (result != null && children != null) {
            for (String child : children) {
                result = new File(result, child);
            }
        }
        return result;
    }

    /**
     * Finds a parent directory of a {@link File} that matches a specific name.
     *
     * @param path     the starting file
     * @param withName the name of the parent directory to find
     * @return the matching parent {@link File}, or {@code null} if not found
     */
    public static File getParent(File path, String withName) {
        File result = null;
        if (path != null) {
            var actual = path.getParentFile();
            while (!withName.equals(actual.getName())) {
                actual = actual.getParentFile();
                if (actual == null) {
                    break;
                }
            }
            result = actual;
        }
        return result;
    }

    /**
     * Gets the parent path string of a given path.
     *
     * @param path the path string
     * @return the parent path string, or the original path if no separator is found
     */
    public static String getParent(String path) {
        if (path.contains(File.separator)) {
            return path.substring(0, path.lastIndexOf(File.separator));
        }
        return path;
    }

    /**
     * Finds a root directory (parent) with a specific name starting from a given path.
     *
     * @param withName the name of the root to find
     * @param fromPath the starting path
     * @return the matching parent {@link File}, or {@code null} if not found
     */
    public static File getRoot(String withName, File fromPath) {
        if (fromPath == null) {
            return null;
        }
        var result = fromPath.getParentFile();
        while (result != null && !Objects.equals(withName, result.getName())) {
            result = result.getParentFile();
        }
        return result;
    }

    /**
     * Extracts the file name from a path string.
     *
     * @param path the path string
     * @return the file name
     */
    public static String getName(String path) {
        if (path == null) {
            return null;
        }
        final var sep = path.lastIndexOf(File.separator);
        if (sep == -1) {
            return path;
        }
        return path.substring(sep + 1);
    }

    /**
     * Extracts the base name (file name without extension) from a path string.
     *
     * @param path the path string
     * @return the base name
     */
    public static String getBaseName(String path) {
        if (path == null) {
            return null;
        }
        path = WizFile.getName(path);
        final var dot = path.lastIndexOf(".");
        if (dot > -1) {
            return path.substring(0, dot);
        }
        return path;
    }

    /**
     * Extracts the extension from a path string.
     *
     * @param path the path string
     * @return the extension (including the dot), or an empty string if none
     */
    public static String getExtension(String path) {
        if (path == null) {
            return null;
        }
        final var dot = path.lastIndexOf(".");
        if (dot > -1) {
            return path.substring(dot);
        }
        return "";
    }

    /**
     * Changes the extension of a path string.
     *
     * @param path         the path string
     * @param newExtension the new extension (with or without dot)
     * @return the path string with the new extension
     */
    public static String changeExtension(String path, String newExtension) {
        if (path == null) {
            return null;
        }
        if (newExtension == null) {
            newExtension = "";
        }
        if (!newExtension.isEmpty() && !newExtension.startsWith(".")) {
            newExtension = "." + newExtension;
        }
        final var dot = path.lastIndexOf(".");
        if (dot > -1) {
            return path.substring(0, dot) + newExtension;
        }
        return path + newExtension;
    }

    /**
     * Appends characters to the base name of a path (before the extension).
     *
     * @param path  the path string
     * @param chars the characters to append
     * @return the modified path string
     */
    public static String addOnBaseName(String path, String chars) {
        if (path == null) {
            return chars;
        }
        if (WizString.isEmpty(chars)) {
            return path;
        }
        var dotIndex = path.lastIndexOf(".");
        if (dotIndex > -1) {
            return path.substring(0, dotIndex) + chars + path.substring(dotIndex);
        }
        return path + chars;
    }

    /**
     * Appends characters to the base name of a {@link File} (before the extension).
     *
     * @param file  the {@link File}
     * @param chars the characters to append
     * @return the modified {@link File}
     */
    public static File addOnBaseName(File file, String chars) {
        return new File(WizFile.addOnBaseName(file.getAbsolutePath(), chars));
    }

    /**
     * Returns a {@link File} object that does not override an existing file.
     * <p>
     * If the given path already exists, this method appends an incremental counter
     * to the base name until a unique path is found.
     * </p>
     *
     * @param path the desired {@link File} path
     * @return a guaranteed unique {@link File} path
     */
    public static File notOverride(File path) {
        if ((path == null) || !path.exists()) {
            return path;
        }
        File result = null;
        var attempt = 2;
        do {
            result = new File(WizFile.addOnBaseName(path.getAbsolutePath(), " (" + attempt + ")"));
            attempt++;
        } while (result.exists());
        return result;
    }

    // =========================================================================
    // I/O AND FILE SYSTEM MANIPULATION
    // =========================================================================

    /**
     * Reads all text from a file using UTF-8 encoding.
     *
     * @param file the {@link File} to read
     * @return the file contents as a {@link String}
     * @throws IOException if an I/O error occurs
     */
    public static String readString(File file) throws IOException {
        if (file == null) return null;
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Reads all lines from a file using UTF-8 encoding.
     *
     * @param file the {@link File} to read
     * @return a {@link List} of strings, one per line
     * @throws IOException if an I/O error occurs
     */
    public static List<String> readLines(File file) throws IOException {
        if (file == null) return null;
        return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Writes a string to a file using UTF-8 encoding, creating or truncating the file.
     *
     * @param file    the target {@link File}
     * @param content the string content to write
     * @throws IOException if an I/O error occurs
     */
    public static void writeString(File file, String content) throws IOException {
        if (file != null) {
            Files.writeString(file.toPath(), content != null ? content : "", StandardCharsets.UTF_8);
        }
    }

    /**
     * Writes lines of text to a file using UTF-8 encoding.
     *
     * @param file  the target {@link File}
     * @param lines the lines to write
     * @throws IOException if an I/O error occurs
     */
    public static void writeLines(File file, Iterable<? extends CharSequence> lines) throws IOException {
        if (file != null && lines != null) {
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
        }
    }

    /**
     * Copies a file or directory.
     *
     * @param source the source {@link File}
     * @param target the target {@link File}
     * @throws IOException if an I/O error occurs
     */
    public static void copy(File source, File target) throws IOException {
        if (source != null && target != null) {
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Moves or renames a file or directory.
     *
     * @param source the source {@link File}
     * @param target the target {@link File}
     * @throws IOException if an I/O error occurs
     */
    public static void move(File source, File target) throws IOException {
        if (source != null && target != null) {
            Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Recursively deletes a file or directory.
     *
     * @param file the {@link File} or directory to delete
     * @return {@code true} if successfully deleted, {@code false} otherwise
     */
    public static boolean delete(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    delete(child);
                }
            }
        }
        return file.delete();
    }
    
    /**
     * Creates a directory and any necessary parent directories.
     *
     * @param dir the directory to create
     * @return {@code true} if successfully created, {@code false} if it already exists or failed
     */
    public static boolean createDir(File dir) {
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }
    
    /**
     * Safely returns a human-readable file size (e.g., "1.2 MB").
     *
     * @param file the target {@link File}
     * @return the formatted size string
     */
    public static String getReadableSize(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return "0 B";
        }
        long length = file.length();
        if (length < 1024) return length + " B";
        int z = (63 - Long.numberOfLeadingZeros(length)) / 10;
        return String.format("%.1f %sB", (double)length / (1L << (z * 10)), " KMGTPE".charAt(z));
    }

}
