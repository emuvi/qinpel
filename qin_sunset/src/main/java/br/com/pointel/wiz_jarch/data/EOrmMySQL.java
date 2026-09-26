package br.com.pointel.wiz_jarch.data;

import java.sql.Connection;

public class EOrmMySQL extends EOrmBase {

    public EOrmMySQL(Connection link) {
        super(link);
    }

    @Override
    protected String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case Bool: builder.append(" BOOLEAN"); break;
            case Bit: builder.append(" BIT"); break;
            case Byte, Tiny: builder.append(" TINYINT"); break;
            case Small: builder.append(" SMALLINT"); break;
            case Int: builder.append(" INT"); break;
            case BigInt, Long: builder.append(" BIGINT"); break;
            case Serial: builder.append(" INT AUTO_INCREMENT"); break;
            case BigSerial: builder.append(" BIGINT AUTO_INCREMENT"); break;
            case Float, Real: builder.append(" FLOAT"); break;
            case Double: builder.append(" DOUBLE"); break;
            case Numeric, BigNumeric:
                builder.append(" DECIMAL");
                if (field.size != null) {
                    builder.append("(").append(field.size);
                    if (field.precision != null) {
                        builder.append(",").append(field.precision);
                    }
                    builder.append(")");
                }
                break;
            case Char: builder.append(" CHAR(1)"); break;
            case Chars:
                builder.append(" VARCHAR");
                if (field.size != null) {
                    builder.append("(").append(field.size).append(")");
                }
                break;
            case Date: builder.append(" DATE"); break;
            case Time: builder.append(" TIME"); break;
            case DateTime: builder.append(" DATETIME"); break;
            case ZoneTime, Timestamp: builder.append(" TIMESTAMP"); break;
            case Bytes, Blob, Object: 
                builder.append(" LONGBLOB"); 
                break;
            case Text: 
                builder.append(" LONGTEXT"); 
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (Boolean.TRUE.equals(field.notNull)) {
            builder.append(" NOT NULL");
        }
        return builder.toString();
    }

    @Override
    public String makeCondition(FilterLikes likes, String with) {
        switch (likes) {
            case StartsWith: return " LIKE CONCAT(" + with + ", '%') ";
            case EndsWith: return " LIKE CONCAT('%', " + with + ") ";
            case Contains: return " LIKE CONCAT('%', " + with + ", '%') ";
            default: return super.makeCondition(likes, with);
        }
    }

    @Override
    protected void appendPagination(StringBuilder builder, Select select) {
        if (select.limit != null) {
            builder.append(" LIMIT ");
            builder.append(select.limit);
        }
        if (select.offset != null) {
            builder.append(" OFFSET ");
            builder.append(select.offset);
        }
    }
}
