package br.com.pointel.wiz_jarch.data;

import java.util.List;

public class KeyForeign implements Data {

    public String inName;
    public String outName;
    public TableHead outTableHead;
    public List<Match> matchList;

    public KeyForeign() {
    }

    public KeyForeign(String inName) {
        this.inName = inName;
    }

    public KeyForeign(String inName, String outName) {
        this.inName = inName;
        this.outName = outName;
    }

    public KeyForeign(String inName, String outName, TableHead outTableHead) {
        this.inName = inName;
        this.outName = outName;
        this.outTableHead = outTableHead;
    }

    public KeyForeign(String inName, TableHead outTableHead) {
        this.inName = inName;
        this.outTableHead = outTableHead;
    }

    public KeyForeign(TableHead outTableHead) {
        this.outTableHead = outTableHead;
    }

    public KeyForeign(String inName, TableHead outTableHead, List<Match> matchList) {
        this.inName = inName;
        this.outTableHead = outTableHead;
        this.matchList = matchList;
    }

    public KeyForeign(TableHead outTableHead, List<Match> matchList) {
        this.outTableHead = outTableHead;
        this.matchList = matchList;
    }

    public KeyForeign(String inName, String outName, TableHead outTableHead, List<Match> matchList) {
        this.inName = inName;
        this.outName = outName;
        this.outTableHead = outTableHead;
        this.matchList = matchList;
    }

    public boolean hasInName() {
        return this.inName != null;
    }

    public boolean hasOutName() {
        return this.outName != null;
    }

    public boolean hasOutTableHead() {
        return this.outTableHead != null;
    }

    public boolean hasMatchList() {
        return this.matchList != null && !this.matchList.isEmpty();
    }

    public KeyForeign withInName(String inName) {
        this.inName = inName;
        return this;
    }

    public KeyForeign withNoInName() {
        this.inName = null;
        return this;
    }

    public KeyForeign withOutName(String outName) {
        this.outName = outName;
        return this;
    }

    public KeyForeign withNoOutName() {
        this.outName = null;
        return this;
    }

    public KeyForeign withOutTableHead(TableHead outTableHead) {
        this.outTableHead = outTableHead;
        return this;
    }

    public KeyForeign withNoOutTableHead() {
        this.outTableHead = null;
        return this;
    }

    public KeyForeign withMatchList(List<Match> matchList) {
        this.matchList = matchList;
        return this;
    }

    public KeyForeign withMatchList(Match... matches) {
        this.matchList = List.of(matches);
        return this;
    }

    public KeyForeign withNoMatchList() {
        this.matchList = null;
        return this;
    }

    public KeyForeign uponInName(String inName) {
        var clone = this.clone();
        clone.inName = inName;
        return clone;
    }

    public KeyForeign uponNoInName() {
        var clone = this.clone();
        clone.inName = null;
        return clone;
    }

    public KeyForeign uponOutName(String outName) {
        var clone = this.clone();
        clone.outName = outName;
        return clone;
    }

    public KeyForeign uponNoOutName() {
        var clone = this.clone();
        clone.outName = null;
        return clone;
    }

    public KeyForeign uponOutTableHead(TableHead outTableHead) {
        var clone = this.clone();
        clone.outTableHead = outTableHead;
        return clone;
    }

    public KeyForeign uponNoOutTableHead() {
        var clone = this.clone();
        clone.outTableHead = null;
        return clone;
    }

    public KeyForeign uponMatchList(List<Match> matchList) {
        var clone = this.clone();
        clone.matchList = matchList;
        return clone;
    }

    public KeyForeign uponMatchList(Match... matches) {
        var clone = this.clone();
        clone.matchList = List.of(matches);
        return clone;
    }

    public KeyForeign uponNoMatchList() {
        var clone = this.clone();
        clone.matchList = null;
        return clone;
    }

    @Override
    public KeyForeign clone() {
        return (KeyForeign) this.deepClone();
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

    public static KeyForeign fromChars(String chars) {
        return Base.fromChars(chars, KeyForeign.class);
    }

}
