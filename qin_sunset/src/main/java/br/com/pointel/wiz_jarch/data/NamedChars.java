package br.com.pointel.wiz_jarch.data;

/**
 * Associates a name with an arbitrary text payload.
 */
public class NamedChars implements Data {

    public String name;
    public String chars;

    /**
     * Creates an empty named-text container.
     */
    public NamedChars() {}

    /**
     * Creates a named-text container with the given name.
     *
     * @param name the name
     */
    public NamedChars(String name) {
        this.name = name;
    }

    /**
     * Creates a named-text container with the given name and payload.
     *
     * @param name the name
     * @param chars the text payload
     */
    public NamedChars(String name, String chars) {
        this.name = name;
        this.chars = chars;
    }

    /**
     * Indicates whether the name is set.
     *
     * @return {@code true} when the name is non-empty; otherwise {@code false}
     */
    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    /**
     * Indicates whether the payload is set.
     *
     * @return {@code true} when the payload is non-empty; otherwise {@code false}
     */
    public boolean hasChars() {
        return this.chars != null && !this.chars.isEmpty();
    }

    /**
     * Updates the name and returns this container.
     *
     * @param name the new name
     * @return this container
     */
    public NamedChars withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this container.
     *
     * @return this container
     */
    public NamedChars withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the payload and returns this container.
     *
     * @param chars the new text payload
     * @return this container
     */
    public NamedChars withChars(String chars) {
        this.chars = chars;
        return this;
    }

    /**
     * Clears the payload and returns this container.
     *
     * @return this container
     */
    public NamedChars withNoChars() {
        this.chars = null;
        return this;
    }

    /**
     * Returns a cloned container with the given name.
     *
     * @param name the replacement name
     * @return a cloned container with the name updated
     */
    public NamedChars uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned container without a name.
     *
     * @return a cloned container with the name cleared
     */
    public NamedChars uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned container with the given payload.
     *
     * @param chars the replacement text payload
     * @return a cloned container with the payload updated
     */
    public NamedChars uponChars(String chars) {
        var clone = this.clone();
        clone.chars = chars;
        return clone;
    }

    /**
     * Returns a cloned container without a payload.
     *
     * @return a cloned container with the payload cleared
     */
    public NamedChars uponNoChars() {
        var clone = this.clone();
        clone.chars = null;
        return clone;
    }

    /**
     * Creates a deep clone of this container.
     *
     * @return a deep copy of this container
     */
    @Override
    public NamedChars clone() {
        return (NamedChars) this.deepClone();
    }

    /**
     * Compares this container to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this container.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this container to JSON text.
     *
     * @return the JSON representation of this container
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a named-text container.
     *
     * @param chars the JSON text to parse
     * @return the parsed container
     */
    public static NamedChars fromChars(String chars) {
        return Base.fromChars(chars, NamedChars.class);
    }

}
