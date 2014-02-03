package wordseeker.logics;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: rad1kal
 * Date: 14.03.13
 */
public class DocxParser implements Parser {
    public static final String DELIM = "\n";
    private String toBeFound;
    private boolean caseSensitive;
    private Pattern pattern;

    public DocxParser(String toBeFound, boolean caseSensitive) {
        this.toBeFound = toBeFound;
        this.caseSensitive = caseSensitive;
    }

    public DocxParser(String regExp) {
        pattern = Pattern.compile(regExp);
    }

    @Override
    public List<String> findExpression(File document) {
        List<String> linesWithMatches = new ArrayList<String>();
        try {
            InputStream is = new FileInputStream(document);
            try {
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                String text = extractor.getText();
                StringTokenizer st = new StringTokenizer(text, DELIM);
                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    if (caseSensitive) {
                        if (s.contains(toBeFound)) {
                            linesWithMatches.add(s);
                        }
                    } else {
                        s = s.toLowerCase();
                        if (s.contains(toBeFound.toLowerCase())) {
                            linesWithMatches.add(s);
                        }
                    }
                }
            } finally {
                is.close();
            }
        } catch (Exception ex) {
            System.err.println(document.getAbsoluteFile());
            ex.printStackTrace();
            return linesWithMatches;
        }
        return linesWithMatches;
    }

    @Override
    public List<String> findRegexp(File doc) {
        List<String> linesWithMatches = new ArrayList<String>();
        try {
            InputStream is = new FileInputStream(doc);
            try {
                XWPFDocument wordDocx = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(wordDocx);
                String text = extractor.getText();
                StringTokenizer st = new StringTokenizer(text, "\n");
                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    Matcher m = pattern.matcher(s);
                    if (m.find())
                        linesWithMatches.add(s);
                }
            } finally {
                is.close();
            }
        } catch (Exception ex) {
            System.err.println(doc.getAbsoluteFile());
            ex.printStackTrace();
            return linesWithMatches;
        }
        return linesWithMatches;
    }

    private List<String> parseFile(File doc) throws IOException {

        InputStream is = new FileInputStream(doc);
        XWPFDocument wordDocx = new XWPFDocument(is);
        XWPFWordExtractor extractor = new XWPFWordExtractor(wordDocx);
        String text = extractor.getText();
        is.close();

        StringTokenizer st = new StringTokenizer(text, "\n");
        List<String> lines = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            Matcher m = pattern.matcher(s);
            if (m.find())
                lines.add(s);
        }
        return lines;
    }

    private List<String> splitString(String longString) {
        List<String> lines = new ArrayList<>();
        int pos = 0, end;
        while ((end = longString.indexOf('\n', pos)) >= 0) {
            lines.add(longString.substring(pos, end));
            pos = end + 1;
        }
        return lines;
    }
}
