
package wordseeker.logics;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * This class provides word and word combinations
 * search facilities for plain text files.
 *
 * @author rad1kal
 */
public class PlainFileParser {
    private Pattern pattern;
    private String toBeFound;
    private boolean isCaseSensitive;
    private final List<String> linesWithMatches;

    /**
     * Constructor for the case when looking for words or word combinations.
     *
     * @param RegExp
     */
    public PlainFileParser(String RegExp) {
        pattern = Pattern.compile(RegExp);
        linesWithMatches = new ArrayList<String>();
    }

    /**
     * Constructor for the regexp search.
     *
     * @param toBeFound
     * @param isCaseSensitive
     */
    public PlainFileParser(String toBeFound, boolean isCaseSensitive) {
        this.toBeFound = toBeFound;
        this.isCaseSensitive = isCaseSensitive;
        linesWithMatches = new ArrayList<String>();
    }

    /**
     * The method is called when looking for regexp.
     *
     * @param file
     * @return
     */
    public List<String> findRegExp(File file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            try {
                String s;
                while ((s = br.readLine()) != null) {
                    Matcher m = pattern.matcher(s);
                    if (m.find())
                        linesWithMatches.add(s);
                }
            } finally {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesWithMatches;
    }

    /**
     * The method is called when looking for particular word or word combintaion.
     *
     * @param file
     * @return
     */
    public List<String> findExpression(File file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            try {
                String s;
                while ((s = br.readLine()) != null) {
                    if (isCaseSensitive) {
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
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linesWithMatches;
    }
}
