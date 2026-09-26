package br.com.pointel.wiz_jarch.data;

public class ToInsert implements Data {

    public String base;
    public Insert insert;

    public ToInsert() {
    }

    public ToInsert(String base) {
        this.base = base;
    }

    public ToInsert(Insert insert) {
        this.insert = insert;
    }

    public ToInsert(String base, Insert insert) {
        this.base = base;
        this.insert = insert;
    }

    public boolean hasBase() {
        return this.base != null;
    }

    public boolean hasInsert() {
        return this.insert != null;
    }

    public ToInsert withBase(String base) {
        this.base = base;
        return this;
    }

    public ToInsert withNoBase() {
        this.base = null;
        return this;
    }

    public ToInsert withInsert(Insert insert) {
        this.insert = insert;
        return this;
    }

    public ToInsert withNoInsert() {
        this.insert = null;
        return this;
    }

    public ToInsert uponBase(String base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    public ToInsert uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    public ToInsert uponInsert(Insert insert) {
        var clone = this.clone();
        clone.insert = insert;
        return clone;
    }

    public ToInsert uponNoInsert() {
        var clone = this.clone();
        clone.insert = null;
        return clone;
    }

    @Override
    public ToInsert clone() {
        return (ToInsert) this.deepClone();
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

    public static ToInsert fromChars(String chars) {
        return Base.fromChars(chars, ToInsert.class);
    }

    public Registry getRegistry() {
        return new Registry(base, insert.tableHead);
    }

}
