package br.com.pointel.wiz_jarch.mage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A utility class for safely managing, inspecting, and manipulating {@link List} objects.
 * <p>
 * This class provides null-safe operations for list traversal, sorting, partitioning, 
 * filtering, and data extraction, abstracting away boilerplate boundary checks.
 * </p>
 */
public class WizList {

    private WizList() {}

    /**
     * Creates an {@link ArrayList} from the given varargs.
     *
     * @param <T>   the generic type of the elements
     * @param items the elements to populate the list with
     * @return a new {@link List} containing the provided elements, or an empty list if the varargs array is null
     */
    @SafeVarargs
    public static <T> List<T> of(T... items) {
        var list = new ArrayList<T>();
        if (items != null) {
            Collections.addAll(list, items);
        }
        return list;
    }

    /**
     * Checks if a {@link List} is {@code null} or strictly empty.
     *
     * @param list the list to check
     * @return {@code true} if the list is null or empty, {@code false} otherwise
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checks if a {@link List} is not {@code null} and contains at least one element.
     *
     * @param list the list to check
     * @return {@code true} if the list is safely populated, {@code false} otherwise
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * Safely retrieves the first element of a {@link List}.
     *
     * @param <T>  the generic type of the elements
     * @param list the list to query
     * @return the first element, or {@code null} if the list is null or empty
     */
    public static <T> T first(List<T> list) {
        return isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * Safely retrieves the last element of a {@link List}.
     *
     * @param <T>  the generic type of the elements
     * @param list the list to query
     * @return the last element, or {@code null} if the list is null or empty
     */
    public static <T> T last(List<T> list) {
        return isNotEmpty(list) ? list.get(list.size() - 1) : null;
    }

    /**
     * Safely retrieves an element at the specified index.
     *
     * @param <T>   the generic type of the elements
     * @param list  the list to query
     * @param index the zero-based index
     * @return the element at the given index, or {@code null} if the list is null or the index is out of bounds
     */
    public static <T> T get(List<T> list, int index) {
        if (isEmpty(list) || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * Inserts a value at the specified index within a newly instantiated {@link List}.
     * <p>
     * If the target index is greater than the current size of the list, 
     * the list is automatically padded with {@code null} values up to the target index.
     * </p>
     *
     * @param <T>   the generic type of the elements
     * @param index the zero-based index to insert at
     * @param value the value to insert
     * @return a new list containing the inserted value
     */
    public static <T> List<T> insert(int index, T value) {
        return insert(index, value, null, null);
    }

    /**
     * Inserts a value at the specified index within the provided {@link List}.
     * <p>
     * If the target index is greater than the current size of the list, 
     * the list is automatically padded with {@code null} values up to the target index.
     * </p>
     *
     * @param <T>    the generic type of the elements
     * @param index  the zero-based index to insert at
     * @param value  the value to insert
     * @param onList the list to mutate (if null, a new list is generated)
     * @return the mutated or newly instantiated list
     */
    public static <T> List<T> insert(int index, T value, List<T> onList) {
        return insert(index, value, onList, null);
    }
    
    /**
     * Inserts a value at the specified index within the provided {@link List}.
     * <p>
     * If the target index is greater than the current size of the list, 
     * the list is automatically padded with the specified {@code withDefault} padding up to the target index.
     * </p>
     *
     * @param <T>         the generic type of the elements
     * @param index       the zero-based index to insert at
     * @param value       the value to insert
     * @param onList      the list to mutate (if null, a new list is generated)
     * @param withDefault the padding element to use for index gaps
     * @return the mutated or newly instantiated list
     */
    public static <T> List<T> insert(int index, T value, List<T> onList, T withDefault) {
        if (onList == null) {
            onList = new ArrayList<>();
        }
        while (onList.size() <= index) {
            onList.add(withDefault);
        }
        onList.set(index, value);
        return onList;
    }

    /**
     * Filters a {@link List} based on a {@link Predicate}, returning a safe new list containing matching elements.
     *
     * @param <T>       the generic type of the elements
     * @param list      the original list to evaluate
     * @param predicate the condition to evaluate elements against
     * @return a new list containing the filtered elements (empty if the original list is null/empty or predicate is null)
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        if (isEmpty(list) || predicate == null) {
            return new ArrayList<>();
        }
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Maps elements of a {@link List} using a mapping {@link Function}, generating a new list with transformed elements.
     *
     * @param <T>    the source generic type
     * @param <R>    the target generic type
     * @param list   the original list to transform
     * @param mapper the transformation function
     * @return a new list encapsulating transformed data
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        if (isEmpty(list) || mapper == null) {
            return new ArrayList<>();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * Joins the {@link String#valueOf(Object)} payload of each element into a single {@link String} separated by a delimiter.
     *
     * @param <T>       the generic type of the elements
     * @param list      the list to concatenate
     * @param delimiter the separator string (empty string is used if null)
     * @return the joined string result
     */
    public static <T> String join(List<T> list, String delimiter) {
        if (isEmpty(list)) {
            return "";
        }
        return list.stream()
                   .map(String::valueOf)
                   .collect(Collectors.joining(delimiter != null ? delimiter : ""));
    }

    /**
     * Reverses the element order of a {@link List}, emitting the result as a new list instance without mutating the original.
     *
     * @param <T>  the generic type of the elements
     * @param list the list to reverse
     * @return a newly populated and reversed list
     */
    public static <T> List<T> reverse(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        var reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    /**
     * Sorts a {@link List} using the provided {@link Comparator}, outputting a distinct new list without modifying the original.
     *
     * @param <T>        the generic type of the elements
     * @param list       the list to sort
     * @param comparator the comparator definition driving the sort
     * @return a newly populated and sorted list
     */
    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        var sorted = new ArrayList<>(list);
        if (comparator != null) {
            sorted.sort(comparator);
        }
        return sorted;
    }

    /**
     * Strips all duplicate elements from a {@link List} while meticulously preserving exact insertion order.
     *
     * @param <T>  the generic type of the elements
     * @param list the list to evaluate
     * @return a new list stripped of duplicate entries
     */
    public static <T> List<T> unique(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    /**
     * Partitions a monolithic {@link List} into a structured list of sublists, where each bucket respects a specified maximum size.
     *
     * @param <T>  the generic type of the elements
     * @param list the list to partition
     * @param size the maximum size boundary for individual chunks
     * @return a parent list encompassing the smaller chunked lists
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        var partitions = new ArrayList<List<T>>();
        if (isEmpty(list) || size <= 0) {
            return partitions;
        }
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(new ArrayList<>(list.subList(i, Math.min(i + size, list.size()))));
        }
        return partitions;
    }
    
}
