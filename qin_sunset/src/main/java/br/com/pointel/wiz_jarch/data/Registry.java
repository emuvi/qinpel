package br.com.pointel.wiz_jarch.data;

/**
 * Associates a base name with a table descriptor.
 */
public class Registry implements Data {

    public String base;
    public TableHead tableHead;

    /**
     * Creates an empty registry.
     */
    public Registry() {
    }

    /**
     * Creates a registry with the given base name.
     *
     * @param base the base name
     */
    public Registry(String base) {
        this.base = base;
    }

    /**
     * Creates a registry with the given table descriptor.
     *
     * @param tableHead the table descriptor
     */
    public Registry(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    /**
     * Creates a registry with the given base name and table descriptor.
     *
     * @param base the base name
     * @param tableHead the table descriptor
     */
    public Registry(String base, TableHead tableHead) {
        this.base = base;
        this.tableHead = tableHead;
    }

    /**
     * Indicates whether the base is set.
     *
     * @return {@code true} when the base is non-null; otherwise {@code false}
     */
    public boolean hasBase() {
        return this.base != null;
    }

    /**
     * Indicates whether the table descriptor is set.
     *
     * @return {@code true} when the table descriptor is non-null; otherwise {@code false}
     */
    public boolean hasTableHead() {
        return this.tableHead != null;
    }

    /**
     * Updates the base and returns this registry.
     *
     * @param base the new base name
     * @return this registry
     */
    public Registry withBase(String base) {
        this.base = base;
        return this;
    }

    /**
     * Clears the base and returns this registry.
     *
     * @return this registry
     */
    public Registry withNoBase() {
        this.base = null;
        return this;
    }

    /**
     * Updates the table descriptor and returns this registry.
     *
     * @param tableHead the new table descriptor
     * @return this registry
     */
    public Registry withTableHead(TableHead tableHead) {
        this.tableHead = tableHead;
        return this;
    }

    /**
     * Clears the table descriptor and returns this registry.
     *
     * @return this registry
     */
    public Registry withNoTableHead() {
        this.tableHead = null;
        return this;
    }

    /**
     * Returns a cloned registry with the given base name.
     *
     * @param base the replacement base name
     * @return a cloned registry with the base updated
     */
    public Registry uponBase(String base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    /**
     * Returns a cloned registry without a base name.
     *
     * @return a cloned registry with the base cleared
     */
    public Registry uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    /**
     * Returns a cloned registry with the given table descriptor.
     *
     * @param tableHead the replacement table descriptor
     * @return a cloned registry with the table descriptor updated
     */
    public Registry uponTableHead(TableHead tableHead) {
        var clone = this.clone();
        clone.tableHead = tableHead;
        return clone;
    }

    /**
     * Returns a cloned registry without a table descriptor.
     *
     * @return a cloned registry with the table descriptor cleared
     */
    public Registry uponNoTableHead() {
        var clone = this.clone();
        clone.tableHead = null;
        return clone;
    }

    /**
     * Creates a deep clone of this registry.
     *
     * @return a deep copy of this registry
     */
    @Override
    public Registry clone() {
        return (Registry) this.deepClone();
    }

    /**
     * Compares this registry to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this registry.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this registry to JSON text.
     *
     * @return the JSON representation of this registry
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a registry.
     *
     * @param chars the JSON text to parse
     * @return the parsed registry
     */
    public static Registry fromChars(String chars) {
        return Base.fromChars(chars, Registry.class);
    }

}
