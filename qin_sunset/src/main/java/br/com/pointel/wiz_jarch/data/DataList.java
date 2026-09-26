package br.com.pointel.wiz_jarch.data;

import java.util.List;

/**
 * A list of data values that also participates in the package-wide deep and
 * JSON serialization contract.
 *
 * @param <T> the element type
 */
public interface DataList<T> extends List<T>, Data {
}
