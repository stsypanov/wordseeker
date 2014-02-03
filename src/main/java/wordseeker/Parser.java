package wordseeker;

import org.apache.commons.io.FilenameUtils;
import wordseeker.gui.table.TableEntry;
import wordseeker.gui.table.TableModel;
import wordseeker.gui.MainPanel;
import wordseeker.logics.PlainFileParser;
import wordseeker.logics.DocParser;
import wordseeker.logics.DocxParser;
import wordseeker.logics.RTFParser;

import java.io.*;
import java.util.List;

public class Parser implements Runnable {
    private static final String DOCX = "docx";
    private static final String DOC = "doc";
    private static final String TXT = "txt";
    private static final String RTF = "rtf";
    private File rootDirectory;
    private DocParser docParser, docRegexParser;
    private DocxParser docxParser, docxRegexRapser;
    private RTFParser rtfParser;
    private PlainFileParser plainFileParser, plainFileRegexParser;
    private boolean docxSelected;
    private boolean docSelected;
    private boolean rtfSelected;
    private boolean txtSelected;
    private String regEx;

    /**
     * Constructor for the case when we use regular expressions.
     *
     * @param rootDirectory init directory for the file parser
     * @param regExp        regular expression as a search parameter
     * @param docxSelected  if true search is performed for *.docs files
     * @param docSelected   if true search is performed for *.doc files
     * @param rtfSelected   if true search is performed for *.rtf files
     * @param txtSelected   if true search is performed for plain text files
     * @throws FileNotFoundException
     */
    public Parser(File rootDirectory, String regExp,
                  boolean docxSelected, boolean docSelected,
                  boolean rtfSelected, boolean txtSelected)
            throws FileNotFoundException {

        if (rootDirectory.exists() && rootDirectory.isDirectory()) {
            this.rootDirectory = rootDirectory;
        } else {
            throw new FileNotFoundException();
        }

        this.regEx = regExp;
        this.docSelected = docSelected;
        this.docxSelected = docxSelected;
        this.rtfSelected = rtfSelected;
        this.txtSelected = txtSelected;

        if (docxSelected) {
            docxRegexRapser = new DocxParser(regExp);
        }
        if (docSelected) {
            docRegexParser = new DocParser(regExp);
        }
        if (rtfSelected) {
            rtfParser = new RTFParser();
        }
        if (txtSelected) {
            plainFileRegexParser = new PlainFileParser(regExp);
        }
    }

    /**
     * Constructor for the case when we use words or word combinations.
     *
     * @param rootDirectory   init directory for the file parser
     * @param toBeFound       string to be found in parsed files
     * @param isCaseSensitive if true only strictly matching strings are viewed
     * @param docxSelected    if true search is performed for *.docs files
     * @param docSelected     if true search is performed for *.doc files
     * @param rtfSelected     if true search is performed for *.rtf files
     * @param txtSelected     if true search is performed for plain text files
     * @throws FileNotFoundException
     */
    public Parser(File rootDirectory, String toBeFound, boolean isCaseSensitive,
                  boolean docxSelected, boolean docSelected,
                  boolean rtfSelected, boolean txtSelected)
            throws FileNotFoundException {
        if (rootDirectory.exists() && rootDirectory.isDirectory()) {
            this.rootDirectory = rootDirectory;
        } else {
            throw new FileNotFoundException();
        }

        this.docSelected = docSelected;
        this.docxSelected = docxSelected;
        this.rtfSelected = rtfSelected;
        this.txtSelected = txtSelected;

        if (docxSelected) {
            docxParser = new DocxParser(toBeFound, isCaseSensitive);
        }
        if (docSelected) {
            docParser = new DocParser(toBeFound, isCaseSensitive);
        }
        if (rtfSelected) {
            rtfParser = new RTFParser();
        }
        if (txtSelected) {
            plainFileParser = new PlainFileParser(toBeFound, isCaseSensitive);
        }
    }

    /**
     * Here file parsing begins in separate thread.
     */
    @Override
    public void run() {
        parseDirectory(rootDirectory);
    }

    /**
     * Scan all subdirectories starting from the root one.
     *
     * @param rootDirectory root directory for the file parser
     */
    private void parseDirectory(final File rootDirectory) {

        final File[] filesInDirectory = rootDirectory.listFiles();

        assert filesInDirectory != null;

        for (File file : filesInDirectory) {

            if (file.isDirectory())
                parseDirectory(file);
            else {
                parseFile(file);

                //scroll JScrollPane
                MainPanel.getJsp().getVerticalScrollBar().setValue(MainPanel.getJsp().getVerticalScrollBar().getMaximum());
            }
        }
    }

    private void parseFile(File file) {
        final String pathToFile = file.getAbsolutePath();
        final String fileExtension = FilenameUtils.getExtension(file.getName());

        if (fileExtension.equals(DOCX) && docxSelected) {
            parseDOCX(file, pathToFile);
        }

        if (fileExtension.equals(DOC) && docSelected) {
            parseDOC(file, pathToFile);
        }

        if (fileExtension.equals(TXT) && txtSelected) {
            parseTXT(file, pathToFile);
        }

        if (fileExtension.equals(RTF) && rtfSelected) {
            parseRTF(file, pathToFile);
        }
    }

    private void parseDOCX(File file, String pathToFile) {
        List<String> linesWithMatches;

        if (regEx != null) {
            linesWithMatches = docxRegexRapser.findRegexp(file);
        } else {
            linesWithMatches = docxParser.findExpression(file);
        }

        if (!linesWithMatches.isEmpty()) {

            for (String lineInFile : linesWithMatches) {
                TableEntry entry = new TableEntry(pathToFile, lineInFile);
                TableModel.getInstance().getListOfEntries().add(entry);
            }
            TableModel.getInstance().fireTableDataChanged();
        }
    }

    private void parseDOC(File file, String pathToFile) {
        List<String> linesWithMatches;

        if (regEx != null) {
            linesWithMatches = docRegexParser.findRegex(file);
        } else {
            linesWithMatches = docParser.findExpression(file);
        }

        if (linesWithMatches != null && !linesWithMatches.isEmpty()) {

            for (String lineInFile : linesWithMatches) {
                TableEntry entry = new TableEntry(pathToFile, lineInFile);
                TableModel.getInstance().getListOfEntries().add(entry);
            }
            TableModel.getInstance().fireTableDataChanged();
        }
    }

    private void parseTXT(File file, String pathToFile) {
        List<String> linesWithMatches;

        if (regEx != null) {
            linesWithMatches = plainFileRegexParser.findRegExp(file);
        } else {
            linesWithMatches = plainFileParser.findExpression(file);
        }

        if (!linesWithMatches.isEmpty()) {

            for (String lineInFile : linesWithMatches) {
                TableEntry entry = new TableEntry(pathToFile, lineInFile);
                TableModel.getInstance().getListOfEntries().add(entry);
            }
            TableModel.getInstance().fireTableDataChanged();
        }
    }

    private void parseRTF(File file, String pathToFile) {
        List<String> linesWithMatches;

        if (regEx != null) {
            linesWithMatches = rtfParser.findRegexp(file);
        } else {
            linesWithMatches = rtfParser.findExpression(file);
        }

        if (!linesWithMatches.isEmpty()) {

            for (String lineInFile : linesWithMatches) {
                TableEntry entry = new TableEntry(pathToFile, lineInFile);
                TableModel.getInstance().getListOfEntries().add(entry);
            }
            TableModel.getInstance().fireTableDataChanged();
        }
    }
}
