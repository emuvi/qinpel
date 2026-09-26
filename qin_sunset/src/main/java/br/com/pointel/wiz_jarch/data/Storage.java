package br.com.pointel.wiz_jarch.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;

import br.com.pointel.wiz_jarch.mage.WizString;

public class Storage {

    private final Map<String, BasicDataSource> stores;
    private final Bases bases;

    public Storage(Bases bases) {
        this.stores = new HashMap<>();
        this.bases = bases;
    }

    public Connection getLink(String ofBaseName) throws Exception {
        var dataSource = getSource(ofBaseName);
        return dataSource.getConnection();
    }

    public EOrm getEOrm(String onBaseName) throws Exception {
        var dataSource = getSource(onBaseName);
        var link = dataSource.getConnection();
        link.setAutoCommit(true);
        var dataWay = this.bases.getFromName(onBaseName);
        var eOrmClass = dataWay.getEOrmClass();
        return eOrmClass.getConstructor(Connection.class).newInstance(link);
    }

    public ESql getESql(String onBaseName) throws Exception {
        var dataSource = getSource(onBaseName);
        var link = dataSource.getConnection();
        link.setAutoCommit(true);
        return new ESql(link);
    }

    private BasicDataSource getSource(String onBaseName) throws Exception {
        if (this.stores.containsKey(onBaseName)) {
            return this.stores.get(onBaseName);
        }
        var dataWay = this.bases.getFromName(onBaseName);
        if (dataWay == null) {
            throw new Exception("Base " + onBaseName + " not found");
        }
        return this.newSourceOnStore(dataWay);
    }

    private BasicDataSource newSourceOnStore(BasedWays dataWays) throws Exception {
        if (dataWays == null) {
            throw new Exception("DataWays cannot be null.");
        }
        var name = dataWays.getName();
        if (WizString.isEmpty(name)) {
            throw new Exception("DataWays name cannot be empty.");
        }
        var url = dataWays.getUrl();
        if (WizString.isEmpty(url)) {
            throw new Exception("DataWays URL cannot be empty.");
        }
        if (dataWays.dataJdbc == null && dataWays.dataLink == null) {
            throw new Exception("Either dataJdbc or dataLink must be provided in DataWays.");
        }
        if (dataWays.dataJdbc != null && dataWays.dataLink != null) {
            throw new Exception("Only one of dataJdbc or dataLink should be provided in DataWays.");
        }
        var newSource = new BasicDataSource();
        newSource.setUrl(url);
        var user = dataWays.getUser();
        if (!WizString.isEmpty(user)) {
            newSource.setUsername(user);
        }
        var pass = dataWays.getPass();
        if (!WizString.isEmpty(pass)) {
            newSource.setPassword(pass);
        }
        newSource.setMinIdle(dataWays.poolMinIdle);
        newSource.setMaxIdle(dataWays.poolMaxIdle);
        newSource.setMaxTotal(dataWays.poolMaxTotal);
        this.stores.put(name, newSource);
        return newSource;
    }

}
