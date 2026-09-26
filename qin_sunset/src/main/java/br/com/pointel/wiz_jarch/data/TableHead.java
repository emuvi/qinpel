package br.com.pointel.wiz_jarch.data;

import java.sql.Connection;

import br.com.pointel.wiz_jarch.mage.WizBased;
import br.com.pointel.wiz_jarch.mage.WizString;

/**
 * Identifies a table using catalog, schema, name, and optional alias values.
 */
public class TableHead implements Data {

    public String catalog;
    public String schema;
    public String name;
    public String alias;

    /**
     * Cached table metadata resolved from this head.
     */
    private transient Table table;

    /**
     * Creates an empty table head.
     */
    public TableHead() {
    }

    /**
     * Creates a table head with the given name.
     *
     * @param name the table name
     */
    public TableHead(String name) {
        this.name = name;
    }

    /**
     * Creates a table head with the given schema and name.
     *
     * @param schema the schema name
     * @param name the table name
     */
    public TableHead(String schema, String name) {
        this.schema = schema;
        this.name = name;
    }

    /**
     * Creates a table head with the given catalog, schema, and name.
     *
     * @param catalog the catalog name
     * @param schema the schema name
     * @param name the table name
     */
    public TableHead(String catalog, String schema, String name) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
    }

    /**
     * Creates a table head with the given catalog, schema, name, and alias.
     *
     * @param catalog the catalog name
     * @param schema the schema name
     * @param name the table name
     * @param alias the table alias
     */
    public TableHead(String catalog, String schema, String name, String alias) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.alias = alias;
    }

    /**
     * Indicates whether the catalog is set.
     *
     * @return {@code true} when the catalog is non-empty; otherwise {@code false}
     */
    public boolean hasCatalog() {
        return this.catalog != null && !this.catalog.isEmpty();
    }

    /**
     * Indicates whether the schema is set.
     *
     * @return {@code true} when the schema is non-empty; otherwise {@code false}
     */
    public boolean hasSchema() {
        return this.schema != null && !this.schema.isEmpty();
    }

    /**
     * Indicates whether the name is set.
     *
     * @return {@code true} when the name is non-empty; otherwise {@code false}
     */
    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    /**
     * Indicates whether the alias is set.
     *
     * @return {@code true} when the alias is non-empty; otherwise {@code false}
     */
    public boolean hasAlias() {
        return this.alias != null && !this.alias.isEmpty();
    }

    /**
     * Updates the catalog and returns this table head.
     *
     * @param catalog the new catalog name
     * @return this table head
     */
    public TableHead withCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    /**
     * Clears the catalog and returns this table head.
     *
     * @return this table head
     */
    public TableHead withNoCatalog() {
        this.catalog = null;
        return this;
    }

    /**
     * Updates the schema and returns this table head.
     *
     * @param schema the new schema name
     * @return this table head
     */
    public TableHead withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    /**
     * Clears the schema and returns this table head.
     *
     * @return this table head
     */
    public TableHead withNoSchema() {
        this.schema = null;
        return this;
    }

    /**
     * Updates the name and returns this table head.
     *
     * @param name the new table name
     * @return this table head
     */
    public TableHead withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this table head.
     *
     * @return this table head
     */
    public TableHead withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the alias and returns this table head.
     *
     * @param alias the new alias
     * @return this table head
     */
    public TableHead withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * Clears the alias and returns this table head.
     *
     * @return this table head
     */
    public TableHead withNoAlias() {
        this.alias = null;
        return this;
    }

    /**
     * Returns a cloned table head with the given catalog.
     *
     * @param catalog the replacement catalog name
     * @return a cloned table head with the catalog updated
     */
    public TableHead uponCatalog(String catalog) {
        var clone = this.clone();
        clone.catalog = catalog;
        return clone;
    }

    /**
     * Returns a cloned table head without a catalog.
     *
     * @return a cloned table head with the catalog cleared
     */
    public TableHead uponNoCatalog() {
        var clone = this.clone();
        clone.catalog = null;
        return clone;
    }

    /**
     * Returns a cloned table head with the given schema.
     *
     * @param schema the replacement schema name
     * @return a cloned table head with the schema updated
     */
    public TableHead uponSchema(String schema) {
        var clone = this.clone();
        clone.schema = schema;
        return clone;
    }

    /**
     * Returns a cloned table head without a schema.
     *
     * @return a cloned table head with the schema cleared
     */
    public TableHead uponNoSchema() {
        var clone = this.clone();
        clone.schema = null;
        return clone;
    }

    /**
     * Returns a cloned table head with the given name.
     *
     * @param name the replacement table name
     * @return a cloned table head with the name updated
     */
    public TableHead uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned table head without a name.
     *
     * @return a cloned table head with the name cleared
     */
    public TableHead uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned table head with the given alias.
     *
     * @param alias the replacement alias
     * @return a cloned table head with the alias updated
     */
    public TableHead uponAlias(String alias) {
        var clone = this.clone();
        clone.alias = alias;
        return clone;
    }

    /**
     * Returns a cloned table head without an alias.
     *
     * @return a cloned table head with the alias cleared
     */
    public TableHead uponNoAlias() {
        var clone = this.clone();
        clone.alias = null;
        return clone;
    }

    /**
     * Creates a deep clone of this table head.
     *
     * @return a deep copy of this table head
     */
    @Override
    public TableHead clone() {
        return (TableHead) this.deepClone();
    }

    /**
     * Compares this table head to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this table head.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this table head to JSON text.
     *
     * @return the JSON representation of this table head
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a table head.
     *
     * @param chars the JSON text to parse
     * @return the parsed table head
     */
    public static TableHead fromChars(String chars) {
        return Base.fromChars(chars, TableHead.class);
    }

    /**
     * Returns the alias when present, otherwise the fully qualified catalog,
     * schema, and name reference.
     *
     * @return the display reference name
     */
    public String getReferenceName() {
        return this.alias != null && !this.alias.isEmpty() ? this.alias : this.getCatalogSchemaName();
    }

    /**
     * Returns the schema-qualified name.
     *
     * @return the schema and name joined by dots
     */
    public String getSchemaName() {
        return WizString.sum(".", this.schema, this.name);
    }

    /**
     * Returns the catalog-qualified name.
     *
     * @return the catalog, schema, and name joined by dots
     */
    public String getCatalogSchemaName() {
        return WizString.sum(".", this.catalog, this.schema, this.name);
    }

    /**
     * Returns a filename-safe representation of the table head.
     *
     * @return the catalog, schema, and name joined by dots
     */
    public String getNameForFile() {
        return WizString.sum(".", this.catalog, this.schema, this.name);
    }

    /**
     * Resolves the full table metadata for this head using the given connection.
     *
     * @param connection the JDBC connection used to inspect the table
     * @return the resolved table metadata
     * @throws Exception if metadata loading fails
     */
    public Table getTable(Connection connection) throws Exception {
        if (table == null) {
            table = WizBased.getTable(this, connection);
        }
        return table;
    }

}
