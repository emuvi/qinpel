package br.com.pointel.wiz_jarch.data;

public class Match implements Data {

    public Integer ord;
    public String inColumn;
    public String outColumn;

    public Match() {
    }

    public Match(String inColumn) {
        this.inColumn = inColumn;
    }

    public Match(Integer ord, String inColumn) {
        this.ord = ord;
        this.inColumn = inColumn;
    }

    public Match(String inColumn, String outColumn) {
        this.inColumn = inColumn;
        this.outColumn = outColumn;
    }

    public Match(Integer ord, String inColumn, String outColumn) {
        this.ord = ord;
        this.inColumn = inColumn;
        this.outColumn = outColumn;
    }

    public boolean hasOrd() {
        return this.ord != null;
    }

    public boolean hasInColumn() {
        return this.inColumn != null && !this.inColumn.isEmpty();
    }

    public boolean hasOutColumn() {
        return this.outColumn != null && !this.outColumn.isEmpty();
    }

    public Match withOrd(Integer ord) {
        this.ord = ord;
        return this;
    }

    public Match withNoOrd() {
        this.ord = null;
        return this;
    }

    public Match withInColumn(String inColumn) {
        this.inColumn = inColumn;
        return this;
    }

    public Match withNoInColumn() {
        this.inColumn = null;
        return this;
    }

    public Match withOutColumn(String outColumn) {
        this.outColumn = outColumn;
        return this;
    }

    public Match withNoOutColumn() {
        this.outColumn = null;
        return this;
    }

    public Match uponOrd(Integer ord) {
        var clone = this.clone();
        clone.ord = ord;
        return clone;
    }

    public Match uponNoOrd() {
        var clone = this.clone();
        clone.ord = null;
        return clone;
    }

    public Match uponInColumn(String inColumn) {
        var clone = this.clone();
        clone.inColumn = inColumn;
        return clone;
    }

    public Match uponNoInColumn() {
        var clone = this.clone();
        clone.inColumn = null;
        return clone;
    }

    public Match uponOutColumn(String outColumn) {
        var clone = this.clone();
        clone.outColumn = outColumn;
        return clone;
    }

    public Match uponNoOutColumn() {
        var clone = this.clone();
        clone.outColumn = null;
        return clone;
    }

    @Override
    public Match clone() {
        return (Match) this.deepClone();
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

    public static Match fromChars(String chars) {
        return Base.fromChars(chars, Match.class);
    }

}
