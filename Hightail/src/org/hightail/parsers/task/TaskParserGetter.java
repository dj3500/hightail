package org.hightail.parsers.task;

import org.htmlparser.util.ParserException;

public abstract class TaskParserGetter {
    private static final TaskParser[] parsers = new TaskParser[] {
        new CodeForcesTaskParser(),
        new CodeChefTaskParser(),
    };

    public static TaskParser getTaskParser(String URL) throws ParserException {
        for(TaskParser parser : parsers) {
            if(parser.isCorrectURL(URL)) {
                return parser;
            }
        }
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
