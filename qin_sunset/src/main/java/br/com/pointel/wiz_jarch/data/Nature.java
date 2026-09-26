package br.com.pointel.wiz_jarch.data;

public enum Nature {

    Bool(Boolean.class, Byte.class, Number.class),
    
    Bit(Byte.class, Boolean.class, Number.class),

    Byte(Byte.class, Short.class, Number.class),

    Tiny(Short.class, Byte.class, Number.class),

    Small(Short.class, Integer.class, Number.class),

    Int(Integer.class, Short.class, Number.class),

    Long(Long.class, java.math.BigInteger.class, Number.class),

    BigInt(java.math.BigInteger.class, Long.class, Number.class),

    Serial(Integer.class, Long.class, Number.class),

    BigSerial(java.math.BigInteger.class, Long.class, Number.class),

    Float(Float.class, Double.class, Number.class),

    Real(Double.class, Float.class, Number.class),

    Double(Double.class, java.math.BigDecimal.class, Number.class),

    Numeric(java.math.BigDecimal.class, Double.class, Number.class),

    BigNumeric(java.math.BigDecimal.class, java.math.BigInteger.class, Number.class),

    Char(Character.class, String.class, Number.class),

    Chars(String.class, byte[].class, java.sql.Clob.class, java.sql.Blob.class),

    Date(java.time.LocalDate.class, java.time.OffsetDateTime.class, java.util.Date.class, java.sql.Date.class),

    Time(java.time.LocalTime.class, java.time.OffsetTime.class, java.util.Date.class, java.sql.Time.class),

    DateTime(java.time.LocalDateTime.class, java.time.OffsetDateTime.class, java.util.Date.class, java.sql.Timestamp.class),

    ZoneTime(java.time.ZonedDateTime.class, java.time.OffsetDateTime.class, java.time.OffsetTime.class, java.util.Date.class, java.sql.Timestamp.class),

    Timestamp(java.time.Instant.class,  java.time.OffsetDateTime.class, java.util.Date.class, java.sql.Timestamp.class),

    Bytes(byte[].class, String.class, java.sql.Blob.class, java.sql.Clob.class),

    Blob(java.sql.Blob.class, java.sql.Clob.class, byte[].class, String.class),

    Text(String.class, byte[].class, java.sql.Clob.class, java.sql.Blob.class),

    Object(Object.class, byte[].class, String.class, java.sql.Blob.class, java.sql.Clob.class);

    private final Class<?>[] mapTypes;

    Nature(Class<?>... mapTypes) {
        this.mapTypes = mapTypes;
    }

    public Class<?>[] getMapTypes() {
        return mapTypes;
    }

}
