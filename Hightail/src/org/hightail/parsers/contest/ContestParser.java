package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author jtarnawski
 */
public abstract class ContestParser {
    abstract public ArrayList<String> parse(String URL) throws ParserException;
}
