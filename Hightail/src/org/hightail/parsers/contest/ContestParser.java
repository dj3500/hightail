package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.hightail.Problem;
import org.hightail.parsers.task.TaskParser;
import org.htmlparser.util.ParserException;

/**
 *
 * @author krig
 */
public interface ContestParser {
    default ArrayList<Problem> getProblemListFromContestURL(String URL) throws ParserException, InterruptedException {
        ArrayList<String> problemURLList = getProblemURLListFromURL(URL);
        if (problemURLList.isEmpty()) {
            throw new ParserException("No links to tasks found.");
        }
        
        ArrayList<Problem> problems = new ArrayList<>();
        ParserException anyException = null;
        for (String link : problemURLList) {
            try {
                problems.add(getTaskParser().parse(link));
            } catch (ParserException e) {
                anyException = e;
            }
        }
        if (anyException != null) {
            if (problems.isEmpty()) {
                // no problems parsed - we throw one of their exceptions
                throw anyException;
            } else {
                // well, some problems didn't parse, but some did, so we'll still return these
            }
        } else {
            // url list was nonempty and there were no exceptions, so problem list is also nonempty
        }

        return problems;

    }
    ArrayList<String> getProblemURLListFromURL(String URL) throws ParserException, InterruptedException;
    boolean isCorrectURL(String URL);
    TaskParser getTaskParser();
}
