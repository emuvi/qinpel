package br.com.pointel.wiz_jarch.data;

public class Inserted {

    public final Insert insert;
    public final int count;
    public final String id;

    public Inserted(Insert insert, int count, String id) {
        this.insert = insert;
        this.count = count;
        this.id = id;
    }

    public boolean hadEffect() {
        return this.count >= 0;
    }

}
