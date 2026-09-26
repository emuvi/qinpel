package br.com.pointel.wiz_jarch.data;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class EOrm implements Closeable {

    private final Connection link;

    protected EOrm(Connection link) {
        this.link = link;
    }

    public Connection getLink() {
        return link;
    }

    public abstract Heads getHeads() throws Exception;

    public abstract void create(Table table, boolean ifNotExists) throws Exception;
    
    public void create(Table table) throws Exception {
        create(table, false);
    }

    public void createIfNotExists(Table table) throws Exception {
        create(table, true);
    }

    public abstract void create(Index index, boolean ifNotExists) throws Exception;

    public void create(Index index) throws Exception {
        create(index, false);
    }

    public void createIfNotExists(Index index) throws Exception {
        create(index, true);
    }

    public abstract Selected select(Select select, Strain strain) throws Exception;

    public Selected select(Select select) throws Exception {
        return select(select, null);
    }

    public abstract Inserted insert(Insert insert, Strain strain) throws Exception;

    public Inserted insert(Insert insert) throws Exception {
        return insert(insert, null);
    }

    public abstract Updated update(Update update, Strain strain) throws Exception;

    public Updated update(Update update) throws Exception {
        return update(update, null);
    }

    public abstract Deleted delete(Delete delete, Strain strain) throws Exception;

    public Deleted delete(Delete delete) throws Exception {
        return delete(delete, null);
    }

    public abstract boolean isPrimaryKeyError(Exception error);

    @Override
    public void close() throws IOException {
        try {
            this.link.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
