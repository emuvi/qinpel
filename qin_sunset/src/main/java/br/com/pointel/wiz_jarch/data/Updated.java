package br.com.pointel.wiz_jarch.data;

public class Updated {

    public final Update update;
    public final int count;

    public Updated(Update update, int count) {
        this.update = update;
        this.count = count;
    }

    public boolean hadEffect() {
        return this.count >= 0;
    }

}
