package br.com.pointel.wiz_jarch.data;

import java.nio.charset.StandardCharsets;

/**
 * Stores a password as UTF-8 encoded bytes.
 */
public class Pass implements Data {
    
    public byte[] data;

    /**
     * Creates an empty password container.
     */
    public Pass() {
        this.data = null;
    }

    /**
     * Creates a password container from raw bytes.
     *
     * @param data the password bytes
     */
    public Pass(byte[] data) {
        this.data = data;
    }

    /**
     * Creates a password container from a UTF-8 string.
     *
     * @param pass the password text
     */
    public Pass(String pass) {
        this.data = pass.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Indicates whether password data is present.
     *
     * @return {@code true} when the byte array is non-empty; otherwise {@code false}
     */
    public boolean hasData() {
        return this.data != null && this.data.length > 0;
    }

    /**
     * Replaces the password bytes.
     *
     * @param data the new password bytes
     * @return this container
     */
    public Pass withData(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Replaces the password text using UTF-8 encoding.
     *
     * @param pass the new password text
     * @return this container
     */
    public Pass withData(String pass) {
        this.data = pass != null ? pass.getBytes(StandardCharsets.UTF_8) : null;
        return this;
    }

    /**
     * Clears the password data.
     *
     * @return this container
     */
    public Pass withNoData() {
        this.data = null;
        return this;
    }

    /**
     * Returns a cloned container with the given password bytes.
     *
     * @param data the replacement password bytes
     * @return a cloned container with the bytes updated
     */
    public Pass uponData(byte[] data) {
        Pass clone = this.clone();
        clone.data = data;
        return clone;
    }

    /**
     * Returns a cloned container with the given password text.
     *
     * @param pass the replacement password text
     * @return a cloned container with the bytes updated
     */
    public Pass uponData(String pass) {
        Pass clone = this.clone();
        clone.data = pass != null ? pass.getBytes(StandardCharsets.UTF_8) : null;
        return clone;
    }

    /**
     * Returns a cloned container without password data.
     *
     * @return a cloned container with the bytes cleared
     */
    public Pass uponNoData() {
        Pass clone = this.clone();
        clone.data = null;
        return clone;
    }

    /**
     * Creates a deep clone of this password container.
     *
     * @return a deep copy of this container
     */
    @Override
    public Pass clone() {
        return (Pass) this.deepClone();
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
     * Deserializes JSON text into a password container.
     *
     * @param chars the JSON text to parse
     * @return the parsed container
     */
    public static Pass fromChars(String chars) {
        return Base.fromChars(chars, Pass.class);
    }

    /**
     * Returns the password as a UTF-8 string.
     *
     * @return the password text, or {@code null}
     */
    public String getPass() {
        return this.data != null ? new String(this.data, StandardCharsets.UTF_8) : null;
    }
    
}
