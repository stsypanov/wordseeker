package wordseeker.logics;

import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides word and word combinations search facilities for *.doc
 * files.
 *
 * @author rad1kal
 */
public class DocParser {

    private Pattern pattern;
    private String toBeFound;
    private boolean isCaseSensitive;

    /**
     * Constructor for the case when looking for words or word combinations.
     *
     * @param toBeFound
     * @param isCaseSensitive
     */
    public DocParser(String toBeFound, boolean isCaseSensitive) {
        this.toBeFound = toBeFound;
        this.isCaseSensitive = isCaseSensitive;
    }

    /**
     * Constructor for regexp search.
     *
     * @param regExp
     */
    public DocParser(String regExp) {
        pattern = Pattern.compile(regExp);
    }

    /**
     * Find word or word combination in document. The method returns an instance
     * of List containing lines with matches.
     *
     * @param document
     * @return linesWithMatches list of String matching regexp
     */
    public List<String> findExpression(File document) {
        List<String> linesWithMatches = new ArrayList<String>();

        try {
            InputStream is = null;
            WordExtractor extractor;

            try {
                is = new FileInputStream(document);
                extractor = new WordExtractor(is);
                String[] paragraphs = extractor.getParagraphText();

                if (!isCaseSensitive) {
                    toBeFound = toBeFound.toLowerCase();
                    for (String s : paragraphs) {
                        String st = s.toLowerCase();
                        if (st.contains(toBeFound)) {
                            linesWithMatches.add(s);
                        }
                    }
                } else {
                    for (String s : paragraphs) {
                        if (s.contains(toBeFound)) {
                            linesWithMatches.add(s);
                        }
                    }
                }
                return linesWithMatches;
            } finally {
//                if (is != null) {
                    is.close();
//                }
            }
        } catch (Exception e) {
            System.err.println(document.getAbsoluteFile());
            e.printStackTrace();
            return linesWithMatches;
        }
    }

    /**
     * Method is called when looking for regexp.
     *
     * @param document
     * @return linesWithMatches list of String matching regexp
     */
    public List<String> findRegex(File document) {
        List<String> linesWithMatches = new ArrayList<String>();

        WordExtractor extractor;

        try {
            InputStream is = new FileInputStream(document);
            try {
                extractor = new WordExtractor(is);
            } finally {
                is.close();
            }
        } catch (Exception e) {
            System.err.println(document.getAbsoluteFile());
            e.printStackTrace();
            return null;
        }

        String[] paragraphs = extractor.getParagraphText();

        for (String s : paragraphs) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                linesWithMatches.add(s);
            }
        }
        return linesWithMatches;
    }
}
