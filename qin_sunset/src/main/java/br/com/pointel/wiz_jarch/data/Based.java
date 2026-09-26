package br.com.pointel.wiz_jarch.data;

/**
 * Enumerates supported database presets, including driver class, JDBC URL
 * template, default port, and ORM implementation.
 */
public enum Based {

    /** SQLite in-memory database preset. */
    SQLiteMemory(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite::memory:",
                    null,
                    EOrmSQLite.class),

    /** SQLite local file database preset. */
    SQLiteLocal(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:$path",
                    null,
                    EOrmSQLite.class),

    /** HSQLDB in-memory database preset. */
    HSQLDBMemory(
                    "org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:mem:$data",
                    9000,
                    EOrmHSQL.class),

    /** HSQLDB local file database preset. */
    HSQLDBLocal(
                    "org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:file:$path;hsqldb.lock_file=true",
                    9000,
                    EOrmHSQL.class),

    /** HSQLDB client database preset. */
    HSQLDBClient(
                    "org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:hsql://$path:$port/$data",
                    9000,
                    EOrmHSQL.class),

    /** Derby embedded database preset. */
    DerbyInner(
                    "org.apache.derby.jdbc.EmbeddedDriver",
                    "jdbc:derby:$path;create=true",
                    1527,
                    EOrmDerby.class),

    /** Derby client database preset. */
    DerbyClient(
                    "org.apache.derby.jdbc.ClientDriver",
                    "jdbc:derby://$path:$port/$data;create=true",
                    1527,
                    EOrmDerby.class),

    /** Firebird local database preset. */
    FirebirdLocal(
                    "org.firebirdsql.jdbc.FBDriver",
                    "jdbc:firebirdsql:local:$path",
                    3050,
                    EOrmFirebird.class),

    /** Firebird embedded database preset. */
    FirebirdInner(
                    "org.firebirdsql.jdbc.FBDriver",
                    "jdbc:firebirdsql:embedded:$path",
                    3050,
                    EOrmFirebird.class),

    /** Firebird client database preset. */
    FirebirdClient(
                    "org.firebirdsql.jdbc.FBDriver",
                    "jdbc:firebirdsql:$path:$port/$data",
                    3050,
                    EOrmFirebird.class),

    /** MySQL client database preset. */
    MySQLClient(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://$path:$port/$data",
                    3306,
                    EOrmMySQL.class),

    /** PostgreSQL client database preset. */
    PostgreClient(
                    "org.postgresql.Driver",
                    "jdbc:postgresql://$path:$port/$data",
                    5432,
                    EOrmPostgre.class);

    /**
     * The JDBC driver class name.
     */
    public final String driverClazz;

    /**
     * The JDBC URL template used to build connections.
     */
    public final String formation;

    /**
     * The default port, when the preset uses one.
     */
    public final Integer defaultPort;

    /**
     * The ORM implementation associated with the preset.
     */
    public final Class<? extends EOrm> eOrmClazz;

    /**
     * Creates a preset with its driver, URL template, default port, and ORM type.
     *
     * @param driverClazz the JDBC driver class name
     * @param formation the JDBC URL template
     * @param defaultPort the default port, or {@code null}
     * @param eOrmClazz the ORM implementation class
     */
    private Based(String driverClazz, String formation, Integer defaultPort,
                    Class<? extends EOrm> eOrmClazz) {
        this.driverClazz = driverClazz;
        this.formation = formation;
        this.defaultPort = defaultPort;
        this.eOrmClazz = eOrmClazz;
    }

    /**
     * Returns the fixed prefix of the JDBC URL template before the first
     * variable placeholder.
     *
     * @return the literal URL prefix, or the full template when no placeholder exists
     */
    public String getUrlIdentity() {
        var dollarAt = this.formation.indexOf("$");
        if (dollarAt == -1) {
            return this.formation;
        }
        return this.formation.substring(0, dollarAt);
    }

    /**
     * Finds the first preset whose URL identity matches the given JDBC URL.
     *
     * @param jdbc the JDBC URL to inspect
     * @return the matching preset, or {@code null}
     */
    public static Based fromURL(String jdbc) {
        for (Based data : Based.values()) {
            if (jdbc.startsWith(data.getUrlIdentity())) {
                return data;
            }
        }
        return null;
    }

    /**
     * Resolves the ORM class for the preset that matches the given JDBC URL.
     *
     * @param jdbc the JDBC URL to inspect
     * @return the matching ORM class, or {@code null}
     */
    public static Class<? extends EOrm> getEOrmClassFromURL(String jdbc) {
        for (Based data : Based.values()) {
            if (jdbc.startsWith(data.getUrlIdentity())) {
                return data.eOrmClazz;
            }
        }
        return null;
    }

}
