package br.com.pointel.wiz_jarch.data;

import java.util.Objects;

/**
 * A list of database configurations with lookup support by base name.
 */
public class Bases extends DataListArray<BasedWays> implements Data {

    /**
     * Creates an empty base collection.
     */
    public Bases() {
    }

    /**
     * Creates a deep clone of this collection.
     *
     * @return a deep copy of this collection
     */
    @Override
    public Bases clone() {
        return (Bases) this.deepClone();
    }

    /**
     * Compares this collection to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this collection.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this collection to JSON text.
     *
     * @return the JSON representation of this collection
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a base collection.
     *
     * @param chars the JSON text to parse
     * @return the parsed collection
     */
    public static Bases fromChars(String chars) {
        return Base.fromChars(chars, Bases.class);
    }

    /**
     * Finds the first base configuration with the given name.
     *
     * @param name the base name to search for
     * @return the matching configuration, or {@code null}
     */
    public BasedWays getFromName(String name) {
        for (var dataWay : this) {
            if (Objects.equals(dataWay.getName(), name)) {
                return dataWay;
            }
        }
        return null;
    }

}
