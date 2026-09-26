package br.com.pointel.wiz_jarch.data;

import java.util.LinkedHashSet;

public class DataSetLinked<T> extends LinkedHashSet<T> implements Data {

    @Override
    public DataSetLinked<T> clone() {
        return (DataSetLinked<T>) this.deepClone();
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

    public static DataSetLinked fromChars(String chars) {
        return Base.fromChars(chars, DataSetLinked.class);
    }
    
}
