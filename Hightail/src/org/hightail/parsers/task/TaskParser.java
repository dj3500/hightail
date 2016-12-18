package org.hightail.parsers.task;

import org.hightail.Problem;
import org.htmlparser.util.ParserException;

/**
 *
 * @author krig
 */
public interface TaskParser {
    Problem parse(String URL, boolean wholeName) throws ParserException, InterruptedException;
    boolean isCorrectURL(String URL);
}
