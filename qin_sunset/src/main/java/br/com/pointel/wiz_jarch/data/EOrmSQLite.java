package br.com.pointel.wiz_jarch.data;

import java.sql.Connection;

public class EOrmSQLite extends EOrmBase {

    public EOrmSQLite(Connection link) {
        super(link);
    }

    @Override
    protected String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case Bool, Bit, Byte, Tiny, Small, Int, BigInt, Serial, Long, BigSerial:
                builder.append(" INTEGER");
                break;
            case Float, Real, Double:
                builder.append(" REAL");
                break;    
            case Numeric, BigNumeric:
                builder.append(" NUMERIC");
                break;
            case Char, Chars, Date, Time, DateTime, ZoneTime, Timestamp, Text:
                builder.append(" TEXT");
                break;
            case Bytes, Blob, Object:
                builder.append(" BLOB");
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

    @Override
    public boolean isPrimaryKeyError(Exception error) {
        if (error == null || error.getMessage() == null) {
            return false;
        }
        String msg = error.getMessage().toLowerCase();
        return msg.contains("unique constraint failed") 
            || msg.contains("primary key constraint failed") 
            || msg.contains("sqlite_constraint_primarykey") 
            || msg.contains("sqlite_constraint_unique");
    }
}
