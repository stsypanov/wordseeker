package wordseeker

import javax.swing.text.BadLocationException
import javax.swing.text.Document
import javax.swing.text.rtf.RTFEditorKit
import java.io.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * @author rad1kal
 */
class RTFParser private constructor() : RTFEditorKit() {

    init {
        try {
            val rtfParser = RTFEditorKit()
            val document = rtfParser.createDefaultDocument()

            val fis = FileInputStream(File("D:\\gg.rtf"))
            val rtfBytes = ByteArray(0)
            fis.read(rtfBytes)
            rtfParser.read(ByteArrayInputStream(rtfBytes), document, 0)
            val text = document.getText(0, document.length)
            println(text)
        } catch (ex: IOException) {
            Logger.getLogger(RTFParser::class.java!!.getName()).log(Level.SEVERE, null, ex)
        } catch (ex: BadLocationException) {
            Logger.getLogger(RTFParser::class.java!!.getName()).log(Level.SEVERE, null, ex)
        }

    }
}
