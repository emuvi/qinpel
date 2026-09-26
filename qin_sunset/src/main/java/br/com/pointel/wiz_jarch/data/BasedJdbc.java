package br.com.pointel.wiz_jarch.data;

/**
 * Stores JDBC connection settings for a database base definition.
 */
public class BasedJdbc implements Data {

    /** The database base name. */
    public String name;
    /** The JDBC connection URL. */
    public String url;
    /** The database user name. */
    public String user;
    /** The database password. */
    public String pass;

    /**
     * Creates an empty JDBC configuration.
     */
    public BasedJdbc() {
    }

    /**
     * Creates a JDBC configuration with the given name.
     *
     * @param name the base name
     */
    public BasedJdbc(String name) {
        this.name = name;
    }

    /**
     * Creates a JDBC configuration with the given name and URL.
     *
     * @param name the base name
     * @param url the JDBC URL
     */
    public BasedJdbc(String name, String url) {
        this.name = name;
        this.url = url;
    }

    /**
     * Creates a JDBC configuration with the given name, URL, and user.
     *
     * @param name the base name
     * @param url the JDBC URL
     * @param user the user name
     */
    public BasedJdbc(String name, String url, String user) {
        this.name = name;
        this.url = url;
        this.user = user;
    }

    /**
     * Creates a JDBC configuration with the given name, URL, user, and password.
     *
     * @param name the base name
     * @param url the JDBC URL
     * @param user the user name
     * @param pass the password
     */
    public BasedJdbc(String name, String url, String user, String pass) {
        this.name = name;
        this.url = url;
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
     * Indicates whether the URL is set.
     *
     * @return {@code true} when the URL is non-empty; otherwise {@code false}
     */
    public boolean hasUrl() {
        return this.url != null && !this.url.isEmpty();
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
    public BasedJdbc withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Clears the name and returns this configuration.
     *
     * @return this configuration
     */
    public BasedJdbc withNoName() {
        this.name = null;
        return this;
    }

    /**
     * Updates the JDBC URL and returns this configuration.
     *
     * @param url the new JDBC URL
     * @return this configuration
     */
    public BasedJdbc withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Clears the JDBC URL and returns this configuration.
     *
     * @return this configuration
     */
    public BasedJdbc withNoUrl() {
        this.url = null;
        return this;
    }

    /**
     * Updates the user and returns this configuration.
     *
     * @param user the new user name
     * @return this configuration
     */
    public BasedJdbc withUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * Clears the user and returns this configuration.
     *
     * @return this configuration
     */
    public BasedJdbc withNoUser() {
        this.user = null;
        return this;
    }

    /**
     * Updates the password and returns this configuration.
     *
     * @param pass the new password
     * @return this configuration
     */
    public BasedJdbc withPass(String pass) {
        this.pass = pass;
        return this;
    }

    /**
     * Clears the password and returns this configuration.
     *
     * @return this configuration
     */
    public BasedJdbc withNoPass() {
        this.pass = null;
        return this;
    }

    /**
     * Returns a cloned configuration with the given name.
     *
     * @param name the replacement base name
     * @return a cloned configuration with the name updated
     */
    public BasedJdbc uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    /**
     * Returns a cloned configuration without a name.
     *
     * @return a cloned configuration with the name cleared
     */
    public BasedJdbc uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given JDBC URL.
     *
     * @param url the replacement JDBC URL
     * @return a cloned configuration with the URL updated
     */
    public BasedJdbc uponUrl(String url) {
        var clone = this.clone();
        clone.url = url;
        return clone;
    }

    /**
     * Returns a cloned configuration without a JDBC URL.
     *
     * @return a cloned configuration with the URL cleared
     */
    public BasedJdbc uponNoUrl() {
        var clone = this.clone();
        clone.url = null;
        return clone;
    }

    /**
     * Returns a cloned configuration with the given user.
     *
     * @param user the replacement user name
     * @return a cloned configuration with the user updated
     */
    public BasedJdbc uponUser(String user) {
        var clone = this.clone();
        clone.user = user;
        return clone;
    }

    /**
     * Returns a cloned configuration without a user.
     *
     * @return a cloned configuration with the user cleared
     */
    public BasedJdbc uponNoUser() {
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
    public BasedJdbc uponPass(String pass) {
        var clone = this.clone();
        clone.pass = pass;
        return clone;
    }

    /**
     * Returns a cloned configuration without a password.
     *
     * @return a cloned configuration with the password cleared
     */
    public BasedJdbc uponNoPass() {
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
    public BasedJdbc clone() {
        return (BasedJdbc) this.deepClone();
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
     * Deserializes JSON text into a JDBC configuration.
     *
     * @param chars the JSON text to parse
     * @return the parsed configuration
     */
    public static BasedJdbc fromChars(String chars) {
        return Base.fromChars(chars, BasedJdbc.class);
    }
}
