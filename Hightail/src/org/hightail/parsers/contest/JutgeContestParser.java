package org.hightail.parsers.contest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.hightail.Problem;
import org.hightail.parsers.task.JutgeTaskParser;
import org.hightail.parsers.task.TaskParser;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

/**
 * Jutge (UPC local online judge) contest parser.
 * @author Sergio Rodriguez Guasch
 */
public class JutgeContestParser implements ContestParser {
    
    @Override
    public ArrayList<Problem> parse(String URL) throws ParserException, InterruptedException {
        /*
            Problem lists (which can be interpreted as contests) are only available if you are logged in.
            Also, it doesn't make much sense here since most problems are public and they are usually solved
            one by one in pratice.
        */
        throw new ParserException("Parsing failed.");
    }

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("jutge.");
    }
    
}
