package org.hightail;

import org.hightail.parsers.contest.*;
import org.hightail.parsers.task.*;
import org.htmlparser.util.ParserException;

public enum SupportedSites {
    CodeForces  (new CodeForcesTaskParser(),    new CodeForcesContestParser()),
    CodeChef    (new CodeChefTaskParser(),      new CodeChefContestParser()),
    ;
    
    private TaskParser taskParser;
    private ContestParser contestParser;
    
    private SupportedSites(TaskParser taskParser, ContestParser contestParser) {
        this.taskParser = taskParser;
        this.contestParser = contestParser;
    }
    
    public static TaskParser getTaskParser(String URL) throws ParserException {
        for (SupportedSites site : values()) {
            if (site.taskParser.isCorrectURL(URL)) {
                return site.taskParser;
            }
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
    
    public static ContestParser getContestParser(String URL) throws ParserException {
        for (SupportedSites site : values()) {
            if (site.contestParser.isCorrectURL(URL)) {
                return site.contestParser;
            }
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
