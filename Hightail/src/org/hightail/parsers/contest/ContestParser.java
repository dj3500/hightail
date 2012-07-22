package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author jtarnawski
 */
public abstract class ContestParser {
    abstract public ArrayList<String> parse(String URL) throws ParserException;
    
    public static ContestParser getContestParser(String URL) throws ParserException {
        if (URL.contains("codeforces.")) {
            return new CodeForcesContestParser();
        }
        
        throw new ParserException("This site is currently unsupported.");
    }
}
