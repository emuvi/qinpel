package br.com.pointel.wiz_jarch.mage;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;

import br.com.pointel.wiz_jarch.data.Nature;
import br.com.pointel.wiz_jarch.flow.FixBool;
import br.com.pointel.wiz_jarch.flow.FixByte;
import br.com.pointel.wiz_jarch.flow.FixChar;
import br.com.pointel.wiz_jarch.flow.FixChars;
import br.com.pointel.wiz_jarch.flow.FixDouble;
import br.com.pointel.wiz_jarch.flow.FixFloat;
import br.com.pointel.wiz_jarch.flow.FixInt;
import br.com.pointel.wiz_jarch.flow.FixLong;
import br.com.pointel.wiz_jarch.flow.FixObject;
import br.com.pointel.wiz_jarch.flow.FixShort;
import br.com.pointel.wiz_jarch.flow.FixVals;
import br.com.pointel.wiz_jarch.flow.MapName;
import br.com.pointel.wiz_jarch.flow.NotFix;
import br.com.pointel.wiz_jarch.flow.NotFixEnvs;
import br.com.pointel.wiz_jarch.flow.NotFixNulls;

/**
 * A central utility class providing powerful reflection, object manipulation,
 * application state resolution, and general language extensions.
 */
public class WizLang {

    private static final Logger log = LoggerFactory.getLogger(WizLang.class);

    private WizLang() {}

    private static String title = null;

    /**
     * Retrieves the application title, which defaults to the simple name of the main class.
     *
     * @return the application title
     */
    public static String getTitle() {
        if (title == null) {
            title = WizLang.getVidlusMainClassSimpleName();
        }
        return title;
    }

    /**
     * Overrides the application title.
     *
     * @param title the new title
     */
    public static void setTitle(String title) {
        WizLang.title = title;
    }

    private static String name = null;

    /**
     * Retrieves a sanitized application name (lowercase, parameter-friendly) derived from the title.
     *
     * @return the sanitized application name
     */
    public static String getName() {
        if (name == null) {
            name = WizString.getParameterName(getTitle()).toLowerCase();
        }
        return name;
    }

    /**
     * Overrides the sanitized application name.
     *
     * @param name the new name
     */
    public static void setName(String name) {
        WizLang.name = name;
    }

    /**
     * Performs a deep clone of a {@link Serializable} object.
     *
     * @param <T>   the type of the object
     * @param value the object to clone
     * @return a deep copy of the object, or {@code null} if the input is null
     * @throws RuntimeException if serialization fails
     */
    public static <T extends Serializable> T deepClone(final T value) {
        try {
            if (value == null) return null;
            return SerializationUtils.clone(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Performs a deep equality check between two objects using reflection.
     * <p>
     * It compares internal members and handles arrays recursively.
     * </p>
     *
     * @param valueA the first object
     * @param valueB the second object
     * @return {@code true} if deeply equal, {@code false} otherwise
     * @throws RuntimeException if reflection fails
     */
    public static boolean deepEquals(Object valueA, Object valueB) {
        try {
            if (valueB == valueA) return true;
            if (valueA == null || valueB == null) return false;
            if (!valueA.getClass().equals(valueB.getClass())) return false;
            if (WizArray.is(valueA)) {
                final var valueAItems = WizArray.get(valueA);
                final var valueBItems = WizArray.get(valueB);
                return Objects.deepEquals(valueAItems, valueBItems);
            } else {
                final var valueAMembers = WizLang.getValuesFromMembers(valueA);
                final var valueBMembers = WizLang.getValuesFromMembers(valueB);
                return Objects.deepEquals(valueAMembers, valueBMembers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Computes a deep hash code for an object by hashing all of its reflective members.
     *
     * @param value the object to hash
     * @return the computed hash code
     * @throws RuntimeException if reflection fails
     */
    public static int deepHash(Object value) {
        try {
            if (WizArray.is(value)) {
                final var valueItems = WizArray.get(value);
                return Objects.hash(valueItems);
            } else {
                final var valueMembers = WizLang.getValuesFromMembers(value);
                return Objects.hash(valueMembers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all fully qualified class names available in the current class loader.
     *
     * @return a {@link Set} of class names
     */
    public static Set<String> getAllClasses() {
        return getAllClasses(WizLang.class.getClassLoader());
    }

    /**
     * Retrieves all fully qualified class names available in the specified class loader.
     *
     * @param classLoader the class loader to scan
     * @return a {@link Set} of class names, or an empty set if scanning fails
     */
    public static Set<String> getAllClasses(ClassLoader classLoader) {
        try {
            var classPath = ClassPath.from(classLoader);
            var classes = classPath.getAllClasses();
            var result = new TreeSet<String>();
            for (var info : classes) {
                result.add(info.getName());
            }
            return result;
        } catch (Exception e) {
            log.error("Error getting all classes", e);
            return java.util.Collections.emptySet();
        }
    }

    /**
     * Retrieves the primitive equivalent of a wrapper class (e.g., {@link Integer} -> {@code int.class}).
     *
     * @param ofClazz the wrapper class
     * @return the primitive class, or the original class if it has no primitive equivalent
     */
    public static Class<?> getPrimitive(Class<?> ofClazz) {
        if (ofClazz == null) {
            return null;
        }
        if (ofClazz.equals(Boolean.class)) {
            return boolean.class;
        }
        if (ofClazz.equals(Byte.class)) {
            return byte.class;
        } else if (ofClazz.equals(Character.class)) {
            return char.class;
        } else if (ofClazz.equals(Double.class)) {
            return double.class;
        } else if (ofClazz.equals(Float.class)) {
            return float.class;
        } else if (ofClazz.equals(Integer.class)) {
            return int.class;
        } else if (ofClazz.equals(Long.class)) {
            return long.class;
        } else if (ofClazz.equals(Short.class)) {
            return short.class;
        } else if (ofClazz.equals(Void.class)) {
            return void.class;
        } else {
            return ofClazz;
        }
    }

    /**
     * Retrieves the wrapper equivalent of a primitive class (e.g., {@code int.class} -> {@link Integer}).
     *
     * @param clazz the primitive class
     * @return the wrapper class, or the original class if it is not primitive
     */
    public static Class<?> stripPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazz.equals(byte.class)) {
            return Byte.class;
        } else if (clazz.equals(char.class)) {
            return Character.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        } else if (clazz.equals(float.class)) {
            return Float.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        } else if (clazz.equals(long.class)) {
            return Long.class;
        } else if (clazz.equals(short.class)) {
            return Short.class;
        } else if (clazz.equals(void.class)) {
            return Void.class;
        } else {
            return clazz;
        }
    }

    /**
     * Checks if a class represents a primitive type.
     *
     * @param clazz the class to check
     * @return {@code true} if primitive, {@code false} otherwise
     */
    public static Boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.equals(boolean.class) ||
            clazz.equals(byte.class) ||
            clazz.equals(char.class) ||
            clazz.equals(double.class) ||
            clazz.equals(float.class) ||
            clazz.equals(int.class) ||
            clazz.equals(long.class) ||
            clazz.equals(short.class) ||
            clazz.equals(void.class);
    }

    /**
     * Checks if a class is a child of another class or implements the interface.
     * <p>
     * Safely ignores primitive/wrapper boundaries.
     * </p>
     *
     * @param child  the potential child class
     * @param parent the potential parent class
     * @return {@code true} if child can be assigned to parent
     */
    public static boolean isChildOf(Class<?> child, Class<?> parent) {
        if (child == null || parent == null) {
            return false;
        }
        child = stripPrimitive(child);
        parent = stripPrimitive(parent);
        if (child.isArray() && parent.isArray()) {
            Class<?> childComponent = child.getComponentType();
            Class<?> parentComponent = parent.getComponentType();
            return isChildOf(childComponent, parentComponent);
        }
        return parent.isAssignableFrom(child);
    }

    /**
     * Checks if a class is a child of any of the given parent classes.
     *
     * @param child   the potential child class
     * @param parents the array of potential parent classes
     * @return {@code true} if child can be assigned to at least one parent
     */
    public static boolean isChildOf(Class<?> child, Class<?>... parents) {
        if (child == null || parents == null || parents.length == 0) {
            return false;
        }
        for (Class<?> parent : parents) {
            if (isChildOf(child, parent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the best matching constructor for a class based on provided {@link Nature} objects and parameter names.
     *
     * @param fromClass  the class to inspect
     * @param valNatures the nature descriptors of the arguments
     * @param valNames   the names of the arguments
     * @return the best matching constructor, or {@code null} if none is found
     */
    public static Constructor<?> getBestConstructor(Class<?> fromClass, Nature[] valNatures, String[] valNames) {
        if (fromClass == null || valNatures == null || valNatures.length == 0) {
            return null;
        }
        int bestScore = 0;
        Constructor<?> bestOne = null;
        for (Constructor<?> constructor : fromClass.getConstructors()) {
            var paramTypes = constructor.getParameterTypes();
            if (paramTypes.length > valNatures.length) {
                continue;
            }
            int score = 0;
            boolean match = true;
            var paramNotes = constructor.getParameterAnnotations();
            for (int iP = 0; iP < paramTypes.length; iP++) {
                String hasMapName = null;
                for (var note : paramNotes[iP]) {
                    if (note instanceof MapName mapName) {
                        hasMapName = mapName.value();
                        break;
                    }
                }
                if (hasMapName != null && valNames != null && valNames.length > iP) {
                    if (Objects.equals(hasMapName, valNames[iP])) {
                        score = score + 100;
                    } else {
                        match = false;
                        break;
                    }
                }
                var paramType = paramTypes[iP];
                var valNature = valNatures[iP];
                boolean found = false;
                for (int iN = 0; iN < valNature.getMapTypes().length; iN++) {
                    Class<?> valNatureType = valNature.getMapTypes()[iN];
                    if (isChildOf(valNatureType, paramType)) {
                        score = score + (100 - (iN * 10));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    match = false;
                    break;
                }
            }
            if (match && score > bestScore) {
                bestOne = constructor;
                bestScore = score;
            }
        }
        return bestOne;
    }

    /**
     * Extracts an array of values representing the state of all declared fields of an object.
     *
     * @param ofObject the object to inspect
     * @return an array of field values, or {@code null} if the input is null
     * @throws Exception if reflection fails
     */
    public static Object[] getValuesFromMembers(Object ofObject) throws Exception {
        if (ofObject == null) {
            return null;
        }
        var fields = ofObject.getClass().getDeclaredFields();
        var values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            var field = fields[i];
            var accessible = field.canAccess(ofObject);
            if (!accessible) {
                field.setAccessible(true);
            }
            values[i] = field.get(ofObject);
            if (!accessible) {
                field.setAccessible(false);
            }
        }
        return values;
    }

    /**
     * Interrogates all declared fields of an object and automatically instantiates/injects 
     * default values or recursively fixes nulls based on specific flow annotations 
     * (e.g., {@link FixBool}, {@link FixByte}, {@link FixObject}).
     *
     * @param ofValue the object to fix
     * @throws Exception if reflection fails
     */
    public static void fixNullsOnMembers(Object ofValue) throws Exception {
        for (var field : ofValue.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(ofValue);
            if (value == null || value.toString().isEmpty()) {
                if (field.isAnnotationPresent(NotFix.class) 
                        || field.isAnnotationPresent(NotFixNulls.class)) {
                    continue;
                } else if (field.isAnnotationPresent(FixBool.class)) {
                    value = field.getAnnotation(FixBool.class).value();
                } else if (field.isAnnotationPresent(FixByte.class)) {
                    value = field.getAnnotation(FixByte.class).value();
                } else if (field.isAnnotationPresent(FixChar.class)) {
                    value = field.getAnnotation(FixChar.class).value();
                } else if (field.isAnnotationPresent(FixChars.class)) {
                    value = field.getAnnotation(FixChars.class).value();
                } else if (field.isAnnotationPresent(FixDouble.class)) {
                    value = field.getAnnotation(FixDouble.class).value();
                } else if (field.isAnnotationPresent(FixFloat.class)) {
                    value = field.getAnnotation(FixFloat.class).value();
                } else if (field.isAnnotationPresent(FixInt.class)) {
                    value = field.getAnnotation(FixInt.class).value();
                } else if (field.isAnnotationPresent(FixLong.class)) {
                    value = field.getAnnotation(FixLong.class).value();
                } else if (field.isAnnotationPresent(FixShort.class)) {
                    value = field.getAnnotation(FixShort.class).value();
                } else if (field.isAnnotationPresent(FixObject.class)) {
                    var valueStr = field.getAnnotation(FixObject.class).value();
                    var valueType = field.getAnnotation(FixObject.class).type();
                    value = WizData.fromJson(valueStr, valueType);
                } else {
                    try {
                        value = field.getType().getConstructor().newInstance();
                    } catch (Exception e) {
                        log.error("Error creating instance of class: " + field.getType().getCanonicalName(), e);
                    }
                }
                if (value instanceof FixVals fixable) {
                    fixable.fixNulls();
                }
                try {
                    WizLang.forceSetField(field, ofValue, value);
                } catch (Exception e) {
                    log.error("Error setting field value: " + field.getName(), e);
                }
            }
        }
    }

    /**
     * Interrogates all declared fields of an object and automatically resolves environment variable
     * syntax inside {@link String} fields unless prevented by annotations like {@link NotFixEnvs}.
     *
     * @param ofValue the object to fix
     * @throws IllegalAccessException if field access is blocked
     * @throws Exception              if resolution fails
     */
    public static void fixEnvsOnMembers(Object ofValue) throws IllegalAccessException, Exception {
        for (var field : ofValue.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(ofValue);
            if (field.isAnnotationPresent(NotFix.class)
                    || field.isAnnotationPresent(NotFixEnvs.class)) {
                continue;
            } 
            if (value instanceof FixVals fixable) {
                fixable.fixEnvs();
            }
            if (value instanceof String strValue) {
                value = WizString.replaceEnvVars(strValue);
                try {
                    WizLang.forceSetField(field, ofValue, value);
                } catch (Exception e) {
                    log.error("Error setting field value: " + field.getName(), e);
                }
            }
        }
    }

    /**
     * Bypasses access controls (e.g., private fields) to forcibly set a value on an object's field.
     *
     * @param field    the field to modify
     * @param ofObject the object instance containing the field
     * @param toValue  the value to inject
     * @throws Exception if reflection fails
     */
    public static void forceSetField(Field field, Object ofObject, Object toValue) throws Exception {
        var accessible = field.canAccess(ofObject);
        if (!accessible) {
            field.setAccessible(true);
        }
        field.set(ofObject, toValue);
        if (!accessible) {
            field.setAccessible(false);
        }
    }

    /**
     * Inspects the active stack trace to intelligently locate the "com.vidlus" root application class, 
     * returning its simple class name.
     *
     * @return the simple name of the main class, or an empty string if not found
     */
    public static String getVidlusMainClassSimpleName() {
        var result = getVidlusMainClassName();
        if (result == null) {
            return "";
        }
        return result.substring(result.lastIndexOf('.') + 1);
    }

    /**
     * Inspects the active stack traces to intelligently locate the "com.vidlus" root application class, 
     * returning its fully qualified class name.
     *
     * @return the fully qualified name of the main class, or {@code null} if not found
     */
    public static String getVidlusMainClassName() {
        try {
            var mainThread = WizThread.getMainThread();
            if (mainThread != null) {
                var stack = mainThread.getStackTrace();
                if (stack != null && stack.length > 0) {
                    for (int i = stack.length - 1; i >= 0; i--) {
                        var elem = stack[i];
                        if (elem != null && elem.getClassName() != null && elem.getClassName().startsWith("com.vidlus")) {
                            return elem.getClassName();
                        }
                    }
                }
            }
            var allStackTraces = Thread.getAllStackTraces();
            if (allStackTraces != null) {
                for (var entry : allStackTraces.entrySet()) {
                    var thread = entry.getKey();
                    if (mainThread != null && thread != null && thread.getId() == mainThread.getId()) {
                        continue;
                    }
                    var stack = entry.getValue();
                    if (stack != null && stack.length > 0) {
                        for (int i = stack.length - 1; i >= 0; i--) {
                            var elem = stack[i];
                            if (elem != null && elem.getClassName() != null && elem.getClassName().startsWith("com.vidlus")) {
                                return elem.getClassName();
                            }
                        }
                    }
                }
            }
        } catch (Throwable t) {
            // ignore exceptions
        }
        return null;
    }

    /**
     * Retrieves the current user's home directory across different OS environments.
     *
     * @return the user home directory as a {@link File}
     */
    public static File getUserDir() {
        return new File(System.getProperty("user.home"));
    }

    /**
     * Retrieves (and creates if necessary) the centralized application data directory (~/.vidlus/[AppName]).
     *
     * @return the application directory as a {@link File}
     */
    public static File getVidlusAppDir() {
        var result = new File(getUserDir(), ".vidlus");
        if (!result.exists()) {
            result.mkdirs();
        }
        result = new File(result, WizLang.getName());
        if (!result.exists()) {
            result.mkdirs();
        }
        return result;
    }

    // =========================================================================
    // GENERAL JAVA LANG UTILITIES
    // =========================================================================

    /**
     * Safely casts an object to a class, returning {@code null} if incompatible or null.
     *
     * @param <T>   the target type
     * @param obj   the object to cast
     * @param clazz the class representing the target type
     * @return the casted object, or {@code null} if casting is not possible
     */
    public static <T> T cast(Object obj, Class<T> clazz) {
        if (obj != null && clazz != null && clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        return null;
    }

    /**
     * Returns the default value if the primary object is null.
     *
     * @param <T>          the type of the objects
     * @param object       the primary object
     * @param defaultValue the fallback object
     * @return the primary object if it's not null, otherwise the fallback object
     */
    public static <T> T defaultIfNull(T object, T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * Checks if an object is logically "empty".
     * <p>
     * Covers Strings (isBlank), Collections (isEmpty), Maps (isEmpty), and Arrays (length == 0).
     * </p>
     *
     * @param obj the object to check
     * @return {@code true} if the object is null or conceptually empty, {@code false} otherwise
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof CharSequence cs) return cs.isEmpty() || cs.toString().isBlank();
        if (obj instanceof Collection<?> col) return col.isEmpty();
        if (obj instanceof Map<?, ?> map) return map.isEmpty();
        if (obj.getClass().isArray()) return Array.getLength(obj) == 0;
        return false;
    }

    /**
     * Safely instantiates a class using its no-args constructor, catching all exceptions.
     *
     * @param <T>   the generic class type
     * @param clazz the class to instantiate
     * @return a new instance of the class, or {@code null} if instantiation fails
     */
    public static <T> T newInstance(Class<T> clazz) {
        if (clazz == null) return null;
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("Failed to instantiate: " + clazz.getName(), e);
            return null;
        }
    }

    /**
     * Silently puts the current thread to sleep without forcing you to catch {@link InterruptedException}.
     *
     * @param millis the number of milliseconds to sleep
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
