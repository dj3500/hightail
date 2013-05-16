package org.hightail.parsers.task;

import org.hightail.Problem;
import org.htmlparser.util.ParserException;

public abstract class TaskParser {
    abstract public Problem parse(String URL) throws ParserException;

    public static TaskParser getTaskParser(String URL) throws ParserException {
        if (URL.contains("codeforces.")) {
            return new CodeForcesTaskParser();
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
