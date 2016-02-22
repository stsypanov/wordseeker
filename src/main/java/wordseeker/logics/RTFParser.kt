package wordseeker.logics

import org.apache.tika.config.TikaConfig
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.sax.BodyContentHandler
import org.xml.sax.ContentHandler

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.ArrayList

/**
 * @author rad1kal
 */
class RTFParser : Parser {

    override fun findExpression(rtf: File): List<String> {
        val list = ArrayList<String>()
        try {
            val input = FileInputStream(rtf)
            val handler = BodyContentHandler()
            val metadata = Metadata()
            val tc = TikaConfig(rtf)
            val arp = AutoDetectParser(tc)
            //            arp.parse(input, handler, metadata, new ParseContext());
            val plainText = handler.toString()
            list.add(plainText)
            println(plainText)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    override fun findRegexp(doc: File): List<String>? {
        return null
    }

    /*public RTFParser(){
        super();
        try {
            File file = null;
            RTFEditorKit rtfParser = new RTFEditorKit();
            Document document = rtfParser.createDefaultDocument();
            byte[] rtfBytes = null;
            file = new File("D:\\gg.rtf");
            InputStream fis = new FileInputStream(file);
            fis.read(rtfBytes);
            rtfParser.read(new ByteArrayInputStream(rtfBytes), document, 0);
            String text = document.getText(0, document.getLength());
            System.out.println(text);
        } catch (IOException ex) {
            Logger.getLogger(RTFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(RTFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
