package br.com.pointel.wiz_jarch.flow;

/**
 * Represents a distinct chunk of data to be extracted by a {@link Datex} parser.
 * A node is bounded by "startsWith" and "endsWith" tokens.
 */
public class DatexNode {

    /**
     * Helper to wrap multiple nodes into an array.
     *
     * @param nodes the nodes to wrap
     * @return an array of the given nodes
     */
    public static DatexNode[] of(DatexNode... nodes){
        return nodes;
    }

    /**
     * Creates a required Node. If this node is not found, parsing will fail.
     *
     * @param name the name of the node
     * @param startsWith the token indicating the start of the extraction
     * @param endsWith the token indicating the end of the extraction
     * @return the constructed required DatexNode
     */
    public static DatexNode must(String name, DatexToken startsWith, DatexToken endsWith) {
        return new DatexNode(name, startsWith, endsWith, true);
    }

    /**
     * Creates a required Node using an array of possible boundary tokens.
     *
     * @param name the name of the node
     * @param startsWith an array of tokens indicating possible starts
     * @param endsWith an array of tokens indicating possible ends
     * @return the constructed required DatexNode
     */
    public static DatexNode must(String name, DatexToken[] startsWith, DatexToken[] endsWith) {
        return new DatexNode(name, startsWith, endsWith, true);
    }

    /**
     * Creates an optional Node. If this node is not found, parsing skips it.
     *
     * @param name the name of the node
     * @param startsWith the token indicating the start of the extraction
     * @param endsWith the token indicating the end of the extraction
     * @return the constructed optional DatexNode
     */
    public static DatexNode may(String name, DatexToken startsWith, DatexToken endsWith) {
        return new DatexNode(name, startsWith, endsWith, false);
    }

    /**
     * Creates an optional Node using an array of possible boundary tokens.
     *
     * @param name the name of the node
     * @param startsWith an array of tokens indicating possible starts
     * @param endsWith an array of tokens indicating possible ends
     * @return the constructed optional DatexNode
     */
    public static DatexNode may(String name, DatexToken[] startsWith, DatexToken[] endsWith) {
        return new DatexNode(name, startsWith, endsWith, false);
    }
    
    private final String name;
    private final DatexToken[] startsWith;
    private final DatexToken[] endsWith;
    private final boolean required;

    private String value;

    /**
     * Constructs a DatexNode with single boundary tokens.
     *
     * @param name the name of the node
     * @param startsWith the start boundary token
     * @param endsWith the end boundary token
     * @param required true if the node must be found during parsing
     */
    public DatexNode(String name, DatexToken startsWith, DatexToken endsWith, boolean required) {
        this(name, DatexToken.of(startsWith), DatexToken.of(endsWith), required);        
    }

    /**
     * Constructs a DatexNode with multiple possible boundary tokens.
     *
     * @param name the name of the node
     * @param startsWith the possible start boundary tokens
     * @param endsWith the possible end boundary tokens
     * @param required true if the node must be found during parsing
     */
    public DatexNode(String name, DatexToken[] startsWith, DatexToken[] endsWith, boolean required) {
        this.name = name;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.required = required;
        this.value = null;
    }

    /**
     * @return the name of this node
     */
    public String getName() {
        return name;
    }

    /**
     * @return the tokens representing where extraction can start
     */
    public DatexToken[] getStartsWith() {
        return startsWith;
    }

    /**
     * @return the tokens representing where extraction can end
     */
    public DatexToken[] getEndsWith() {
        return endsWith;
    }

    /**
     * @return true if this node must be found for parsing to succeed
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @return the successfully extracted string value, or null if absent
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the extracted value for this node.
     *
     * @param value the parsed value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Erases the extracted value from this node, resetting it for a new parse.
     */
    public void clean() {
        this.value = null;
    }

    /**
     * @return true if a value has been successfully extracted into this node
     */
    public boolean isPresent() {
        return value != null && !value.isBlank();
    }

    /**
     * Gets the extracted value or a fallback default.
     *
     * @param defaultValue the value to return if nothing was extracted
     * @return the extracted value, or the default value if absent
     */
    public String getValueOrDefault(String defaultValue) {
        return isPresent() ? value : defaultValue;
    }

    /**
     * Parses the extracted value as an integer.
     *
     * @param defaultValue the fallback value if absent or parsing fails
     * @return the extracted integer, or the default value
     */
    public int getAsInt(int defaultValue) {
        if (!isPresent()) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the extracted value as a long.
     *
     * @param defaultValue the fallback value if absent or parsing fails
     * @return the extracted long, or the default value
     */
    public long getAsLong(long defaultValue) {
        if (!isPresent()) return defaultValue;
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the extracted value as a double.
     *
     * @param defaultValue the fallback value if absent or parsing fails
     * @return the extracted double, or the default value
     */
    public double getAsDouble(double defaultValue) {
        if (!isPresent()) return defaultValue;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses the extracted value as a boolean.
     *
     * @param defaultValue the fallback value if absent or parsing fails
     * @return the extracted boolean, or the default value
     */
    public boolean getAsBoolean(boolean defaultValue) {
        if (!isPresent()) return defaultValue;
        return Boolean.parseBoolean(value.trim());
    }

    @Override
    public String toString() {
        return "DatexNode{" + "name='" + name + '\'' + ", required=" + required + ", present=" + isPresent() + ", value='" + value + '\'' + '}';
    }

}
