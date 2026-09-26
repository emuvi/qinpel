package br.com.pointel.wiz_jarch.data;

import java.util.List;

public class Index implements Data {

    public String name;
    public TableHead tableHead;
    public List<Field> fieldList;

    public Index() {
    }

    public Index(String name) {
        this.name = name;
    }

    public Index(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Index(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public Index(String name, TableHead tableHead) {
        this.name = name;
        this.tableHead = tableHead;
    }

    public Index(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Index(String name, TableHead tableHead, List<Field> fieldList) {
        this.name = name;
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasFieldList() {
        return this.fieldList != null && !this.fieldList.isEmpty();
    }

    public Index withName(String name) {
        this.name = name;
        return this;
    }

    public Index withNoName() {
        this.name = null;
        return this;
    }

    public Index withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Index withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Index withFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    public Index withFieldList(Field... fieldArgs) {
        this.fieldList = List.of(fieldArgs);
        return this;
    }

    public Index withNoFieldList() {
        this.fieldList = null;
        return this;
    }

    public Index uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public Index uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public Index uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Index uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Index uponFieldList(List<Field> fieldList) {
        var clone = this.clone();
        clone.fieldList = fieldList;
        return clone;
    }

    public Index uponFieldList(Field... fieldArgs) {
        var clone = this.clone();
        clone.fieldList = List.of(fieldArgs);
        return clone;
    }

    public Index uponNoFieldList() {
        var clone = this.clone();
        clone.fieldList = null;
        return clone;
    }

    @Override
    public Index clone() {
        return (Index) this.deepClone();
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

    public static Index fromChars(String chars) {
        return Base.fromChars(chars, Index.class);
    }

}
