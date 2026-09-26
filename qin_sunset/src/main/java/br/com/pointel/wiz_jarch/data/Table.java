package br.com.pointel.wiz_jarch.data;

import java.util.List;
import java.util.Objects;

import br.com.pointel.wiz_jarch.mage.WizBased;

/**
 * Describes a database table, including its columns and key definitions.
 */
public class Table implements Data {

    public TableHead tableHead;
    public List<Field> fieldList;
    public List<KeyPrimary> keyPrimaryList;
    public List<KeyForeign> keyForeignList;

    /**
     * Creates an empty table descriptor.
     */
    public Table() {
    }

    /**
     * Creates a table descriptor with the given table head.
     *
     * @param tableHead the table head
     */
    public Table(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    /**
     * Creates a table descriptor with the given table head and field list.
     *
     * @param tableHead the table head
     * @param fieldList the table fields
     */
    public Table(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    /**
     * Creates a table descriptor with fields and primary keys.
     *
     * @param tableHead the table head
     * @param fieldList the table fields
     * @param keyPrimaryList the primary-key definitions
     */
    public Table(TableHead tableHead, List<Field> fieldList, List<KeyPrimary> keyPrimaryList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyPrimaryList = keyPrimaryList;
    }

    /**
     * Creates a table descriptor with fields, primary keys, and foreign keys.
     *
     * @param tableHead the table head
     * @param fieldList the table fields
     * @param keyPrimaryList the primary-key definitions
     * @param keyForeignList the foreign-key definitions
     */
    public Table(TableHead tableHead, List<Field> fieldList, List<KeyPrimary> keyPrimaryList, List<KeyForeign> keyForeignList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyPrimaryList = keyPrimaryList;
        this.keyForeignList = keyForeignList;
    }

    /**
     * Indicates whether the table head is set.
     *
     * @return {@code true} when the table head is non-null; otherwise {@code false}
     */
    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    /**
     * Indicates whether any fields are present.
     *
     * @return {@code true} when the field list is non-empty; otherwise {@code false}
     */
    public boolean hasFieldList() {
        return this.fieldList != null && !this.fieldList.isEmpty();
    }

    /**
     * Indicates whether any primary-key definitions are present.
     *
     * @return {@code true} when the primary-key list is non-empty; otherwise {@code false}
     */
    public boolean hasKeyPrimaryList() {
        return this.keyPrimaryList != null && !this.keyPrimaryList.isEmpty();
    }

    /**
     * Indicates whether any foreign-key definitions are present.
     *
     * @return {@code true} when the foreign-key list is non-empty; otherwise {@code false}
     */
    public boolean hasKeyForeignList() {
        return this.keyForeignList != null && !this.keyForeignList.isEmpty();
    }

    /**
     * Updates the table head and returns this descriptor.
     *
     * @param tableHead the new table head
     * @return this descriptor
     */
    public Table withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    /**
     * Clears the table head and returns this descriptor.
     *
     * @return this descriptor
     */
    public Table withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    /**
     * Updates the field list and returns this descriptor.
     *
     * @param fieldList the new field list
     * @return this descriptor
     */
    public Table withFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    /**
     * Replaces the field list with the given fields.
     *
     * @param fieldArgs the new field list
     * @return this descriptor
     */
    public Table withFieldList(Field... fieldArgs) {
        this.fieldList = List.of(fieldArgs);
        return this;
    }

    /**
     * Clears the field list and returns this descriptor.
     *
     * @return this descriptor
     */
    public Table withNoFieldList() {
        this.fieldList = null;
        return this;
    }

    /**
     * Updates the primary-key list and returns this descriptor.
     *
     * @param keyPrimaryList the new primary-key list
     * @return this descriptor
     */
    public Table withKeyPrimaryList(List<KeyPrimary> keyPrimaryList) {
        this.keyPrimaryList = keyPrimaryList;
        return this;
    }

    /**
     * Replaces the primary-key list with the given keys.
     *
     * @param keyPrimaryArgs the new primary-key list
     * @return this descriptor
     */
    public Table withKeyPrimaryList(KeyPrimary... keyPrimaryArgs) {
        this.keyPrimaryList = List.of(keyPrimaryArgs);
        return this;
    }

    /**
     * Clears the primary-key list and returns this descriptor.
     *
     * @return this descriptor
     */
    public Table withNoKeyPrimaryList() {
        this.keyPrimaryList = null;
        return this;
    }

    /**
     * Updates the foreign-key list and returns this descriptor.
     *
     * @param keyForeignList the new foreign-key list
     * @return this descriptor
     */
    public Table withKeyForeignList(List<KeyForeign> keyForeignList) {
        this.keyForeignList = keyForeignList;
        return this;
    }

    /**
     * Replaces the foreign-key list with the given keys.
     *
     * @param keyForeignArgs the new foreign-key list
     * @return this descriptor
     */
    public Table withKeyForeignList(KeyForeign... keyForeignArgs) {
        this.keyForeignList = List.of(keyForeignArgs);
        return this;
    }

    /**
     * Clears the foreign-key list and returns this descriptor.
     *
     * @return this descriptor
     */
    public Table withNoKeyForeignList() {
        this.keyForeignList = null;
        return this;
    }

    /**
     * Returns a cloned table with the given table head.
     *
     * @param tableHead the replacement table head
     * @return a cloned table with the table head updated
     */
    public Table uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    /**
     * Returns a cloned table without a table head.
     *
     * @return a cloned table with the table head cleared
     */
    public Table uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    /**
     * Returns a cloned table with the given field list.
     *
     * @param fieldList the replacement field list
     * @return a cloned table with the fields updated
     */
    public Table uponFieldList(List<Field> fieldList) {
        var clone = this.clone();
        clone.fieldList = fieldList;
        return clone;
    }

    /**
     * Returns a cloned table with the given fields.
     *
     * @param fieldArgs the replacement fields
     * @return a cloned table with the fields updated
     */
    public Table uponFieldList(Field... fieldArgs) {
        var clone = this.clone();
        clone.fieldList = List.of(fieldArgs);
        return clone;
    }

    /**
     * Returns a cloned table without a field list.
     *
     * @return a cloned table with the field list cleared
     */
    public Table uponNoFieldList() {
        var clone = this.clone();
        clone.fieldList = null;
        return clone;
    }

    /**
     * Returns a cloned table with the given primary-key list.
     *
     * @param keyPrimaryList the replacement primary-key list
     * @return a cloned table with the primary keys updated
     */
    public Table uponKeyPrimaryList(List<KeyPrimary> keyPrimaryList) {
        var clone = this.clone();
        clone.keyPrimaryList = keyPrimaryList;
        return clone;
    }

    /**
     * Returns a cloned table with the given primary keys.
     *
     * @param keyPrimaryArgs the replacement primary keys
     * @return a cloned table with the primary keys updated
     */
    public Table uponKeyPrimaryList(KeyPrimary... keyPrimaryArgs) {
        var clone = this.clone();
        clone.keyPrimaryList = List.of(keyPrimaryArgs);
        return clone;
    }

    /**
     * Returns a cloned table without a primary-key list.
     *
     * @return a cloned table with the primary-key list cleared
     */
    public Table uponNoKeyPrimaryList() {
        var clone = this.clone();
        clone.keyPrimaryList = null;
        return clone;
    }

    /**
     * Returns a cloned table with the given foreign-key list.
     *
     * @param keyForeignList the replacement foreign-key list
     * @return a cloned table with the foreign keys updated
     */
    public Table uponKeyForeignList(List<KeyForeign> keyForeignList) {
        var clone = this.clone();
        clone.keyForeignList = keyForeignList;
        return clone;
    }

    /**
     * Returns a cloned table with the given foreign keys.
     *
     * @param keyForeignArgs the replacement foreign keys
     * @return a cloned table with the foreign keys updated
     */
    public Table uponKeyForeignList(KeyForeign... keyForeignArgs) {
        var clone = this.clone();
        clone.keyForeignList = List.of(keyForeignArgs);
        return clone;
    }

    /**
     * Returns a cloned table without a foreign-key list.
     *
     * @return a cloned table with the foreign-key list cleared
     */
    public Table uponNoKeyForeignList() {
        var clone = this.clone();
        clone.keyForeignList = null;
        return clone;
    }

    /**
     * Creates a deep clone of this table.
     *
     * @return a deep copy of this table
     */
    @Override
    public Table clone() {
        return (Table) this.deepClone();
    }

    /**
     * Compares this table to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this table.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this table to JSON text.
     *
     * @return the JSON representation of this table
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a table.
     *
     * @param chars the JSON text to parse
     * @return the parsed table
     */
    public static Table fromChars(String chars) {
        return Base.fromChars(chars, Table.class);
    }

    /**
     * Returns the reference name from the table head.
     *
     * @return the reference name
     */
    public String getReferenceName() {
        return this.tableHead.getReferenceName();
    }

    /**
     * Returns the schema-qualified table name.
     *
     * @return the schema-qualified name
     */
    public String getSchemaName() {
        return this.tableHead.getSchemaName();
    }

    /**
     * Returns the catalog-qualified table name.
     *
     * @return the catalog-qualified name
     */
    public String getCatalogSchemaName() {
        return this.tableHead.getCatalogSchemaName();
    }

    /**
     * Finds a field by name.
     *
     * @param fieldName the field name to search for
     * @return the matching field, or {@code null}
     */
    public Field getFieldByName(String fieldName) {
        if (this.hasFieldList()) {
            for (Field field : this.fieldList) {
                if (Objects.equals(field.name, fieldName)) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * Builds an insert descriptor from this table.
     *
     * @return the insert descriptor
     */
    public Insert toInsert() {
        return WizBased.makeInsert(this);
    }

    /**
     * Builds a select descriptor from this table.
     *
     * @return the select descriptor
     */
    public Select toSelect() {
        return WizBased.makeSelect(this);
    }

    /**
     * Builds an update descriptor from this table.
     *
     * @return the update descriptor
     */
    public Update toUpdate() {
        return WizBased.makeUpdate(this);
    }

    /**
     * Builds a delete descriptor from this table.
     *
     * @return the delete descriptor
     */
    public Delete toDelete() {
        return WizBased.makeDelete(this);
    }

}
