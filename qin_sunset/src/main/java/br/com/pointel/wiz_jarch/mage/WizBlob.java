package br.com.pointel.wiz_jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;

import javax.sql.rowset.serial.SerialBlob;

/**
 * A utility class for safely managing, converting, and interacting with {@link Blob} structures.
 * <p>
 * {@code WizBlob} provides static methods engineered to seamlessly parse various inputs (such as raw byte arrays, strings,
 * and serializable objects) into functional {@link Blob} instances. It handles proper streaming, memory release mechanisms,
 * and serialization pipelines without the boilerplate standard JDBC interaction requires.
 * </p>
 */
public class WizBlob {

    private WizBlob() {
    }

    /**
     * Determines whether the given object is natively compatible or actively translatable into a {@link Blob}.
     * <p>
     * Supported mapping formats encompass active {@link Blob} or {@link Clob} objects, raw {@code byte[]} structures,
     * {@link String} and {@link Number} formats, or any explicitly mapped {@link Serializable} object.
     * </p>
     *
     * @param value the target object to evaluate
     * @return {@code true} if the object can map directly into a {@link Blob}; {@code false} otherwise (or if the input is null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    /**
     * Converts a supported, versatile object format structurally into a {@link Blob}.
     * <p>
     * General textual fields and numbers are formatted into a stream utilizing a standard UTF-8 charset. Unstructured
     * complex items are piped through an {@link ObjectOutputStream}.
     * </p>
     *
     * @param value the raw structural parameter aimed for translation
     * @return the successfully mapped {@link Blob} containing the parameter's bytes, or {@code null} if the input is identically null
     * @throws Exception if the targeted value fails translation or fails during serialization bindings
     */
    public static Blob get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            return Blob.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return new SerialBlob(byte[].class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            var clob = Clob.class.cast(value);
            return new SerialBlob(clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            return new SerialBlob(string.getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            var number = Number.class.cast(value);
            return new SerialBlob(String.valueOf(number).getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                oos.flush();
                return new SerialBlob(bos.toByteArray());
            }
        }
        throw new Exception("Could not convert to a Blob value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object into a {@link Blob}, providing a protective fallback in case of translation failure.
     * <p>
     * Any structural or serialization exception thrown during processing is suppressed, deferring back to the provided fallback.
     * </p>
     *
     * @param value     the raw parameter queued for translation
     * @param orDefault the default structured {@link Blob} returned upon an aborted conversion attempt
     * @return the correctly generated {@link Blob}, or the designated fallback default
     */
    public static Blob get(Object value, Blob orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Harvests the complete raw byte array spanning the targeted {@link Blob}.
     *
     * @param blob the active {@link Blob} targeted for extraction
     * @return the structural {@code byte[]} mapped into memory, or {@code null} if the provided Blob is itself null
     * @throws Exception if stream access terminates unexpectedly or is denied structurally
     */
    public static byte[] getBytes(Blob blob) throws Exception {
        if (blob == null) return null;
        return blob.getBytes(1, (int) blob.length());
    }

    /**
     * Extracts a targeted {@link Blob} translating its byte layout into a structured UTF-8 string.
     *
     * @param blob the targeted {@link Blob} queued for extraction
     * @return the successfully formatted structural {@link String}, or {@code null} if the provided Blob is itself null
     * @throws Exception if character conversion fails or byte acquisition is interrupted
     */
    public static String getString(Blob blob) throws Exception {
        return getString(blob, StandardCharsets.UTF_8);
    }

    /**
     * Extracts a targeted {@link Blob} translating its byte layout dynamically via the explicitly provided character set boundary.
     *
     * @param blob    the targeted {@link Blob} queued for extraction
     * @param charset the character translation protocol utilized for mapping bytes to a formatted string
     * @return the accurately mapped {@link String}, or {@code null} if the provided Blob is itself null
     * @throws Exception if stream bounds fall outside specified encoding lengths or standard access fails
     */
    public static String getString(Blob blob, Charset charset) throws Exception {
        byte[] bytes = getBytes(blob);
        if (bytes == null) return null;
        return new String(bytes, charset);
    }

    /**
     * Structurally unbinds a serialized {@link Blob}, extracting its byte streams directly into its prior Object form.
     * <p>
     * Specifically designed to decode complex fields written down utilizing standard {@link ObjectOutputStream} bounds.
     * </p>
     *
     * @param blob the fully compiled serial {@link Blob}
     * @return the parsed object restored to its pre-serialization framework format, or {@code null} if the blob provided was null
     * @throws Exception if stream deserialization blocks fail unexpectedly
     */
    public static Object getObject(Blob blob) throws Exception {
        byte[] bytes = getBytes(blob);
        if (bytes == null) return null;
        try (var bis = new ByteArrayInputStream(bytes);
             var ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }

    /**
     * Isolates the standard {@link InputStream} bridged to the internal bytes of the provided {@link Blob}.
     * <p>
     * The returned stream sits entirely at the user's discretion. Subsequent programmatic closures must be handled independently.
     * </p>
     *
     * @param blob the functional {@link Blob} needing extraction
     * @return the structured binary {@link InputStream} mapping directly to the item's stored state, or {@code null} if the param is null
     * @throws Exception if the internal byte connection faces critical stream failures
     */
    public static InputStream getInputStream(Blob blob) throws Exception {
        if (blob == null) return null;
        return blob.getBinaryStream();
    }

    /**
     * Quantifies the complete structural length natively comprising a given {@link Blob} parameter.
     * <p>
     * Abortive runtime constraints blocking measurement evaluate transparently strictly to {@code 0}.
     * </p>
     *
     * @param blob the parsed object parameter
     * @return the total volume footprint configured within the object strictly in bytes, or {@code 0} if null or unmeasurable
     */
    public static long length(Blob blob) {
        if (blob == null) return 0;
        try {
            return blob.length();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Forcefully releases all underlying system boundaries and bindings occupied by an active {@link Blob}.
     * <p>
     * Functions safely. Null targets are entirely ignored, and forced systemic exceptions caught during cleanup are heavily suppressed.
     * </p>
     *
     * @param blob the active item designated for memory clearing (may be null)
     */
    public static void free(Blob blob) {
        if (blob != null) {
            try {
                blob.free();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    /**
     * Checks if an provided {@link Blob} exists totally devoid of contained bytes.
     *
     * @param blob the target item undergoing evaluation
     * @return {@code true} if identically null, or if its reported length evaluates definitively to zero; {@code false} otherwise
     */
    public static boolean isEmpty(Blob blob) {
        return length(blob) == 0;
    }

    /**
     * Checks if a structured {@link Blob} encapsulates at minimum a single byte within its domain.
     *
     * @param blob the target item undergoing evaluation
     * @return {@code true} if mathematically greater than an empty volume; {@code false} otherwise
     */
    public static boolean isNotEmpty(Blob blob) {
        return !isEmpty(blob);
    }

}
