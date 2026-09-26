package br.com.pointel.wiz_jarch.data;

/**
 * Immutable key-value pair used as a terminal data holder.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class PairEnd<K, V> implements Data {
    
    private final K key;
    private final V val;

    /**
     * Creates a pair with the given key and value.
     *
     * @param key the key value
     * @param val the associated value
     */
    public PairEnd(K key, V val) {
        this.key = key;
        this.val = val;
    }

    /**
     * Returns the key.
     *
     * @return the key, or {@code null}
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value.
     *
     * @return the value, or {@code null}
     */
    public V getVal() {
        return val;
    }

    /**
     * Returns the key.
     *
     * @return the key, or {@code null}
     */
    public K key() {
        return key;
    }

    /**
     * Returns the value.
     *
     * @return the value, or {@code null}
     */
    public V val() {
        return val;
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
     * Creates a new pair with the given key.
     *
     * @param key the replacement key
     * @return a new pair with the key updated
     */
    public PairEnd<K, V> uponKey(K key) {
        return of(key, val);
    }

    /**
     * Creates a new pair without a key.
     *
     * @return a new pair with the key cleared
     */
    public PairEnd<K, V> uponNoKey() {
        return of(null, val);
    }

    /**
     * Creates a new pair with the given value.
     *
     * @param val the replacement value
     * @return a new pair with the value updated
     */
    public PairEnd<K, V> uponVal(V val) {
        return of(key, val);
    }

    /**
     * Creates a new pair without a value.
     *
     * @return a new pair with the value cleared
     */
    public PairEnd<K, V> uponNoVal() {
        return of(key, null);
    }

    /**
     * Creates a deep clone of this pair.
     *
     * @return a deep copy of this pair
     */
    @Override
    public PairEnd<K, V> clone() {
        return (PairEnd<K, V>) this.deepClone();
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
    public static PairEnd fromChars(String chars) {
        return Base.fromChars(chars, PairEnd.class);
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
    public static <K, V> PairEnd<K, V> of(K key, V val) {
        return new PairEnd<>(key, val);
    }

}
