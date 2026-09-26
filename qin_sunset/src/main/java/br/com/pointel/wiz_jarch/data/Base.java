package br.com.pointel.wiz_jarch.data;

import java.io.Serializable;

import br.com.pointel.wiz_jarch.mage.WizData;
import br.com.pointel.wiz_jarch.mage.WizLang;

/**
 * Common contract for data objects that support deep cloning, structural
 * equality, hashing, and JSON serialization.
 */
public interface Base extends Serializable {

    /**
     * Creates a deep clone of this instance.
     *
     * @return a deep copy of this object
     */
    public default Base deepClone() {
        return WizLang.deepClone(this);
    }

    /**
     * Compares this instance to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    public default boolean deepEquals(Object that) {
        return WizLang.deepEquals(this, that);
    }

    /**
     * Computes a deep structural hash code for this instance.
     *
     * @return the deep hash value
     */
    public default int deepHash() {
        return WizLang.deepHash(this);
    }

    /**
     * Serializes this instance to JSON text.
     *
     * @return the JSON representation of this object
     */
    public default String toChars() {
        return WizData.toJson(this);
    }

    /**
     * Deserializes JSON text into an instance of the given type.
     *
     * @param chars the JSON text to parse
     * @param clazz the target type
     * @param <T> the result type
     * @return the parsed object
     */
    public static <T> T fromChars(String chars, Class<T> clazz) {
        return WizData.fromJson(chars, clazz);
    }

}
