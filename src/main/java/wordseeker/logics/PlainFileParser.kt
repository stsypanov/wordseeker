package wordseeker.logics

import java.io.*
import java.nio.charset.Charset
import java.nio.file.Files
import java.util.*
import java.util.regex.*

/**
 * This class provides word and word combinations
 * search facilities for plain text files.

 * @author rad1kal
 */
class PlainFileParser {
    private var pattern: Pattern? = null
    private var toBeFound: String? = null
    private var isCaseSensitive: Boolean = false
    private var linesWithMatches: MutableList<String>

    /**
     * Constructor for the case when looking for words or word combinations.

     * @param RegExp
     */
    constructor(RegExp: String) {
        pattern = Pattern.compile(RegExp)
        linesWithMatches = ArrayList<String>()
    }

    /**
     * Constructor for the regexp search.

     * @param toBeFound
     * *
     * @param isCaseSensitive
     */
    constructor(toBeFound: String, isCaseSensitive: Boolean) {
        this.toBeFound = toBeFound
        this.isCaseSensitive = isCaseSensitive
        linesWithMatches = ArrayList<String>()
    }

    /**
     * The method is called when looking for regexp.

     * @param file
     * *
     * @return
     */
    fun findRegExp(file: File): List<String> {
        try {
            val br = BufferedReader(InputStreamReader(
                    FileInputStream(file)))
            try {
                val allLines = Files.readAllLines(file.toPath(), Charset.defaultCharset())
                linesWithMatches.addAll(allLines.filter { pattern!!.matcher(it).find() })
            } finally {
                br.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return linesWithMatches
    }

    /**
     * The method is called when looking for particular word or word combintaion.

     * @param file
     * *
     * @return
     */
    fun findExpression(file: File): List<String> {
        try {
            var br = BufferedReader(InputStreamReader(
                    FileInputStream(file)))
            try {
                var s: String = br.readLine()
                while (!s.isEmpty()) {
                    if (isCaseSensitive) {
                        if (s.contains(toBeFound as String)) {
                            linesWithMatches.add(s)
                        }
                    } else {
                        s = s.toLowerCase()
                        if (s.contains(toBeFound!!.toLowerCase())) {
                            linesWithMatches.add(s)
                        }
                    }
                    s =  br.readLine()
                }
            } finally {
                br.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return linesWithMatches
    }
}
