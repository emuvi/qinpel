package br.com.pointel.wiz_jarch.flow;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import br.com.pointel.wiz_jarch.mage.WizString;

/**
 * A low-level CSV writer that safely formats string arrays into valid CSV rows.
 * Automatically handles wrapping strings in quotes if they contain commas, spaces, or quotes,
 * and escapes internal double-quotes according to CSV standards.
 */
public class CSVWrite implements Closeable {

    private final PrintWriter writer;

    /**
     * Wraps a generic character writer for CSV output.
     *
     * @param writer the underlying character stream writer
     * @throws Exception if wrapping fails
     */
    public CSVWrite(Writer writer) throws Exception {
        this.writer = new PrintWriter(writer);
    }

    /**
     * Takes an array of strings, formats them according to CSV rules, and writes a single line
     * to the output stream. Appends a platform-specific line separator at the end.
     *
     * @param columns varargs representing the data to write as columns
     */
    public void writeLine(String... columns) {
        if (columns != null) {
            for (var i = 0; i < columns.length; i++) {
                var column = WizString.replaceBreaks(columns[i]);
                // Enclose in quotes if the data contains special CSV formatting characters
                if (WizString.contains(column, '"', ' ', ',', ';')) {
                    column = '"' + column.replace("\"", "\"\"") + '"';
                }
                if (i > 0) {
                    this.writer.write(",");
                }
                if (WizString.isNotEmpty(column)) {
                    this.writer.write(column);
                }
            }
        }
        this.writer.write(System.lineSeparator());
    }

    /**
     * Writes multiple rows to the output stream.
     *
     * @param lines an iterable collection of string arrays to write
     */
    public void writeLines(Iterable<String[]> lines) {
        if (lines != null) {
            for (String[] line : lines) {
                writeLine(line);
            }
        }
    }

    /**
     * Writes multiple rows to the output stream.
     *
     * @param lines an array of string arrays to write
     */
    public void writeLines(String[][] lines) {
        if (lines != null) {
            for (String[] line : lines) {
                writeLine(line);
            }
        }
    }

    /**
     * Flushes the underlying PrintWriter.
     */
    public void flush() {
        this.writer.flush();
    }

    /**
     * Closes the underlying PrintWriter.
     *
     * @throws IOException if closing the stream fails
     */
    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
