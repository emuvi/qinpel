package br.com.pointel.wiz_jarch.data;

import java.util.Map;

/**
 * A map of data values that participates in the shared deep and JSON
 * serialization contract.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface DataMap<K, V> extends Map<K, V>, Data {
}
