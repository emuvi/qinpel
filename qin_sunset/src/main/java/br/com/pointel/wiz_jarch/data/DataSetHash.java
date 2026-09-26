package br.com.pointel.wiz_jarch.data;

import java.util.HashSet;

/**
 * A HashSet implementation that implements the Data interface.
 *
 * @param <T> the element type
 */
public class DataSetHash<T> extends HashSet<T> implements Data {
    
    /**
     * Constructs an empty DataSetHash.
     */
    public DataSetHash() {
        super();
    }

    @Override
    public DataSetHash<T> clone() {
        return (DataSetHash<T>) this.deepClone();
    }
    
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a DataSetHash.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataSetHash
     */
    public static DataSetHash fromChars(String chars) {
        return Base.fromChars(chars, DataSetHash.class);
    }
    
}
