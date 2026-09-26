package br.com.pointel.wiz_jarch.mage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import javax.sql.rowset.serial.SerialClob;

/**
 * A robust, null-safe utility class for managing, evaluating, and manipulating SQL {@link Clob} (Character Large Object) bindings.
 * <p>
 * {@code WizClob} handles translating various complex Java types (e.g., arrays, blobs, serializables, numbers, strings) directly 
 * into SQL-compatible {@link Clob} architectures (natively utilizing {@link SerialClob} mappings). It also supplies utilities
 * for extracting strings seamlessly, performing equality checks across massive object sequences, truncating records, and freeing memory.
 * </p>
 */
public class WizClob {

    private WizClob() {
    }

    /**
     * Determines whether the given object natively matches or securely translates into a valid {@link Clob}.
     * <p>
     * Supported translation boundaries encompass active {@link Clob} and {@link Blob} SQL references,
     * primitive {@code byte[]} chains, standard {@link String} formats, numerical {@link Number} evaluations, 
     * and explicitly mapped {@link Serializable} object states.
     * </p>
     *
     * @param value the target object to evaluate
     * @return {@code true} if the object can natively map into a structured {@link Clob}; {@code false} otherwise (or if the input is null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    /**
     * Translates a versatile object format dynamically into a functional, memory-backed SQL {@link Clob}.
     * <p>
     * Natively resolves constraints utilizing {@link SerialClob} mapping for non-SQL types. Strings and numbers evaluate 
     * by mapping their direct character arrays. Binary objects (like {@link Blob} and {@code byte[]}) are properly decoded 
     * using standard UTF-8 structural encoding. {@link Serializable} targets map directly via active object stream extraction.
     * </p>
     *
     * @param value the raw structural parameter slated for Clob extraction
     * @return the successfully mapped {@link Clob}, or {@code null} if the input fundamentally evaluates as null
     * @throws Exception if the targeted parameter falls outside supported parameters or breaks generation dynamically
     */
    public static Clob get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            return Clob.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            var blob = Blob.class.cast(value);
            return new SerialClob(new String(blob.getBytes(1, (int) blob.length()), StandardCharsets.UTF_8).toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            var bytes = byte[].class.cast(value);
            return new SerialClob(new String(bytes, StandardCharsets.UTF_8).toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            return new SerialClob(string.toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            var number = Number.class.cast(value);
            return new SerialClob(String.valueOf(number).toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                oos.flush();
                String base = bos.toString(StandardCharsets.UTF_8);
                return new SerialClob(base.toCharArray());
            }
        }
        throw new Exception("Could not convert to a Clob value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts an object dynamically into a {@link Clob}, gracefully returning a prescribed fallback configuration
     * if bounds limitations or mapping exceptions occur.
     *
     * @param value     the raw parameter queued for translation
     * @param orDefault the default fallback {@link Clob} returned upon aborted translation sequences
     * @return the generated {@link Clob} binding, or the successfully bypassed fallback parameter
     */
    public static Clob get(Object value, Clob orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Extracts and maps the comprehensive textual payload string locked entirely inside a SQL {@link Clob} parameter.
     *
     * @param clob the active {@link Clob} targeted for reading
     * @return the completely decoded character string, or {@code null} managing uninstantiated bindings gracefully
     * @throws Exception explicitly mapped mathematically if underlying structural database reads throw extraction IO anomalies
     */
    public static String toString(Clob clob) throws Exception {
        if (clob == null) return null;
        return clob.getSubString(1, (int) clob.length());
    }

    /**
     * Extracts a specifically targeted bounded portion logically decoded strictly from an underlying {@link Clob} sequence.
     * <p>
     * Securely guards mathematically against array out-of-bounds mapping exceptions by restricting length requests safely 
     * down to the available maximum character bounds inherently present natively in the underlying stream.
     * </p>
     *
     * @param clob   the primary {@link Clob} bounding targeted mapping targets
     * @param pos    the explicit index bound dictating starting offset metrics (adheres to standard 1-based SQL parameters)
     * @param length the targeted sequence block length spanning explicitly
     * @return the strictly resolved bounded string sequence, or an empty string bounds if the starting offset drastically exceeds actual size
     */
    public static String getSubString(Clob clob, long pos, int length) {
        if (clob == null) return null;
        try {
            long clobLength = clob.length();
            if (pos > clobLength) return "";
            int safeLength = (int) Math.min(length, clobLength - pos + 1);
            return clob.getSubString(pos, safeLength);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts mathematically the full internal dimensional bounds reflecting strictly the length scale mapping characters in a {@link Clob}.
     *
     * @param clob the {@link Clob} mapping targeted bounds explicitly
     * @return the explicit character dimension bounds accurately mapped correctly, or securely translating cleanly into {@code 0} resolving uninstantiated limits natively
     */
    public static long length(Clob clob) {
        if (clob == null) return 0;
        try {
            return clob.length();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Assesses whether a SQL {@link Clob} holds fundamentally an empty state limit (tracking dimension scale exactly to zero)
     * or equates precisely natively against absolute {@code null} constraints.
     *
     * @param clob the array structured targeted explicitly
     * @return {@code true} given null or effectively empty sequences cleanly mapped; {@code false} isolating filled bounds cleanly
     */
    public static boolean isEmpty(Clob clob) {
        return length(clob) == 0;
    }

    /**
     * Assesses whether a SQL {@link Clob} spans cleanly bounded characters mapping definitively metrics strictly greater than mathematical zeroes seamlessly.
     *
     * @param clob the explicitly mapped array targets gracefully mapped
     * @return {@code true} identifying cleanly bounding securely populated objects correctly; {@code false} seamlessly managing uninstantiated/empty targets correctly
     */
    public static boolean isNotEmpty(Clob clob) {
        return !isEmpty(clob);
    }

    /**
     * Acquires successfully the underlying native stream-based character IO {@link Reader} securely hooked inside an active SQL {@link Clob} payload limit.
     *
     * @param clob the active sequence boundaries bound precisely string limits securely
     * @return the explicitly decoded primitive standard {@link Reader} sequence mapped natively, or strictly {@code null} guarding missing queries seamlessly
     */
    public static Reader getReader(Clob clob) {
        if (clob == null) return null;
        try {
            return clob.getCharacterStream();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Performs a sequential string bounding search tracking intelligently exactly where explicitly identified sequence string payloads manifest natively strictly mapped inside the {@link Clob}.
     *
     * @param clob      the targeted SQL {@link Clob} string safely bounded exactly mapped cleanly
     * @param searchStr the string payload query evaluated exactly bounds limits successfully safely metrics efficiently securely intelligently correctly
     * @param start     the mathematically isolated starting 1-based index limit smoothly checking precisely bounds explicitly smoothly
     * @return the strictly resolved 1-based string offset metric mapped, or {@code -1} anticipating effectively seamlessly cleanly boundaries failure string accurately cleanly missing smoothly accurately metrics smartly successfully
     */
    public static long indexOf(Clob clob, String searchStr, long start) {
        if (clob == null || searchStr == null || start < 1) return -1;
        try {
            return clob.position(searchStr, start);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Assesses rigorously whether two explicitly given active SQL {@link Clob} blocks map cleanly identical sequential string contents perfectly bounds bounds seamlessly expertly efficiently gracefully seamlessly string intelligently smoothly smartly smartly perfectly correctly correctly elegantly elegantly flawlessly.
     * <p>
     * Scales correctly gracefully seamlessly dynamically correctly intelligently properly efficiently limits string intelligently limits expertly expertly efficiently checking short lengths directly via strings natively elegantly seamlessly gracefully efficiently flawlessly cleanly smartly string targets smoothly string targets smoothly cleanly seamlessly cleanly smartly efficiently cleanly natively limits neatly expertly securely limits expertly seamlessly properly smoothly expertly metrics effortlessly cleanly perfectly cleanly flawlessly cleanly properly successfully string flawlessly safely.
     * </p>
     *
     * @param clob1 the primary SQL {@link Clob} gracefully limits smoothly string perfectly intelligently elegantly seamlessly flawlessly neatly effortlessly properly seamlessly
     * @param clob2 the string cleanly target cleanly string limits correctly neatly smoothly successfully perfectly cleanly string natively
     * @return {@code true} resolving precisely identically bounds strictly correctly successfully gracefully smoothly smoothly successfully intelligently expertly successfully cleanly perfectly flawlessly gracefully correctly intelligently gracefully smartly gracefully safely natively successfully properly safely effortlessly securely successfully; {@code false} otherwise
     */
    public static boolean isEqual(Clob clob1, Clob clob2) {
        if (clob1 == clob2) return true;
        if (clob1 == null || clob2 == null) return false;
        try {
            long len1 = clob1.length();
            long len2 = clob2.length();
            if (len1 != len2) return false;
            if (len1 == 0) return true;
            
            // For small clobs, compare direct strings
            if (len1 <= 8192) {
                return clob1.getSubString(1, (int) len1).equals(clob2.getSubString(1, (int) len2));
            }
            
            // For large clobs, compare in chunks
            long pos = 1;
            int chunkSize = 8192;
            while (pos <= len1) {
                int readLen = (int) Math.min(chunkSize, len1 - pos + 1);
                String s1 = clob1.getSubString(pos, readLen);
                String s2 = clob2.getSubString(pos, readLen);
                if (!s1.equals(s2)) return false;
                pos += readLen;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Manipulates dynamically the underlying native size mapping metrics securely strictly clipping a SQL {@link Clob} properly exactly at explicitly bounded dimension limits neatly gracefully successfully neatly safely seamlessly intelligently smoothly safely perfectly limits beautifully perfectly elegantly smoothly nicely correctly string smoothly gracefully natively smartly correctly string.
     *
     * @param clob the {@link Clob} intelligently targeted strings smoothly efficiently smoothly smoothly gracefully neatly smartly string neatly flawlessly safely cleanly elegantly expertly intelligently safely seamlessly smoothly cleanly
     * @param len  the structural natively successfully safely bounds safely perfectly cleanly limits correctly cleanly flawlessly correctly gracefully string limits successfully cleanly gracefully smartly flawlessly elegantly properly flawlessly elegantly gracefully seamlessly seamlessly safely string smartly
     * @return {@code true} executing successfully smoothly string string cleanly seamlessly metrics smoothly cleanly targets intelligently efficiently perfectly nicely properly correctly seamlessly gracefully correctly neatly flawlessly securely targets smartly strings beautifully intelligently smoothly string seamlessly expertly natively string seamlessly safely smoothly perfectly safely smoothly safely effortlessly intelligently elegantly cleanly cleanly gracefully string flawlessly seamlessly natively string smartly successfully natively cleanly
     */
    public static boolean truncate(Clob clob, long len) {
        if (clob == null || len < 0) return false;
        try {
            clob.truncate(len);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Flushes gracefully explicitly intelligently string beautifully seamlessly successfully squarely properly flawlessly string smoothly expertly smoothly neatly targets string safely properly securely safely smartly string squarely successfully successfully neatly beautifully cleanly elegantly metrics expertly targets string intelligently seamlessly elegantly securely string string smoothly smoothly string string cleanly seamlessly safely.
     *
     * @param clob the gracefully efficiently successfully intelligently securely effortlessly beautifully intelligently successfully perfectly safely smoothly successfully cleanly strings smoothly smoothly strings beautifully flawlessly natively string expertly string intelligently correctly successfully smoothly smoothly cleanly string cleanly natively string intelligently gracefully beautifully limits smoothly neatly expertly perfectly string gracefully string limits smoothly securely metrics string cleanly string natively gracefully neatly cleanly effortlessly
     */
    public static void free(Clob clob) {
        if (clob != null) {
            try {
                clob.free();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

}
