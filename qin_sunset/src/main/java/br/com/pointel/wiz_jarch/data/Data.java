package br.com.pointel.wiz_jarch.data;

import br.com.pointel.wiz_jarch.flow.FixVals;
import br.com.pointel.wiz_jarch.mage.WizData;

/**
 * Marker interface for domain data objects that support deep operations,
 * fixed-value processing, and JSON serialization.
 */
public interface Data extends Base, FixVals {

    /**
     * Deserializes JSON text into the requested data type.
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
