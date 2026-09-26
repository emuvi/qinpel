package br.com.pointel.wiz_jarch.data;

import org.apache.commons.collections4.list.TreeList;

/**
 * A TreeList implementation that implements the Data interface.
 *
 * @param <T> the element type
 */
public class DataListTree<T> extends TreeList<T> implements Data {
    
    /**
     * Constructs an empty DataListTree.
     */
    public DataListTree() {
        super();
    }

    @Override
    public DataListTree<T> clone() {
        return (DataListTree<T>) this.deepClone();
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
     * Deserializes JSON text into a DataListTree.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataListTree
     */
    public static DataListTree fromChars(String chars) {
        return Base.fromChars(chars, DataListTree.class);
    }
    
}
