package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.hightail.Problem;
import org.htmlparser.util.ParserException;

/**
 *
 * @author krig
 */
public interface ContestParser {
    ArrayList<Problem> parse(String URL) throws ParserException, InterruptedException;
    boolean isCorrectURL(String URL);
}
