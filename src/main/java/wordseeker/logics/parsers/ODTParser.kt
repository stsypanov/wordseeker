package wordseeker.logics.parsers

import org.odftoolkit.odfdom.doc.OdfTextDocument
import org.w3c.dom.NodeList
import wordseeker.logics.Parser

import java.io.File
import java.util.regex.Pattern

/**
 * Created with IntelliJ IDEA.
 * User: Злой Московит
 * Date: 17.11.13
 * Time: 22:54
 */
class ODTParser /*implements Parser*///    public static final String DELIM = "\n";
//    private final String toBeFound;
//    private final boolean caseSensitive;
//    private final Pattern pattern;
//
//    public ODTParser(String toBeFound, boolean caseSensitive) {
//        this.toBeFound = toBeFound;
//        this.caseSensitive = caseSensitive;
//    }
//
//    public ODTParser(Pattern pattern) {
//        this.pattern = pattern;
//    }
//
//    @Override
//    public List<String> findExpression(File doc) {
//        try {
//            OdfTextDocument document = OdfTextDocument.loadDocument(doc);
//            NodeList childNodes = document.getContentDom().getRootElement().getChildNodes();
//            for (int i = 0; i < childNodes.getLength(); i++){
//                org.w3c.dom.Node node = childNodes.item(i);
//                node.getTextContent();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<String> findRegexp(File doc) {
//        return null;
//    }
