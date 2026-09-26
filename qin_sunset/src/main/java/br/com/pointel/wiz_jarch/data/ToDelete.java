package br.com.pointel.wiz_jarch.data;

public class ToDelete implements Data {

    public String base;
    public Delete delete;

    public ToDelete() {
    }

    public ToDelete(String base) {
        this.base = base;
    }

    public ToDelete(Delete delete) {
        this.delete = delete;
    }

    public ToDelete(String base, Delete delete) {
        this.base = base;
        this.delete = delete;
    }

    public boolean hasBase() {
        return this.base != null && !this.base.isEmpty();
    }

    public boolean hasDelete() {
        return this.delete != null;
    }

    public ToDelete withBase(String base) {
        this.base = base;
        return this;
    }

    public ToDelete withNoBase() {
        this.base = null;
        return this;
    }

    public ToDelete withDelete(Delete delete) {
        this.delete = delete;
        return this;
    }

    public ToDelete withNoDelete() {
        this.delete = null;
        return this;
    }

    public ToDelete uponBase(String base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    public ToDelete uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    public ToDelete uponDelete(Delete delete) {
        var clone = this.clone();
        clone.delete = delete;
        return clone;
    }

    public ToDelete uponNoDelete() {
        var clone = this.clone();
        clone.delete = null;
        return clone;
    }

    @Override
    public ToDelete clone() {
        return (ToDelete) this.deepClone();
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

    public static ToDelete fromChars(String chars) {
        return Base.fromChars(chars, ToDelete.class);
    }

    public Registry getRegistry() {
        return new Registry(base, delete.tableHead);
    }

}
