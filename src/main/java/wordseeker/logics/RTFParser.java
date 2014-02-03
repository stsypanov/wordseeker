
package wordseeker.logics;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rad1kal
 */
public class RTFParser implements Parser {

    public RTFParser() {

    }

    @Override
    public List<String> findExpression(File rtf) {
        List<String> list = new ArrayList<String>();
        try {
            InputStream input = new FileInputStream(rtf);
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            TikaConfig tc = new TikaConfig(rtf);
            AutoDetectParser arp = new AutoDetectParser(tc);
//            arp.parse(input, handler, metadata, new ParseContext());
            String plainText = handler.toString();
            list.add(plainText);
            System.out.println(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String> findRegexp(File doc) {
        return null;
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
