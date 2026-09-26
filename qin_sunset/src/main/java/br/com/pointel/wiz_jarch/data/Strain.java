package br.com.pointel.wiz_jarch.data;

/**
 * Defines optional restriction, modification, and inclusion strings used by
 * query operations.
 */
public class Strain implements Data {

    public String restrict;
    public String modify;
    public String include;

    /**
     * Creates an empty strain.
     */
    public Strain() {
    }

    /**
     * Creates a strain with the given restriction.
     *
     * @param restrict the restriction text
     */
    public Strain(String restrict) {
        this.restrict = restrict;
    }

    /**
     * Creates a strain with restriction and modification text.
     *
     * @param restrict the restriction text
     * @param modify the modification text
     */
    public Strain(String restrict, String modify) {
        this.restrict = restrict;
        this.modify = modify;
    }

    /**
     * Creates a strain with restriction, modification, and inclusion text.
     *
     * @param restrict the restriction text
     * @param modify the modification text
     * @param include the inclusion text
     */
    public Strain(String restrict, String modify, String include) {
        this.restrict = restrict;
        this.modify = modify;
        this.include = include;
    }

    /**
     * Indicates whether the restriction text is set.
     *
     * @return {@code true} when the restriction is non-null; otherwise {@code false}
     */
    public boolean hasRestrict() {
        return this.restrict != null;
    }

    /**
     * Indicates whether the modification text is set.
     *
     * @return {@code true} when the modification is non-null; otherwise {@code false}
     */
    public boolean hasModify() {
        return this.modify != null;
    }

    /**
     * Indicates whether the inclusion text is set.
     *
     * @return {@code true} when the inclusion is non-null; otherwise {@code false}
     */
    public boolean hasInclude() {
        return this.include != null;
    }

    /**
     * Updates the restriction text and returns this strain.
     *
     * @param restrict the new restriction text
     * @return this strain
     */
    public Strain withRestrict(String restrict) {
        this.restrict = restrict;
        return this;
    }

    /**
     * Clears the restriction text and returns this strain.
     *
     * @return this strain
     */
    public Strain withNoRestrict() {
        this.restrict = null;
        return this;
    }

    /**
     * Updates the modification text and returns this strain.
     *
     * @param modify the new modification text
     * @return this strain
     */
    public Strain withModify(String modify) {
        this.modify = modify;
        return this;
    }

    /**
     * Clears the modification text and returns this strain.
     *
     * @return this strain
     */
    public Strain withNoModify() {
        this.modify = null;
        return this;
    }

    /**
     * Updates the inclusion text and returns this strain.
     *
     * @param include the new inclusion text
     * @return this strain
     */
    public Strain withInclude(String include) {
        this.include = include;
        return this;
    }

    /**
     * Clears the inclusion text and returns this strain.
     *
     * @return this strain
     */
    public Strain withNoInclude() {
        this.include = null;
        return this;
    }

    /**
     * Returns a cloned strain with the given restriction text.
     *
     * @param restrict the replacement restriction text
     * @return a cloned strain with the restriction updated
     */
    public Strain uponRestrict(String restrict) {
        var clone = this.clone();
        clone.restrict = restrict;
        return clone;
    }

    /**
     * Returns a cloned strain without restriction text.
     *
     * @return a cloned strain with the restriction cleared
     */
    public Strain uponNoRestrict() {
        var clone = this.clone();
        clone.restrict = null;
        return clone;
    }

    /**
     * Returns a cloned strain with the given modification text.
     *
     * @param modify the replacement modification text
     * @return a cloned strain with the modification updated
     */
    public Strain uponModify(String modify) {
        var clone = this.clone();
        clone.modify = modify;
        return clone;
    }

    /**
     * Returns a cloned strain without modification text.
     *
     * @return a cloned strain with the modification cleared
     */
    public Strain uponNoModify() {
        var clone = this.clone();
        clone.modify = null;
        return clone;
    }

    /**
     * Returns a cloned strain with the given inclusion text.
     *
     * @param include the replacement inclusion text
     * @return a cloned strain with the inclusion updated
     */
    public Strain uponInclude(String include) {
        var clone = this.clone();
        clone.include = include;
        return clone;
    }

    /**
     * Returns a cloned strain without inclusion text.
     *
     * @return a cloned strain with the inclusion cleared
     */
    public Strain uponNoInclude() {
        var clone = this.clone();
        clone.include = null;
        return clone;
    }

    /**
     * Creates a deep clone of this strain.
     *
     * @return a deep copy of this strain
     */
    @Override
    public Strain clone() {
        return (Strain) this.deepClone();
    }

    /**
     * Compares this strain to another object using deep structural equality.
     *
     * @param that the object to compare
     * @return {@code true} when both objects are deeply equal; otherwise {@code false}
     */
    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    /**
     * Computes a deep structural hash code for this strain.
     *
     * @return the deep hash value
     */
    @Override
    public int hashCode() {
        return this.deepHash();
    }

    /**
     * Serializes this strain to JSON text.
     *
     * @return the JSON representation of this strain
     */
    @Override
    public String toString() {
        return this.toChars();
    }

    /**
     * Deserializes JSON text into a strain.
     *
     * @param chars the JSON text to parse
     * @return the parsed strain
     */
    public static Strain fromChars(String chars) {
        return Base.fromChars(chars, Strain.class);
    }

}
