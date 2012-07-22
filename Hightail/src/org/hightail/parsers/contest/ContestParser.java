package org.hightail.parsers.contest;

import java.util.ArrayList;

/**
 *
 * @author jtarnawski
 */
public abstract class ContestParser {
    abstract public ArrayList<String> parse(String URL);
}
