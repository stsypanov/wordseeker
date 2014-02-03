
package wordseeker;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rad1kal
 */
public class RTFParser extends RTFEditorKit {

    private RTFParser() {
        super();
        try {
            RTFEditorKit rtfParser = new RTFEditorKit();
            Document document = rtfParser.createDefaultDocument();

            InputStream fis = new FileInputStream(new File("D:\\gg.rtf"));
            byte[] rtfBytes = new byte[0];
            fis.read(rtfBytes);
            rtfParser.read(new ByteArrayInputStream(rtfBytes), document, 0);
            String text = document.getText(0, document.getLength());
            System.out.println(text);
        } catch (IOException ex) {
            Logger.getLogger(RTFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(RTFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
