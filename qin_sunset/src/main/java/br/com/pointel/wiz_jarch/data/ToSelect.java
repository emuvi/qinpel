package br.com.pointel.wiz_jarch.data;

public class ToSelect implements Data {

    public String base;
    public Select select;

    public ToSelect() {
    }

    public ToSelect(String base) {
        this.base = base;
    }

    public ToSelect(Select select) {
        this.select = select;
    }

    public ToSelect(String base, Select select) {
        this.base = base;
        this.select = select;
    }

    public boolean hasBase() {
        return this.base != null && !this.base.isEmpty();
    }

    public boolean hasSelect() {
        return this.select != null;
    }

    public ToSelect withBase(String base) {
        this.base = base;
        return this;
    }

    public ToSelect withNoBase() {
        this.base = null;
        return this;
    }

    public ToSelect withSelect(Select select) {
        this.select = select;
        return this;
    }

    public ToSelect withNoSelect() {
        this.select = null;
        return this;
    }

    public ToSelect uponBase(String base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    public ToSelect uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    public ToSelect uponSelect(Select select) {
        var clone = this.clone();
        clone.select = select;
        return clone;
    }

    public ToSelect uponNoSelect() {
        var clone = this.clone();
        clone.select = null;
        return clone;
    }

    @Override
    public ToSelect clone() {
        return (ToSelect) this.deepClone();
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

    public static ToSelect fromChars(String chars) {
        return Base.fromChars(chars, ToSelect.class);
    }

    public Registry getRegistry() {
        return new Registry(base, select.tableHead);
    }

}
