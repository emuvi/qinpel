package br.com.pointel.wiz_jarch.data;

public class ToGetID implements Data {

    public String name;
    public Valued filter;

    public ToGetID() {}

    public ToGetID(String name) {
        this.name = name;
    }

    public ToGetID(Valued filter) {
        this.filter = filter;
    }

    public ToGetID(String name, Valued filter) {
        this.name = name;
        this.filter = filter;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasFilter() {
        return this.filter != null;
    }

    public ToGetID withName(String name) {
        this.name = name;
        return this;
    }

    public ToGetID withNoName() {
        this.name = null;
        return this;
    }

    public ToGetID withFilter(Valued filter) {
        this.filter = filter;
        return this;
    }

    public ToGetID withNoFilter() {
        this.filter = null;
        return this;
    }

    public ToGetID uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public ToGetID uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public ToGetID uponFilter(Valued filter) {
        var clone = this.clone();
        clone.filter = filter;
        return clone;
    }

    public ToGetID uponNoFilter() {
        var clone = this.clone();
        clone.filter = null;
        return clone;
    }

    @Override
    public ToGetID clone() {
        return (ToGetID) this.deepClone();
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

    public static ToGetID fromChars(String chars) {
        return Base.fromChars(chars, ToGetID.class);
    }

}
