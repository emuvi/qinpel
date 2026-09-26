package br.com.pointel.wiz_jarch.data;

public class Typed implements Data {

    public String name;
    public Nature type;
    public String alias;

    public Typed() {
    }

    public Typed(String name) {
        this.name = name;
    }

    public Typed(String name, Nature type) {
        this.name = name;
        this.type = type;
    }

    public Typed(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Typed(String name, Nature type, String alias) {
        this.name = name;
        this.type = type;
        this.alias = alias;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasType() {
        return this.type != null;
    }

    public boolean hasAlias() {
        return this.alias != null && !this.alias.isEmpty();
    }

    public Typed withName(String name) {
        this.name = name;
        return this;
    }

    public Typed withNoName() {
        this.name = null;
        return this;
    }

    public Typed withType(Nature type) {
        this.type = type;
        return this;
    }

    public Typed withNoType() {
        this.type = null;
        return this;
    }

    public Typed withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Typed withNoAlias() {
        this.alias = null;
        return this;
    }

    public Typed uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public Typed uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public Typed uponType(Nature type) {
        var clone = this.clone();
        clone.type = type;
        return clone;
    }

    public Typed uponNoType() {
        var clone = this.clone();
        clone.type = null;
        return clone;
    }

    public Typed uponAlias(String alias) {
        var clone = this.clone();
        clone.alias = alias;
        return clone;
    }

    public Typed uponNoAlias() {
        var clone = this.clone();
        clone.alias = null;
        return clone;
    }

    @Override
    public Typed clone() {
        return (Typed) this.deepClone();
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

    public static Valued fromChars(String chars) {
        return Base.fromChars(chars, Valued.class);
    }
    
}
