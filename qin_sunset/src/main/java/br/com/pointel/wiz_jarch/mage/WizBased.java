package br.com.pointel.wiz_jarch.mage;

import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.pointel.wiz_jarch.data.Delete;
import br.com.pointel.wiz_jarch.data.Field;
import br.com.pointel.wiz_jarch.data.Insert;
import br.com.pointel.wiz_jarch.data.KeyForeign;
import br.com.pointel.wiz_jarch.data.KeyPrimary;
import br.com.pointel.wiz_jarch.data.Match;
import br.com.pointel.wiz_jarch.data.Nature;
import br.com.pointel.wiz_jarch.data.OrdName;
import br.com.pointel.wiz_jarch.data.Select;
import br.com.pointel.wiz_jarch.data.Table;
import br.com.pointel.wiz_jarch.data.TableHead;
import br.com.pointel.wiz_jarch.data.Typed;
import br.com.pointel.wiz_jarch.data.Update;

/**
 * A comprehensive utility class for executing database operations and mapping JDBC result sets.
 * <p>
 * {@code WizBased} provides static methods to streamline common JDBC tasks, including
 * the dynamic generation of DML objects ({@link Insert}, {@link Select}, {@link Update}, {@link Delete}),
 * execution of parameterized queries and batches, and automatic reflection-based mapping
 * from {@link ResultSet} rows to standard Java objects.
 * </p>
 * <p>
 * It also encapsulates resource management (e.g., quiet closure of connections) and metadata extraction
 * to seamlessly adapt between SQL data types and internal {@link Nature} enums.
 * </p>
 */
public class WizBased {

    private WizBased() {
    }

    /**
     * Constructs an {@link Insert} statement object derived from the provided {@link Table} metadata.
     * <p>
     * This method maps the table's header and field values into the corresponding data structures
     * required for executing a database insertion.
     * </p>
     *
     * @param fromTable the source table providing the schema and data for the insert
     * @return an {@link Insert} object containing the table header and the list of valued fields
     */
	public static Insert makeInsert(Table fromTable) {
	    var result = new Insert();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
	            result.valuedList = fromTable.fieldList.stream().map(Field::toValued).toList();
	        }
	    }
	    return result;
	}

    /**
     * Constructs a {@link Select} statement object derived from the provided {@link Table} metadata.
     * <p>
     * This method maps the table's header, typed fields, and any primary key filters to formulate
     * an object representing a database selection query.
     * </p>
     *
     * @param fromTable the source table providing the schema and filters for the select
     * @return a {@link Select} object containing the table header, field list, and primary key filters
     */
	public static Select makeSelect(Table fromTable) {
	    var result = new Select();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
	            result.fieldList = fromTable.fieldList.stream().map(Field::toTyped).toList();
				var keyFields = fromTable.fieldList.stream().filter(Field::isKeyPrimary).toList();
				if (!keyFields.isEmpty()) {
					result.filterList = keyFields.stream().map(Field::toFilter).toList();
				}
	        }
	    }
	    return result;
	}

    /**
     * Constructs an {@link Update} statement object derived from the provided {@link Table} metadata.
     * <p>
     * The update operation distinguishes between standard fields (used as the new values) and
     * primary key fields (used to construct the {@code WHERE} filter clause).
     * </p>
     *
     * @param fromTable the source table providing the schema, data, and keys for the update
     * @return an {@link Update} object containing the values to be set and the filters to target specific rows
     * @throws IllegalArgumentException if the table lacks non-primary key fields (no values to update) or lacks primary key fields (no filters to target)
     */
	public static Update makeUpdate(Table fromTable) {
	    var result = new Update();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
	            var normalFields = fromTable.fieldList.stream().filter(Field::isNotKeyPrimary).toList();
				if (normalFields.isEmpty()) {
					throw new IllegalArgumentException("Could not create an Update from Table because it has no non-primary key fields.");
				}
				result.valuedList = normalFields.stream().map(Field::toValued).toList();
				var keyFields = fromTable.fieldList.stream().filter(Field::isKeyPrimary).toList();
				if (keyFields.isEmpty()) {
					throw new IllegalArgumentException("Could not create an Update from Table because it has no primary key fields.");
				}
				result.filterList = keyFields.stream().map(Field::toFilter).toList();
	        }
	    }
	    return result;
	}

    /**
     * Constructs a {@link Delete} statement object derived from the provided {@link Table} metadata.
     * <p>
     * The deletion targets rows based exclusively on the primary key fields defined in the table.
     * </p>
     *
     * @param fromTable the source table providing the schema and primary keys for the delete operation
     * @return a {@link Delete} object configured with the primary key filters
     * @throws IllegalArgumentException if the table has no primary key fields to use as filters
     */
	public static Delete makeDelete(Table fromTable) {
	    var result = new Delete();
	    if (fromTable != null) {
	        if (fromTable.hasTableHead()) {
	            result.tableHead = fromTable.tableHead;
	        }
	        if (fromTable.hasFieldList()) {
				var keyFields = fromTable.fieldList.stream().filter(Field::isKeyPrimary).toList();
				if (keyFields.isEmpty()) {
					throw new IllegalArgumentException("Could not create a Delete from Table because it has no primary key fields.");
				}
				result.filterList = keyFields.stream().map(Field::toFilter).toList();
	        }
	    }
	    return result;
	}

    /**
     * Translates a complete {@link ResultSet} into a {@link List} of populated Java objects of the target class.
     * <p>
     * This method iterates through all available rows, internally extracting column metadata to seamlessly
     * match standard constructors or dynamically map values to the corresponding fields.
     * </p>
     *
     * @param <T>     the generic target class type
     * @param result  the {@link ResultSet} positioned before the first row
     * @param onClass the {@link Class} representing the target data object
     * @return a {@link List} containing a newly instantiated and mapped object for each row
     * @throws Exception if iterating the result set, class instantiation, or reflection mappings fail
     */
    public static <T> List<T> mapResults(ResultSet result, Class<T> onClass) throws Exception {
        return WizBased.mapResults(result, null, onClass);
    }

    /**
     * Translates a complete {@link ResultSet} into a {@link List} of populated Java objects,
     * guiding the reflection mapping with an optional explicit field list.
     *
     * @param <T>       the generic target class type
     * @param result    the {@link ResultSet} positioned before the first row
     * @param fieldList a {@link List} of {@link Typed} fields to aid in matching data types; may be {@code null}
     * @param onClass   the {@link Class} representing the target data object
     * @return a {@link List} containing a newly instantiated and mapped object for each row
     * @throws Exception if iterating the result set, class instantiation, or reflection mappings fail
     */
    public static <T> List<T> mapResults(ResultSet result, List<Typed> fieldList, Class<T> onClass) throws Exception {
        var results = new ArrayList<T>();
        while (result.next()) {
            var mapped = WizBased.mapResult(result, fieldList, onClass);
            results.add(mapped);
        }
        return results;
    }

    /**
     * Translates the current row of a {@link ResultSet} into a populated Java object of the target class.
     * <p>
     * This method automatically interprets column types and names to locate an appropriate constructor
     * or inject values directly into object fields.
     * </p>
     *
     * @param <T>     the generic target class type
     * @param result  the {@link ResultSet} currently positioned at the targeted row
     * @param onClass the {@link Class} representing the target data object
     * @return an instance of the target class, populated with the row's data
     * @throws Exception if data extraction, class instantiation, or reflection mappings fail
     */
    public static <T> T mapResult(ResultSet result, Class<T> onClass) throws Exception {
        return WizBased.mapResult(result, null, onClass);
    }

    /**
     * Translates the current row of a {@link ResultSet} into a populated Java object, utilizing an
     * explicit field list for more robust type mapping when available.
     * <p>
     * The process attempts to find an exact matching standard constructor based on column classes.
     * Failing that, it attempts to deduce the best available constructor. For any remaining columns
     * not consumed by the constructor, values are injected directly into instance fields by name.
     * </p>
     *
     * @param <T>       the generic target class type
     * @param result    the {@link ResultSet} currently positioned at the targeted row
     * @param fieldList a {@link List} of {@link Typed} fields to aid in matching data types; may be {@code null}
     * @param onClass   the {@link Class} representing the target data object
     * @return an instance of the target class, populated with the row's data
     * @throws Exception if data extraction, class instantiation, or reflection mappings fail
     */
    public static <T> T mapResult(ResultSet result, List<Typed> fieldList, Class<T> onClass) throws Exception {
        if (WizData.isNatureData(onClass)) {
			return WizData.getOn(result.getObject(1), onClass);
		}
		var colsClasses = WizBased.getColumnsClasses(result);
        var colsValues = WizBased.getColumnsValues(result);
        try {
            var standardOne = onClass.getConstructor(colsClasses);
            return standardOne.newInstance(colsValues);
        } catch (NoSuchMethodException e) {
            Nature[] colsNatures;
            if (fieldList == null || fieldList.isEmpty()) {
                colsNatures = WizBased.getNaturesFrom(result);
            } else {
                colsNatures = fieldList.stream().map(f -> f.type).toArray(Nature[]::new);
            }
            var colsNames = WizBased.getColumnsNames(result);
            var bestOne = WizLang.getBestConstructor(onClass, colsNatures, colsNames);
            if (bestOne == null) {
                bestOne = onClass.getConstructor();
            }
            Object[] argsValues = new Object[bestOne.getParameterCount()];
            Class<?>[] argsTypes = bestOne.getParameterTypes();
            for (int i = 0; i < argsValues.length; i++) {
                argsValues[i] = WizData.getOn(colsValues[i], argsTypes[i]);
            }
            var instance = onClass.cast(bestOne.newInstance(argsValues));
            if (argsValues.length < colsValues.length) {
                var columnsNames = WizBased.getColumnsNames(result);
                for (int i = argsValues.length; i < colsValues.length; i++) {
                    var columnName = columnsNames[i];
                    var field = onClass.getDeclaredField(columnName);
                    var onValue = WizData.getOn(colsValues[i], field.getType());
                    WizLang.forceSetField(field, instance, onValue);
                }
            }
            return instance;
        }
    }

    /**
     * Extracts deep relational schema metadata for a designated table using an active database connection.
     * <p>
     * The resulting {@link Table} object comprehensively defines the schema, including lists of all fields,
     * their data types (translated to internal {@link Nature} enum values), nullability, autoincrement traits, 
     * primary keys, and foreign key relationships.
     * </p>
     *
     * @param tableHead  the metadata outlining the catalog, schema, and name of the table to inspect
     * @param connection an active JDBC {@link Connection} to execute metadata queries against
     * @return a fully constructed {@link Table} object modeling the physical database schema
     * @throws Exception if retrieving schema information via {@link java.sql.DatabaseMetaData} fails
     */
	public static Table getTable(TableHead tableHead, Connection connection) throws Exception {
	    var fieldList = new ArrayList<br.com.pointel.wiz_jarch.data.Field>();
	    var keyPrimaryList = new ArrayList<KeyPrimary>();
	    var keyForeignList = new ArrayList<KeyForeign>();
	    var table = new Table(tableHead, fieldList, keyPrimaryList, keyForeignList);
	    var meta = connection.getMetaData();
	    var primaryRS = meta.getPrimaryKeys(tableHead.catalog, tableHead.schema, tableHead.name);
	    while (primaryRS.next()) {
	        var keyName = primaryRS.getString("PK_NAME");
	        var keyPrimary = keyPrimaryList.stream()
	                .filter(p -> Objects.equals(p.name, keyName))
	                .findFirst().orElse(null);
	        if (keyPrimary == null) {
	            keyPrimary = new KeyPrimary(keyName, new ArrayList<>());
	            keyPrimaryList.add(keyPrimary);
	        }
	        var sequence = primaryRS.getInt("KEY_SEQ");
	        var columnName = primaryRS.getString("COLUMN_NAME");
	        var keyColumn = new OrdName(sequence, columnName);
	        keyPrimary.columnList.add(keyColumn);
	    }
	    var columnsRS = meta.getColumns(tableHead.catalog, tableHead.schema, tableHead.name, "%");
	    while (columnsRS.next()) {
	        final var fieldOrd = columnsRS.getInt("ORDINAL_POSITION");
	        final var fieldName = columnsRS.getString("COLUMN_NAME");
	        final var fieldNature = WizBased.getNatureOfSQL(columnsRS.getInt("DATA_TYPE"));
	        final var fieldSize = columnsRS.getInt("COLUMN_SIZE");
	        final var fieldPrecision = columnsRS.getInt("DECIMAL_DIGITS");
	        final var fieldNotNull = "NO".equals(columnsRS.getString("IS_NULLABLE"));
	        final var fieldAutoIncrement = !"NO".equals(columnsRS.getString("IS_AUTOINCREMENT"));
	        final var fieldAutoGenerated = !"NO".equals(columnsRS.getString("IS_GENERATEDCOLUMN"));
	        final var fieldKeyPrimary = keyPrimaryList.stream().anyMatch(k -> k.columnList.stream().anyMatch(c -> Objects.equals(c.name, fieldName)));
	        final var field = new br.com.pointel.wiz_jarch.data.Field(fieldOrd, fieldName, fieldNature, fieldSize, fieldPrecision, fieldNotNull, fieldAutoIncrement, fieldAutoGenerated, fieldKeyPrimary);
	        field.setTable(table);
	        fieldList.add(field);
	    }
	    var foreignRS = meta.getImportedKeys(tableHead.catalog, tableHead.schema, tableHead.name);
	    while (foreignRS.next()) {
	        var inName = foreignRS.getString("FK_NAME");
	        var outName = foreignRS.getString("PK_NAME");
	        var keyForeign = keyForeignList.stream()
	                .filter(f -> Objects.equals(f.inName, inName) && Objects.equals(f.outName, outName))
	                .findFirst().orElse(null);
	        if (keyForeign == null) {
	            var outCatalog = foreignRS.getString("PKTABLE_CAT");
	            var outSchema = foreignRS.getString("PKTABLE_SCHEM");
	            var outTable = foreignRS.getString("PKTABLE_NAME");
	            var outTableHead = new TableHead(outCatalog, outSchema, outTable);
	            keyForeign = new KeyForeign(inName, outName, outTableHead, new ArrayList<>());
	            keyForeignList.add(keyForeign);
	        }
	        var sequence = foreignRS.getInt("KEY_SEQ");
	        var inColumn = foreignRS.getString("FKCOLUMN_NAME");
	        var outColumn = foreignRS.getString("PKCOLUMN_NAME");
	        var match = new Match(sequence, inColumn, outColumn);
	        keyForeign.matchList.add(match);
	    }
	    return table;
	}

    /**
     * Extracts an array of strictly ordered column labels from the provided {@link ResultSet}.
     *
     * @param results the active {@link ResultSet} to interrogate
     * @return a {@link String} array denoting the column names in sequential order
     * @throws Exception if fetching metadata encounters an error
     */
	public static String[] getColumnsNames(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var names = new String[meta.getColumnCount()];
	    for (int i = 1; i <= names.length; i++) {
	        names[(i - 1)] = meta.getColumnName(i);
	    }
	    return names;
	}

    /**
     * Extracts an array of strictly ordered Java {@link Class} definitions from the provided {@link ResultSet}.
     *
     * @param results the active {@link ResultSet} to interrogate
     * @return a {@link Class} array denoting the mapped Java types for each column
     * @throws Exception if fetching metadata or loading the respective class fails
     */
	public static Class<?>[] getColumnsClasses(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var classes = new Class<?>[meta.getColumnCount()];
	    for (int i = 1; i <= classes.length; i++) {
	        var className = meta.getColumnClassName(i);
	        classes[(i - 1)] = Class.forName(className);
	    }
	    return classes;
	}

    /**
     * Fetches an array of values mapping sequentially to every column on the current row of the {@link ResultSet}.
     *
     * @param results the active {@link ResultSet}, positioned at a valid row
     * @return an {@link Object} array harboring the extracted data points for the current row
     * @throws Exception if object extraction runs into an error
     */
	public static Object[] getColumnsValues(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var values = new Object[meta.getColumnCount()];
	    for (int i = 1; i <= values.length; i++) {
	        values[(i - 1)] = results.getObject(i);
	    }
	    return values;
	}

    /**
     * Translates column types found in the provided {@link ResultSet} into internal {@link Nature} definitions.
     *
     * @param results the active {@link ResultSet} to interrogate
     * @return an array mapping every column's JDBC type to a respective {@link Nature} enum
     * @throws Exception if the conversion process or metadata extraction fails
     */
	public static Nature[] getNaturesFrom(ResultSet results) throws Exception {
	    var meta = results.getMetaData();
	    var natures = new Nature[meta.getColumnCount()];
	    for (int i = 1; i <= natures.length; i++) {
	        natures[(i - 1)] = WizBased.getNatureOfSQL(meta.getColumnType(i));
	    }
	    return natures;
	}

    /**
     * Determines the most appropriate internal {@link Nature} enum corresponding to a standard JDBC type ID.
     *
     * @param jdbcType the integer type code outlined in {@link java.sql.Types}
     * @return the corresponding {@link Nature} categorization for the data type
     * @throws UnsupportedOperationException if the JDBC type does not have a mapped {@link Nature}
     */
	public static Nature getNatureOfSQL(int jdbcType) {
	    switch (jdbcType) {
	        case 16: // BOOLEAN
	            return Nature.Bool;
	        case -7: // BIT
	            return Nature.Bit;
	        case -6: // TINYINT
	            return Nature.Byte;
	        case 5: // SMALLINT
	            return Nature.Small;
	        case 4: // INTEGER
	            return Nature.Int;
	        case -8: // ROWID
	            return Nature.Serial;
	        case -5: // BIGINT
	            return Nature.Long;
	        case 6: // FLOAT
	            return Nature.Float;
	        case 7: // REAL
	            return Nature.Real;
	        case 8: // DOUBLE
	            return Nature.Double;
	        case 2: // NUMERIC
	            return Nature.Numeric;
	        case 3: // DECIMAL
	            return Nature.BigNumeric;
	        case 1: // CHAR
	        case -15: // NCHAR
	        case -11: // NCHAR
	            return Nature.Char;
	        case 12: // VARCHAR
	        case -1: // LONGVARCHAR
	        case -9: // NVARCHAR
	            return Nature.Chars;
	        case 91: // DATE
	            return Nature.Date;
	        case 92: // TIME
	            return Nature.Time;
	        case 93: // TIMESTAMP
	            return Nature.Timestamp;
	        case 2013: // TIME_WITH_TIMEZONE
	        case 2014: // TIMESTAMP_WITH_TIMEZONE
	            return Nature.ZoneTime;
	        case -2: // BINARY
	        case -3: // VARBINARY
	            return Nature.Bytes;
	        case -4: // LONGVARBINARY
	        case 2004: // BLOB
	            return Nature.Blob;
	        case -16: // LONGNVARCHAR
	        case 2005: // CLOB
	            return Nature.Text;
	        case 1111: // OTHER
	        case 2000: // OBJECT
	            return Nature.Object;
	        default:
	            throw new UnsupportedOperationException("Could not identify the value nature of jdbc type: " + jdbcType);
	    }
	}

    /**
     * Binds a variable-length sequence of Java objects as parameter arguments on a given {@link PreparedStatement}.
     *
     * @param statement the active {@link PreparedStatement} awaiting bindings
     * @param params    the arguments to be bound incrementally starting at parameter index 1
     * @throws Exception if setting the parameters raises a JDBC error
     */
	public static void setParams(PreparedStatement statement, Object[] params) throws Exception {
	    for (int i = 0; i < params.length; i++) {
	        statement.setObject(i + 1, params[i]);
	    }
	}

    /**
     * Consumes and outputs all ensuing rows within a {@link ResultSet} to the standard output buffer ({@link System#out}).
     * <p>
     * Every row spans a single line. Inside a row, data columns are visibly segmented using a " | " separator.
     * </p>
     *
     * @param results the target {@link ResultSet} to print
     * @throws Exception if iterating or inspecting the cursor fails
     */
	public static void printAllValues(ResultSet results) throws Exception {
	    WizBased.printAllValues(results, System.out);
	}

    /**
     * Consumes and outputs all ensuing rows within a {@link ResultSet} to a designated {@link PrintStream}.
     * <p>
     * Every row spans a single line. Inside a row, data columns are visibly segmented using a " | " separator.
     * </p>
     *
     * @param results the target {@link ResultSet} to print
     * @param out     the target {@link PrintStream} for data rendering
     * @throws Exception if iterating or inspecting the cursor fails
     */
	public static void printAllValues(ResultSet results, PrintStream out) throws Exception {
	    var count = results.getMetaData().getColumnCount();
	    while (results.next()) {
	        var line = new StringBuilder();
	        for (int i = 1; i <= count; i++) {
	            if (i > 1) {
	                line.append(" | ");
	            }
	            line.append(String.valueOf(results.getObject(i)));
	        }    
	        out.println(line.toString());
	    }
	}

    /**
     * Outputs the column metadata mapping from a given {@link ResultSet} into standard output ({@link System#out}).
     * <p>
     * Yields one line per column detailing index, name, and translated internal {@link Nature}.
     * Example: {@code [1] ID : Int}
     * </p>
     *
     * @param results the active {@link ResultSet} holding the relevant shape data
     * @throws Exception if pulling JDBC metadata structures triggers an error
     */
	public static void printColumnsNamesAndNatures(ResultSet results) throws Exception {
	    WizBased.printColumnsNamesAndNatures(results, System.out);
	}

    /**
     * Outputs the column metadata mapping from a given {@link ResultSet} into a designated {@link PrintStream}.
     * <p>
     * Yields one line per column detailing index, name, and translated internal {@link Nature}.
     * Example: {@code [1] ID : Int}
     * </p>
     *
     * @param results the active {@link ResultSet} holding the relevant shape data
     * @param out     the designated output stream
     * @throws Exception if pulling JDBC metadata structures triggers an error
     */
	public static void printColumnsNamesAndNatures(ResultSet results, PrintStream out) throws Exception {
	    var meta = results.getMetaData();
	    for (int i = 1; i <= meta.getColumnCount(); i++) {
	        out.println(String.format("[%d] %s : %s",
	                i, meta.getColumnName(i), String.valueOf(getNatureOfSQL(meta.getColumnType(i)))));
	    }
	}

    /**
     * Triggers the {@code close()} methodology on a provided {@link AutoCloseable} instance while intercepting
     * and wholly suppressing any subsequent closure exceptions.
     * <p>
     * This method securely handles {@code null} values without generating {@link NullPointerException}.
     * </p>
     *
     * @param closeable the targeted object to be closed quietly (may be null)
     */
	public static void closeQuietly(AutoCloseable closeable) {
	    if (closeable != null) {
	        try {
	            closeable.close();
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	}

    /**
     * Iteratively triggers {@code close()} against a {@link ResultSet}, a {@link Statement}, and finally a {@link Connection},
     * completely suppressing any raised closure exceptions.
     * <p>
     * Valuable strictly as a broad safety net inside finalization blocks.
     * </p>
     *
     * @param conn the connection targeted for closing
     * @param stmt the statement targeted for closing
     * @param rs   the result set targeted for closing
     */
	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
	    closeQuietly(rs);
	    closeQuietly(stmt);
	    closeQuietly(conn);
	}

    /**
     * Executes a transaction abort by invoking {@code rollback()} against the {@link Connection}. Any underlying
     * SQL exceptions spawned during this rollback attempt are muted.
     *
     * @param conn the active transaction connection to be rolled back (may be null)
     */
	public static void rollbackQuietly(Connection conn) {
	    if (conn != null) {
	        try {
	            conn.rollback();
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	}

    /**
     * Executes a transaction finalization by invoking {@code commit()} against the {@link Connection}. Any underlying
     * SQL exceptions spawned during this commit attempt are muted.
     *
     * @param conn the active transaction connection to be committed (may be null)
     */
	public static void commitQuietly(Connection conn) {
	    if (conn != null) {
	        try {
	            conn.commit();
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	}

    /**
     * Interrogates the active catalog database to check for the pre-existence of a uniquely named table.
     *
     * @param conn      a viable {@link Connection} hooked to the target database
     * @param catalog   the explicit database catalog namespace (can be null)
     * @param schema    the explicit database schema namespace (can be null)
     * @param tableName the strict name of the table sought after
     * @return {@code true} if an artifact bearing this table name resolves in the schema; {@code false} otherwise
     * @throws Exception if retrieving structural database metadata faults
     */
	public static boolean tableExists(Connection conn, String catalog, String schema, String tableName) throws Exception {
	    var meta = conn.getMetaData();
	    try (ResultSet rs = meta.getTables(catalog, schema, tableName, null)) {
	        return rs.next();
	    }
	}

    /**
     * Invokes a mutating DML SQL string ({@code INSERT}, {@code UPDATE}, or {@code DELETE}), appropriately substituting 
     * argument values for any parameterized slots designated by question marks ({@code ?}).
     *
     * @param conn   the viable {@link Connection} over which the update traverses
     * @param sql    the query skeleton intended for execution
     * @param params any positional values expected by the parameters outlined within the query
     * @return the total volume of discrete rows successfully updated or wiped by the query
     * @throws Exception if a database manipulation issue develops
     */
	public static int executeUpdate(Connection conn, String sql, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        return stmt.executeUpdate();
	    }
	}

    /**
     * Drives an insertion statement directly into the database engine while distinctly enforcing a request for
     * whatever primary key auto-generation materialized during the insert process.
     *
     * @param conn   the viable {@link Connection} over which the update traverses
     * @param sql    the query skeleton intended for execution
     * @param params any positional values expected by the parameters outlined within the query
     * @return a standalone {@link Object} mapping the primary key emitted for this freshly injected row, or {@code null}
     * @throws Exception if a database manipulation issue develops or if retrieving generated keys fails
     */
	public static Object executeInsertAndGetGeneratedKey(Connection conn, String sql, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        int affected = stmt.executeUpdate();
	        if (affected > 0) {
	            try (ResultSet rs = stmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    return rs.getObject(1);
	                }
	            }
	        }
	        return null;
	    }
	}

    /**
     * Injects a continuous, sequential array of multiple parameter bundles strictly mapped to a singular,
     * re-usable SQL template, vastly bolstering performance through batch optimizations.
     *
     * @param conn       the viable {@link Connection} overseeing the data batch
     * @param sql        the single repeating parameter-bearing statement skeleton
     * @param paramsList the expansive nested array payload housing positional variables applied repetitively
     * @return a primitive {@code int[]} where each array node reflects the row update threshold achieved by the matching batch cycle
     * @throws Exception if the batch aggregation limits out or execution catastrophically faults
     */
	public static int[] executeBatch(Connection conn, String sql, List<Object[]> paramsList) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (paramsList != null) {
	            for (Object[] params : paramsList) {
	                if (params != null) {
	                    setParams(stmt, params);
	                }
	                stmt.addBatch();
	            }
	        }
	        return stmt.executeBatch();
	    }
	}

    /**
     * Projects a query into the database engine, fetching a multi-row {@link ResultSet}, and sequentially mapping
     * its outputs into a clean collection of strictly-typed, instantiated objects via {@link #mapResults(ResultSet, Class)}.
     *
     * @param <T>     the generic layout mapping of the resultant data wrapper instances
     * @param conn    the viable {@link Connection} targeted for selection operations
     * @param sql     the query statement
     * @param onClass the strict reference mapped strictly to the target data structures
     * @param params  any positional values demanded by query variables
     * @return a distinct {@link List} containing parsed object models aligned synchronously with row indexes
     * @throws Exception if execution halts or reflexive bindings malfunction
     */
	public static <T> List<T> queryForList(Connection conn, String sql, Class<T> onClass, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        try (ResultSet rs = stmt.executeQuery()) {
	            return mapResults(rs, onClass);
	        }
	    }
	}

    /**
     * Projects a query into the database engine tailored entirely around selecting one distinct record (e.g. searching via Primary Key).
     * Any extra resulting rows returned beyond the initial element will be ignored.
     *
     * @param <T>     the generic layout mapping of the resultant data wrapper instance
     * @param conn    the viable {@link Connection} targeted for selection operations
     * @param sql     the query statement
     * @param onClass the strict reference mapped strictly to the target data structure
     * @param params  any positional values demanded by query variables
     * @return a purely single-instanced object wrapping the first found database row; or strictly {@code null} upon zero rows
     * @throws Exception if execution halts or reflexive bindings malfunction
     */
	public static <T> T queryForObject(Connection conn, String sql, Class<T> onClass, Object... params) throws Exception {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        if (params != null) {
	            setParams(stmt, params);
	        }
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return mapResult(rs, onClass);
	            }
	            return null;
	        }
	    }
	}

}
