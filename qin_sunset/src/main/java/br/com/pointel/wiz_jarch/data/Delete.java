package br.com.pointel.wiz_jarch.data;

import java.util.List;

public class Delete implements Data {

    public TableHead tableHead;
    public List<Filter> filterList;
    public Integer limit;

    public Delete() {
    }

    public Delete(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Delete(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.filterList = filterList;
    }

    public Delete(TableHead tableHead, List<Filter> filterList, Integer limit) {
        this.tableHead = tableHead;
        this.filterList = filterList;
        this.limit = limit;
    }

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasFilterList() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public Delete withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Delete withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Delete withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    public Delete withFilterList(Filter... filterArgs) {
        this.filterList = List.of(filterArgs);
        return this;
    }

    public Delete withNoFilterList() {
        this.filterList = null;
        return this;
    }

    public Delete withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Delete withNoLimit() {
        this.limit = null;
        return this;
    }

    public Delete uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Delete uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Delete uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    public Delete uponFilterList(Filter... filterArgs) {
        var clone = this.clone();
        clone.filterList = List.of(filterArgs);
        return clone;
    }

    public Delete uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    public Delete uponLimit(Integer limit) {
        var clone = this.clone();
        clone.limit = limit;
        return clone;
    }

    public Delete uponNoLimit() {
        var clone = this.clone();
        clone.limit = null;
        return clone;
    }

    @Override
    public Delete clone() {
        return (Delete) this.deepClone();
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

    public static Delete fromChars(String chars) {
        return Base.fromChars(chars, Delete.class);
    }

    public Delete filterWithValues(Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        if (this.filterList == null || this.filterList.size() < values.length) {
            throw new IllegalArgumentException("Filter list is null or has less elements than values");
        }
        for (int i = 0; i < values.length; i++) {
            this.filterList.get(i).valued.value = values[i];
        }
        return this;
    }

    public Delete filterUponValues(Object... values) {
        var clone = this.clone();
        clone.filterWithValues(values);
        return clone;
    }

}
