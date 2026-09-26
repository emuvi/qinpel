package br.com.pointel.wiz_jarch.data;

import java.util.ArrayList;

public class Heads extends ArrayList<TableHead> implements Data {

    public Heads() {
    }

    @Override
    public Heads clone() {
        return (Heads) this.deepClone();
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

    public static Heads fromChars(String chars) {
        return Base.fromChars(chars, Heads.class);
    }

}
