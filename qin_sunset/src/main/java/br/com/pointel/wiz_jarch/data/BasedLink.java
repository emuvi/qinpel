package br.com.pointel.wiz_jarch.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import br.com.pointel.wiz_jarch.mage.WizData;
import br.com.pointel.wiz_jarch.mage.WizInteger;
import br.com.pointel.wiz_jarch.mage.WizString;

/**
 * Stores connection-link settings for a database base and builds JDBC links
 * from those settings.
 */
public class BasedLink implements Data {
    
    /** The base name. */
    public String name;
    /** The database preset. */
    public Based base;
    /** The host address or file path. */
    public String path;
    /** The port number. */
    public Integer port;
    /** The database name. */
    public String data;
    /** The user name. */
    public String user;
    /** The password. */
    public String pass;

    private transient Connection linked = null;

    /**
     * Creates an empty link configuration.
     */
    public BasedLink() {
    }

    /**
     * Creates a link configuration with the given name.
     *
     * @param name the base name
     */
    public BasedLink(String name) {
        this.name = name;
    }

    /**
     * Creates a link configuration with the given name and base preset.
     *
     * @param name the base name
     * @param base the database preset
     */
    public BasedLink(String name, Based base) {
        this.name = name;
        this.base = base;
    }

    /**
     * Creates a link configuration with the given name, base preset, and path.
     *
     * @param name the base name
     * @param base the database preset
     * @param path the host or file path
     */
    public BasedLink(String name, Based base, String path) {
        this.name = name;
        this.base = base;
        this.path = path;
    }

    /**
     * Creates a link configuration with the given name, base preset, path, and port.
     *
     * @param name the base name
     * @param base the database preset
     * @param path the host or file path
     * @param port the port number
     */
    public BasedLink(String name, Based base, String path, Integer port) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
    }

    /**
     * Creates a link configuration with the given name, base preset, path, port,
     * and data name.
     *
     * @param name the base name
     * @param base the database preset
     * @param path the host or file path
     * @param port the port number
     * @param data the database name
     */
    public BasedLink(String name, Based base, String path, Integer port, String data) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
    }

    /**
     * Creates a link configuration with the given connection settings.
     *
     * @param name the base name
     * @param base the database preset
     * @param path the host or file path
     * @param port the port number
     * @param data the database name
     * @param user the user name
     * @param pass the password
     */
    public BasedLink(String name, Based base, String path, Integer port, String data, String user, String pass) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Indicates whether the name is set.
     *
     * @return {@code true} when the name is non-empty; otherwise {@code false}
     */
    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    /**
     * Indicates whether the base preset is set.
     *
     * @return {@code true} when the preset is non-null; otherwise {@code false}
     */
    public boolean hasBase() {
        return this.base != null;
    }

    /**
     * Indicates whether the path is set.
     *
     * @return {@code true} when the path is non-empty; otherwise {@code false}
     */
    public boolean hasPath() {
        return this.path != null && !this.path.isEmpty();
    }

    /**
     * Indicates whether the port is set.
     *
     * @return {@code true} when the port is non-null; otherwise {@code false}
     */
    public boolean hasPort() {
        return this.port != null;
    }

    /**
     * Indicates whether the database name is set.
     *
     * @return {@code true} when the database name is non-empty; otherwise {@code false}
     */
    public boolean hasData() {
        return this.data != null && !this.data.isEmpty();
    }

    /**
     * Indicates whether the user is set.
     *
     * @return {@code true} when the user is non-empty; otherwise {@code false}
     */
    public boolean hasUser() {
        return this.user != null && !this.user.isEmpty();
    }

    /**
     * Indicates whether the password is set.
     *
     * @return {@code true} when the password is non-empty; otherwise {@code false}
     */
    public boolean hasPass() {
        return this.pass != null && !this.pass.isEmpty();
    }

    /**
     * Updates the name and returns this configuration.
     *
     * @param name the new base name
     * @return this configuration
     */
    public BasedLink withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the base preset and returns this configuration.
     *
     * @param base the new base preset
     * @return this configuration
     */
    public BasedLink withBase(Based base) {
        this.base = base;
        return this;
    }

    /**
     * Clears the base preset and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoBase() {
        this.base = null;
        return this;
    }

    /**
     * Updates the path and returns this configuration.
     *
     * @param path the new path
     * @return this configuration
     */
    public BasedLink withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Clears the path and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoPath() {
        this.path = null;
        return this;
    }

    /**
     * Updates the port and returns this configuration.
     *
     * @param port the new port
     * @return this configuration
     */
    public BasedLink withPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Clears the port and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoPort() {
        this.port = null;
        return this;
    }

    /**
     * Updates the database name and returns this configuration.
     *
     * @param data the new database name
     * @return this configuration
     */
    public BasedLink withData(String data) {
        this.data = data;
        return this;
    }

    /**
     * Clears the database name and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoData() {
        this.data = null;
        return this;
    }

    /**
     * Updates the user and returns this configuration.
     *
     * @param user the new user name
     * @return this configuration
     */
    public BasedLink withUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * Clears the user and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoUser() {
        this.user = null;
        return this;
    }

    /**
     * Updates the password and returns this configuration.
     *
     * @param pass the new password
     * @return this configuration
     */
    public BasedLink withPass(String pass) {
        this.pass = pass;
        return this;
    }

    /**
     * Clears the password and returns this configuration.
     *
     * @return this configuration
     */
    public BasedLink withNoPass() {
        this.pass = null;
        return this;
    }

    /**
     * Returns a cloned configuration with the given name.
     *
     * @param name the replacement base name
     * @return a cloned configuration with the name updated
     */
    public BasedLink uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned configuration without a name.
     *
     * @return a cloned configuration with the name cleared
     */
    public BasedLink uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given base preset.
     *
     * @param base the replacement base preset
     * @return a cloned configuration with the base updated
     */
    public BasedLink uponBase(Based base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    /**
     * Returns a cloned configuration without a base preset.
     *
     * @return a cloned configuration with the base cleared
     */
    public BasedLink uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given path.
     *
     * @param path the replacement path
     * @return a cloned configuration with the path updated
     */
    public BasedLink uponPath(String path) {
        var clone = this.clone();
        clone.path = path;
        return clone;
    }

    /**
     * Returns a cloned configuration without a path.
     *
     * @return a cloned configuration with the path cleared
     */
    public BasedLink uponNoPath() {
        var clone = this.clone();
        clone.path = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given port.
     *
     * @param port the replacement port
     * @return a cloned configuration with the port updated
     */
    public BasedLink uponPort(Integer port) {
        var clone = this.clone();
        clone.port = port;
        return clone;
    }

    /**
     * Returns a cloned configuration without a port.
     *
     * @return a cloned configuration with the port cleared
     */
    public BasedLink uponNoPort() {
        var clone = this.clone();
        clone.port = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given database name.
     *
     * @param data the replacement database name
     * @return a cloned configuration with the data updated
     */
    public BasedLink uponData(String data) {
        var clone = this.clone();
        clone.data = data;
        return clone;
    }

    /**
     * Returns a cloned configuration without a database name.
     *
     * @return a cloned configuration with the data cleared
     */
    public BasedLink uponNoData() {
        var clone = this.clone();
        clone.data = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given user.
     *
     * @param user the replacement user name
     * @return a cloned configuration with the user updated
     */
    public BasedLink uponUser(String user) {
        var clone = this.clone();
        clone.user = user;
        return clone;
    }

    /**
     * Returns a cloned configuration without a user.
     *
     * @return a cloned configuration with the user cleared
     */
    public BasedLink uponNoUser() {
        var clone = this.clone();
        clone.user = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given password.
     *
     * @param pass the replacement password
     * @return a cloned configuration with the password updated
     */
    public BasedLink uponPass(String pass) {
        var clone = this.clone();
        clone.pass = pass;
        return clone;
    }

    /**
     * Returns a cloned configuration without a password.
     *
     * @return a cloned configuration with the password cleared
     */
    public BasedLink uponNoPass() {
        var clone = this.clone();
        clone.pass = null;
        return clone;
    }

    /**
     * Creates a deep clone of this configuration.
     *
     * @return a deep copy of this configuration
     */
    @Override
    public BasedLink clone() {
        return (BasedLink) this.deepClone();
    }

    /**
     * Compares this configuration to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this configuration.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this configuration to JSON text.
     *
     * @return the JSON representation of this configuration
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a link configuration.
     *
     * @param chars the JSON text to parse
     * @return the parsed configuration
     */
    public static BasedLink fromChars(String chars) {
        return Base.fromChars(chars, BasedLink.class);
    }

    /**
     * Builds the JDBC URL for this configuration by substituting known
     * placeholders in the preset template.
     *
     * @return the formatted JDBC URL
     */
    public String formUrl() {
        var result = this.base.formation;
        if (result.contains("$path") && this.path != null) {
            result = result.replace("$path", this.path);
        }
        if (result.contains("$port")) {
            if (this.port != null) {
                result = result.replace("$port", this.port.toString());
            } else if (this.base != null) {
                result = result.replace("$port", this.base.defaultPort.toString());
            }
        }
        if (result.contains("$data") && this.data != null) {
            result = result.replace("$data", this.data);
        }
        return result;
    }

    /**
     * Opens a JDBC connection using the configured base and credentials.
     *
     * @return an open JDBC connection
     * @throws Exception if the driver cannot be loaded or the connection fails
     */
    public Connection connect() throws Exception {
        Class.forName(this.base.driverClazz);
        if ((this.user != null && !this.user.isEmpty() && this.pass != null)) {
            return DriverManager.getConnection(this.formUrl(), this.user, this.pass);
        }
        return DriverManager.getConnection(this.formUrl());
    }

    /**
     * Returns a cached connection, creating it when needed.
     *
     * @return the linked JDBC connection
     * @throws Exception if the connection cannot be created
     */
    public Connection link() throws Exception {
        if (this.linked == null) {
            this.linked = this.connect();
        }
        if (this.linked.isClosed()) {
            this.linked = this.connect();
        }
        return this.linked;
    }

    /**
     * Creates the ORM implementation associated with the configured preset.
     *
     * @param link the JDBC connection to wrap
     * @return the ORM instance
     * @throws Exception if the ORM cannot be instantiated reflectively
     */
    public EOrm getEOrm(Connection link) throws Exception {
        return this.base.eOrmClazz.getConstructor(Connection.class).newInstance(link);
    }

    /**
     * Deserializes an assigned-text representation into a link configuration.
     *
     * @param chars the assigned text to parse
     * @return the parsed configuration
     * @throws Exception if parsing the assigned values fails
     */
    public static BasedLink fromAssigned(String chars) throws Exception {
        BasedLink result = new BasedLink();
        Map<String, String> assigned = WizString.getAssigned(chars);
        result.name = assigned.get("name");
        result.base = WizData.fromJson(assigned.get("base"), Based.class);
        result.path = assigned.get("path");
        result.port = WizInteger.get(assigned.get("port"));
        result.data = assigned.get("data");
        result.user = assigned.get("user");
        result.pass = assigned.get("pass");
        return result;
    }
}
