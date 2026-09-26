package br.com.pointel.wiz_jarch.data;

import java.util.List;

/**
 * Describes an UPDATE operation, including target table, assigned values,
 * filters, and optional row limit.
 */
public class Update implements Data {

    public TableHead tableHead;
    public List<Valued> valuedList;
    public List<Filter> filterList;
    public Integer limit;

    /**
     * Creates an empty update descriptor.
     */
    public Update() {
    }

    /**
     * Creates an update descriptor for the given table.
     *
     * @param tableHead the table to update
     */
    public Update(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    /**
     * Creates an update descriptor for the given table and values.
     *
     * @param tableHead the table to update
     * @param valuedList the values to assign
     */
    public Update(TableHead tableHead, List<Valued> valuedList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
    }

    /**
     * Creates an update descriptor with values and filters.
     *
     * @param tableHead the table to update
     * @param valuedList the values to assign
     * @param filterList the filter clauses
     */
    public Update(TableHead tableHead, List<Valued> valuedList, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.filterList = filterList;
    }

    /**
     * Creates an update descriptor with values, filters, and a row limit.
     *
     * @param tableHead the table to update
     * @param valuedList the values to assign
     * @param filterList the filter clauses
     * @param limit the row limit
     */
    public Update(TableHead tableHead, List<Valued> valuedList, List<Filter> filterList, Integer limit) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.filterList = filterList;
        this.limit = limit;
    }

    /**
     * Indicates whether a table is set.
     *
     * @return {@code true} when the table is non-null; otherwise {@code false}
     */
    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    /**
     * Indicates whether any values are present.
     *
     * @return {@code true} when the value list is non-empty; otherwise {@code false}
     */
    public boolean hasValuedList() {
        return this.valuedList != null && !this.valuedList.isEmpty();
    }

    /**
     * Indicates whether any filters are present.
     *
     * @return {@code true} when the filter list is non-empty; otherwise {@code false}
     */
    public boolean hasFilterList() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    /**
     * Indicates whether a row limit is set.
     *
     * @return {@code true} when the limit is non-null; otherwise {@code false}
     */
    public boolean hasLimit() {
        return this.limit != null;
    }

    /**
     * Updates the target table and returns this update descriptor.
     *
     * @param tableHead the new table
     * @return this update descriptor
     */
    public Update withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    /**
     * Clears the target table and returns this update descriptor.
     *
     * @return this update descriptor
     */
    public Update withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    /**
     * Updates the value list and returns this update descriptor.
     *
     * @param valuedList the new values
     * @return this update descriptor
     */
    public Update withValuedList(List<Valued> valuedList) {
        this.valuedList = valuedList;
        return this;
    }

    /**
     * Replaces the value list with the given values.
     *
     * @param valuedArgs the new values
     * @return this update descriptor
     */
    public Update withValuedList(Valued... valuedArgs) {
        this.valuedList = List.of(valuedArgs);
        return this;
    }

    /**
     * Clears the value list and returns this update descriptor.
     *
     * @return this update descriptor
     */
    public Update withNoValuedList() {
        this.valuedList = null;
        return this;
    }

    /**
     * Updates the filter list and returns this update descriptor.
     *
     * @param filterList the new filter clauses
     * @return this update descriptor
     */
    public Update withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    /**
     * Replaces the filter list with the given filters.
     *
     * @param filterArgs the new filter clauses
     * @return this update descriptor
     */
    public Update withFilterList(Filter... filterArgs) {
        this.filterList = List.of(filterArgs);
        return this;
    }

    /**
     * Clears the filter list and returns this update descriptor.
     *
     * @return this update descriptor
     */
    public Update withNoFilterList() {
        this.filterList = null;
        return this;
    }

    /**
     * Updates the row limit and returns this update descriptor.
     *
     * @param limit the new limit
     * @return this update descriptor
     */
    public Update withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Clears the row limit and returns this update descriptor.
     *
     * @return this update descriptor
     */
    public Update withNoLimit() {
        this.limit = null;
        return this;
    }

    /**
     * Returns a cloned update descriptor with the given table.
     *
     * @param tableHead the replacement table
     * @return a cloned descriptor with the table updated
     */
    public Update uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    /**
     * Returns a cloned update descriptor without a table.
     *
     * @return a cloned descriptor with the table cleared
     */
    public Update uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    /**
     * Returns a cloned update descriptor with the given value list.
     *
     * @param valuedList the replacement values
     * @return a cloned descriptor with the values updated
     */
    public Update uponValuedList(List<Valued> valuedList) {
        var clone = this.clone();
        clone.valuedList = valuedList;
        return clone;
    }

    /**
     * Returns a cloned update descriptor with the given values.
     *
     * @param valuedArgs the replacement values
     * @return a cloned descriptor with the values updated
     */
    public Update uponValuedList(Valued... valuedArgs) {
        var clone = this.clone();
        clone.valuedList = List.of(valuedArgs);
        return clone;
    }

    /**
     * Returns a cloned update descriptor without values.
     *
     * @return a cloned descriptor with the values cleared
     */
    public Update uponNoValuedList() {
        var clone = this.clone();
        clone.valuedList = null;
        return clone;
    }

    /**
     * Returns a cloned update descriptor with the given filter list.
     *
     * @param filterList the replacement filter list
     * @return a cloned descriptor with the filters updated
     */
    public Update uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    /**
     * Returns a cloned update descriptor with the given filters.
     *
     * @param filterArgs the replacement filters
     * @return a cloned descriptor with the filters updated
     */
    public Update uponFilterList(Filter... filterArgs) {
        var clone = this.clone();
        clone.filterList = List.of(filterArgs);
        return clone;
    }

    /**
     * Returns a cloned update descriptor without filters.
     *
     * @return a cloned descriptor with the filter list cleared
     */
    public Update uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    /**
     * Returns a cloned update descriptor with the given row limit.
     *
     * @param limit the replacement limit
     * @return a cloned descriptor with the limit updated
     */
    public Update uponLimit(Integer limit) {
        var clone = this.clone();
        clone.limit = limit;
        return clone;
    }

    /**
     * Returns a cloned update descriptor without a row limit.
     *
     * @return a cloned descriptor with the limit cleared
     */
    public Update uponNoLimit() {
        var clone = this.clone();
        clone.limit = null;
        return clone;
    }

    /**
     * Creates a deep clone of this update descriptor.
     *
     * @return a deep copy of this update descriptor
     */
    @Override
    public Update clone() {
        return (Update) this.deepClone();
    }

    /**
     * Compares this update descriptor to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this update descriptor.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this update descriptor to JSON text.
     *
     * @return the JSON representation of this update descriptor
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into an update descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static Update fromChars(String chars) {
        return Base.fromChars(chars, Update.class);
    }

    /**
     * Fills the update values in order using the provided arguments.
     *
     * @param values the values to assign
     * @return this update descriptor
     * @throws IllegalArgumentException if there are fewer values than update slots
     */
    public Update valuedWithValues(Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        if (this.valuedList == null || this.valuedList.size() < values.length) {
            throw new IllegalArgumentException("Valued list is null or has less elements than values");
        }
        for (int i = 0; i < values.length; i++) {
            this.valuedList.get(i).value = values[i];
        }
        return this;
    }

    /**
     * Returns a cloned update descriptor with values filled in order.
     *
     * @param values the values to assign
     * @return a cloned update descriptor with the values updated
     */
    public Update valuedUponValues(Object... values) {
        var clone = this.clone();
        clone.valuedWithValues(values);
        return clone;
    }

    /**
     * Fills the filter values in order using the provided arguments.
     *
     * @param values the values to assign
     * @return this update descriptor
     * @throws IllegalArgumentException if there are fewer filters than values
     */
    public Update filterWithValues(Object... values) {
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

    /**
     * Returns a cloned update descriptor with filter values filled in order.
     *
     * @param values the values to assign
     * @return a cloned update descriptor with the filter values updated
     */
    public Update filterUponValues(Object... values) {
        var clone = this.clone();
        clone.filterWithValues(values);
        return clone;
    }

}
