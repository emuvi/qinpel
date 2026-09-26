package br.com.pointel.wiz_jarch.flow;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.pointel.wiz_jarch.data.Nature;
import br.com.pointel.wiz_jarch.data.Typed;
import br.com.pointel.wiz_jarch.mage.WizBased;
import br.com.pointel.wiz_jarch.mage.WizData;

/**
 * Translates active SQL Database {@link ResultSet} records into standard string arrays 
 * suitable for CSV exporting. Leverages field Natures to ensure date/time and numeric 
 * fields are formatted appropriately.
 */
public class CSVMaker {
    private final ResultSet results;
    private final Nature[] natures;

    /**
     * Constructs a CSVMaker initialized with a specific list of typed fields.
     * If typeds is null, it attempts to dynamically resolve the natures from the ResultSet metadata.
     *
     * @param results the active database ResultSet
     * @param typeds  a list of Typed field definitions describing the result set
     * @throws Exception if resolving natures from the ResultSet fails
     */
    public CSVMaker(ResultSet results, List<Typed> typeds) throws Exception {
        this.results = results;
        if (typeds != null) {
            this.natures = new Nature[typeds.size()];
            for (int i = 0; i < typeds.size(); i++) {
                this.natures[i] = typeds.get(i).type;
            }
        } else {
            this.natures = WizBased.getNaturesFrom(results);
        }
    }

    /**
     * Constructs a CSVMaker initialized with an array of specific natures.
     * If natures is null, it attempts to dynamically resolve the natures from the ResultSet metadata.
     *
     * @param results the active database ResultSet
     * @param natures an array of Nature enumerations describing the result set columns
     * @throws Exception if resolving natures from the ResultSet fails
     */
    public CSVMaker(ResultSet results, Nature[] natures) throws Exception {
        this.results = results;
        if (natures != null) {
            this.natures = natures;
        } else {
            this.natures = WizBased.getNaturesFrom(results);
        }
    }

    /**
     * Advances the ResultSet cursor to the next row and extracts its columns into a string array.
     * Values are formatted into strings based on their data type's Nature.
     *
     * @return an array of formatted strings representing the row data, or null if the ResultSet is exhausted
     * @throws Exception if a database access error occurs or formatting fails
     */
    public String[] makeLine() throws Exception {
        if (!this.results.next()) {
            return null;
        }
        var result = new String[this.natures.length];
        for (int i = 1; i <= this.natures.length; i++) {
            result[(i - 1)] = WizData.toFormatted(this.natures[(i - 1)], this.results
                            .getObject(i));
        }
        return result;
    }

    /**
     * Advances the ResultSet cursor through all remaining rows and extracts them into a list of string arrays.
     *
     * @return a list of string arrays representing the remaining rows
     * @throws Exception if a database access error occurs or formatting fails
     */
    public List<String[]> makeAllLines() throws Exception {
        var lines = new ArrayList<String[]>();
        String[] line;
        while ((line = makeLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
