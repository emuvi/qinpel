package br.com.pointel.wiz_jarch.data;

public class ToUpdate implements Data {

    public String base;
    public Update update;

    public ToUpdate() {}

    public ToUpdate(String base) {
        this.base = base;
    }

    public ToUpdate(Update update) {
        this.update = update;
    }

    public ToUpdate(String base, Update update) {
        this.base = base;
        this.update = update;
    }

    public boolean hasBase() {
        return this.base != null && !this.base.isEmpty();
    }

    public boolean hasUpdate() {
        return this.update != null;
    }

    public ToUpdate withBase(String base) {
        this.base = base;
        return this;
    }

    public ToUpdate withNoBase() {
        this.base = null;
        return this;
    }

    public ToUpdate withUpdate(Update update) {
        this.update = update;
        return this;
    }

    public ToUpdate withNoUpdate() {
        this.update = null;
        return this;
    }

    public ToUpdate uponBase(String base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    public ToUpdate uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    public ToUpdate uponUpdate(Update update) {
        var clone = this.clone();
        clone.update = update;
        return clone;
    }

    public ToUpdate uponNoUpdate() {
        var clone = this.clone();
        clone.update = null;
        return clone;
    }

    @Override
    public ToUpdate clone() {
        return (ToUpdate) this.deepClone();
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

    public static ToUpdate fromChars(String chars) {
        return Base.fromChars(chars, ToUpdate.class);
    }

    public Registry getRegistry() {
        return new Registry(base, update.tableHead);
    }

}
