package br.com.pointel.wiz_jarch.data;

/**
 * Mutable key-value pair used throughout the data package.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class Pair<K, V> implements Data {
    
    private K key;
    private V val;

    /**
     * Creates an empty pair.
     */
    public Pair() {
    }

    /**
     * Creates a pair with the given key and value.
     *
     * @param key the key value
     * @param val the associated value
     */
    public Pair(K key, V val) {
        this.key = key;
        this.val = val;
    }

    /**
     * Returns the current key.
     *
     * @return the key, or {@code null}
     */
    public K getKey() {
        return key;
    }

    /**
     * Updates the key.
     *
     * @param key the new key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Returns the current value.
     *
     * @return the value, or {@code null}
     */
    public V getVal() {
        return val;
    }

    /**
     * Updates the value.
     *
     * @param val the new value
     */
    public void setVal(V val) {
        this.val = val;
    }

    /**
     * Returns the current key.
     *
     * @return the key, or {@code null}
     */
    public K key() {
        return key;
    }

    /**
     * Updates the key and returns this pair.
     *
     * @param key the new key
     * @return this pair
     */
    public Pair<K, V> key(K key) {
        this.key = key;
        return this;
    }

    /**
     * Returns the current value.
     *
     * @return the value, or {@code null}
     */
    public V val() {
        return val;
    }

    /**
     * Updates the value and returns this pair.
     *
     * @param val the new value
     * @return this pair
     */
    public Pair<K, V> val(V val) {
        this.val = val;
        return this;
    }

    /**
     * Indicates whether the key is set.
     *
     * @return {@code true} when the key is non-null; otherwise {@code false}
     */
    public boolean hasKey() {
        return this.key != null;
    }

    /**
     * Indicates whether the value is set.
     *
     * @return {@code true} when the value is non-null; otherwise {@code false}
     */
    public boolean hasVal() {
        return this.val != null;
    }

    /**
     * Returns a cloned pair with the given key.
     *
     * @param key the replacement key
     * @return a cloned pair with the key updated
     */
    public Pair<K, V> uponKey(K key) {
        var clone = this.clone();
        clone.key = key;
        return clone;
    }

    /**
     * Returns a cloned pair with the key cleared.
     *
     * @return a cloned pair without a key
     */
    public Pair<K, V> uponNoKey() {
        var clone = this.clone();
        clone.key = null;
        return clone;
    }

    /**
     * Returns a cloned pair with the given value.
     *
     * @param val the replacement value
     * @return a cloned pair with the value updated
     */
    public Pair<K, V> uponVal(V val) {
        var clone = this.clone();
        clone.val = val;
        return clone;
    }

    /**
     * Returns a cloned pair with the value cleared.
     *
     * @return a cloned pair without a value
     */
    public Pair<K, V> uponNoVal() {
        var clone = this.clone();
        clone.val = null;
        return clone;
    }

    /**
     * Creates a deep clone of this pair.
     *
     * @return a deep copy of this pair
     */
    @Override
    public Pair<K, V> clone() {
        return (Pair<K, V>) this.deepClone();
    }

    /**
     * Compares this pair to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this pair.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this pair to JSON text.
     *
     * @return the JSON representation of this pair
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a raw pair instance.
     *
     * @param chars the JSON text to parse
     * @return the parsed pair
     */
    public static Pair fromChars(String chars) {
        return Base.fromChars(chars, Pair.class);
    }

    /**
     * Creates a pair from the supplied key and value.
     *
     * @param key the key value
     * @param val the associated value
     * @param <K> the key type
     * @param <V> the value type
     * @return a new pair containing the given values
     */
    public static <K, V> Pair<K, V> of(K key, V val) {
        return new Pair<>(key, val);
    }

}
