package br.com.pointel.wiz_jarch.flow;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import br.com.pointel.wiz_jarch.mage.WizArray;

/**
 * Utility class providing file extension matching algorithms to identify 
 * Microsoft Office documents.
 */
public class DocReaderMSOUtils {

    /** List of supported Microsoft PowerPoint file extensions. */
    public static String[] MSPOWERPOINT_EXTENSIONS = new String[]{ "ppt", "pptx" };

    /** List of supported Microsoft Excel file extensions. */
    public static String[] MSEXCEL_EXTENSIONS = new String[]{ "xls", "xlsx" };

    /** List of supported Microsoft Word file extensions. */
    public static String[] MSWORD_EXTENSIONS = new String[]{ "doc", "docx" };

    /**
     * Checks if the given file has a recognized Microsoft Word extension.
     *
     * @param file the file to check
     * @return true if the file is a Microsoft Word document
     */
    public static boolean isMSWordFile(File file) {
        return isMSWordFile(file.getName());
    }

    /**
     * Checks if the given file name has a recognized Microsoft Word extension.
     *
     * @param fileName the name of the file to check
     * @return true if the file is a Microsoft Word document
     */
    public static boolean isMSWordFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), MSWORD_EXTENSIONS);
    }

    /**
     * Checks if the given file has a recognized Microsoft PowerPoint extension.
     *
     * @param file the file to check
     * @return true if the file is a Microsoft PowerPoint document
     */
    public static boolean isMSPowerPointFile(File file) {
        return isMSPowerPointFile(file.getName());
    }

    /**
     * Checks if the given file name has a recognized Microsoft PowerPoint extension.
     *
     * @param fileName the name of the file to check
     * @return true if the file is a Microsoft PowerPoint document
     */
    public static boolean isMSPowerPointFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), MSPOWERPOINT_EXTENSIONS);
    }

    /**
     * Checks if the given file has a recognized Microsoft Excel extension.
     *
     * @param file the file to check
     * @return true if the file is a Microsoft Excel document
     */
    public static boolean isMSExcelFile(File file) {
        return isMSExcelFile(file.getName());
    }

    /**
     * Checks if the given file name has a recognized Microsoft Excel extension.
     *
     * @param fileName the name of the file to check
     * @return true if the file is a Microsoft Excel document
     */
    public static boolean isMSExcelFile(String fileName) {
        return WizArray.has(FilenameUtils.getExtension(fileName).toLowerCase(), MSEXCEL_EXTENSIONS);
    }
    /**
     * Combines all registered Microsoft Office extensions into a single list.
     *
     * @return a combined list of all supported Microsoft Office file extensions
     */
    public static java.util.List<String> getAllExtensions() {
        var list = new java.util.ArrayList<String>();
        list.addAll(java.util.Arrays.asList(MSWORD_EXTENSIONS));
        list.addAll(java.util.Arrays.asList(MSEXCEL_EXTENSIONS));
        list.addAll(java.util.Arrays.asList(MSPOWERPOINT_EXTENSIONS));
        return list;
    }
    
}
