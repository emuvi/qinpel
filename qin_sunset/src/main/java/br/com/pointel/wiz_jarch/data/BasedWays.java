package br.com.pointel.wiz_jarch.data;

import br.com.pointel.wiz_jarch.flow.FixInt;
import br.com.pointel.wiz_jarch.flow.NotFixNulls;

/**
 * Combines JDBC and link-based connection settings with pool configuration.
 */
public class BasedWays implements Data {
    
    /** The JDBC connection settings. */
    @NotFixNulls
    public BasedJdbc dataJdbc;
    /** The link connection settings. */
    @NotFixNulls
    public BasedLink dataLink;

    /** The minimum idle pool size. */
    @FixInt(1) 
    public Integer poolMinIdle;
    /** The maximum idle pool size. */
    @FixInt(5)
    public Integer poolMaxIdle;
    /** The maximum total pool size. */
    @FixInt(10)
    public Integer poolMaxTotal;

    /**
     * Creates an empty configuration bundle.
     */
    public BasedWays() {
    }

    /**
     * Creates a bundle backed by JDBC settings and default pool sizes.
     *
     * @param dataJdbc the JDBC configuration
     */
    public BasedWays(BasedJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        this.poolMinIdle = 2;
        this.poolMaxIdle = 5;
        this.poolMaxTotal = 10;
    }

    /**
     * Creates a bundle backed by link settings and default pool sizes.
     *
     * @param dataLink the link configuration
     */
    public BasedWays(BasedLink dataLink) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.poolMinIdle = 2;
        this.poolMaxIdle = 5;
        this.poolMaxTotal = 10;
    }

    /**
     * Creates a JDBC-backed bundle with explicit pool values.
     *
     * @param dataJdbc the JDBC configuration
     * @param poolMinIdle the minimum idle count
     * @param poolMaxIdle the maximum idle count
     * @param poolMaxTotal the maximum total count
     */
    public BasedWays(BasedJdbc dataJdbc, Integer poolMinIdle, Integer poolMaxIdle, Integer poolMaxTotal) {
        this.dataJdbc = dataJdbc;
        this.dataLink = null;
        this.poolMinIdle = poolMinIdle;
        this.poolMaxIdle = poolMaxIdle;
        this.poolMaxTotal = poolMaxTotal;
    }

    /**
     * Creates a link-backed bundle with explicit pool values.
     *
     * @param dataLink the link configuration
     * @param poolMinIdle the minimum idle count
     * @param poolMaxIdle the maximum idle count
     * @param poolMaxTotal the maximum total count
     */
    public BasedWays(BasedLink dataLink, Integer poolMinIdle, Integer poolMaxIdle, Integer poolMaxTotal) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.poolMinIdle = poolMinIdle;
        this.poolMaxIdle = poolMaxIdle;
        this.poolMaxTotal = poolMaxTotal;
    }

    /**
     * Indicates whether JDBC settings are present.
     *
     * @return {@code true} when JDBC settings are present; otherwise {@code false}
     */
    public boolean hasDataJdbc() {
        return this.dataJdbc != null;
    }

    /**
     * Indicates whether link settings are present.
     *
     * @return {@code true} when link settings are present; otherwise {@code false}
     */
    public boolean hasDataLink() {
        return this.dataLink != null;
    }

    /**
     * Indicates whether the minimum idle pool size is set.
     *
     * @return {@code true} when the value is non-null; otherwise {@code false}
     */
    public boolean hasPoolMinIdle() {
        return this.poolMinIdle != null;
    }

    /**
     * Indicates whether the maximum idle pool size is set.
     *
     * @return {@code true} when the value is non-null; otherwise {@code false}
     */
    public boolean hasPoolMaxIdle() {
        return this.poolMaxIdle != null;
    }

    /**
     * Indicates whether the maximum total pool size is set.
     *
     * @return {@code true} when the value is non-null; otherwise {@code false}
     */
    public boolean hasPoolMaxTotal() {
        return this.poolMaxTotal != null;
    }

    /**
     * Updates the JDBC settings and returns this bundle.
     *
     * @param dataJdbc the new JDBC configuration
     * @return this bundle
     */
    public BasedWays withDataJdbc(BasedJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        return this;
    }

    /**
     * Clears the JDBC settings and returns this bundle.
     *
     * @return this bundle
     */
    public BasedWays withNoDataJdbc() {
        this.dataJdbc = null;
        return this;
    }

    /**
     * Updates the link settings and returns this bundle.
     *
     * @param dataLink the new link configuration
     * @return this bundle
     */
    public BasedWays withDataLink(BasedLink dataLink) {
        this.dataLink = dataLink;
        return this;
    }

    /**
     * Clears the link settings and returns this bundle.
     *
     * @return this bundle
     */
    public BasedWays withNoDataLink() {
        this.dataLink = null;
        return this;
    }

    /**
     * Updates the minimum idle pool size and returns this bundle.
     *
     * @param poolMinIdle the new minimum idle count
     * @return this bundle
     */
    public BasedWays withPoolMinIdle(Integer poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
        return this;
    }

    /**
     * Clears the minimum idle pool size and returns this bundle.
     *
     * @return this bundle
     */
    public BasedWays withNoPoolMinIdle() {
        this.poolMinIdle = null;
        return this;
    }

    /**
     * Updates the maximum idle pool size and returns this bundle.
     *
     * @param poolMaxIdle the new maximum idle count
     * @return this bundle
     */
    public BasedWays withPoolMaxIdle(Integer poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
        return this;
    }

    /**
     * Clears the maximum idle pool size and returns this bundle.
     *
     * @return this bundle
     */
    public BasedWays withNoPoolMaxIdle() {
        this.poolMaxIdle = null;
        return this;
    }

    /**
     * Updates the maximum total pool size and returns this bundle.
     *
     * @param poolMaxTotal the new maximum total count
     * @return this bundle
     */
    public BasedWays withPoolMaxTotal(Integer poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
        return this;
    }

    /**
     * Clears the maximum total pool size and returns this bundle.
     *
     * @return this bundle
     */
    public BasedWays withNoPoolMaxTotal() {
        this.poolMaxTotal = null;
        return this;
    }

    /**
     * Returns a cloned bundle with the given JDBC settings.
     *
     * @param dataJdbc the replacement JDBC configuration
     * @return a cloned bundle with the JDBC settings updated
     */
    public BasedWays uponDataJdbc(BasedJdbc dataJdbc) {
        var clone = this.clone();
        clone.dataJdbc = dataJdbc;
        return clone;
    }

    /**
     * Returns a cloned bundle without JDBC settings.
     *
     * @return a cloned bundle with the JDBC settings cleared
     */
    public BasedWays uponNoDataJdbc() {
        var clone = this.clone();
        clone.dataJdbc = null;
        return clone;
    }

    /**
     * Returns a cloned bundle with the given link settings.
     *
     * @param dataLink the replacement link configuration
     * @return a cloned bundle with the link settings updated
     */
    public BasedWays uponDataLink(BasedLink dataLink) {
        var clone = this.clone();
        clone.dataLink = dataLink;
        return clone;
    }

    /**
     * Returns a cloned bundle without link settings.
     *
     * @return a cloned bundle with the link settings cleared
     */
    public BasedWays uponNoDataLink() {
        var clone = this.clone();
        clone.dataLink = null;
        return clone;
    }

    /**
     * Returns a cloned bundle with the given minimum idle count.
     *
     * @param poolMinIdle the replacement minimum idle count
     * @return a cloned bundle with the value updated
     */
    public BasedWays uponPoolMinIdle(Integer poolMinIdle) {
        var clone = this.clone();
        clone.poolMinIdle = poolMinIdle;
        return clone;
    }

    /**
     * Returns a cloned bundle without a minimum idle count.
     *
     * @return a cloned bundle with the value cleared
     */
    public BasedWays uponNoPoolMinIdle() {
        var clone = this.clone();
        clone.poolMinIdle = null;
        return clone;
    }

    /**
     * Returns a cloned bundle with the given maximum idle count.
     *
     * @param poolMaxIdle the replacement maximum idle count
     * @return a cloned bundle with the value updated
     */
    public BasedWays uponPoolMaxIdle(Integer poolMaxIdle) {
        var clone = this.clone();
        clone.poolMaxIdle = poolMaxIdle;
        return clone;
    }

    /**
     * Returns a cloned bundle without a maximum idle count.
     *
     * @return a cloned bundle with the value cleared
     */
    public BasedWays uponNoPoolMaxIdle() {
        var clone = this.clone();
        clone.poolMaxIdle = null;
        return clone;
    }

    /**
     * Returns a cloned bundle with the given maximum total count.
     *
     * @param poolMaxTotal the replacement maximum total count
     * @return a cloned bundle with the value updated
     */
    public BasedWays uponPoolMaxTotal(Integer poolMaxTotal) {
        var clone = this.clone();
        clone.poolMaxTotal = poolMaxTotal;
        return clone;
    }

    /**
     * Returns a cloned bundle without a maximum total count.
     *
     * @return a cloned bundle with the value cleared
     */
    public BasedWays uponNoPoolMaxTotal() {
        var clone = this.clone();
        clone.poolMaxTotal = null;
        return clone;
    }

    /**
     * Creates a deep clone of this bundle.
     *
     * @return a deep copy of this bundle
     */
    @Override
    public BasedWays clone() {
        return (BasedWays) this.deepClone();
    }

    /**
     * Compares this bundle to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this bundle.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this bundle to JSON text.
     *
     * @return the JSON representation of this bundle
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a connection configuration bundle.
     *
     * @param chars the JSON text to parse
     * @return the parsed bundle
     */
    public static BasedWays fromChars(String chars) {
        return Base.fromChars(chars, BasedWays.class);
    }

    /**
     * Returns the configured base name from JDBC settings or link settings.
     *
     * @return the base name, or {@code null}
     */
    public String getName() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.name;
        }
        if (this.dataLink != null) {
            return this.dataLink.name;
        }
        return null;
    }

    /**
     * Returns the JDBC URL from JDBC settings or the formatted link URL.
     *
     * @return the URL, or {@code null}
     */
    public String getUrl() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.url;
        }
        if (this.dataLink != null) {
            return this.dataLink.formUrl();
        }
        return null;
    }

    /**
     * Returns the configured user name from JDBC settings or link settings.
     *
     * @return the user name, or {@code null}
     */
    public String getUser() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.user;
        }
        if (this.dataLink != null) {
            return this.dataLink.user;
        }
        return null;
    }

    /**
     * Returns the configured password from JDBC settings or link settings.
     *
     * @return the password, or {@code null}
     */
    public String getPass() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.pass;
        }
        if (this.dataLink != null) {
            return this.dataLink.pass;
        }
        return null;
    }

    /**
     * Resolves the ORM class from the configured link or from the URL preset.
     *
     * @return the ORM class, or {@code null}
     */
    public Class<? extends EOrm> getEOrmClass() {
        if (this.dataLink != null && this.dataLink.base != null) {
            return this.dataLink.base.eOrmClazz;
        }
        return Based.getEOrmClassFromURL(this.getUrl());
    }

}
