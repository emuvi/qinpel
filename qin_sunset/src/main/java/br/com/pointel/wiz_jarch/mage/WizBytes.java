package br.com.pointel.wiz_jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * A robust utility class designed for managing, transforming, and inspecting raw byte arrays ({@code byte[]}).
 * <p>
 * {@code WizBytes} provides static methods handling standard byte conversions, structural manipulations 
 * (like splitting, reversing, and concatenating arrays), encoding/decoding implementations (Hexadecimal and Base64), 
 * cryptographic hashing (MD5, SHA-1, SHA-256), and gzip stream compression.
 * </p>
 * <p>
 * Null safety is applied universally, typically rendering a natural mathematical evaluation (e.g., returning {@code false},
 * {@code null}, or {@code 0}) upon encountering uninstantiated references.
 * </p>
 */
public class WizBytes {

    /**
     * Standardized structural prefix utilized when serializing byte arrays specifically into Base64 formats.
     * Default marker string: {@code "byte[]^64:"}
     */
    public static String byteArrayOnBase64Prefix = "byte[]^64:";

    private WizBytes() {
    }

    /**
     * Determines whether the given object natively matches or securely translates into a primitive {@code byte[]}.
     * <p>
     * Supported mapping bounds encompass active byte arrays, {@link Blob} and {@link Clob} SQL references,
     * {@link String} formats, standard {@link Number} evaluations, and explicitly mapped {@link Serializable} objects.
     * </p>
     *
     * @param value the target object to evaluate
     * @return {@code true} if the object can natively map into a byte array structure; {@code false} otherwise (or if the input is null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    /**
     * Translates a supported, versatile object format dynamically into a primitive {@code byte[]}.
     * <p>
     * Translating strings actively evaluates against pre-existing structural prefixes (e.g., {@link #byteArrayOnBase64Prefix})
     * to perform on-the-fly Base64 unboxing. Empty strings translate intrinsically to {@code null}. {@link Serializable}
     * items translate cleanly through a managed {@link ObjectOutputStream}.
     * </p>
     *
     * @param value the raw structural parameter slated for byte extraction
     * @return the successfully compiled {@code byte[]}, or {@code null} if the input evaluates as null or an empty string
     * @throws Exception if the targeted parameter falls entirely out of bound or fails unboxing dynamically
     */
    public static byte[] get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return byte[].class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            var blob = Blob.class.cast(value);
            return blob.getBytes(1, (int) blob.length());
        }
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            var clob = Clob.class.cast(value);
            return clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8);
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isEmpty()) return null;
            if (string.startsWith(byteArrayOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(byteArrayOnBase64Prefix.length()));
            } else if (string.startsWith(WizObject.objectOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(WizObject.objectOnBase64Prefix.length()));
            } else {
                return string.getBytes(StandardCharsets.UTF_8);
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return String.valueOf(Number.class.cast(value)).getBytes(StandardCharsets.UTF_8);
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                    var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(Serializable.class.cast(value));
                oos.flush();
                return bos.toByteArray();
            }
        }
        throw new Exception("Could not convert to a byte[] value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a raw byte array directly into a prefixed Base64 structural string.
     * <p>
     * The finalized string prepends {@link #byteArrayOnBase64Prefix} to mark its underlying encoded nature.
     * </p>
     *
     * @param value the byte array slated for formatting
     * @return the generated prefix-stamped Base64 string, or an empty format ({@code ""}) given a null array
     */
    public static String format(byte[] value) {
        if (value == null) return "";
        return byteArrayOnBase64Prefix + WizBytes.encodeToBase64(value);
    }

    /**
     * Encodes a raw byte array squarely into a Base64 structural string utilizing standard platform mapping.
     *
     * @param bytes the raw byte layout slated for encoding
     * @return the mathematically accurate Base64 output string
     */
    public static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Unboxes a raw Base64-formatted string dynamically into its underlying byte array structure.
     *
     * @param formatted the targeted Base64 string slated for decoding
     * @return the correctly extracted byte array mapping
     * @throws IllegalArgumentException if the parameter structurally breaks Base64 bounds or contains illegible mapping bits
     */
    public static byte[] decodeFromBase64(String formatted) {
        return Base64.getDecoder().decode(formatted);
    }

    /**
     * Encodes a standard byte array mapped directly into a textual Hexadecimal sequence.
     * <p>
     * Formatting employs lowercase character bindings ({@code 0-9, a-f}) generating exactly two hex digits for each contained byte.
     * </p>
     *
     * @param bytes the raw byte sequence aimed for mapping
     * @return the mathematically precise hexadecimal sequence translated strictly into a string
     */
    public static String encodeToHex(byte[] bytes) {
        var hexString = new StringBuilder(2 * bytes.length);
        for (byte element : bytes) {
            var hex = Integer.toHexString(0xff & element);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Extracts and calculates the MD5 cryptographic checksum representing the targeted file.
     *
     * @param file the mapped file target on the system path
     * @return a mapped Hexadecimal string defining the complete MD5 hash footprint
     * @throws Exception if read privileges fail, target path remains unresolved, or internal hash algorithm breaks down
     */
    public static String getMD5(File file) throws Exception {
        return WizBytes.getMD5(Files.readAllBytes(file.toPath()));
    }

    /**
     * Calculates the explicit MD5 cryptographic checksum representing an unboxed byte array.
     *
     * @param bytes the raw structural array undergoing hash processing
     * @return a mapped Hexadecimal string defining the computed MD5 hash footprint
     * @throws Exception if internal {@link MessageDigest} generation evaluates unexpectedly
     */
    public static String getMD5(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("MD5").digest(bytes));
    }

    /**
     * Extracts and calculates the SHA-1 cryptographic checksum representing the targeted file.
     *
     * @param file the mapped file target on the system path
     * @return a mapped Hexadecimal string defining the complete SHA-1 hash footprint
     * @throws Exception if read privileges fail, target path remains unresolved, or internal hash algorithm breaks down
     */
    public static String getSHA1(File file) throws Exception {
        return WizBytes.getSHA1(Files.readAllBytes(file.toPath()));
    }

    /**
     * Calculates the explicit SHA-1 cryptographic checksum representing an unboxed byte array.
     *
     * @param bytes the raw structural array undergoing hash processing
     * @return a mapped Hexadecimal string defining the computed SHA-1 hash footprint
     * @throws Exception if internal {@link MessageDigest} generation evaluates unexpectedly
     */
    public static String getSHA1(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("SHA-1").digest(bytes));
    }

    /**
     * Extracts and calculates the SHA-256 cryptographic checksum representing the targeted file.
     *
     * @param file the mapped file target on the system path
     * @return a mapped Hexadecimal string defining the complete SHA-256 hash footprint
     * @throws Exception if read privileges fail, target path remains unresolved, or internal hash algorithm breaks down
     */
    public static String getSHA256(File file) throws Exception {
        return WizBytes.getSHA256(Files.readAllBytes(file.toPath()));
    }

    /**
     * Calculates the explicit SHA-256 cryptographic checksum representing an unboxed byte array.
     *
     * @param bytes the raw structural array undergoing hash processing
     * @return a mapped Hexadecimal string defining the computed SHA-256 hash footprint
     * @throws Exception if internal {@link MessageDigest} generation evaluates unexpectedly
     */
    public static String getSHA256(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    }

    /**
     * Converts an object dynamically into a primitive byte array, mapping any thrown translation
     * errors smoothly to a prescribed fallback array.
     *
     * @param value     the raw parameter queued for translation
     * @param orDefault the default byte array fallback utilized upon aborted processing limits
     * @return the successfully generated byte array, or the designated default upon failure constraints
     */
    public static byte[] get(Object value, byte[] orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Translates a formalized hexadecimal string cleanly into its native internal primitive byte array block.
     * <p>
     * Strictly verifies mathematical requirements enforcing string sequences explicitly bound to even lengths
     * encompassing allowed character mapping ranges ({@code 0-9, a-f, A-F}).
     * </p>
     *
     * @param hexString the sequence evaluated for decoding bounds
     * @return the accurately translated primitive byte array mapping, or {@code null} given identically null input
     * @throws IllegalArgumentException if the sequence length breaks mathematically uneven bounds or encompasses invalid textual tokens
     */
    public static byte[] decodeFromHex(String hexString) {
        if (hexString == null) return null;
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            int high = Character.digit(hexString.charAt(i), 16);
            int low = Character.digit(hexString.charAt(i + 1), 16);
            if (high == -1 || low == -1) {
                throw new IllegalArgumentException("Invalid hex character in string");
            }
            bytes[i / 2] = (byte) ((high << 4) + low);
        }
        return bytes;
    }

    /**
     * Functionally bridges two separate byte arrays into a newly allocated sequenced array.
     *
     * @param a the leading byte array sequence
     * @param b the trailing byte array sequence appended sequentially
     * @return the combined primitive {@code byte[]} clone, or simply a cloned copy if one parameter was selectively null
     */
    public static byte[] concat(byte[] a, byte[] b) {
        if (a == null) return b != null ? b.clone() : null;
        if (b == null) return a.clone();
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * Harvests an explicit sequential sub-block extracted from an overarching byte array.
     * <p>
     * Out-of-bounds parameters structurally resolve cleanly without failure by coercing constraints safely back
     * into naturally allowed array parameters limit bounds.
     * </p>
     *
     * @param bytes  the baseline array slated for extraction
     * @param start  the explicit zero-indexed extraction point constraint
     * @param length the target offset length limit dictating block volume
     * @return the generated bounded primitive array, or {@code null} given identically null input mapping
     */
    public static byte[] subBytes(byte[] bytes, int start, int length) {
        if (bytes == null) return null;
        if (start < 0) start = 0;
        if (length < 0) length = 0;
        if (start + length > bytes.length) length = bytes.length - start;
        if (length <= 0) return new byte[0];
        byte[] result = new byte[length];
        System.arraycopy(bytes, start, result, 0, length);
        return result;
    }

    /**
     * Assesses whether a primitive byte array holds structurally an empty state limit bounds (length mapping to zero)
     * or equates precisely to an absolute {@code null} reference.
     *
     * @param bytes the array targeted for measurement
     * @return {@code true} given null or completely empty dimensions; {@code false} explicitly otherwise
     */
    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * Checks if a structured byte array securely encompasses at least one populated index dimension element.
     *
     * @param bytes the array targeted for bounds measurement
     * @return {@code true} provided the array maintains a valid mapped length mathematically greater than 0
     */
    public static boolean isNotEmpty(byte[] bytes) {
        return !isEmpty(bytes);
    }

    /**
     * Executes a linear search scanning sequentially to evaluate the earliest position explicitly containing
     * the designated targeted byte.
     *
     * @param bytes the primary byte array undergoing search parsing
     * @param b     the isolated byte value to secure against
     * @return the explicit valid zero-based index mapping locating the element natively, or {@code -1} assuming total failure limits
     */
    public static int indexOf(byte[] bytes, byte b) {
        if (bytes == null) return -1;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == b) return i;
        }
        return -1;
    }

    /**
     * Executes a reverse linear search sequentially to extract the ultimate final trailing sequence occurrence containing
     * the mathematically exact target byte.
     *
     * @param bytes the designated sequential array undergoing backward resolution parsing
     * @param b     the functional targeting byte offset to match against
     * @return the correctly bounded ultimate array index offset matching logically, or {@code -1} anticipating failure sequences
     */
    public static int lastIndexOf(byte[] bytes, byte b) {
        if (bytes == null) return -1;
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] == b) return i;
        }
        return -1;
    }

    /**
     * Explicitly identifies if a structured primitive byte array securely houses at least a singular instance mapping
     * functionally to the provided targeted query byte.
     *
     * @param bytes the array queried
     * @param b     the byte evaluated
     * @return {@code true} if exactly mapped during linear scan blocks; {@code false} otherwise
     */
    public static boolean contains(byte[] bytes, byte b) {
        return indexOf(bytes, b) >= 0;
    }

    /**
     * Assesses if a primitive byte array initiates sequentially matching exactly bounded components encompassing an explicitly given suffix mapping.
     *
     * @param bytes  the structural byte array evaluating bounding alignments
     * @param prefix the exact array expected positioned immediately at start boundaries
     * @return {@code true} if prefix mapping encompasses successfully bounds precisely; {@code false} if constraints fall short dynamically
     */
    public static boolean startsWith(byte[] bytes, byte[] prefix) {
        if (bytes == null || prefix == null) return false;
        if (prefix.length > bytes.length) return false;
        for (int i = 0; i < prefix.length; i++) {
            if (bytes[i] != prefix[i]) return false;
        }
        return true;
    }

    /**
     * Assesses if a primitive byte array concludes sequentially matching exactly bounded components encompassing an explicitly given suffix mapping.
     *
     * @param bytes  the structural byte array evaluating bounds offsets
     * @param suffix the strict suffix anticipated tracking precisely the ending length metrics
     * @return {@code true} resolving exact match bounds securely; {@code false} mapping failures bounds completely or null references
     */
    public static boolean endsWith(byte[] bytes, byte[] suffix) {
        if (bytes == null || suffix == null) return false;
        if (suffix.length > bytes.length) return false;
        int offset = bytes.length - suffix.length;
        for (int i = 0; i < suffix.length; i++) {
            if (bytes[offset + i] != suffix[i]) return false;
        }
        return true;
    }

    /**
     * Validates mathematically if two distinct primitive byte arrays encapsulate functionally exact mirrored components encompassing exact sequencing.
     *
     * @param a the leading array metric compared logically
     * @param b the trailing target array component mapped for comparison bounds
     * @return {@code true} securing strict identity overlap precisely; {@code false} reflecting unequal value offsets
     */
    public static boolean equals(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }

    /**
     * Compares two byte arrays lexicographically treating explicitly each constituent byte element distinctly mapped
     * identically via primitive unsigned translation values bounds dynamically.
     * <p>
     * Uninstantiated null bindings natively structure systematically translating as implicitly 'lesser' constraints relative to initialized sequences.
     * </p>
     *
     * @param a the structural leading bound mapping sequentially
     * @param b the mapping parameter array translating lexicographical ordering offsets
     * @return a definitively negative constraint mapping resolving {@code a < b}, zero matching symmetrically {@code a == b}, or identically positive bounding tracking logically {@code a > b}
     */
    public static int compare(byte[] a, byte[] b) {
        if (a == b) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++) {
            int unsignedA = a[i] & 0xFF;
            int unsignedB = b[i] & 0xFF;
            if (unsignedA != unsignedB) {
                return Integer.compare(unsignedA, unsignedB);
            }
        }
        return Integer.compare(a.length, b.length);
    }

    /**
     * Generates explicitly an entirely reallocated primitive array mapping the initial sequence components exactly structurally inversed functionally.
     *
     * @param bytes the targeting array block structurally configured for reverse translation ordering
     * @return the manipulated swapped structured clone cleanly initialized bounded array mapping
     */
    public static byte[] reverse(byte[] bytes) {
        if (bytes == null) return null;
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[bytes.length - 1 - i];
        }
        return result;
    }

    /**
     * Executes natively mapped active stream components functionally compressing an input array structurally mapped cleanly via standard GZIP configurations constraints.
     *
     * @param bytes the raw uncompressed standard sequence mapped sequentially
     * @return the newly constructed strictly compressed bounded mapping block natively utilizing native GZIP formats
     * @throws Exception explicitly if mapping algorithms abort unexpectedly generating IO sequence boundaries errors
     */
    public static byte[] compress(byte[] bytes) throws Exception {
        if (bytes == null) return null;
        try (var bos = new ByteArrayOutputStream(bytes.length);
             var gzip = new GZIPOutputStream(bos)) {
            gzip.write(bytes);
            gzip.close();
            return bos.toByteArray();
        }
    }

    /**
     * Systematically translates mapped active dynamically-compressed array formats structurally unpacking bound sequences functionally mapping native uncompressed configurations algorithms cleanly utilizing standard GZIP configurations constraints.
     *
     * @param bytes the actively compressed bounds array mapped tracking sequential constraints
     * @return the cleanly native strictly decompressed baseline mapping block natively utilizing explicitly native primitive array bindings
     * @throws Exception explicitly mapped mathematically aborting algorithm parameters bounds evaluating formatting boundaries errors
     */
    public static byte[] decompress(byte[] bytes) throws Exception {
        if (bytes == null) return null;
        try (var bis = new ByteArrayInputStream(bytes);
             var gzip = new GZIPInputStream(bis);
             var bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
    }

}
