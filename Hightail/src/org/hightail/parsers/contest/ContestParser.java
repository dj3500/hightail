package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.hightail.Problem;
import org.htmlparser.util.ParserException;

public abstract class ContestParser {
    // return list of pairs <task url, task name> 
    abstract public ArrayList<Problem> parse(String URL) throws ParserException;
    
    public static ContestParser getContestParser(String URL) throws ParserException {
        if (URL.contains("codeforces.")) {
            return new CodeForcesContestParser();
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
