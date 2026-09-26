package br.com.pointel.wiz_jarch.data;

import java.util.Set;

/**
 * A set of data values that participates in the shared deep and JSON
 * serialization contract.
 *
 * @param <T> the element type
 */
public interface DataSet<T> extends Set<T>, Data {
}
