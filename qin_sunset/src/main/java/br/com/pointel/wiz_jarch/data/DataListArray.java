package br.com.pointel.wiz_jarch.data;

import java.util.ArrayList;

/**
 * An ArrayList implementation that implements the Data interface.
 *
 * @param <T> the element type
 */
public class DataListArray<T> extends ArrayList<T> implements Data {

    /**
     * Constructs an empty DataListArray.
     */
    public DataListArray() {
        super();
    }

    @Override
    public DataListArray<T> clone() {
        return (DataListArray<T>) this.deepClone();
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
     * Deserializes JSON text into a DataListArray.
     *
     * @param chars the JSON text to parse
     * @return the parsed DataListArray
     */
    public static DataListArray fromChars(String chars) {
        return Base.fromChars(chars, DataListArray.class);
    }
    
}
