package org.hightail.parsers.task;

import org.hightail.TestcaseSet;
import org.htmlparser.util.ParserException;

/**
 *
 * @author jtarnawski
 */
public abstract class TaskParser {
    abstract public TestcaseSet parse(String URL) throws ParserException;
}
