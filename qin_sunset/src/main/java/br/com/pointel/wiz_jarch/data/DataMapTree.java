package br.com.pointel.wiz_jarch.data;

import java.util.TreeMap;

/**
 * A TreeMap implementation that implements the Data interface.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DataMapTree<K, V> extends TreeMap<K, V> implements Data {
    
    /**
     * Constructs an empty DataMapTree.
     */
    public DataMapTree() {
        super();
    }

    @Override
    public DataMapTree<K, V> clone() {
        return (DataMapTree<K, V>) this.deepClone();
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
     * Deserializes JSON text into a DataMapTree.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataMapTree
     */
    public static DataMapTree fromChars(String chars) {
        return Base.fromChars(chars, DataMapTree.class);
    }

}
