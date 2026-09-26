package br.com.pointel.wiz_jarch.data;

import java.sql.ResultSet;
import java.util.List;

import br.com.pointel.wiz_jarch.mage.WizBased;

public class Selected {
    
    public final Select select;
    public final ResultSet resultSet;

    public Selected(Select select, ResultSet resultSet) {
        this.select = select;
        this.resultSet = resultSet;
    }

    public boolean hasResults() throws Exception {
        return this.resultSet != null && this.resultSet.isBeforeFirst();
    }

    public <T> T mapResult(Class<T> onClass) throws Exception {
        if (resultSet.next()) {
            return WizBased.mapResult(resultSet, select.fieldList, onClass);
        } else {
            return null;
        }
    }

    public <T> List<T> mapResults(Class<T> onClass) throws Exception {
        return WizBased.mapResults(resultSet, select.fieldList, onClass);
    }
    
}
