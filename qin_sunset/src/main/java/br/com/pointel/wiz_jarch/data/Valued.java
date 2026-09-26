package br.com.pointel.wiz_jarch.data;

/**
 * Associates a field name with a type and/or value.
 */
public class Valued implements Data {

    public String name;
    public Nature type;
    public Object value;

    /**
     * Creates an empty valued descriptor.
     */
    public Valued() {}

    /**
     * Creates a valued descriptor with the given name.
     *
     * @param name the field name
     */
    public Valued(String name) {
        this.name = name;
    }

    /**
     * Creates a valued descriptor with the given name and type.
     *
     * @param name the field name
     * @param type the field type
     */
    public Valued(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Creates a valued descriptor with the given name and value.
     *
     * @param name the field name
     * @param value the value
     */
    public Valued(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Creates a valued descriptor with the given name, type, and value.
     *
     * @param name the field name
     * @param type the field type
     * @param value the value
     */
    public Valued(String name, Nature type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
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
     * Indicates whether the type is set.
     *
     * @return {@code true} when the type is non-null; otherwise {@code false}
     */
    public boolean hasType() {
        return this.type != null;
    }

    /**
     * Indicates whether the value is set.
     *
     * @return {@code true} when the value is non-null; otherwise {@code false}
     */
    public boolean hasValue() {
        return this.value != null;
    }

    /**
     * Updates the name and returns this descriptor.
     *
     * @param name the new field name
     * @return this descriptor
     */
    public Valued withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this descriptor.
     *
     * @return this descriptor
     */
    public Valued withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the type and returns this descriptor.
     *
     * @param type the new field type
     * @return this descriptor
     */
    public Valued withType(Nature type) {
        this.type = type;
        return this;
    }

    /**
     * Clears the type and returns this descriptor.
     *
     * @return this descriptor
     */
    public Valued withNoType() {
        this.type = null;
        return this;
    }

    /**
     * Updates the value and returns this descriptor.
     *
     * @param value the new value
     * @return this descriptor
     */
    public Valued withValue(Object value) {
        this.value = value;
        return this;
    }

    /**
     * Clears the value and returns this descriptor.
     *
     * @return this descriptor
     */
    public Valued withNoValue() {
        this.value = null;
        return this;
    }

    /**
     * Returns a cloned descriptor with the given name.
     *
     * @param name the replacement field name
     * @return a cloned descriptor with the name updated
     */
    public Valued uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a name.
     *
     * @return a cloned descriptor with the name cleared
     */
    public Valued uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned descriptor with the given type.
     *
     * @param type the replacement field type
     * @return a cloned descriptor with the type updated
     */
    public Valued uponType(Nature type) {
        var clone = this.clone();
        clone.type = type;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a type.
     *
     * @return a cloned descriptor with the type cleared
     */
    public Valued uponNoType() {
        var clone = this.clone();
        clone.type = null;
        return clone;
    }

    /**
     * Returns a cloned descriptor with the given value.
     *
     * @param value the replacement value
     * @return a cloned descriptor with the value updated
     */
    public Valued uponValue(Object value) {
        var clone = this.clone();
        clone.value = value;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a value.
     *
     * @return a cloned descriptor with the value cleared
     */
    public Valued uponNoValue() {
        var clone = this.clone();
        clone.value = null;
        return clone;
    }

    /**
     * Creates a deep clone of this descriptor.
     *
     * @return a deep copy of this descriptor
     */
    @Override
    public Valued clone() {
        return (Valued) this.deepClone();
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
     * Deserializes JSON text into a valued descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static Valued fromChars(String chars) {
        return Base.fromChars(chars, Valued.class);
    }

}
