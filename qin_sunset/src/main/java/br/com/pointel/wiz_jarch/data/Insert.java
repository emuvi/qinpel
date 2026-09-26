package br.com.pointel.wiz_jarch.data;

import java.util.List;

/**
 * Describes an INSERT operation, including target table, values, and optional
 * generated-id handling.
 */
public class Insert implements Data {

    public TableHead tableHead;
    public List<Valued> valuedList;
    public ToGetID toGetID;

    /**
     * Creates an empty insert descriptor.
     */
    public Insert() {
    }

    /**
     * Creates an insert descriptor for the given table.
     *
     * @param tableHead the table to insert into
     */
    public Insert(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    /**
     * Creates an insert descriptor for the given table and values.
     *
     * @param tableHead the table to insert into
     * @param valuedList the values to insert
     */
    public Insert(TableHead tableHead, List<Valued> valuedList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
    }

    /**
     * Creates an insert descriptor for the given table, values, and id request.
     *
     * @param tableHead the table to insert into
     * @param valuedList the values to insert
     * @param toGetID the id retrieval descriptor
     */
    public Insert(TableHead tableHead, List<Valued> valuedList, ToGetID toGetID) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.toGetID = toGetID;
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
     * Indicates whether an id request is set.
     *
     * @return {@code true} when the id descriptor is non-null; otherwise {@code false}
     */
    public boolean hasToGetID() {
        return this.toGetID != null;
    }

    /**
     * Updates the target table and returns this insert descriptor.
     *
     * @param tableHead the new table
     * @return this insert descriptor
     */
    public Insert withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    /**
     * Clears the target table and returns this insert descriptor.
     *
     * @return this insert descriptor
     */
    public Insert withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    /**
     * Updates the value list and returns this insert descriptor.
     *
     * @param valuedList the new values
     * @return this insert descriptor
     */
    public Insert withValuedList(List<Valued> valuedList) {
        this.valuedList = valuedList;
        return this;
    }

    /**
     * Replaces the value list with the given values.
     *
     * @param valuedArgs the new values
     * @return this insert descriptor
     */
    public Insert withValuedList(Valued... valuedArgs) {
        this.valuedList = List.of(valuedArgs);
        return this;
    }

    /**
     * Clears the value list and returns this insert descriptor.
     *
     * @return this insert descriptor
     */
    public Insert withNoValuedList() {
        this.valuedList = null;
        return this;
    }

    /**
     * Updates the id request and returns this insert descriptor.
     *
     * @param toGetID the new id descriptor
     * @return this insert descriptor
     */
    public Insert withToGetID(ToGetID toGetID) {
        this.toGetID = toGetID;
        return this;
    }

    /**
     * Clears the id request and returns this insert descriptor.
     *
     * @return this insert descriptor
     */
    public Insert withNoToGetID() {
        this.toGetID = null;
        return this;
    }

    /**
     * Returns a cloned insert descriptor with the given table.
     *
     * @param tableHead the replacement table
     * @return a cloned insert descriptor with the table updated
     */
    public Insert uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    /**
     * Returns a cloned insert descriptor without a table.
     *
     * @return a cloned insert descriptor with the table cleared
     */
    public Insert uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    /**
     * Returns a cloned insert descriptor with the given value list.
     *
     * @param valuedList the replacement values
     * @return a cloned insert descriptor with the values updated
     */
    public Insert uponValuedList(List<Valued> valuedList) {
        var clone = this.clone();
        clone.valuedList = valuedList;
        return clone;
    }

    /**
     * Returns a cloned insert descriptor with the given values.
     *
     * @param valuedArgs the replacement values
     * @return a cloned insert descriptor with the values updated
     */
    public Insert uponValuedList(Valued... valuedArgs) {
        var clone = this.clone();
        clone.valuedList = List.of(valuedArgs);
        return clone;
    }

    /**
     * Returns a cloned insert descriptor without values.
     *
     * @return a cloned insert descriptor with the values cleared
     */
    public Insert uponNoValuedList() {
        var clone = this.clone();
        clone.valuedList = null;
        return clone;
    }

    /**
     * Returns a cloned insert descriptor with the given id request.
     *
     * @param toGetID the replacement id descriptor
     * @return a cloned insert descriptor with the id request updated
     */
    public Insert uponToGetID(ToGetID toGetID) {
        var clone = this.clone();
        clone.toGetID = toGetID;
        return clone;
    }

    /**
     * Returns a cloned insert descriptor without an id request.
     *
     * @return a cloned insert descriptor with the id request cleared
     */
    public Insert uponNoToGetID() {
        var clone = this.clone();
        clone.toGetID = null;
        return clone;
    }

    /**
     * Creates a deep clone of this insert descriptor.
     *
     * @return a deep copy of this insert descriptor
     */
    @Override
    public Insert clone() {
        return (Insert) this.deepClone();
    }

    /**
     * Compares this insert descriptor to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this insert descriptor.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this insert descriptor to JSON text.
     *
     * @return the JSON representation of this insert descriptor
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into an insert descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static Insert fromChars(String chars) {
        return Base.fromChars(chars, Insert.class);
    }

    /**
     * Fills the insert values in order using the provided arguments.
     *
     * @param values the values to assign
     * @return this insert descriptor
     * @throws IllegalArgumentException if there are fewer values than insert slots
     */
    public Insert valuedWithValues(Object... values) {
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
     * Returns a cloned insert descriptor with values filled in order.
     *
     * @param values the values to assign
     * @return a cloned insert descriptor with the values updated
     */
    public Insert valuedUponValues(Object... values) {
        var clone = this.clone();
        clone.valuedWithValues(values);
        return clone;
    }

}
