package org.hightail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.hightail.parsers.contest.*;
import org.hightail.parsers.task.*;
import org.htmlparser.util.ParserException;

public enum SupportedSites {
    CodeForces  (
            "codeforces",
            new CodeForcesTaskParser(),
            new CodeForcesContestParser()),
    CodeChef    (
            "codechef",
            new CodeChefTaskParser(),
            new CodeChefContestParser()),
    ;
    
    private TaskParser taskParser;
    private ContestParser contestParser;
    private String defaultDirectory;
    
    private SupportedSites(String defaultDirectory, TaskParser taskParser, ContestParser contestParser) {
        this.defaultDirectory = defaultDirectory;
        this.taskParser = taskParser;
        this.contestParser = contestParser;
    }
    
    public String getDirectory() {
        return Config.get(toString() + "Directory", defaultDirectory);
    }
    
    private static void isCorrectURL(String url) throws ParserException {
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int code = conn.getResponseCode();
            if (code != 200) {
                throw new ParserException("Incorrect URL.");
            }
        } catch (IOException ex) {
            throw new ParserException("Malformed URL.");
        }
    }
    
    public static TaskParser getTaskParser(String URL) throws ParserException {
        isCorrectURL(URL);
        for (SupportedSites site : values()) {
            if (site.taskParser.isCorrectURL(URL)) {
                return site.taskParser;
            }
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
    
    public static ContestParser getContestParser(String URL) throws ParserException {
        isCorrectURL(URL);
        for (SupportedSites site : values()) {
            if (site.contestParser.isCorrectURL(URL)) {
                return site.contestParser;
            }
        }
        
        throw new ParserException("Incorrect url or this site is currently unsupported.");
    }
}
