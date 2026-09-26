package br.com.pointel.wiz_jarch.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pointel.wiz_jarch.flow.Base36;
import br.com.pointel.wiz_jarch.mage.WizData;
import br.com.pointel.wiz_jarch.mage.WizString;

public class EOrmBase extends EOrm {

    private static final Logger log = LoggerFactory.getLogger(EOrmBase.class);

    public EOrmBase(Connection link) {
        super(link);
    }

    @Override
    public Heads getHeads() throws Exception {
        var meta = getLink().getMetaData();
        var set = meta.getTables(null, null, "%", new String[] {"TABLE"});
        var result = new Heads();
        while (set.next()) {
            result.add(new TableHead(set.getString(1), set.getString(2), set.getString(3)));
        }
        return result;
    }

    @Override
    public void create(Table table, boolean ifNotExists) throws Exception {
        if (ifNotExists) {
            var meta = getLink().getMetaData();
            var rs = meta.getTables(null, null, table.tableHead.name.toUpperCase(), null);
            if (!rs.next()) {
                rs = meta.getTables(null, null, table.tableHead.name.toLowerCase(), null);
            }
            if (!rs.next()) {
                rs = meta.getTables(null, null, table.tableHead.name, null);
            }
            if (rs.next()) {
                log.info("Table already exists: {}", table.tableHead.getCatalogSchemaName());
                return;
            }
        }
        var builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(table.getCatalogSchemaName());
        builder.append(" (");
        var primaryKeyFromFields = new ArrayList<String>();
        for (var i = 0; i < table.fieldList.size(); i++) {
            var field = table.fieldList.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(makeNature(field));
            if (Boolean.TRUE.equals(field.keyPrimary)) {
                primaryKeyFromFields.add(field.name);
            }
        }
        if (table.keyPrimaryList != null) {
            for (var primaryKey : table.keyPrimaryList) {
                builder.append(", PRIMARY KEY ");
                if (primaryKey.name != null && !primaryKey.name.isEmpty()) {
                    builder.append(primaryKey.name);
                }
                builder.append(" ( ");
                var equalsToPrimaryKeyOnFields = true;
                for (var i = 0; i < primaryKey.columnList.size(); i++) {
                    var column = primaryKey.columnList.get(i);
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(column.name);
                    if (equalsToPrimaryKeyOnFields && i < primaryKeyFromFields.size()) {
                        if (!Objects.equals(column.name, primaryKeyFromFields.get(i))) {
                            equalsToPrimaryKeyOnFields = false;
                        }
                    } else {
                        equalsToPrimaryKeyOnFields = false;
                    }
                }
                if (equalsToPrimaryKeyOnFields && primaryKeyFromFields.size() == primaryKey.columnList.size()) {
                    primaryKeyFromFields.clear();
                }
                builder.append(" ) ");
            }
        }
        if (!primaryKeyFromFields.isEmpty()) {
            builder.append(", PRIMARY KEY (");
            for (int i = 0; i < primaryKeyFromFields.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(primaryKeyFromFields.get(i));
            }
            builder.append(")");
        }
        if (table.keyForeignList != null) {
            for (var foreignKey : table.keyForeignList) {
                builder.append(", FOREIGN KEY ");
                if (foreignKey.inName != null && !foreignKey.inName.isEmpty()) {
                    builder.append(foreignKey.inName);
                }
                builder.append(" ( ");
                for (var i = 0; i < foreignKey.matchList.size(); i++) {
                    var match = foreignKey.matchList.get(i);
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(match.inColumn);
                }
                builder.append(" ) ");
                builder.append(" REFERENCES ");
                builder.append(foreignKey.outTableHead.getCatalogSchemaName());
                builder.append(" ( ");
                for (var i = 0; i < foreignKey.matchList.size(); i++) {
                    var match = foreignKey.matchList.get(i);
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(match.outColumn);
                }
                builder.append(" ) ");
            }
        }
        builder.append(")");
        final String sql = builder.toString();
        log.debug("Creating table with SQL: {}", sql);
        try (var stmt = getLink().createStatement()) {
            stmt.execute(sql);
            log.info("Table created successfully: {}", table.tableHead.getCatalogSchemaName());
        }
    }

    @Override
    public void create(Index index, boolean ifNotExists) throws Exception {
        if (index.tableHead == null || index.tableHead.name == null || index.tableHead.name.isEmpty()) {
            throw new Exception("Could not create index because: tableHead not defined");
        }
        if (index.fieldList == null || index.fieldList.isEmpty()) {
            throw new Exception("Could not create index because: fieldList not defined");
        }
        String indexName = index.name;
        if (indexName == null || indexName.isEmpty()) {
            var nameBuilder = new StringBuilder("idx_").append(index.tableHead.name);
            for (var field : index.fieldList) {
                nameBuilder.append("_").append(field.name);
            }
            indexName = nameBuilder.toString();
        }
        if (ifNotExists) {
            var meta = getLink().getMetaData();
            var rs = meta.getIndexInfo(null, null, index.tableHead.name.toUpperCase(), false, false);
            boolean exists = false;
            while (rs.next()) {
                if (indexName.equalsIgnoreCase(rs.getString("INDEX_NAME"))) {
                    exists = true; break;
                }
            }
            if (!exists) {
                rs = meta.getIndexInfo(null, null, index.tableHead.name.toLowerCase(), false, false);
                while (rs.next()) {
                    if (indexName.equalsIgnoreCase(rs.getString("INDEX_NAME"))) {
                        exists = true; break;
                    }
                }
            }
            if (!exists) {
                rs = meta.getIndexInfo(null, null, index.tableHead.name, false, false);
                while (rs.next()) {
                    if (indexName.equalsIgnoreCase(rs.getString("INDEX_NAME"))) {
                        exists = true; break;
                    }
                }
            }
            if (exists) {
                log.info("Index already exists: {}", indexName);
                return;
            }
        }
        var builder = new StringBuilder();
        builder.append("CREATE INDEX ");
        builder.append(indexName);
        builder.append(" ON ");
        builder.append(index.tableHead.getCatalogSchemaName());
        builder.append(" (");
        for (var i = 0; i < index.fieldList.size(); i++) {
            var field = index.fieldList.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(field.name);
        }
        builder.append(")");
        final String sql = builder.toString();
        log.debug("Creating index with SQL: {}", sql);
        try (var stmt = getLink().createStatement()) {
            stmt.execute(sql);
            log.info("Index created successfully: {}", index.name);
        }
    }

    @Override
    public Selected select(Select select, Strain strain) throws Exception {
        var builder = new StringBuilder("SELECT ");
        var fromSource = select.tableHead.getCatalogSchemaName();
        var dataSource = select.tableHead.alias != null
                        && !select.tableHead.alias.isEmpty()
                            ? select.tableHead.alias
                            : fromSource;
        if (select.fieldList == null || select.fieldList.isEmpty()) {
            builder.append("*");
        } else {
            for (var i = 0; i < select.fieldList.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                if (!select.fieldList.get(i).name.contains(".")) {
                    builder.append(dataSource);
                    builder.append(".");
                }
                builder.append(select.fieldList.get(i).name);
            }
        }
        builder.append(" FROM ");
        builder.append(fromSource);
        if (select.tableHead.alias != null && !select.tableHead.alias
                        .isEmpty()) {
            builder.append(" AS ");
            builder.append(select.tableHead.alias);
        }
        if (select.hasJoinList()) {
            for (var join : select.joinList) {
                if (join.ties != null) {
                    builder.append(" ");
                    builder.append(join.ties.toString());
                    builder.append(" ");
                }
                builder.append(" JOIN ");
                var withSource = join.tableHead.getCatalogSchemaName();
                var withAlias = withSource;
                builder.append(withSource);
                if (join.alias != null) {
                    builder.append(" AS ");
                    withAlias = join.alias;
                    builder.append(withAlias);
                } else if (join.tableHead.alias != null) {
                    builder.append(" AS ");
                    withAlias = join.tableHead.alias;
                    builder.append(withAlias);
                }
                if (join.hasFilterList()) {
                    builder.append(" ON ");
                    builder.append(makeClauses(join.filterList, dataSource, withAlias));
                }
            }
        }
        if (select.hasFilterList()) {
            builder.append(" WHERE ");
            builder.append(makeClauses(select.filterList, dataSource, null));
        }
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(!select.hasFilterList() ? " WHERE " : " AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        if (select.orderList != null && !select.orderList.isEmpty()) {
            builder.append(" ORDER BY ");
            for (var i = 0; i < select.orderList.size(); i++) {
                if (i > 0) {
                    builder.append(" , ");
                }
                var order = select.orderList.get(i);
                builder.append(order.name);
                if (order.desc != null && order.desc) {
                    builder.append(" DESC");
                }
            }
        }
        appendPagination(builder, select);
        var selectSQL = builder.toString();
        log.debug("Selecting with SQL: {}", selectSQL);
        var prepared = getLink().prepareStatement(selectSQL);
        var paramIndex = 1;
        if (select.hasJoinList()) {
            for (var join : select.joinList) {
                if (join.hasFilterList()) {
                    for (var clause : join.filterList) {
                        if (clause.valued != null && clause.valued.value != null) {
                            setParameter(prepared, paramIndex, clause.valued);
                            paramIndex++;
                        }
                    }
                }
            }
        }
        if (select.hasFilterList()) {
            for (var clause : select.filterList) {
                if (clause.valued != null && clause.valued.value != null) {
                    setParameter(prepared, paramIndex, clause.valued);
                    paramIndex++;
                }
            }
        }
        var resultSet = prepared.executeQuery();
        return new Selected(select, resultSet);
    }

    protected void appendPagination(StringBuilder builder, Select select) {
        if (select.offset != null) {
            builder.append(" OFFSET ");
            builder.append(select.offset);
            builder.append(" ROWS");
        }
        if (select.limit != null) {
            builder.append(" FETCH NEXT ");
            builder.append(select.limit);
            builder.append(" ROWS ONLY");
        }
    }

    @Override
    public Inserted insert(Insert insert, Strain strain) throws Exception {
        String id = "";
        try {
            id = getID(getLink(), insert);
        } catch (Exception e) {
            log.trace("Fallback to native generated keys due to: {}", e.getMessage());
        }
        var strained = new ArrayList<Pair<String, String>>();
        if (strain != null && strain.include != null && !strain.include.isEmpty()) {
            var includes = strain.include.split("\\|");
            for (var element : includes) {
                if (!element.isEmpty() && element.contains("=")) {
                    var parts = element.split("\\=");
                    strained.add(Pair.of(parts[0].trim(), parts[1].trim()));
                }
            }
        }
        var builder = new StringBuilder("INSERT INTO ");
        builder.append(insert.tableHead.getCatalogSchemaName());
        builder.append(" (");
        for (var i = 0; i < insert.valuedList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(insert.valuedList.get(i).name);
        }
        if (!strained.isEmpty()) {
            for (var toStrain : strained) {
                builder.append(", ");
                builder.append(toStrain.getLeft());
            }
        }
        builder.append(") VALUES (");
        for (var i = 0; i < insert.valuedList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            final var valued = insert.valuedList.get(i);
            if (valued.value != null) {
                builder.append("?");
            } else {
                builder.append("NULL");
            }
        }
        if (!strained.isEmpty()) {
            builder.append(", ");
            for (var toStrain : strained) {
                if (!toStrain.getRight().isEmpty()) {
                    builder.append("?");
                } else {
                    builder.append("NULL");
                }
            }
        }
        builder.append(")");
        var insertSQL = builder.toString();
        log.debug("Inserting with SQL: {}", insertSQL);
        var prepared = getLink().prepareStatement(insertSQL, java.sql.Statement.RETURN_GENERATED_KEYS);
        var paramIndex = 1;
        for (var valued : insert.valuedList) {
            if (valued.value != null) {
                setParameter(prepared, paramIndex, valued);
                paramIndex++;
            }
        }
        if (!strained.isEmpty()) {
            for (var toStrain : strained) {
                if (!toStrain.getRight().isEmpty()) {
                    setParameter(prepared, paramIndex, new Valued(toStrain.getLeft(), toStrain.getRight()));
                    paramIndex++;
                }
            }
        }
        var count = prepared.executeUpdate();
        if (insert.toGetID != null && insert.toGetID.name != null && !insert.toGetID.name.isEmpty()) {
            if (id == null || id.isEmpty()) {
                try {
                    var rs = prepared.getGeneratedKeys();
                    if (rs.next()) {
                        id = rs.getString(1);
                        putID(insert, id);
                    }
                } catch (Exception e) {
                    log.warn("Could not retrieve generated keys: {}", e.getMessage());
                }
            }
        }
        return new Inserted(insert, count, id);
    }

    @Override
    public Updated update(Update update, Strain strain) throws Exception {
        var builder = new StringBuilder("UPDATE ");
        var dataSource = update.tableHead.getCatalogSchemaName();
        builder.append(dataSource);
        builder.append(" SET ");
        for (var i = 0; i < update.valuedList.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(update.valuedList.get(i).name);
            builder.append(" = ");
            if (update.valuedList.get(i).value == null) {
                builder.append("NULL");
            } else {
                builder.append("?");
            }
        }
        if (strain != null && strain.modify != null && !strain.modify.isEmpty()) {
            builder.append(", ");
            builder.append(strain.modify);
        }
        builder.append(" WHERE ");
        builder.append(makeClauses(update.filterList, null, null));
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(" AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        var updateSQL = builder.toString();
        log.debug("Updating with SQL: {}", updateSQL);
        var prepared = getLink().prepareStatement(updateSQL);
        var param_index = 1;
        for (var valued : update.valuedList) {
            if (valued != null) {
                setParameter(prepared, param_index, valued);
                param_index++;
            }
        }
        if (update.filterList != null && !update.filterList.isEmpty()) {
            for (var clause : update.filterList) {
                if (clause.valued != null) {
                    setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }
        var count = prepared.executeUpdate();
        return new Updated(update, count);
    }

    @Override
    public Deleted delete(Delete delete, Strain strain) throws Exception {
        var builder = new StringBuilder("DELETE FROM ");
        var dataSource = delete.tableHead.getCatalogSchemaName();
        builder.append(dataSource);
        builder.append(" WHERE ");
        builder.append(makeClauses(delete.filterList, null, null));
        if (strain != null && strain.restrict != null && !strain.restrict.isEmpty()) {
            builder.append(" AND ");
            var restricted = replaceVariables(strain.restrict, dataSource);
            builder.append(restricted);
        }
        var deleteSQL = builder.toString();
        log.debug("Deleting with SQL: {}", deleteSQL);
        var prepared = getLink().prepareStatement(deleteSQL);
        var param_index = 1;
        if (delete.filterList != null && !delete.filterList.isEmpty()) {
            for (var clause : delete.filterList) {
                if (clause.valued.value != null) {
                    setParameter(prepared, param_index, clause.valued);
                    param_index++;
                }
            }
        }
        var count = prepared.executeUpdate();
        return new Deleted(delete, count);
    }

    @Override
    public boolean isPrimaryKeyError(Exception error) {
        if (error == null) return false;
        if (error instanceof java.sql.SQLException sqlEx) {
            String state = sqlEx.getSQLState();
            if (state != null && state.startsWith("23")) {
                return true;
            }
        }
        if (error.getMessage() != null) {
            String msg = error.getMessage().toLowerCase();
            return msg.contains("unique constraint") || msg.contains("duplicate key");
        }
        return false;
    }

    protected String replaceVariables(String onSource, String dataSource) {
        if (onSource == null) {
            return null;
        }
        return onSource.replace("${dataSource}", dataSource);
    }

    protected void putID(Insert insert, Object next) {
        for (var valued : insert.valuedList) {
            if (Objects.equals(insert.toGetID.name, valued.name)) {
                valued.value = next;
                break;
            }
        }
    }

    protected String getID(Connection link, Insert insert) throws Exception {
        if (insert.toGetID == null || insert.toGetID.name == null || insert.toGetID.name.isEmpty()) {
            return "";
        }
        var format = getIDFormat(link, insert);
        if (format == null || format.isEmpty()) {
            throw new Exception("Could not get the ID because: format not found for the table " + insert.tableHead.name);
        }
        var formatParts = format.split(";");
        if (formatParts.length < 2) {
            throw new Exception("Could not get the ID because: format mal formed");
        }
        var formatType = formatParts[0];
        var formatSize = Integer.parseInt(formatParts[1]);
        switch (formatType) {
            case "MX":
                return getIDNumberMax(link, insert, formatSize);
            case "CX":
                return getIDCharsMax(link, insert, formatSize);
            case "NS":
                return getIDNumberSequential(link, insert, formatSize);
            case "CS":
                return getIDCharsSequential(link, insert, formatSize);
            default:
                throw new Exception(
                                "Could not get the ID because: could not identify the format type");
        }
    }

    protected String getIDFormat(Connection link, Insert insert) throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT formato FROM codigos WHERE tabela = '"
                                        + insert.tableHead.name + "'");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    protected String getIDNumberMax(Connection link, Insert insert, int formatSize)
                    throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT MAX(" + insert.toGetID.name + ") FROM "
                                        + insert.tableHead.name + " WHERE "
                                        + insert.toGetID.filter.name + " = '"
                                        + insert.toGetID.filter.value.toString() + "'");
        String last = null;
        if (rst.next()) {
            last = rst.getString(1);
        }
        if (last == null || last.isEmpty()) {
            last = WizString.fillAtStart("", '0', formatSize);
        }
        String next = WizString.getNext(last, true);
        putID(insert, next);
        return next;
    }

    protected String getIDCharsMax(Connection link, Insert insert, int formatSize)
                    throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT MAX(" + insert.toGetID.name + ") FROM "
                                        + insert.tableHead.name + " WHERE "
                                        + insert.toGetID.filter.name + " = '"
                                        + insert.toGetID.filter.value.toString() + "'");
        String last = null;
        if (rst.next()) {
            last = rst.getString(1);
        }
        if (last == null || last.isEmpty()) {
            last = WizString.fillAtStart("", '0', formatSize);
        }
        String next = WizString.getNext(last, false);
        putID(insert, next);
        return next;
    }

    protected String getIDNumberSequential(Connection link, Insert insert, int formatSize)
                    throws Exception {
        String sequence = getIDSequence(link, insert);
        var rst = link.createStatement().executeQuery("SELECT nextval('" + sequence
                        + "')");
        Long nextVal = null;
        if (rst.next()) {
            nextVal = rst.getLong(1);
        }
        if (nextVal == null) {
            nextVal = 1l;
        }
        var next = nextVal.toString();
        next = WizString.fillAtStart(next, '0', formatSize);
        putID(insert, next);
        return next;
    }

    protected String getIDCharsSequential(Connection link, Insert insert, int formatSize)
                    throws Exception {
        String sequence = getIDSequence(link, insert);
        var rst = link.createStatement().executeQuery("SELECT nextval('" + sequence
                        + "')");
        Long nextVal = null;
        if (rst.next()) {
            nextVal = rst.getLong(1);
        }
        if (nextVal == null) {
            nextVal = 1l;
        }
        var next = Base36.fromBase10(nextVal);
        next = WizString.fillAtStart(next, '0', formatSize);
        putID(insert, next);
        return next;
    }

    protected String getIDSequence(Connection link, Insert insert) throws Exception {
        var rst = link.createStatement()
                        .executeQuery("SELECT sequencia FROM codigos WHERE tabela = '"
                                        + insert.tableHead.name + "'");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    protected String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case Bool:
                builder.append(" BOOLEAN");
                break;
            case Bit:
                builder.append(" BIT");
                break;
            case Byte:
                builder.append(" SMALLINT");
                break;
            case Tiny:
                builder.append(" SMALLINT");
                break;
            case Small:
                builder.append(" SMALLINT");
                break;
            case Int:
                builder.append(" INTEGER");
                break;
            case Serial:
                builder.append(" INTEGER GENERATED BY DEFAULT AS IDENTITY");
                break;
            case Long:
                builder.append(" LONG");
                break;
            case BigSerial:
                builder.append(" BIGINT GENERATED BY DEFAULT AS IDENTITY");
                break;
            case Float:
                builder.append(" FLOAT");
                break;
            case Real:
                builder.append(" REAL");
                break;
            case Double:
                builder.append(" DOUBLE PRECISION");
                break;
            case Numeric:
                builder.append(" NUMERIC");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    if (field.precision != null) {
                        builder.append(",");
                        builder.append(field.precision);
                    }
                    builder.append(")");
                }
                break;
            case BigNumeric:
                builder.append(" BIG_NUMERIC");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    if (field.precision != null) {
                        builder.append(",");
                        builder.append(field.precision);
                    }
                    builder.append(")");
                }
                break;
            case Char:
                builder.append(" CHAR(1)");
                break;
            case Chars:
                builder.append(" VARCHAR");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case Date:
                builder.append(" DATE");
                break;
            case Time:
                builder.append(" TIME");
                break;
            case DateTime:
                builder.append(" TIMESTAMP");
                break;
            case Timestamp:
                builder.append(" TIMESTAMP");
                break;
            case Bytes, Blob:
                builder.append(" BLOB");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case Text:
                builder.append(" TEXT");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            case Object:
                builder.append(" OBJECT");
                if (field.size != null) {
                    builder.append("(");
                    builder.append(field.size);
                    builder.append(")");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (Boolean.TRUE.equals(field.notNull)) {
            builder.append(" NOT NULL");
        }
        return builder.toString();
    }

    protected String makeClauses(List<Filter> filterList, String fromSource, String withAlias) {
        if ((filterList == null) || filterList.isEmpty()) {
            return "";
        }
        var builder = new StringBuilder();
        var nextIsOr = false;
        for (var i = 0; i < filterList.size(); i++) {
            var clause = filterList.get(i);
            if (clause.valued == null && clause.linked == null) {
                continue;
            }
            if (i > 0) {
                builder.append(nextIsOr ? " OR " : " AND ");
            }
            if (clause.seems == FilterSeems.IsNot) {
                builder.append(" NOT ");
            }
            if (clause.valued != null) {
                if (fromSource != null && !fromSource.isEmpty() && !clause.valued.name.contains(".")) {
                    builder.append(fromSource);
                    builder.append(".");
                }
                builder.append(clause.valued.name);
                if (clause.valued.value == null) {
                    builder.append(" IS NULL ");
                } else {
                    builder.append(makeCondition(clause.likes, "?"));
                }
            } else if (clause.linked != null) {
                if (fromSource != null && !fromSource.isEmpty()) {
                    builder.append(fromSource);
                    builder.append(".");
                }
                builder.append(clause.linked.name);
                if (clause.linked.upon == null) {
                    builder.append(" IS NULL ");
                } else {
                    var formWith = new StringBuilder();
                    if (withAlias != null && !withAlias.isEmpty()) {
                        formWith.append(withAlias);
                        formWith.append(".");
                    }
                    formWith.append(clause.linked.upon);
                    builder.append(makeCondition(clause.likes, formWith.toString()));
                }
            }
            nextIsOr = clause.ties == FilterTies.Or;
        }
        return builder.toString();
    }

    protected String makeCondition(FilterLikes likes, String upon) {
        switch (likes) {
            case Equals: return " = " + upon + " ";
            case Bigger: return " > " + upon + " ";
            case Lesser: return " < " + upon + " ";
            case BiggerOrEquals: return " >= " + upon + " ";
            case LesserOrEquals: return " <= " + upon + " ";
            case StartsWith: return " LIKE " + upon + " || '%' ";
            case EndsWith: return " LIKE '%' || " + upon + " ";
            case Contains: return " LIKE '%' || " + upon + " || '%' ";
            default: throw new UnsupportedOperationException();
        }
    }

    protected void setParameter(PreparedStatement prepared, int index, Valued valued)
                    throws Exception {
        if (valued.type == null) {
            prepared.setObject(index, valued.value);
        } else {
            switch (valued.type) {
                case Bool:
                    prepared.setBoolean(index, WizData.getOn(valued.value, Boolean.class));
                    break;
                case Bit:
                    prepared.setByte(index, WizData.getOn(valued.value, Byte.class));
                    break;
                case Byte:
                    prepared.setByte(index, WizData.getOn(valued.value, Byte.class));
                    break;
                case Tiny:
                    prepared.setShort(index, WizData.getOn(valued.value, Short.class));
                    break;
                case Small:
                    prepared.setShort(index, WizData.getOn(valued.value, Short.class));
                    break;
                case Int:
                    prepared.setInt(index, WizData.getOn(valued.value, Integer.class));
                    break;
                case Long:
                    prepared.setLong(index, WizData.getOn(valued.value, Long.class));
                    break;
                case BigInt:
                    prepared.setObject(index, WizData.getOn(valued.value, BigInteger.class));
                    break;
                case Serial:
                    prepared.setInt(index, WizData.getOn(valued.value, Integer.class));
                    break;
                case BigSerial:
                    prepared.setObject(index, WizData.getOn(valued.value, BigInteger.class));
                    break;
                case Float:
                    prepared.setFloat(index, WizData.getOn(valued.value, Float.class));
                    break;
                case Real:
                    prepared.setDouble(index, WizData.getOn(valued.value, Double.class));
                    break;
                case Double:
                    prepared.setDouble(index, WizData.getOn(valued.value, Double.class));
                    break;
                case Numeric:
                    prepared.setBigDecimal(index, WizData.getOn(valued.value, BigDecimal.class));
                    break;
                case BigNumeric:
                    prepared.setBigDecimal(index, WizData.getOn(valued.value, BigDecimal.class));
                    break;
                case Char:
                    prepared.setString(index, String.valueOf(WizData.getOn(valued.value, Character.class)));
                    break;
                case Chars:
                    prepared.setString(index, WizData.getOn(valued.value, String.class));
                    break;
                case Date:
                    prepared.setObject(index, WizData.getOn(valued.value, LocalDate.class));
                    break;
                case Time:
                    prepared.setObject(index, WizData.getOn(valued.value, LocalTime.class));
                    break;
                case DateTime:
                    prepared.setObject(index, WizData.getOn(valued.value, LocalDateTime.class));
                    break;
                case ZoneTime:
                    prepared.setObject(index, WizData.getOn(valued.value, ZonedDateTime.class));
                    break;
                case Timestamp:
                    prepared.setObject(index, WizData.getOn(valued.value, Instant.class));
                    break;
                case Bytes:
                    prepared.setBytes(index, WizData.getOn(valued.value, byte[].class));
                    break;
                case Blob:
                    prepared.setBlob(index, WizData.getOn(valued.value, Blob.class));
                    break;
                case Text:
                    prepared.setString(index, WizData.getOn(valued.value, String.class));
                    break;
                case Object:
                    prepared.setObject(index, valued.value);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported field type for parameter: " + valued.type);
            }
        }
    }

}
