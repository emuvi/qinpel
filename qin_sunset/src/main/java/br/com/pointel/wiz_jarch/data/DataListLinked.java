package br.com.pointel.wiz_jarch.data;

import java.util.LinkedList;

/**
 * A LinkedList implementation that implements the Data interface.
 *
 * @param <T> the element type
 */
public class DataListLinked<T> extends LinkedList<T> implements Data {

    /**
     * Constructs an empty DataListLinked.
     */
    public DataListLinked() {
        super();
    }

    @Override
    public DataListLinked<T> clone() {
        return (DataListLinked<T>) this.deepClone();
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
     * Deserializes JSON text into a DataListLinked.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataListLinked
     */
    public static DataListLinked fromChars(String chars) {
        return Base.fromChars(chars, DataListLinked.class);
    }
    
}
