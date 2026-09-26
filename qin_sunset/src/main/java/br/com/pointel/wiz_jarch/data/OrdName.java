package br.com.pointel.wiz_jarch.data;

/**
 * Associates a sort order with a field name.
 */
public class OrdName implements Data {

    public Integer ord;
    public String name;

    /**
     * Creates an empty ordered-name descriptor.
     */
    public OrdName() {
    }

    /**
     * Creates an ordered-name descriptor with the given name.
     *
     * @param name the field name
     */
    public OrdName(String name) {
        this.name = name;
    }

    /**
     * Creates an ordered-name descriptor with the given order and name.
     *
     * @param ord the order value
     * @param name the field name
     */
    public OrdName(Integer ord, String name) {
        this.ord = ord;
        this.name = name;
    }

    /**
     * Indicates whether the order is set.
     *
     * @return {@code true} when the order is non-null; otherwise {@code false}
     */
    public boolean hasOrd() {
        return this.ord != null;
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
     * Updates the order and returns this descriptor.
     *
     * @param ord the new order
     * @return this descriptor
     */
    public OrdName withOrd(Integer ord) {
        this.ord = ord;
        return this;
    }

    /**
     * Clears the order and returns this descriptor.
     *
     * @return this descriptor
     */
    public OrdName withNoOrd() {
        this.ord = null;
        return this;
    }

    /**
     * Updates the name and returns this descriptor.
     *
     * @param name the new field name
     * @return this descriptor
     */
    public OrdName withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this descriptor.
     *
     * @return this descriptor
     */
    public OrdName withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Returns a cloned descriptor with the given order.
     *
     * @param ord the replacement order
     * @return a cloned descriptor with the order updated
     */
    public OrdName uponOrd(Integer ord) {
        var clone = this.clone();
        clone.ord = ord;
        return clone;
    }

    /**
     * Returns a cloned descriptor without an order.
     *
     * @return a cloned descriptor with the order cleared
     */
    public OrdName uponNoOrd() {
        var clone = this.clone();
        clone.ord = null;
        return clone;
    }

    /**
     * Returns a cloned descriptor with the given name.
     *
     * @param name the replacement field name
     * @return a cloned descriptor with the name updated
     */
    public OrdName uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a name.
     *
     * @return a cloned descriptor with the name cleared
     */
    public OrdName uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Creates a deep clone of this descriptor.
     *
     * @return a deep copy of this descriptor
     */
    @Override
    public OrdName clone() {
        return (OrdName) this.deepClone();
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
     * Deserializes JSON text into an ordered-name descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static OrdName fromChars(String chars) {
        return Base.fromChars(chars, OrdName.class);
    }

}
