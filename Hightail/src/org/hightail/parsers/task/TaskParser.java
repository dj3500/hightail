package org.hightail.parsers.task;

import org.hightail.TestcaseSet;
import org.htmlparser.util.ParserException;

public abstract class TaskParser {
    abstract public TestcaseSet parse(String URL) throws ParserException;

    public static TaskParser getTaskParser(String URL) throws ParserException {
        if (URL.contains("codeforces.")) {
            return new CodeForcesTaskParser();
        }
        
        throw new ParserException("This site is currently unsupported.");
    }
}
