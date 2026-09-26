package br.com.pointel.wiz_jarch.data;

import java.sql.Connection;

public class EOrmPostgre extends EOrmBase {

    public EOrmPostgre(Connection link) {
        super(link);
    }

    @Override
    public String makeNature(Field field) {
        var builder = new StringBuilder(field.name);
        switch (field.nature) {
            case Bool: builder.append(" BOOLEAN"); break;
            case Bit: builder.append(" BIT"); break;
            case Byte, Tiny: builder.append(" SMALLINT"); break;
            case Small: builder.append(" SMALLINT"); break;
            case Int: builder.append(" INTEGER"); break;
            case BigInt: builder.append(" BIGINT"); break;
            case Serial: builder.append(" SERIAL"); break;
            case Long: builder.append(" BIGINT"); break;
            case BigSerial: builder.append(" BIGSERIAL"); break;
            case Float, Real: builder.append(" REAL"); break;
            case Double: builder.append(" DOUBLE PRECISION"); break;
            case Numeric, BigNumeric: 
                builder.append(" NUMERIC");
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
            case DateTime: builder.append(" TIMESTAMP WITHOUT TIME ZONE"); break;
            case ZoneTime: builder.append(" TIMESTAMP WITH TIME ZONE"); break;
            case Timestamp: builder.append(" TIMESTAMP"); break;
            case Bytes, Blob: builder.append(" BYTEA"); break;
            case Text: builder.append(" TEXT"); break;
            case Object: builder.append(" JSONB"); break;
            default: throw new UnsupportedOperationException();
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
}
