package br.com.pointel.wiz_jarch.data;

public class Filter implements Data {

    public FilterSeems seems;
    public FilterLikes likes;
    public Valued valued;
    public Linked linked;
    public FilterTies ties;

    public Filter() {
        this(FilterSeems.Is, FilterLikes.Equals, null, null, FilterTies.And);
    }

    public Filter(Valued valued) {
        this(FilterSeems.Is, FilterLikes.Equals, valued, null, FilterTies.And);
    }

    public Filter(Linked linked) {
        this(FilterSeems.Is, FilterLikes.Equals, null, linked, FilterTies.And);
    }

    public Filter(FilterSeems seem, FilterLikes likes, Valued valued, Linked linked, FilterTies ties) {
        this.seems = seem;
        this.likes = likes;
        this.valued = valued;
        this.linked = linked;
        this.ties = ties;
    }

    public boolean hasSeems() {
        return this.seems != null;
    }

    public boolean hasLikes() {
        return this.likes != null;
    }

    public boolean hasValued() {
        return this.valued != null;
    }

    public boolean hasLinked() {
        return this.linked != null;
    }

    public boolean hasTies() {
        return this.ties != null;
    }

    public Filter withSeems(FilterSeems seems) {
        this.seems = seems;
        return this;
    }

    public Filter withNoSeems() {
        this.seems = null;
        return this;
    }

    public Filter withLikes(FilterLikes likes) {
        this.likes = likes;
        return this;
    }

    public Filter withNoLikes() {
        this.likes = null;
        return this;
    }

    public Filter withValued(Valued valued) {
        this.valued = valued;
        return this;
    }

    public Filter withNoValued() {
        this.valued = null;
        return this;
    }

    public Filter withLinked(Linked linked) {
        this.linked = linked;
        return this;
    }

    public Filter withNoLinked() {
        this.linked = null;
        return this;
    }

    public Filter withTies(FilterTies ties) {
        this.ties = ties;
        return this;
    }

    public Filter withNoTies() {
        this.ties = null;
        return this;
    }

    public Filter uponSeems(FilterSeems seems) {
        var clone = this.clone();
        clone.seems = seems;
        return clone;
    }

    public Filter uponNoSeems() {
        var clone = this.clone();
        clone.seems = null;
        return clone;
    }

    public Filter uponLikes(FilterLikes likes) {
        var clone = this.clone();
        clone.likes = likes;
        return clone;
    }

    public Filter uponNoLikes() {
        var clone = this.clone();
        clone.likes = null;
        return clone;
    }

    public Filter uponValued(Valued valued) {
        var clone = this.clone();
        clone.valued = valued;
        return clone;
    }

    public Filter uponNoValued() {
        var clone = this.clone();
        clone.valued = null;
        return clone;
    }

    public Filter uponLinked(Linked linked) {
        var clone = this.clone();
        clone.linked = linked;
        return clone;
    }

    public Filter uponNoLinked() {
        var clone = this.clone();
        clone.linked = null;
        return clone;
    }

    public Filter uponTies(FilterTies ties) {
        var clone = this.clone();
        clone.ties = ties;
        return clone;
    }

    public Filter uponNoTies() {
        var clone = this.clone();
        clone.ties = null;
        return clone;
    }

    @Override
    public Filter clone() {
        return (Filter) this.deepClone();
    }

    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Filter fromChars(String chars) {
        return Base.fromChars(chars, Filter.class);
    }
    
    public Filter withField(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        this.valued = field.toValued();
        return this;
    }

    public Filter withField(Field field, Object value) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        this.valued = field.toValued(value);
        return this;
    }

    public Filter withValue(Object value) {
        if (this.valued == null) {
            throw new IllegalArgumentException("Valued cannot be null");
        }
        this.valued.value = value;
        return this;
    }

    public Filter uponField(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        var clone = this.clone();
        clone.valued = field.toValued();
        return clone;
    }

    public Filter uponField(Field field, Object value) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        var clone = this.clone();
        clone.valued = field.toValued(value);
        return clone;
    }

    public Filter uponValue(Object value) {
        var clone = this.clone();
        if (clone.valued == null) {
            throw new IllegalArgumentException("Valued cannot be null");
        }
        clone.valued.value = value;
        return clone;
    }

}
