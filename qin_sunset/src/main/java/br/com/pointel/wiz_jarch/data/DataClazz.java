package br.com.pointel.wiz_jarch.data;

import br.com.pointel.wiz_jarch.mage.WizData;

/**
 * Stores serialized data together with the canonical class name used to
 * reconstruct it.
 */
public class DataClazz implements Data {

    /** The serialized data string. */
    public String data;
    /** The canonical class name. */
    public String clazz;

    /**
     * Creates an empty data/class pair.
     */
    public DataClazz() {
    }

    /**
     * Creates a data/class pair from a value object.
     *
     * @param value the source value
     */
    public DataClazz(Object value) {
        this.data = value == null ? null : WizData.toJson(value);
        this.clazz = value == null ? null : value.getClass().getCanonicalName();
    }

    /**
     * Creates a data/class pair from raw values.
     *
     * @param data the serialized data
     * @param clazz the canonical class name
     */
    public DataClazz(String data, String clazz) {
        this.data = data;
        this.clazz = clazz;
    }

    /**
     * Indicates whether serialized data is present.
     *
     * @return {@code true} when data is non-null; otherwise {@code false}
     */
    public boolean hasData() {
        return this.data != null;
    }

    /**
     * Indicates whether the class name is present.
     *
     * @return {@code true} when the class name is non-null; otherwise {@code false}
     */
    public boolean hasClazz() {
        return this.clazz != null;
    }

    /**
     * Updates the serialized data and returns this container.
     *
     * @param data the new serialized data
     * @return this container
     */
    public DataClazz withData(String data) {
        this.data = data;
        return this;
    }

    /**
     * Clears the serialized data and returns this container.
     *
     * @return this container
     */
    public DataClazz withNoData() {
        this.data = null;
        return this;
    }

    /**
     * Updates the class name and returns this container.
     *
     * @param clazz the new canonical class name
     * @return this container
     */
    public DataClazz withClazz(String clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * Clears the class name and returns this container.
     *
     * @return this container
     */
    public DataClazz withNoClazz() {
        this.clazz = null;
        return this;
    }

    /**
     * Returns a cloned container with the given serialized data.
     *
     * @param data the replacement serialized data
     * @return a cloned container with the data updated
     */
    public DataClazz uponData(String data) {
        var clone = this.clone();
        clone.data = data;
        return clone;
    }

    /**
     * Returns a cloned container without serialized data.
     *
     * @return a cloned container with the data cleared
     */
    public DataClazz uponNoData() {
        var clone = this.clone();
        clone.data = null;
        return clone;
    }

    /**
     * Returns a cloned container with the given class name.
     *
     * @param clazz the replacement canonical class name
     * @return a cloned container with the class updated
     */
    public DataClazz uponClazz(String clazz) {
        var clone = this.clone();
        clone.clazz = clazz;
        return clone;
    }

    /**
     * Returns a cloned container without a class name.
     *
     * @return a cloned container with the class cleared
     */
    public DataClazz uponNoClazz() {
        var clone = this.clone();
        clone.clazz = null;
        return clone;
    }

    /**
     * Creates a deep clone of this container.
     *
     * @return a deep copy of this container
     */
    @Override
    public DataClazz clone() {
        return (DataClazz) this.deepClone();
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
     * Deserializes JSON text into a data/class pair.
     *
     * @param chars the JSON text to parse
     * @return the parsed container
     */
    public static DataClazz fromChars(String chars) {
        return Base.fromChars(chars, DataClazz.class);
    }

    /**
     * Reconstructs the stored value using the recorded class name.
     *
     * @return the reconstructed value, or {@code null}
     * @throws Exception if the class cannot be loaded or the value cannot be parsed
     */
    public Object getValue() throws Exception {
        return this.data == null ? null : WizData.fromJson(this.data, Class.forName( this.clazz));
    }

    /**
     * Reconstructs the stored value and converts it to the requested type.
     *
     * @param clazz the target type
     * @param <T> the result type
     * @return the reconstructed and converted value
     * @throws Exception if reconstruction or conversion fails
     */
    public <T> T getValueOn(Class<T> clazz) throws Exception {
        return WizData.getOn(getValue(), clazz);
    }

}
