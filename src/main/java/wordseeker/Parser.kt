package wordseeker

import org.apache.commons.io.FilenameUtils
import wordseeker.gui.table.TableEntry
import wordseeker.gui.table.TableModel
import wordseeker.gui.MainPanel
import wordseeker.logics.DocParser
import wordseeker.logics.PlainFileParser
import wordseeker.logics.DocxParser
import wordseeker.logics.RTFParser

import java.io.*

class Parser : Runnable {
    private var rootDirectory: File
    private var docParser: DocParser? = null
    private var docRegexParser: DocParser? = null
    private var docxParser: DocxParser? = null
    private var docxRegexRapser: DocxParser? = null
    private var rtfParser: RTFParser? = null
    private var plainFileParser: PlainFileParser? = null
    private var plainFileRegexParser: PlainFileParser? = null
    private var docxSelected: Boolean = false
    private var docSelected: Boolean = false
    private var rtfSelected: Boolean = false
    private var txtSelected: Boolean = false
    private var regEx: String? = null

    /**
     * Constructor for the case when we use regular expressions.

     * @param rootDirectory init directory for the file parser
     * *
     * @param regExp        regular expression as a search parameter
     * *
     * @param docxSelected  if true search is performed for *.docs files
     * *
     * @param docSelected   if true search is performed for *.doc files
     * *
     * @param rtfSelected   if true search is performed for *.rtf files
     * *
     * @param txtSelected   if true search is performed for plain text files
     * *
     * @throws FileNotFoundException
     */
    @Throws(FileNotFoundException::class)
    constructor(rootDirectory: File, regExp: String,
                docxSelected: Boolean, docSelected: Boolean,
                rtfSelected: Boolean, txtSelected: Boolean) {

        if (rootDirectory.exists() && rootDirectory.isDirectory) {
            this.rootDirectory = rootDirectory
        } else {
            throw FileNotFoundException()
        }

        this.regEx = regExp
        this.docSelected = docSelected
        this.docxSelected = docxSelected
        this.rtfSelected = rtfSelected
        this.txtSelected = txtSelected

        if (docxSelected) {
            docxRegexRapser = DocxParser(regExp)
        }
        if (docSelected) {
            docRegexParser = DocParser(regExp)
        }
        if (rtfSelected) {
            rtfParser = RTFParser()
        }
        if (txtSelected) {
            plainFileRegexParser = PlainFileParser(regExp)
        }
    }

    /**
     * Constructor for the case when we use words or word combinations.

     * @param rootDirectory   init directory for the file parser
     * *
     * @param toBeFound       string to be found in parsed files
     * *
     * @param isCaseSensitive if true only strictly matching strings are viewed
     * *
     * @param docxSelected    if true search is performed for *.docs files
     * *
     * @param docSelected     if true search is performed for *.doc files
     * *
     * @param rtfSelected     if true search is performed for *.rtf files
     * *
     * @param txtSelected     if true search is performed for plain text files
     * *
     * @throws FileNotFoundException
     */
    @Throws(FileNotFoundException::class)
    constructor(rootDirectory: File, toBeFound: String, isCaseSensitive: Boolean,
                docxSelected: Boolean, docSelected: Boolean,
                rtfSelected: Boolean, txtSelected: Boolean) {
        if (rootDirectory.exists() && rootDirectory.isDirectory) {
            this.rootDirectory = rootDirectory
        } else {
            throw FileNotFoundException()
        }

        this.docSelected = docSelected
        this.docxSelected = docxSelected
        this.rtfSelected = rtfSelected
        this.txtSelected = txtSelected

        if (docxSelected) {
            docxParser = DocxParser(toBeFound, isCaseSensitive)
        }
        if (docSelected) {
            docParser = DocParser(toBeFound, isCaseSensitive)
        }
        if (rtfSelected) {
            rtfParser = RTFParser()
        }
        if (txtSelected) {
            plainFileParser = PlainFileParser(toBeFound, isCaseSensitive)
        }
    }

    /**
     * Here file parsing begins in separate thread.
     */
    override fun run() {
        parseDirectory(rootDirectory)
    }

    /**
     * Scan all subdirectories starting from the root one.

     * @param rootDirectory root directory for the file parser
     */
    private fun parseDirectory(rootDirectory: File) {

        val filesInDirectory = rootDirectory.listFiles()

        assert(filesInDirectory != null)

        for (file in filesInDirectory!!) {

            if (file.isDirectory)
                parseDirectory(file)
            else {
                parseFile(file)

                //scroll JScrollPane
                MainPanel.jsp.verticalScrollBar.value = MainPanel.jsp.verticalScrollBar.maximum
            }
        }
    }

    private fun parseFile(file: File) {
        val pathToFile = file.absolutePath
        val fileExtension = FilenameUtils.getExtension(file.name)

        if (fileExtension == DOCX && docxSelected) {
            parseDOCX(file, pathToFile)
        }

        if (fileExtension == DOC && docSelected) {
            parseDOC(file, pathToFile)
        }

        if (fileExtension == TXT && txtSelected) {
            parseTXT(file, pathToFile)
        }

        if (fileExtension == RTF && rtfSelected) {
            parseRTF(file, pathToFile)
        }
    }

    private fun parseDOCX(file: File, pathToFile: String) {
        val linesWithMatches: List<String>

        if (regEx != null) {
            linesWithMatches = docxRegexRapser!!.findRegexp(file)
        } else {
            linesWithMatches = docxParser!!.findExpression(file)
        }

        if (!linesWithMatches.isEmpty()) {

            for (lineInFile in linesWithMatches) {
                val entry = TableEntry(pathToFile, lineInFile)
                TableModel.instance.listOfEntries!!.add(entry)
            }
            TableModel.instance.fireTableDataChanged()
        }
    }

    private fun parseDOC(file: File, pathToFile: String) {
        val linesWithMatches: List<String>?

        if (regEx != null) {
            linesWithMatches = docRegexParser!!.findRegex(file)
        } else {
            linesWithMatches = docParser!!.findExpression(file)
        }

        if (linesWithMatches != null && !linesWithMatches.isEmpty()) {

            for (lineInFile in linesWithMatches) {
                val entry = TableEntry(pathToFile, lineInFile)
                TableModel.instance.listOfEntries!!.add(entry)
            }
            TableModel.instance.fireTableDataChanged()
        }
    }

    private fun parseTXT(file: File, pathToFile: String) {
        val linesWithMatches: List<String>

        if (regEx != null) {
            linesWithMatches = plainFileRegexParser!!.findRegExp(file)
        } else {
            linesWithMatches = plainFileParser!!.findExpression(file)
        }

        if (!linesWithMatches.isEmpty()) {

            for (lineInFile in linesWithMatches) {
                val entry = TableEntry(pathToFile, lineInFile)
                TableModel.instance.listOfEntries!!.add(entry)
            }
            TableModel.instance.fireTableDataChanged()
        }
    }

    private fun parseRTF(file: File, pathToFile: String) {
        val linesWithMatches: List<String>

        if (regEx != null) {
            linesWithMatches = rtfParser!!.findRegexp(file)!!
        } else {
            linesWithMatches = rtfParser!!.findExpression(file)
        }

        if (!linesWithMatches.isEmpty()) {

            for (lineInFile in linesWithMatches) {
                val entry = TableEntry(pathToFile, lineInFile)
                TableModel.instance.listOfEntries!!.add(entry)
            }
            TableModel.instance.fireTableDataChanged()
        }
    }

    companion object {
        private val DOCX = "docx"
        private val DOC = "doc"
        private val TXT = "txt"
        private val RTF = "rtf"
    }
}
