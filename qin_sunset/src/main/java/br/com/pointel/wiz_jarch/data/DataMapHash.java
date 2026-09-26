package br.com.pointel.wiz_jarch.data;

import java.util.HashMap;

/**
 * A HashMap implementation that implements the Data interface.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DataMapHash<K, V> extends HashMap<K, V> implements Data {
    
    /**
     * Constructs an empty DataMapHash.
     */
    public DataMapHash() {
        super();
    }

    @Override
    public DataMapHash<K, V> clone() {
        return (DataMapHash<K, V>) this.deepClone();
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
     * Deserializes JSON text into a DataMapHash.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataMapHash
     */
    public static DataMapHash fromChars(String chars) {
        return Base.fromChars(chars, DataMapHash.class);
    }

}
