package br.com.pointel.wiz_jarch.mage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * A utility class for loading, saving, and executing Groovy scripts.
 * <p>
 * This class provides a streamlined API for interacting with the Groovy shell,
 * managing script files within a designated folder, and binding variables to 
 * the script execution context.
 * </p>
 */
public class WizGroovy {

    private static String extension = ".gvy";

    /**
     * The default folder where Groovy scripts are stored.
     */
    public static File folder = new File("gvy");

    private WizGroovy() {}

    /**
     * Gets the default file extension for Groovy scripts.
     *
     * @return the file extension (e.g., {@code ".gvy"})
     */
    public static String getExtension() {
        return extension;
    }

    /**
     * Sets the file extension for Groovy scripts.
     * <p>
     * Automatically normalizes the extension to lowercase and ensures it starts with a dot.
     * </p>
     *
     * @param extension the new extension (e.g., {@code ".gvy"} or {@code "gvy"})
     */
    public static void setExtension(String extension) {
        extension = extension == null ? "" : extension.toLowerCase().trim();
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        WizGroovy.extension = extension;
    }

    /**
     * Gets the default folder where Groovy scripts are stored.
     *
     * @return the default folder as a {@link File}
     */
    public static File getFolder() {
        return folder;
    }

    /**
     * Sets the default folder where Groovy scripts are stored.
     *
     * @param folder the new default folder as a {@link File}
     */
    public static void setFolder(File folder) {
        WizGroovy.folder = folder;
    }

    /**
     * Gets the names of all Groovy scripts in the default folder.
     *
     * @return a {@link List} of script file names with their extensions
     */
    public static List<String> getNames() {
        return getNames(folder);
    }
 
    /**
     * Gets the names of all Groovy scripts in a specified folder.
     * <p>
     * Creates the folder if it does not already exist.
     * </p>
     *
     * @param folder the folder to list
     * @return a {@link List} of script file names with their extensions
     */
    public static List<String> getNames(File folder) {
        var result = new ArrayList<String>();
        try {
            Files.createDirectories(folder.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        var files = folder.listFiles();
        if (files != null) {
            for (var inside : files) {
                if (inside.isFile() && inside.getName().toLowerCase().endsWith(extension)) {
                    result.add(inside.getName());
                }
            }
        }
        return result;
    }

    /**
     * Populates a combo box model with the names of all Groovy scripts from the default folder.
     *
     * @param field the {@link DefaultComboBoxModel} to populate
     */
    public static void loadNames(DefaultComboBoxModel<String> field) {
        loadNames(field, folder);
    }
    
    /**
     * Populates a combo box model with the names of all Groovy scripts from a specified folder.
     *
     * @param field  the {@link DefaultComboBoxModel} to populate
     * @param folder the folder to list scripts from
     */
    public static void loadNames(DefaultComboBoxModel<String> field, File folder) {
        field.removeAllElements();
        for (var chatName : getNames(folder)) {
            field.addElement(chatName);
        }
    }

    /**
     * Checks if a Groovy script exists in the default folder.
     *
     * @param name the script name (without the extension)
     * @return {@code true} if the script exists, {@code false} otherwise
     */
    public static boolean exists(String name) {
        return exists(name, folder);
    }

    /**
     * Checks if a Groovy script exists in a specified folder.
     *
     * @param name   the script name (without the extension)
     * @param folder the folder to check in
     * @return {@code true} if the script exists, {@code false} otherwise
     */
    public static boolean exists(String name, File folder) {
        var groovyFile = new File(folder, name + extension);
        return groovyFile.exists() && groovyFile.isFile();
    }

    /**
     * Loads a Groovy script from the default folder as a string.
     *
     * @param name the script name (without the extension)
     * @return the script source code as a {@link String}
     * @throws IOException if the file cannot be read
     */
    public static String load(String name) throws IOException {
        return load(name, folder);
    }
    
    /**
     * Loads a Groovy script from a specified folder as a string.
     *
     * @param name   the script name (without the extension)
     * @param folder the folder to load from
     * @return the script source code as a {@link String}
     * @throws IOException if the file cannot be read
     */
    public static String load(String name, File folder) throws IOException {
        var groovyFile = new File(folder, name + extension);
        return Files.readString(groovyFile.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Saves a Groovy script to the default folder.
     *
     * @param name   the script name (without the extension)
     * @param source the script source code
     * @throws IOException if the file cannot be written
     */
    public static void save(String name, String source) throws IOException {
        save(name, folder, source);
    }

    /**
     * Saves a Groovy script to a specified folder.
     * <p>
     * Creates the directory if it does not exist.
     * </p>
     *
     * @param name   the script name (without the extension)
     * @param folder the folder to save to
     * @param source the script source code
     * @throws IOException if the file cannot be written
     */
    public static void save(String name, File folder, String source) throws IOException {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        var groovyFile = new File(folder, name + extension);
        Files.writeString(groovyFile.toPath(), source, StandardCharsets.UTF_8);
    }

    /**
     * Deletes a Groovy script from the default folder.
     *
     * @param name the script name (without the extension)
     * @return {@code true} if successfully deleted, {@code false} otherwise
     */
    public static boolean delete(String name) {
        return delete(name, folder);
    }

    /**
     * Deletes a Groovy script from a specified folder.
     *
     * @param name   the script name (without the extension)
     * @param folder the folder to delete from
     * @return {@code true} if successfully deleted, {@code false} otherwise
     */
    public static boolean delete(String name, File folder) {
        var groovyFile = new File(folder, name + extension);
        if (groovyFile.exists()) {
            return groovyFile.delete();
        }
        return false;
    }

    /**
     * Loads and executes a Groovy script from the default folder.
     *
     * @param name the script name (without the extension)
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name) throws Exception {
        return run(load(name));
    }

    /**
     * Loads and executes a Groovy script from a specified folder.
     *
     * @param name   the script name (without the extension)
     * @param folder the folder to load from
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name, File folder) throws Exception {
        return run(load(name, folder));
    }
    
    /**
     * Loads and executes a Groovy script from the default folder with supplied variables.
     *
     * @param name      the script name (without the extension)
     * @param variables the variables available to the script binding
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name, Map<String, Object> variables) throws Exception {
        return run(load(name), variables);
    }

    /**
     * Loads and executes a Groovy script from a specified folder with supplied variables.
     *
     * @param name      the script name (without the extension)
     * @param folder    the folder to load from
     * @param variables the variables available to the script binding
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name, File folder, Map<String, Object> variables) throws Exception {
        return run(load(name, folder), variables);
    }

    /**
     * Parses Groovy source code into a {@link Script} object.
     *
     * @param source the Groovy source code
     * @return the compiled {@link Script} object
     */
    public static Script parse(String source) {
        return new GroovyShell().parse(source);
    }

    /**
     * Parses Groovy source code into a {@link Script} object with supplied variables.
     *
     * @param source    the Groovy source code
     * @param variables the variables available to the script binding
     * @return the compiled {@link Script} object
     */
    public static Script parse(String source, Map<String, Object> variables) {
        var binding = new Binding(variables);
        return new GroovyShell(binding).parse(source);
    }
    
    /**
     * Executes Groovy source code and returns the result.
     *
     * @param source the Groovy source code
     * @return the result of the script execution
     * @throws Exception if the script cannot be executed
     */
    public static Object run(String source) throws Exception {
        return run(source, null);
    }
    
    /**
     * Executes Groovy source code with supplied variables and returns the result.
     *
     * @param source    the Groovy source code
     * @param variables the variables available to the script binding
     * @return the result of the script execution
     * @throws Exception if the script cannot be executed
     */
    public static Object run(String source, Map<String, Object> variables) throws Exception {
        var binding = new Binding(variables);
        var shell = new GroovyShell(binding);
        return shell.evaluate(source);
    }

    /**
     * Evaluates a Groovy expression and returns the result.
     * <p>
     * This is a convenience method equivalent to {@link #run(String)}.
     * </p>
     *
     * @param expression the Groovy expression to evaluate
     * @return the result of the expression
     * @throws Exception if the expression cannot be evaluated
     */
    public static Object eval(String expression) throws Exception {
        return run(expression, null);
    }
    
    /**
     * Creates a map of variables from {@link Variable} records.
     * <p>
     * Useful for quickly constructing the variables map for script execution.
     * </p>
     *
     * @param variables the {@link Variable} records to include
     * @return a {@link Map} of variable names to values
     */
    public static Map<String, Object> vars(Variable... variables) {
        var result = new HashMap<String, Object>();
        if (variables != null) {
            for (var variable : variables) {
                result.put(variable.name(), variable.value());
            }
        }
        return result;
    }
    
    /**
     * Creates a {@link Variable} record for use in the {@link #vars(Variable...)} method.
     *
     * @param name  the variable name
     * @param value the variable value
     * @return a {@link Variable} record
     */
    public static Variable map(String name, Object value) {
        return new Variable(name, value);
    }
    
    /**
     * A record representing a named variable for script execution.
     *
     * @param name  the variable name
     * @param value the variable value
     */
    public static record Variable(String name, Object value) {}

    /**
     * A reusable shell context for executing multiple scripts 
     * while sharing the same variables and environment.
     * <p>
     * Maintains a persistent Groovy {@link Binding} across multiple evaluations.
     * </p>
     */
    public static class ShellContext {
        private final Binding binding;
        private final GroovyShell shell;

        /**
         * Creates a new {@link ShellContext} with an empty set of variables.
         */
        public ShellContext() {
            this(new HashMap<>());
        }

        /**
         * Creates a new {@link ShellContext} with initial variables.
         *
         * @param initialVariables the initial variables available to scripts
         */
        public ShellContext(Map<String, Object> initialVariables) {
            this.binding = new Binding(initialVariables);
            this.shell = new GroovyShell(this.binding);
        }

        /**
         * Sets or updates a variable in the shell context.
         *
         * @param name  the variable name
         * @param value the variable value
         * @return this {@link ShellContext} for method chaining
         */
        public ShellContext set(String name, Object value) {
            this.binding.setVariable(name, value);
            return this;
        }

        /**
         * Gets a variable from the shell context.
         *
         * @param name the variable name
         * @return the variable value, or {@code null} if not found
         */
        public Object get(String name) {
            return this.binding.hasVariable(name) ? this.binding.getVariable(name) : null;
        }

        /**
         * Evaluates a Groovy script within this context.
         * <p>
         * The script has access to all variables in the binding.
         * </p>
         *
         * @param script the Groovy source code to evaluate
         * @return the result of the script execution
         */
        public Object eval(String script) {
            return this.shell.evaluate(script);
        }

        /**
         * Parses a Groovy script within this context.
         *
         * @param script the Groovy source code to parse
         * @return the compiled {@link Script} object
         */
        public Script parse(String script) {
            return this.shell.parse(script);
        }
    }
    
}
