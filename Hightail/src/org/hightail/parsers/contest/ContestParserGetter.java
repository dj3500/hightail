package org.hightail.parsers.contest;

import org.htmlparser.util.ParserException;

public abstract class ContestParserGetter {
    private static final ContestParser[] parsers = new ContestParser[] {
        new CodeForcesContestParser()
    };
    public static ContestParser getContestParser(String URL) throws ParserException {
        for (ContestParser parser : parsers) {
            if (parser.isCorrectURL(URL)) {
                return parser;
            }
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
