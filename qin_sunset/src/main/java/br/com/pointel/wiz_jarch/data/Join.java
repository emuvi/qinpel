package br.com.pointel.wiz_jarch.data;

import java.util.List;

public class Join implements Data {

    public TableHead tableHead;
    public String alias;
    public List<Filter> filterList;
    public JoinTies ties;

    public Join() {
    }

    public Join(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Join(TableHead tableHead, String alias) {
        this.tableHead = tableHead;
        this.alias = alias;
    }

    public Join(TableHead tableHead, JoinTies ties) {
        this.tableHead = tableHead;
        this.ties = ties;
    }

    public Join(TableHead tableHead, String alias, JoinTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.ties = ties;
    }

    public Join(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.filterList = filterList;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
    }

    public Join(TableHead tableHead, List<Filter> filterList, JoinTies ties) {
        this.tableHead = tableHead;
        this.filterList = filterList;
        this.ties = ties;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList, JoinTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
        this.ties = ties;
    }

    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    public boolean hasAlias() {
        return this.alias != null && !this.alias.isEmpty();
    }

    public boolean hasFilterList() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    public boolean hasTies() {
        return this.ties != null;
    }

    public Join withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    public Join withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    public Join withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Join withNoAlias() {
        this.alias = null;
        return this;
    }

    public Join withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    public Join withFilterList(Filter... filterArgs) {
        this.filterList = List.of(filterArgs);
        return this;
    }

    public Join withNoFilterList() {
        this.filterList = null;
        return this;
    }

    public Join withTies(JoinTies ties) {
        this.ties = ties;
        return this;
    }

    public Join withNoTies() {
        this.ties = null;
        return this;
    }

    public Join uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    public Join uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    public Join uponAlias(String alias) {
        var clone = this.clone();
        clone.alias = alias;
        return clone;
    }

    public Join uponNoAlias() {
        var clone = this.clone();
        clone.alias = null;
        return clone;
    }

    public Join uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    public Join uponFilterList(Filter... filterArgs) {
        var clone = this.clone();
        clone.filterList = List.of(filterArgs);
        return clone;
    }

    public Join uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    public Join uponTies(JoinTies ties) {
        var clone = this.clone();
        clone.ties = ties;
        return clone;
    }

    public Join uponNoTies() {
        var clone = this.clone();
        clone.ties = null;
        return clone;
    }

    @Override
    public Join clone() {
        return (Join) this.deepClone();
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

    public static Join fromChars(String chars) {
        return Base.fromChars(chars, Join.class);
    }

}
