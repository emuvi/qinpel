package br.com.pointel.wiz_jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import br.com.pointel.wiz_jarch.mage.WizArray;

/**
 * Utility class providing file extension matching algorithms to identify 
 * Portable Document Format (PDF) files.
 */
public class DocReaderPDFUtils {

    /** List of supported PDF file extensions. */
    public static String[] PDF_EXTENSIONS = new String[]{ "pdf", "fdf", "xfdf", "pdx", "ppdf" };

    /**
     * Checks if the given file has a recognized PDF extension.
     *
     * @param file the file to check
     * @return true if the file is recognized as a PDF format
     */
    public static boolean isPDFFile(File file) {
        return isPDFFile(file.getName());
    }

    /**
     * Checks if the given file name has a recognized PDF extension.
     *
     * @param fileName the name of the file to check
     * @return true if the file name has a recognized PDF extension
     */
    public static boolean isPDFFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), PDF_EXTENSIONS);
    }
    /**
     * Retrieves all registered PDF extensions.
     *
     * @return a list of all supported PDF file extensions
     */
    public static java.util.List<String> getAllExtensions() {
        return java.util.Arrays.asList(PDF_EXTENSIONS);
    }
    
}
