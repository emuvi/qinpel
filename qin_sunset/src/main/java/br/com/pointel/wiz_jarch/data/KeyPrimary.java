package br.com.pointel.wiz_jarch.data;

import java.util.List;

public class KeyPrimary implements Data {

    public String name;
    public List<OrdName> columnList;

    public KeyPrimary() {
    }

    public KeyPrimary(String name) {
        this.name = name;
    }

    public KeyPrimary(List<OrdName> columnList) {
        this.columnList = columnList;
    }

    public KeyPrimary(String name, List<OrdName> columnList) {
        this.name = name;
        this.columnList = columnList;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasColumnList() {
        return this.columnList != null && !this.columnList.isEmpty();
    }

    public KeyPrimary withName(String name) {
        this.name = name;
        return this;
    }

    public KeyPrimary withNoName() {
        this.name = null;
        return this;
    }

    public KeyPrimary withColumnList(List<OrdName> columnList) {
        this.columnList = columnList;
        return this;
    }

    public KeyPrimary withColumnList(OrdName... columns) {
        this.columnList = List.of(columns);
        return this;
    }

    public KeyPrimary withNoColumnList() {
        this.columnList = null;
        return this;
    }

    public KeyPrimary uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public KeyPrimary uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public KeyPrimary uponColumnList(List<OrdName> columnList) {
        var clone = this.clone();
        clone.columnList = columnList;
        return clone;
    }

    public KeyPrimary uponColumnList(OrdName... columns) {
        var clone = this.clone();
        clone.columnList = List.of(columns);
        return clone;
    }

    public KeyPrimary uponNoColumnList() {
        var clone = this.clone();
        clone.columnList = null;
        return clone;
    }

    @Override
    public KeyPrimary clone() {
        return (KeyPrimary) this.deepClone();
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

    public static KeyPrimary fromChars(String chars) {
        return Base.fromChars(chars, KeyPrimary.class);
    }

}
