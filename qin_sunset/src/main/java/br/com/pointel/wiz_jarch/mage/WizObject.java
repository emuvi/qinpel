package br.com.pointel.wiz_jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Objects;

import br.com.pointel.wiz_jarch.data.Base;
import br.com.pointel.wiz_jarch.data.DataClazz;
import br.com.pointel.wiz_jarch.flow.FixVals;

/**
 * A utility class providing common {@link Object} operations, null-safe validations,
 * base64 serialization, and file I/O operations for Java objects.
 */
public class WizObject {

    /**
     * Internal prefix identifying that a serialized string represents a {@link DataClazz} object payload.
     */
    public static final String dataClazzPrefix = "dataClazz:";

    /**
     * Internal prefix identifying that a serialized string represents a base64 encoded {@link Serializable} object.
     */
    public static final String objectOnBase64Prefix = "object^64:";

    private WizObject() {
    }

    // =========================================================================
    // VALIDATION & CORE UTILS
    // =========================================================================

    /**
     * Checks if the given object evaluates to {@code null}.
     *
     * @param object the object to check
     * @return {@code true} if the object is null, {@code false} otherwise
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Checks if the given object strictly evaluates to a non-{@code null} value.
     *
     * @param object the object to check
     * @return {@code true} if the object is not null, {@code false} otherwise
     */
    public static boolean isNotNull(Object object) {
        return object != null;
    }

    /**
     * Compares two objects for structural equality in a null-safe manner.
     *
     * @param a the first object
     * @param b the second object
     * @return {@code true} if both objects are exactly equal or both are null, {@code false} otherwise
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * Generates a hash code for the given object in a null-safe execution wrapper.
     *
     * @param object the object to hash
     * @return the generated hash code, or {@code 0} if the object is null
     */
    public static int hashCode(Object object) {
        return Objects.hashCode(object);
    }

    /**
     * Converts an object to its string representation securely bypassing null limits.
     *
     * @param object the object to convert
     * @return the string representation, or an empty string if the object is null
     */
    public static String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    /**
     * Converts an object to its string representation, securely falling back to a default value if the object is null.
     *
     * @param object        the object to convert
     * @param defaultIfNull the default string to return if the object is null
     * @return the string representation, or {@code defaultIfNull} if the object is null
     */
    public static String toString(Object object, String defaultIfNull) {
        return object == null ? defaultIfNull : object.toString();
    }

    /**
     * Validates that a specified object reference strictly contains a non-{@code null} value.
     *
     * @param <T>    the type of the reference
     * @param object the object reference to check for nullity
     * @return the object reference securely if not null
     * @throws NullPointerException if the evaluated object is null
     */
    public static <T> T requireNotNull(T object) {
        return Objects.requireNonNull(object);
    }

    /**
     * Validates that a specified object reference strictly contains a non-{@code null} value, throwing a custom message if it fails.
     *
     * @param <T>     the type of the reference
     * @param object  the object reference to check for nullity
     * @param message the detail message exposed when a {@link NullPointerException} is triggered
     * @return the object reference securely if not null
     * @throws NullPointerException if the evaluated object is null
     */
    public static <T> T requireNotNull(T object, String message) {
        return Objects.requireNonNull(object, message);
    }

    /**
     * Returns the first non-{@code null} object extracted from a given array of objects.
     *
     * @param <T>        the type of the objects
     * @param objectList the array of objects to actively check
     * @return the first non-null object, or {@code null} if all elements are null or the underlying array itself is null
     */
    @SafeVarargs
    public static <T> T getFirstNonNull(T... objectList) {
        if (objectList == null) {
            return null;
        }
        for (T object : objectList) {
            if (object != null) {
                return object;
            }
        }
        return null;
    }

    // =========================================================================
    // CONVERSION & SERIALIZATION
    // =========================================================================

    /**
     * Checks if a value is architecturally compatible with {@code WizObject} serialization or conversion routines.
     *
     * @param value the object to check
     * @return {@code true} if the object evaluates as a {@link DataClazz}, {@link String}, or a generic {@link Object} (provided it is not null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), DataClazz.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Object.class);
    }

    /**
     * Extracts or actively decodes the actual value from a structured object or specially formatted string footprint.
     * <p>
     * Handled footprint paradigms include {@link DataClazz} definitions, base64 encoded byte arrays, and complete base64 serialized objects.
     * </p>
     *
     * @param value the object or formatted string payload to decode
     * @return the natively decoded object, or the original object if no special formatting metadata is detected
     * @throws Exception if object structural deserialization or base64 decoding fails
     */
    public static Object get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), DataClazz.class)) {
            return DataClazz.class.cast(value).getValue();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isEmpty()) return null;
            if (string.startsWith(dataClazzPrefix)) {
                return DataClazz.fromChars(string.substring(dataClazzPrefix.length())).getValue();
            } else if (string.startsWith(WizBytes.byteArrayOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(WizBytes.byteArrayOnBase64Prefix.length()));
            } else if (string.startsWith(WizObject.objectOnBase64Prefix)) {
                var bytes = WizBytes.decodeFromBase64(string.substring(WizObject.objectOnBase64Prefix.length()));
                try (var bis = new ByteArrayInputStream(bytes);
                     var ois = new ObjectInputStream(bis)) {
                    return ois.readObject();
                }
            } else {
                return string;
            }
        }
        return value;
    }

    /**
     * Formats an object down into a highly portable string representation footprint.
     * <p>
     * - If the object maps to {@link DataClazz}, {@link FixVals}, {@link Base}, or {@link Record}, it formats leveraging specific prefix identifiers.<br>
     * - If mapped as a byte array, it executes a base64 encoded compression algorithm.<br>
     * - Otherwise, it strictly serializes the entire entity footprint into a base64 string construct.
     * </p>
     *
     * @param value the object payload to heavily serialize or strictly format
     * @return the formatted or encoded base64 string configuration
     * @throws RuntimeException if the object violates the {@link Serializable} pattern or mapping structurally fails
     */
    public static String format(Object value) {
        if (value == null) return "";
        if (WizLang.isChildOf(value.getClass(), DataClazz.class)) {
            return dataClazzPrefix + DataClazz.class.cast(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), FixVals.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), Base.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), Record.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return WizBytes.byteArrayOnBase64Prefix + WizBytes.encodeToBase64(byte[].class.cast(value));
        }
        try (var bos = new ByteArrayOutputStream();
             var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            oos.flush();
            return objectOnBase64Prefix + WizBytes.encodeToBase64(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object of class: " + value.getClass().getName(), e);
        }
    }
    
    // =========================================================================
    // FILE I/O
    // =========================================================================

    /**
     * Securely reads a serialized object payload mapped from a {@link File} footprint, casting it to the desired runtime class structure.
     *
     * @param <T>   the expected class type of the generated object
     * @param file  the localized physical file housing the serialized object string
     * @param clazz the targeted {@link Class} model
     * @return the structurally deserialized object cast to the expected class pattern
     * @throws Exception if an I/O blockage occurs, the base class signature is missing, or type casting fails
     */
    public static <T> T read(File file, Class<T> clazz) throws Exception {
        return clazz.cast(read(file));
    }

    /**
     * Securely reads a raw serialized object payload mapped natively from a {@link File}.
     *
     * @param file the localized physical file housing the serialized object payload
     * @return the natively deserialized standard object
     * @throws Exception if an I/O blockage occurs or the underlying object class mapping fails
     */
    public static Object read(File file) throws Exception {
        try (var ois = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            return ois.readObject();
        }
    }

    /**
     * Executes writing behavior saving a highly compatible {@link Serializable} object physically to a given {@link File}.
     *
     * @param file   the targeted file mapping mapped for direct writes
     * @param object the configured {@link Serializable} object payload
     * @throws Exception if I/O constraints occur blocking physical write permissions
     */
    public static void write(File file, Serializable object) throws Exception {
        try (var oos = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            oos.writeObject(object);
            oos.flush();
        }
    }

}
