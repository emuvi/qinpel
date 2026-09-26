package br.com.pointel.wiz_jarch.data;

/**
 * Describes a sort order for a named field.
 */
public class Order implements Data {

    public String name;
    public Boolean desc;

    /**
     * Creates an empty order descriptor.
     */
    public Order() {
    }

    /**
     * Creates an ascending order descriptor for the given name.
     *
     * @param name the field name
     */
    public Order(String name) {
        this.name = name;
    }

    /**
     * Creates an order descriptor for the given name and direction.
     *
     * @param name the field name
     * @param desc whether the order is descending
     */
    public Order(String name, Boolean desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * Indicates whether the name is set.
     *
     * @return {@code true} when the name is non-null; otherwise {@code false}
     */
    public boolean hasName() {
        return this.name != null;
    }

    /**
     * Indicates whether the direction is set.
     *
     * @return {@code true} when the direction is non-null; otherwise {@code false}
     */
    public boolean hasDesc() {
        return this.desc != null;
    }

    /**
     * Updates the name and returns this descriptor.
     *
     * @param name the new field name
     * @return this descriptor
     */
    public Order withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this descriptor.
     *
     * @return this descriptor
     */
    public Order withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the direction and returns this descriptor.
     *
     * @param desc {@code true} for descending order
     * @return this descriptor
     */
    public Order withDesc(Boolean desc) {
        this.desc = desc;
        return this;
    }

    /**
     * Clears the direction and returns this descriptor.
     *
     * @return this descriptor
     */
    public Order withNoDesc() {
        this.desc = null;
        return this;
    }

    /**
     * Returns a cloned descriptor with the given name.
     *
     * @param name the replacement field name
     * @return a cloned descriptor with the name updated
     */
    public Order uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a name.
     *
     * @return a cloned descriptor with the name cleared
     */
    public Order uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned descriptor with the given direction.
     *
     * @param desc the replacement direction
     * @return a cloned descriptor with the direction updated
     */
    public Order uponDesc(Boolean desc) {
        var clone = this.clone();
        clone.desc = desc;
        return clone;
    }

    /**
     * Returns a cloned descriptor without a direction.
     *
     * @return a cloned descriptor with the direction cleared
     */
    public Order uponNoDesc() {
        var clone = this.clone();
        clone.desc = null;
        return clone;
    }

    /**
     * Creates a deep clone of this descriptor.
     *
     * @return a deep copy of this descriptor
     */
    @Override
    public Order clone() {
        return (Order) this.deepClone();
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
     * Deserializes JSON text into an order descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static Order fromChars(String chars) {
        return Base.fromChars(chars, Order.class);
    }

}
