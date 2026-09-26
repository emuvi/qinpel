package br.com.pointel.wiz_jarch.data;

/**
 * Describes a named link relationship with an optional target name.
 */
public class Linked implements Data {

    public String name;
    public String upon;

    /**
     * Creates an empty link descriptor.
     */
    public Linked() {
    }

    /**
     * Creates a link descriptor with the given name.
     *
     * @param name the link name
     */
    public Linked(String name) {
        this.name = name;
    }

    /**
     * Creates a link descriptor with the given name and target.
     *
     * @param name the link name
     * @param upon the target name
     */
    public Linked(String name, String upon) {
        this.name = name;
        this.upon = upon;
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
     * Indicates whether the target name is set.
     *
     * @return {@code true} when the target name is non-empty; otherwise {@code false}
     */
    public boolean hasUpon() {
        return this.upon != null && !this.upon.isEmpty();
    }

    /**
     * Updates the name and returns this descriptor.
     *
     * @param name the new name
     * @return this descriptor
     */
    public Linked withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this descriptor.
     *
     * @return this descriptor
     */
    public Linked withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the target name and returns this descriptor.
     *
     * @param upon the new target name
     * @return this descriptor
     */
    public Linked withUpon(String upon) {
        this.upon = upon;
        return this;
    }

    /**
     * Clears the target name and returns this descriptor.
     *
     * @return this descriptor
     */
    public Linked withNoUpon() {
        this.upon = null;
        return this;
    }

    /**
     * Returns a cloned descriptor with the given name.
     *
     * @param name the replacement name
     * @return a cloned descriptor with the name updated
     */
    public Linked uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a name.
     *
     * @return a cloned descriptor with the name cleared
     */
    public Linked uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned descriptor with the given target name.
     *
     * @param upon the replacement target name
     * @return a cloned descriptor with the target updated
     */
    public Linked uponUpon(String upon) {
        var clone = this.clone();
        clone.upon = upon;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a target name.
     *
     * @return a cloned descriptor with the target cleared
     */
    public Linked uponNoUpon() {
        var clone = this.clone();
        clone.upon = null;
        return clone;
    }

    /**
     * Creates a deep clone of this descriptor.
     *
     * @return a deep copy of this descriptor
     */
    @Override
    public Linked clone() {
        return (Linked) this.deepClone();
    }

    /**
     * Compares this descriptor to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this descriptor.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this descriptor to JSON text.
     *
     * @return the JSON representation of this descriptor
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a link descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static Linked fromChars(String chars) {
        return Base.fromChars(chars, Linked.class);
    }

}
