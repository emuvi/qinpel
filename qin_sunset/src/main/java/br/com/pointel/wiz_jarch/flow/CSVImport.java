package br.com.pointel.wiz_jarch.flow;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;
import org.slf4j.LoggerFactory;

import br.com.pointel.wiz_jarch.data.BasedLink;
import br.com.pointel.wiz_jarch.data.Field;
import br.com.pointel.wiz_jarch.data.Insert;
import br.com.pointel.wiz_jarch.data.Table;
import br.com.pointel.wiz_jarch.data.TableHead;
import br.com.pointel.wiz_jarch.data.Valued;
import br.com.pointel.wiz_jarch.mage.WizFile;

/**
 * A background task (Runnable) that imports CSV and structure (.tab) files into a live database.
 * Capable of importing single files or scraping an entire directory.
 */
public class CSVImport implements Runnable {

    private final File origin;
    private final BasedLink destiny;
    private final Pace pace;

    /**
     * Constructs a CSVImport task with a default progress Pace logger.
     *
     * @param origin  the source CSV file or directory containing CSVs
     * @param destiny the destination database connection configuration
     */
    public CSVImport(File origin, BasedLink destiny) {
        this(origin, destiny, null);
    }

    /**
     * Constructs a CSVImport task with a custom progress Pace logger.
     *
     * @param origin  the source CSV file or directory containing CSVs
     * @param destiny the destination database connection configuration
     * @param pace    the Pace instance to track insertion progress and logging
     */
    public CSVImport(File origin, BasedLink destiny, Pace pace) {
        this.origin = origin;
        this.destiny = destiny;
        this.pace = pace != null ? pace
            : new Pace(LoggerFactory.getLogger(CSVImport.class));
    }

    /**
     * Executes the import workflow. Connects to the target database and processes
     * the requested files. It automatically handles matching CSVs with their corresponding
     * metadata definitions (.tab) if present.
     */
    @Override
    public void run() {
        try {
            if (!origin.exists()) {
                throw new Exception("The origin must exist.");
            }
            try (var connection = destiny.connect()) {
                pace.info("Connected to destiny database.");
                if (origin.isFile()) {
                    importCSVFile(origin, connection);
                } else {
                    for (File inside : origin.listFiles()) {
                        if (isCSVFile(inside)) {
                            importCSVFile(inside, connection);
                        }
                    }
                }
            }
            pace.info("Finished to import from CSV.");
        } catch (Exception e) {
            pace.error("Could not import", e);
        }
    }

    /**
     * Tests if a given file matches the CSV signature.
     *
     * @param file the file to test
     * @return true if the file is a CSV
     */
    public static boolean isCSVFile(File file) {
        return file != null && file.isFile() && file.getName().toLowerCase().endsWith(".csv");
    }

    /**
     * Handles the complex logic of parsing a single CSV file, reconciling its column headers
     * against a database table or a local .tab metadata file, creating the table if needed,
     * and performing batch inserts of all row records.
     *
     * @param csvFile the CSV data payload
     * @param link    the active database connection
     * @throws Exception if an I/O or SQL error occurs
     */
    private void importCSVFile(File csvFile, Connection link) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Importing CSV File: " + csvFile.getName());
        var eOrmDestiny = destiny.getEOrm(link);
        var tableName = WizFile.getBaseName(csvFile.getName());
        var tableFile = new File(csvFile.getParent(), tableName + ".tab");
        Table table;
        
        // Ensure table metadata is synchronized
        if (tableFile.exists()) {
            pace.info("Loading table metadata from file.");
            table = Table.fromChars(Files.readString(tableFile.toPath()));
            eOrmDestiny.create(table, true);
        } else {
            pace.info("Loading table metadata from connection.");
            String schema = null;
            var name = tableName;
            if (name.contains(".")) {
                schema = WizFile.getBaseName(name);
                name = WizFile.getExtension(name);
            }
            table = new TableHead(null, schema, name).getTable(link);
        }
        
        // Parse CSV and Insert
        try (var reader = new CSVFile(csvFile, CSVFile.Mode.READ)) {
            pace.info("CSV File: " + csvFile.getName() + " opened.");
            var firstLine = true;
            String[] line;
            var lineCount = 0l;
            while ((line = reader.readLine()) != null) {
                pace.waitIfPausedAndThrowIfStopped();
                lineCount++;
                pace.info("Processing line  " + lineCount + " of file: " + csvFile
                                .getName());
                var values = new Object[line.length];
                for (var i = 0; i < values.length; i++) {
                    if (firstLine) {
                        values[i] = line[i];
                    } else {
                        var field = table.fieldList.get(i);
                        values[i] = field.fromFormatted(line[i]);
                        fixValuesForSQLTypes(values, i, field);
                    }
                }
                var fieldList = new ArrayList<Field>();
                if (firstLine) {
                    pace.info("Making sure the table fieldList match on the first line.");
                    firstLine = false;
                    for (Object value : values) {
                        for (Field field : table.fieldList) {
                            if (Objects.equals(value, field.name)) {
                                fieldList.add(field);
                                break;
                            }
                        }
                    }
                    if (fieldList.size() == values.length) {
                        table.fieldList = fieldList;
                    }
                } else {
                    pace.info("Inserting line  " + lineCount + " of file: " + csvFile
                                    .getName());
                    var valuedList = new ArrayList<Valued>();
                    for (var i = 0; i < values.length; i++) {
                        var field = table.fieldList.get(i);
                        var valued = new Valued(field.name, field.nature, values[i]);
                        valuedList.add(valued);
                    }
                    pace.waitIfPausedAndThrowIfStopped();
                    eOrmDestiny.insert(new Insert(table.tableHead, valuedList));
                }
            }
        }
    }

    /**
     * Translates parsed util.Dates into native sql.Date/Time/Timestamp objects prior to JDBC insertion.
     *
     * @param values the array containing the row data
     * @param i      the index of the specific value being fixed
     * @param field  the field schema dictating the target nature
     */
    private void fixValuesForSQLTypes(Object[] values, int i, Field field) {
        if (values[i] == null) {
            return;
        }
        switch (field.nature) {
            case Date:
                values[i] = new java.sql.Date(((java.util.Date) values[i]).getTime());
                break;
            case Time:
                values[i] = new java.sql.Time(((java.util.Date) values[i]).getTime());
                break;
            case Timestamp:
                values[i] = new java.sql.Timestamp(((java.util.Date) values[i])
                                .getTime());
                break;
            default:
                break;
        }
    }
}
