package br.com.pointel.wiz_jarch.data;

import java.util.LinkedHashMap;

/**
 * A LinkedHashMap implementation that implements the Data interface.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DataMapLinked<K, V> extends LinkedHashMap<K, V> implements Data {
    
    /**
     * Constructs an empty DataMapLinked.
     */
    public DataMapLinked() {
        super();
    }

    @Override
    public DataMapLinked<K, V> clone() {
        return (DataMapLinked<K, V>) this.deepClone();
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
     * Deserializes JSON text into a DataMapLinked.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataMapLinked
     */
    public static DataMapLinked fromChars(String chars) {
        return Base.fromChars(chars, DataMapLinked.class);
    }

}
