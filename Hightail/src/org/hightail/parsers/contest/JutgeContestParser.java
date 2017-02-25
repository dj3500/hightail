package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.hightail.parsers.task.JutgeTaskParser;
import org.hightail.parsers.task.TaskParser;
import org.htmlparser.util.ParserException;

/**
 * Jutge (UPC local online judge) contest parser.
 * @author Sergio Rodriguez Guasch
 */
public class JutgeContestParser implements ContestParser {
    
    @Override
    public ArrayList<String> getProblemURLListFromURL(String URL) throws ParserException, InterruptedException {
        /*
            Problem lists (which can be interpreted as contests) are only available if you are logged in.
            Also, it doesn't make much sense here since most problems are public and they are usually solved
            one by one in pratice.
        */
        throw new ParserException("Contest parser for Jutge is not implemented.");
    }
    
    private final TaskParser taskParser = new JutgeTaskParser();

    @Override
    public TaskParser getTaskParser() {
        return taskParser;
    }    

    @Override
    public boolean isCorrectURL(String URL) {
        return URL.contains("jutge.");
    }
}
