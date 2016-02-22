package wordseeker.logics

import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.StringTokenizer
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * User: rad1kal
 * Date: 14.03.13
 */
class DocxParser : Parser {
    private var toBeFound: String = ""
    private var caseSensitive: Boolean = false
    private var pattern: Pattern? = null

    constructor(toBeFound: String, caseSensitive: Boolean) {
        this.toBeFound = toBeFound
        this.caseSensitive = caseSensitive
    }

    constructor(regExp: String) {
        pattern = Pattern.compile(regExp)
    }

    override fun findExpression(document: File): List<String> {
        val linesWithMatches = ArrayList<String>()
        try {
            val `is` = FileInputStream(document)
            try {
                val doc = XWPFDocument(`is`)
                val extractor = XWPFWordExtractor(doc)
                val text = extractor.text
                val st = StringTokenizer(text, DELIM)
                while (st.hasMoreTokens()) {
                    var s = st.nextToken()
                    if (caseSensitive) {
                        if (s.contains(toBeFound)) {
                            linesWithMatches.add(s)
                        }
                    } else {
                        s = s.toLowerCase()
                        if (s.contains(toBeFound.toLowerCase())) {
                            linesWithMatches.add(s)
                        }
                    }
                }
            } finally {
                `is`.close()
            }
        } catch (ex: Exception) {
            System.err.println(document.absoluteFile)
            ex.printStackTrace()
            return linesWithMatches
        }

        return linesWithMatches
    }

    override fun findRegexp(doc: File): List<String> {
        val linesWithMatches = ArrayList<String>()
        try {
            val `is` = FileInputStream(doc)
            try {
                val wordDocx = XWPFDocument(`is`)
                val extractor = XWPFWordExtractor(wordDocx)
                val text = extractor.text
                val st = StringTokenizer(text, "\n")
                while (st.hasMoreTokens()) {
                    val s = st.nextToken()
                    val m = pattern?.matcher(s)
                    if (m?.find()!!)
                        linesWithMatches.add(s)
                }
            } finally {
                `is`.close()
            }
        } catch (ex: Exception) {
            System.err.println(doc.absoluteFile)
            ex.printStackTrace()
            return linesWithMatches
        }

        return linesWithMatches
    }

    @Throws(IOException::class)
    private fun parseFile(doc: File): List<String> {

        val `is` = FileInputStream(doc)
        val wordDocx = XWPFDocument(`is`)
        val extractor = XWPFWordExtractor(wordDocx)
        val text = extractor.text
        `is`.close()

        val st = StringTokenizer(text, "\n")
        val lines = ArrayList<String>()
        while (st.hasMoreTokens()) {
            val s = st.nextToken()
            val m = pattern!!.matcher(s)
            if (m.find())
                lines.add(s)
        }
        return lines
    }

//    private fun splitString(longString: String): List<String> {
//        val lines = ArrayList<String>()
//        var pos = 0
//        var end: Int
//        while ((end = longString.indexOf('\n', pos)) >= 0) {
//            lines.add(longString.substring(pos, end))
//            pos = end + 1
//        }
//        return lines
//    }

    companion object {
        val DELIM = "\n"
    }
}
