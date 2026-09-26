package br.com.pointel.wiz_jarch.mage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import br.com.pointel.wiz_jarch.data.Pair;

/**
 * A comprehensive utility class for operations on arrays and array-like collections.
 * <p>
 * {@code WizArray} provides static methods for common tasks such as searching,
 * transforming, filtering, and manipulating both primitive and object arrays.
 * It is designed to be null-safe and handles various edge cases gracefully.
 * </p>
 * <p>
 * Additionally, it provides interoperability methods to check and convert
 * collections (like {@link List}, {@link Set}, and {@link Map}) into standard
 * object arrays.
 * </p>
 */
public class WizArray {
    
    private WizArray() {
    }

    /**
     * Determines whether the given object is an array or an array-like collection.
     * <p>
     * An object is considered array-like if it is a standard Java array, or if it
     * implements {@link List}, {@link Set}, or {@link Map}.
     * </p>
     *
     * @param value the object to evaluate
     * @return {@code true} if the object is an array, list, set, or map; {@code false} otherwise (or if null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return value.getClass().isArray()
            || WizLang.isChildOf(value.getClass(), List.class)
            || WizLang.isChildOf(value.getClass(), Set.class)
            || WizLang.isChildOf(value.getClass(), Map.class);
    }

    /**
     * Converts an array-like object into a standard {@code Object[]} array.
     * <p>
     * This method handles raw arrays, {@link List}s, {@link Set}s, and {@link Map}s.
     * In the case of a {@code Map}, it is converted into an array of {@link Pair} objects,
     * where each pair represents a key-value entry from the map.
     * </p>
     *
     * @param value the array-like object to convert (array, {@link List}, {@link Set}, or {@link Map})
     * @return an {@code Object[]} representation of the given value, or {@code null} if the input is {@code null}
     * @throws IllegalArgumentException if the provided value cannot be converted into an {@code Object[]}
     */
    public static Object[] get(Object value) {
        if (value == null) return null;
        if (value.getClass().isArray()) {
            return (Object[]) value;
        }
        if (WizLang.isChildOf(value.getClass(), List.class)) {
            return List.class.cast(value).toArray();
        }
        if (WizLang.isChildOf(value.getClass(), Set.class)) {
            return Set.class.cast(value).toArray();
        }
        if (WizLang.isChildOf(value.getClass(), Map.class)) {
            Map<?, ?> map = Map.class.cast(value);
            Object[] result = new Object[map.size()];
            int i = 0;
            for (var entry : map.entrySet()) {
                result[i++] = new Pair<>(entry.getKey(), entry.getValue());
            }
            return result;
        }
        throw new IllegalArgumentException("Could not convert to Object[] the value of class: " + value.getClass().getName());
    }

    /**
     * Checks if a specific value exists within the given object array.
     * <p>
     * This method uses {@link Objects#equals(Object, Object)} for comparison,
     * safely handling {@code null} values for both the target value and the array elements.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param value   the value to search for
     * @param onArray the array to search in
     * @return {@code true} if the array contains the given value; {@code false} otherwise (or if the array is null)
     */
    public static <T> boolean has(T value, T... onArray) {
        if (onArray != null) {
            for (Object daMatriz : onArray) {
                if (Objects.equals(value, daMatriz)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if all the values from the first array exist within the second array.
     * <p>
     * The method determines if the {@code onArray} contains every single element
     * present in the {@code values} array. The comparison is done using 
     * {@link Objects#equals(Object, Object)}.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param values  the array of target values to search for
     * @param onArray the array to search in
     * @return {@code true} if all values are found in the target array; {@code false} if any is missing or if either array is null
     */
    public static <T> boolean has(T[] values, T[] onArray) {
        if (values == null || onArray == null) {
            return false;
        }
        var founds = new boolean[values.length];
        for (Object item : onArray) {
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(values[i], item)) {
                    founds[i] = true;
                }
            }
        }
        for (var found : founds) {
            if (!found) {
                return false;
            }
        }
        return true; 
    }

    /**
     * Checks if any value from the first array exists within the second array.
     * <p>
     * This acts as an intersection check. If at least one element from the
     * {@code values} array is found in {@code onArray}, it returns {@code true}.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param values  the array of target values to search for
     * @param onArray the array to search in
     * @return {@code true} if at least one value is found in the target array; {@code false} otherwise
     */
    public static <T> boolean hasAny(T[] values, T[] onArray) {
        if (values == null || onArray == null) {
            return false;
        }
        for (Object item : onArray) {
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(values[i], item)) {
                    return true;
                }
            }
        }
        return false; 
    }

    /**
     * Checks if a boolean value exists within the given boolean array.
     *
     * @param value   the boolean value to search for
     * @param onArray the boolean array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(boolean value, boolean... onArray) {
        if (onArray != null) {
            for (boolean daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a byte value exists within the given byte array.
     *
     * @param value   the byte value to search for
     * @param onArray the byte array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(byte value, byte... onArray) {
        if (onArray != null) {
            for (byte daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a short value exists within the given short array.
     *
     * @param value   the short value to search for
     * @param onArray the short array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(short value, short... onArray) {
        if (onArray != null) {
            for (short daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a char value exists within the given char array.
     *
     * @param value   the char value to search for
     * @param onArray the char array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(char value, char... onArray) {
        if (onArray != null) {
            for (char daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if an int value exists within the given int array.
     *
     * @param value   the int value to search for
     * @param onArray the int array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(int value, int... onArray) {
        if (onArray != null) {
            for (int daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a long value exists within the given long array.
     *
     * @param value   the long value to search for
     * @param onArray the long array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(long value, long... onArray) {
        if (onArray != null) {
            for (long daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a float value exists within the given float array.
     * <p>
     * This method relies on {@link Float#compare(float, float)} to ensure
     * accurate floating-point comparison, properly handling NaN and zero cases.
     * </p>
     *
     * @param value   the float value to search for
     * @param onArray the float array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(float value, float... onArray) {
        if (onArray != null) {
            for (float daMatriz : onArray) {
                if (Float.compare(daMatriz, value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a double value exists within the given double array.
     * <p>
     * This method relies on {@link Double#compare(double, double)} to ensure
     * accurate double-precision floating-point comparison, properly handling NaN and zero cases.
     * </p>
     *
     * @param value   the double value to search for
     * @param onArray the double array to search in
     * @return {@code true} if the value is found; {@code false} otherwise
     */
    public static boolean has(double value, double... onArray) {
        if (onArray != null) {
            for (double daMatriz : onArray) {
                if (Double.compare(daMatriz, value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Inserts a value into an array at a specific index.
     * <p>
     * A new array is created with an increased size. The specified value is placed
     * at the {@code index}, and all existing elements at or after the index are
     * shifted one position to the right. If the provided index is greater than the
     * length of the array, the array is padded with {@code null}s up to the index.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param index   the position at which to insert the new value
     * @param value   the value to be inserted
     * @param onArray the original array
     * @return a new array containing the inserted value, or {@code null} if the original array is null
     */
    @SuppressWarnings("all")
    public static <T> T[] insert(int index, T value, T... onArray) {
        if (onArray == null) {
            return null;
        }
        var result = Arrays.copyOf(onArray, Math.max(onArray.length + 1, index + 1));
        result[index] = value;
        for (var i = index; i < onArray.length; i++) {
            result[i + 1] = onArray[i];
        }
        return result;
    }

    /**
     * Filters out all {@code null} elements from the provided array.
     * <p>
     * A new array is returned containing only the non-null elements, 
     * preserving their original relative order.
     * </p>
     *
     * @param <T>      the type of the array elements
     * @param elements the array to filter
     * @return a new array devoid of {@code null} elements, or {@code null} if the input array is null
     */
    @SuppressWarnings("all")
    public static <T> T[] getNotNull(T... elements) {
        if (elements == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T element : elements) {
            if (element != null) {
                list.add(element);
            }
        }
        var result = Arrays.copyOf(elements, list.size());
        for (var i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Instantiates a new array of the specified size and populates it with a single value.
     *
     * @param <T>   the type of the array elements
     * @param clazz the {@link Class} representing the component type of the array
     * @param value the value to populate the entire array with
     * @param size  the length of the new array
     * @return a newly created array of the requested size, filled with the provided value
     */
    @SuppressWarnings("all")
    public static <T> T[] make(Class<T> clazz, T value, int size) {
        var result = (T[]) Array.newInstance(clazz, size);
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Returns the index of the first occurrence of the specified value in the array.
     * <p>
     * Comparison is performed using {@link Objects#equals(Object, Object)}, making it null-safe.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param value   the value to search for
     * @param onArray the array to traverse
     * @return the zero-based index of the first occurrence, or {@code -1} if the value is not found
     */
    public static <T> int indexOf(T value, T... onArray) {
        return indexOf(value, 0, onArray);
    }

    /**
     * Returns the index of the first occurrence of the specified value in the array,
     * starting the search from a designated index.
     *
     * @param <T>        the type of the array elements
     * @param value      the value to search for
     * @param startIndex the index to commence the search from (negative values are treated as 0)
     * @param onArray    the array to traverse
     * @return the zero-based index of the first occurrence starting from {@code startIndex}, or {@code -1} if not found
     */
    public static <T> int indexOf(T value, int startIndex, T... onArray) {
        if (onArray == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < onArray.length; i++) {
            if (Objects.equals(value, onArray[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified value in the array.
     * <p>
     * The array is searched backwards starting from the last element.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param value   the value to search for
     * @param onArray the array to traverse
     * @return the zero-based index of the last occurrence, or {@code -1} if the value is not found
     */
    public static <T> int lastIndexOf(T value, T... onArray) {
        if (onArray == null) {
            return -1;
        }
        for (int i = onArray.length - 1; i >= 0; i--) {
            if (Objects.equals(value, onArray[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the specified array is {@code null} or contains zero elements.
     *
     * @param <T>   the type of the array elements
     * @param array the array to inspect
     * @return {@code true} if the array is null or its length is 0; {@code false} otherwise
     */
    public static <T> boolean isEmpty(T... array) {
        return array == null || array.length == 0;
    }

    /**
     * Checks if the specified array is neither {@code null} nor empty.
     *
     * @param <T>   the type of the array elements
     * @param array the array to inspect
     * @return {@code true} if the array is not null and has at least one element; {@code false} otherwise
     */
    public static <T> boolean isNotEmpty(T... array) {
        return !isEmpty(array);
    }

    /**
     * Appends the specified element to the end of an array.
     * <p>
     * This creates a new array that is one element larger than the original,
     * copying all existing elements before inserting the new one at the end.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param array   the original array to append to
     * @param element the new element to append
     * @return a new array with the element appended, or {@code null} if the original array is null
     */
    @SuppressWarnings("all")
    public static <T> T[] add(T[] array, T element) {
        if (array == null) {
            return null;
        }
        T[] result = Arrays.copyOf(array, array.length + 1);
        result[array.length] = element;
        return result;
    }

    /**
     * Removes the element at a specific index from the array.
     * <p>
     * A new array is returned with a length reduced by one, containing all elements
     * except the one situated at the specified {@code index}. If the index is out of bounds,
     * the original array is returned unaltered.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param index   the zero-based index of the element to be removed
     * @param onArray the array from which to remove the element
     * @return a new array with the element removed, or the original array if the index is invalid
     */
    @SuppressWarnings("all")
    public static <T> T[] remove(int index, T... onArray) {
        if (onArray == null || index < 0 || index >= onArray.length) {
            return onArray;
        }
        T[] result = Arrays.copyOf(onArray, onArray.length - 1);
        System.arraycopy(onArray, 0, result, 0, index);
        if (index < onArray.length - 1) {
            System.arraycopy(onArray, index + 1, result, index, onArray.length - index - 1);
        }
        return result;
    }
    
    /**
     * Removes the first occurrence of the specified value from the array.
     * <p>
     * The array is searched from the beginning, and if the value is found, a new array
     * is created excluding that first matching element. If the value does not exist,
     * the original array is returned.
     * </p>
     *
     * @param <T>     the type of the array elements
     * @param value   the value to remove
     * @param onArray the array from which to remove the value
     * @return a new array with the first occurrence removed, or the original array if the value wasn't found
     */
    @SuppressWarnings("all")
    public static <T> T[] removeElement(T value, T... onArray) {
        int index = indexOf(value, onArray);
        if (index == -1) {
            return onArray;
        }
        return remove(index, onArray);
    }

    /**
     * Concatenates two arrays sequentially into a single new array.
     * <p>
     * The resulting array contains all elements of {@code array1} followed by all
     * elements of {@code array2}. If one array is null, a clone of the other is returned.
     * </p>
     *
     * @param <T>    the type of the array elements
     * @param array1 the first array
     * @param array2 the second array, to be appended after the first
     * @return a new concatenated array, or {@code null} if both inputs are null
     */
    @SuppressWarnings("all")
    public static <T> T[] concat(T[] array1, T... array2) {
        if (array1 == null) {
            return array2 != null ? array2.clone() : null;
        }
        if (array2 == null) {
            return array1.clone();
        }
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Reverses the order of the elements within the given array.
     * <p>
     * A new array is returned, leaving the original array completely untouched.
     * </p>
     *
     * @param <T>   the type of the array elements
     * @param array the array whose elements should be reversed
     * @return a new array with the elements in reverse order, or {@code null} if the input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] reverse(T... array) {
        if (array == null) {
            return null;
        }
        T[] result = array.clone();
        int i = 0;
        int j = result.length - 1;
        while (j > i) {
            T tmp = result[j];
            result[j] = result[i];
            result[i] = tmp;
            j--;
            i++;
        }
        return result;
    }

    /**
     * Extracts a continuous sub-section from the specified array.
     * <p>
     * The extracted section begins at {@code startIndexInclusive} and ends right before
     * {@code endIndexExclusive}. Indices are safely bound to the array's length; out-of-bound
     * indices will not throw exceptions.
     * </p>
     *
     * @param <T>                 the type of the array elements
     * @param array               the source array to extract from
     * @param startIndexInclusive the beginning index, inclusive
     * @param endIndexExclusive   the ending index, exclusive
     * @return a new array containing the requested sub-range, or {@code null} if the input array is null
     */
    @SuppressWarnings("all")
    public static <T> T[] subArray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return Arrays.copyOf(array, 0);
        }
        return Arrays.copyOfRange(array, startIndexInclusive, endIndexExclusive);
    }

    /**
     * Combines all elements of the array into a single {@link String}, separated by a delimiter.
     * <p>
     * Elements are converted to strings using their {@link Object#toString()} method.
     * </p>
     *
     * @param <T>       the type of the array elements
     * @param array     the array of elements to join
     * @param separator the string to place between each element
     * @return the joined string, or {@code null} if the input array is null
     */
    public static <T> String join(T[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder buf = new StringBuilder();
        buf.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            buf.append(separator).append(array[i]);
        }
        return buf.toString();
    }

    /**
     * Evaluates each element of an array against a {@link Predicate}, returning a new array
     * composed only of the elements that satisfied the condition.
     *
     * @param <T>       the type of the array elements
     * @param array     the original array to filter
     * @param predicate the condition each element must pass to be included
     * @return a new array containing the elements that matched the predicate, or {@code null} if any input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] filter(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T item : array) {
            if (predicate.test(item)) {
                list.add(item);
            }
        }
        T[] result = Arrays.copyOf(array, list.size());
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Transforms every element in the array to a new form using a {@link Function}.
     * <p>
     * A new array of the target class type is created and populated with the results
     * of applying the mapper function to each original element.
     * </p>
     *
     * @param <T>    the type of the original array elements
     * @param <R>    the type of the resulting array elements
     * @param array  the source array
     * @param mapper the function applied to each element to generate a new value
     * @param clazz  the {@link Class} representing the component type of the resulting array
     * @return a new array containing the transformed elements, or {@code null} if any input is null
     */
    @SuppressWarnings("all")
    public static <T, R> R[] map(T[] array, Function<T, R> mapper, Class<R> clazz) {
        if (array == null || mapper == null || clazz == null) {
            return null;
        }
        R[] result = (R[]) Array.newInstance(clazz, array.length);
        for (int i = 0; i < array.length; i++) {
            result[i] = mapper.apply(array[i]);
        }
        return result;
    }

    /**
     * Retrieves the first element in the array that satisfies the provided {@link Predicate}.
     *
     * @param <T>       the type of the array elements
     * @param array     the array to search
     * @param predicate the condition to evaluate
     * @return the first element that matches the condition, or {@code null} if no match is found or inputs are null
     */
    public static <T> T first(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return null;
        }
        for (T item : array) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Verifies whether at least one element in the array meets the given condition.
     *
     * @param <T>       the type of the array elements
     * @param array     the array to evaluate
     * @param predicate the condition to test elements against
     * @return {@code true} if any element matches; {@code false} otherwise (or if inputs are null)
     */
    public static <T> boolean anyMatch(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return false;
        }
        for (T item : array) {
            if (predicate.test(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies whether all elements in the array meet the given condition.
     * <p>
     * An empty array inherently satisfies this condition.
     * </p>
     *
     * @param <T>       the type of the array elements
     * @param array     the array to evaluate
     * @param predicate the condition to test elements against
     * @return {@code true} if every element matches (or if the array is empty); {@code false} otherwise
     */
    public static <T> boolean allMatch(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return false;
        }
        if (array.length == 0) {
            return true;
        }
        for (T item : array) {
            if (!predicate.test(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies whether no elements in the array meet the given condition.
     *
     * @param <T>       the type of the array elements
     * @param array     the array to evaluate
     * @param predicate the condition to test elements against
     * @return {@code true} if absolutely no elements match; {@code false} otherwise
     */
    public static <T> boolean noneMatch(T[] array, Predicate<T> predicate) {
        return !anyMatch(array, predicate);
    }

    /**
     * Filters out duplicate elements from the array, keeping only unique occurrences.
     * <p>
     * The relative insertion order of the unique elements is preserved.
     * </p>
     *
     * @param <T>   the type of the array elements
     * @param array the source array
     * @return a new array containing only distinct elements, or {@code null} if the input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] distinct(T[] array) {
        if (array == null) {
            return null;
        }
        Set<T> set = new LinkedHashSet<>(Arrays.asList(array));
        T[] result = Arrays.copyOf(array, set.size());
        int i = 0;
        for (T item : set) {
            result[i++] = item;
        }
        return result;
    }

    /**
     * Interchanges the elements located at two designated indices within the array.
     * <p>
     * If any of the indices are out of bounds, the operation gracefully aborts without
     * throwing an exception.
     * </p>
     *
     * @param <T>   the type of the array elements
     * @param array the array to modify in-place
     * @param i     the index of the first element to swap
     * @param j     the index of the second element to swap
     */
    public static <T> void swap(T[] array, int i, int j) {
        if (array == null || i < 0 || j < 0 || i >= array.length || j >= array.length) {
            return;
        }
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Randomizes the order of the elements in the array in-place.
     * <p>
     * This implementation utilizes the Fisher-Yates shuffle algorithm to ensure an
     * unbiased, uniform random distribution of the elements.
     * </p>
     *
     * @param <T>   the type of the array elements
     * @param array the array to be randomly shuffled
     */
    public static <T> void shuffle(T[] array) {
        if (array == null) {
            return;
        }
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            swap(array, i, index);
        }
    }

    /**
     * Sorts the array in-place based on the rules dictated by a custom {@link Comparator}.
     *
     * @param <T>        the type of the array elements
     * @param array      the array to be sorted
     * @param comparator the logic defining the ordering of the elements
     */
    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            return;
        }
        Arrays.sort(array, comparator);
    }

    /**
     * Identifies the minimum (smallest) element in the array using a provided {@link Comparator}.
     *
     * @param <T>        the type of the array elements
     * @param array      the array to evaluate
     * @param comparator the logic determining which element is smaller
     * @return the smallest element found, or {@code null} if the array is null or empty
     */
    public static <T> T min(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length == 0) {
            return null;
        }
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comparator.compare(array[i], min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Identifies the maximum (largest) element in the array using a provided {@link Comparator}.
     *
     * @param <T>        the type of the array elements
     * @param array      the array to evaluate
     * @param comparator the logic determining which element is larger
     * @return the largest element found, or {@code null} if the array is null or empty
     */
    public static <T> T max(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length == 0) {
            return null;
        }
        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comparator.compare(array[i], max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Computes the total number of elements in the array that fulfill the given condition.
     *
     * @param <T>       the type of the array elements
     * @param array     the array to tally
     * @param predicate the condition required for an element to be counted
     * @return the integer count of elements matching the predicate
     */
    public static <T> int count(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return 0;
        }
        int c = 0;
        for (T item : array) {
            if (predicate.test(item)) {
                c++;
            }
        }
        return c;
    }

    /**
     * Consolidates a two-dimensional array (an array of arrays) into a single, flat one-dimensional array.
     * <p>
     * This effectively concatenates all the sub-arrays sequentially. Null sub-arrays
     * within the main array are safely ignored and skipped over.
     * </p>
     *
     * @param <T>   the type of the array elements
     * @param array the 2D array to be flattened
     * @return a single 1D array encompassing all elements of the sub-arrays, or {@code null} if the input is null or solely comprised of null sub-arrays
     */
    @SuppressWarnings("all")
    public static <T> T[] flatten(T[][] array) {
        if (array == null) {
            return null;
        }
        int size = 0;
        T[] firstNonNull = null;
        for (T[] sub : array) {
            if (sub != null) {
                size += sub.length;
                if (firstNonNull == null) {
                    firstNonNull = sub;
                }
            }
        }
        if (firstNonNull == null) {
            return null;
        }
        T[] result = Arrays.copyOf(firstNonNull, size);
        int destPos = 0;
        for (T[] sub : array) {
            if (sub != null) {
                System.arraycopy(sub, 0, result, destPos, sub.length);
                destPos += sub.length;
            }
        }
        return result;
    }
}
