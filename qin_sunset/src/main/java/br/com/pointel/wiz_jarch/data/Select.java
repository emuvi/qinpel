package br.com.pointel.wiz_jarch.data;

import java.sql.ResultSet;
import java.util.List;

import br.com.pointel.wiz_jarch.mage.WizBased;

/**
 * Describes a SELECT query, including fields, joins, filters, ordering, and
 * pagination.
 */
public class Select implements Data {

    public TableHead tableHead;
    public List<Typed> fieldList;
    public List<Join> joinList;
    public List<Filter> filterList;
    public List<Order> orderList;
    public Integer offset;
    public Integer limit;

    /**
     * Creates an empty select descriptor.
     */
    public Select() {
    }

    /**
     * Creates a select descriptor for the given table.
     *
     * @param tableHead the table to query
     */
    public Select(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    /**
     * Creates a select descriptor for the given table and field list.
     *
     * @param tableHead the table to query
     * @param fieldList the selected fields
     */
    public Select(TableHead tableHead, List<Typed> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    /**
     * Creates a select descriptor with joins.
     *
     * @param tableHead the table to query
     * @param fieldList the selected fields
     * @param joinList the join clauses
     */
    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
    }

    /**
     * Creates a select descriptor with joins and filters.
     *
     * @param tableHead the table to query
     * @param fieldList the selected fields
     * @param joinList the join clauses
     * @param filterList the filter clauses
     */
    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
    }

    /**
     * Creates a select descriptor with joins, filters, and ordering.
     *
     * @param tableHead the table to query
     * @param fieldList the selected fields
     * @param joinList the join clauses
     * @param filterList the filter clauses
     * @param orderList the ordering clauses
     */
    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList, List<Order> orderList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
    }

    /**
     * Creates a select descriptor with paging offset.
     *
     * @param tableHead the table to query
     * @param fieldList the selected fields
     * @param joinList the join clauses
     * @param filterList the filter clauses
     * @param orderList the ordering clauses
     * @param offset the result offset
     */
    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList, List<Order> orderList, Integer offset) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
        this.offset = offset;
    }

    /**
     * Creates a select descriptor with paging offset and limit.
     *
     * @param tableHead the table to query
     * @param fieldList the selected fields
     * @param joinList the join clauses
     * @param filterList the filter clauses
     * @param orderList the ordering clauses
     * @param offset the result offset
     * @param limit the result limit
     */
    public Select(TableHead tableHead, List<Typed> fieldList, List<Join> joinList, List<Filter> filterList, List<Order> orderList, Integer offset, Integer limit) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
        this.offset = offset;
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
     * Indicates whether any fields are selected.
     *
     * @return {@code true} when the field list is non-empty; otherwise {@code false}
     */
    public boolean hasFieldList() {
        return this.fieldList != null && !this.fieldList.isEmpty();
    }

    /**
     * Indicates whether any joins are present.
     *
     * @return {@code true} when the join list is non-empty; otherwise {@code false}
     */
    public boolean hasJoinList() {
        return this.joinList != null && !this.joinList.isEmpty();
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
     * Indicates whether any ordering clauses are present.
     *
     * @return {@code true} when the order list is non-empty; otherwise {@code false}
     */
    public boolean hasOrderList() {
        return this.orderList != null && !this.orderList.isEmpty();
    }

    /**
     * Indicates whether an offset is set.
     *
     * @return {@code true} when the offset is non-null; otherwise {@code false}
     */
    public boolean hasOffset() {
        return this.offset != null;
    }

    /**
     * Indicates whether a limit is set.
     *
     * @return {@code true} when the limit is non-null; otherwise {@code false}
     */
    public boolean hasLimit() {
        return this.limit != null;
    }

    /**
     * Updates the table and returns this select descriptor.
     *
     * @param tableHead the new table
     * @return this select descriptor
     */
    public Select withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    /**
     * Clears the table and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    /**
     * Updates the field list and returns this select descriptor.
     *
     * @param fieldList the selected fields
     * @return this select descriptor
     */
    public Select withFieldList(List<Typed> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    /**
     * Replaces the field list with the given fields.
     *
     * @param fieldArgs the selected fields
     * @return this select descriptor
     */
    public Select withFieldList(Typed... fieldArgs) {
        this.fieldList = List.of(fieldArgs);
        return this;
    }

    /**
     * Clears the field list and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoFieldList() {
        this.fieldList = null;
        return this;
    }

    /**
     * Updates the join list and returns this select descriptor.
     *
     * @param joinList the join clauses
     * @return this select descriptor
     */
    public Select withJoinList(List<Join> joinList) {
        this.joinList = joinList;
        return this;
    }

    /**
     * Replaces the join list with the given joins.
     *
     * @param joinArgs the join clauses
     * @return this select descriptor
     */
    public Select withJoinList(Join... joinArgs) {
        this.joinList = List.of(joinArgs);
        return this;
    }

    /**
     * Clears the join list and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoJoinList() {
        this.joinList = null;
        return this;
    }

    /**
     * Updates the filter list and returns this select descriptor.
     *
     * @param filterList the filter clauses
     * @return this select descriptor
     */
    public Select withFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        return this;
    }

    /**
     * Replaces the filter list with the given filters.
     *
     * @param filterArgs the filter clauses
     * @return this select descriptor
     */
    public Select withFilterList(Filter... filterArgs) {
        this.filterList = List.of(filterArgs);
        return this;
    }

    /**
     * Clears the filter list and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoFilterList() {
        this.filterList = null;
        return this;
    }

    /**
     * Updates the order list and returns this select descriptor.
     *
     * @param orderList the ordering clauses
     * @return this select descriptor
     */
    public Select withOrderList(List<Order> orderList) {
        this.orderList = orderList;
        return this;
    }

    /**
     * Replaces the order list with the given order clauses.
     *
     * @param orderArgs the ordering clauses
     * @return this select descriptor
     */
    public Select withOrderList(Order... orderArgs) {
        this.orderList = List.of(orderArgs);
        return this;
    }

    /**
     * Clears the order list and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoOrderList() {
        this.orderList = null;
        return this;
    }

    /**
     * Updates the offset and returns this select descriptor.
     *
     * @param offset the new offset
     * @return this select descriptor
     */
    public Select withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Clears the offset and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoOffset() {
        this.offset = null;
        return this;
    }

    /**
     * Updates the limit and returns this select descriptor.
     *
     * @param limit the new limit
     * @return this select descriptor
     */
    public Select withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Clears the limit and returns this select descriptor.
     *
     * @return this select descriptor
     */
    public Select withNoLimit() {
        this.limit = null;
        return this;
    }

    /**
     * Returns a cloned select descriptor with the given table.
     *
     * @param tableHead the replacement table
     * @return a cloned descriptor with the table updated
     */
    public Select uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    /**
     * Returns a cloned select descriptor without a table.
     *
     * @return a cloned descriptor with the table cleared
     */
    public Select uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given field list.
     *
     * @param fieldList the replacement field list
     * @return a cloned descriptor with the field list updated
     */
    public Select uponFieldList(List<Typed> fieldList) {
        var clone = this.clone();
        clone.fieldList = fieldList;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given fields.
     *
     * @param fieldArgs the replacement fields
     * @return a cloned descriptor with the field list updated
     */
    public Select uponFieldList(Typed... fieldArgs) {
        var clone = this.clone();
        clone.fieldList = List.of(fieldArgs);
        return clone;
    }

    /**
     * Returns a cloned select descriptor without a field list.
     *
     * @return a cloned descriptor with the field list cleared
     */
    public Select uponNoFieldList() {
        var clone = this.clone();
        clone.fieldList = null;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given join list.
     *
     * @param joinList the replacement join list
     * @return a cloned descriptor with the join list updated
     */
    public Select uponJoinList(List<Join> joinList) {
        var clone = this.clone();
        clone.joinList = joinList;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given joins.
     *
     * @param joinArgs the replacement joins
     * @return a cloned descriptor with the join list updated
     */
    public Select uponJoinList(Join... joinArgs) {
        var clone = this.clone();
        clone.joinList = List.of(joinArgs);
        return clone;
    }

    /**
     * Returns a cloned select descriptor without a join list.
     *
     * @return a cloned descriptor with the join list cleared
     */
    public Select uponNoJoinList() {
        var clone = this.clone();
        clone.joinList = null;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given filter list.
     *
     * @param filterList the replacement filter list
     * @return a cloned descriptor with the filter list updated
     */
    public Select uponFilterList(List<Filter> filterList) {
        var clone = this.clone();
        clone.filterList = filterList;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given filters.
     *
     * @param filterArgs the replacement filters
     * @return a cloned descriptor with the filter list updated
     */
    public Select uponFilterList(Filter... filterArgs) {
        var clone = this.clone();
        clone.filterList = List.of(filterArgs);
        return clone;
    }

    /**
     * Returns a cloned select descriptor without a filter list.
     *
     * @return a cloned descriptor with the filter list cleared
     */
    public Select uponNoFilterList() {
        var clone = this.clone();
        clone.filterList = null;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given order list.
     *
     * @param orderList the replacement order list
     * @return a cloned descriptor with the order list updated
     */
    public Select uponOrderList(List<Order> orderList) {
        var clone = this.clone();
        clone.orderList = orderList;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given ordering clauses.
     *
     * @param orderArgs the replacement ordering clauses
     * @return a cloned descriptor with the order list updated
     */
    public Select uponOrderList(Order... orderArgs) {
        var clone = this.clone();
        clone.orderList = List.of(orderArgs);
        return clone;
    }

    /**
     * Returns a cloned select descriptor without an order list.
     *
     * @return a cloned descriptor with the order list cleared
     */
    public Select uponNoOrderList() {
        var clone = this.clone();
        clone.orderList = null;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given offset.
     *
     * @param offset the replacement offset
     * @return a cloned descriptor with the offset updated
     */
    public Select uponOffset(Integer offset) {
        var clone = this.clone();
        clone.offset = offset;
        return clone;
    }

    /**
     * Returns a cloned select descriptor without an offset.
     *
     * @return a cloned descriptor with the offset cleared
     */
    public Select uponNoOffset() {
        var clone = this.clone();
        clone.offset = null;
        return clone;
    }

    /**
     * Returns a cloned select descriptor with the given limit.
     *
     * @param limit the replacement limit
     * @return a cloned descriptor with the limit updated
     */
    public Select uponLimit(Integer limit) {
        var clone = this.clone();
        clone.limit = limit;
        return clone;
    }

    /**
     * Returns a cloned select descriptor without a limit.
     *
     * @return a cloned descriptor with the limit cleared
     */
    public Select uponNoLimit() {
        var clone = this.clone();
        clone.limit = null;
        return clone;
    }

    /**
     * Creates a deep clone of this select descriptor.
     *
     * @return a deep copy of this select descriptor
     */
    @Override
    public Select clone() {
        return (Select) this.deepClone();
    }

    /**
     * Compares this select descriptor to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this select descriptor.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this select descriptor to JSON text.
     *
     * @return the JSON representation of this select descriptor
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a select descriptor.
     *
     * @param chars the JSON text to parse
     * @return the parsed descriptor
     */
    public static Select fromChars(String chars) {
        return Base.fromChars(chars, Select.class);
    }

    /**
     * Fills the filter values in order using the provided arguments.
     *
     * @param values the values to assign to filters
     * @return this select descriptor
     * @throws IllegalArgumentException if there are fewer filters than values
     */
    public Select filterWithValues(Object... values) {
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
     * Returns a cloned select descriptor with filter values filled in order.
     *
     * @param values the values to assign to filters
     * @return a cloned select descriptor with the filter values updated
     */
    public Select filterUponValues(Object... values) {
        var clone = this.clone();
        clone.filterWithValues(values);
        return clone;
    }

    /**
     * Maps the first result row to the requested class.
     *
     * @param resultSet the JDBC result set
     * @param clazz the target class
     * @param <T> the result type
     * @return the mapped value
     * @throws Exception if mapping fails
     */
    public <T> T mapResult(ResultSet resultSet, Class<T> clazz) throws Exception {
        return WizBased.mapResult(resultSet, fieldList, clazz);
    }

    /**
     * Maps all result rows to the requested class.
     *
     * @param resultSet the JDBC result set
     * @param clazz the target class
     * @param <T> the result type
     * @return the mapped values
     * @throws Exception if mapping fails
     */
    public <T> List<T> mapResults(ResultSet resultSet, Class<T> clazz) throws Exception {
        return WizBased.mapResults(resultSet, fieldList, clazz);
    }

}
