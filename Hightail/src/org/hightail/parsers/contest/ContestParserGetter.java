package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.hightail.Problem;
import org.htmlparser.util.ParserException;

public abstract class ContestParserGetter {
    private static final ContestParser[] parsers = new ContestParser[] {
        new CodeForcesContestParser(),
        new CodeChefContestParser(),
    };
    public static ContestParser getContestParser(String URL) throws ParserException {
        for(ContestParser parser : parsers) {
            if(parser.isCorrectURL(URL)) {
                return parser;
            }
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
