package br.com.pointel.wiz_jarch.data;

import java.util.TreeSet;

public class DataSetTree<T> extends TreeSet<T> implements Data {
    
    @Override
    public DataSetTree<T> clone() {
        return (DataSetTree<T>) this.deepClone();
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

    public static DataSetTree fromChars(String chars) {
        return Base.fromChars(chars, DataSetTree.class);
    }
    
}
