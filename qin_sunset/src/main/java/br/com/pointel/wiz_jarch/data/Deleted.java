package br.com.pointel.wiz_jarch.data;

public class Deleted {

    public final Delete delete;
    public final int count;

    public Deleted(Delete delete, int count) {
        this.delete = delete;
        this.count = count;
    }

    public boolean hadEffect() {
        return this.count >= 0;
    }

}
