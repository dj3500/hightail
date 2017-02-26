package org.hightail.parsers.contest;

import java.util.ArrayList;
import org.hightail.AuthenticationInfo;
import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.htmlparser.util.ParserException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AtCoderContestParserTest {
    private final String ATCODERUSERNAME = "hightail", ATCODERPASSWORD = "simplepassword123";
    
    @BeforeClass
    public static void setUpClass() {
        Config.fillInUnsetValuesWithDefaults();
    }

    @Test
    public void testPracticeWithCredentials() {
        try {
            final String url = "http://practice.contest.atcoder.jp"; // no slash at the end
            AuthenticationInfo.setUsername(ATCODERUSERNAME);
            AuthenticationInfo.setPassword(ATCODERPASSWORD);
            
            ContestParser parser = SupportedSites.getContestParser(url);
            ArrayList<Problem> problems = parser.getProblemListFromContestURL(url);
            assertTrue(problems.size() >= 1);
        } catch (ParserException | InterruptedException ex) {
            fail("Exception was thrown.");
        }
    }
    
    
    @Test
    public void testAGCWithCredentials() {
        try {
            final String url = "https://agc010.contest.atcoder.jp/"; // has slash at the end and HTTPS
            AuthenticationInfo.setUsername(ATCODERUSERNAME);
            AuthenticationInfo.setPassword(ATCODERPASSWORD);
            
            ContestParser parser = SupportedSites.getContestParser(url);
            ArrayList<Problem> problems = parser.getProblemListFromContestURL(url);
            assertTrue(problems.size() == 6);
        } catch (ParserException | InterruptedException ex) {
            fail("Exception was thrown.");
        }
    }
    
    @Test
    public void testAGCWithoutCredentials() {
        try {
            final String url = "http://agc010.contest.atcoder.jp/"; // has slash at the end
            AuthenticationInfo.setUsername("");
            AuthenticationInfo.setPassword("");
            
            ContestParser parser = SupportedSites.getContestParser(url);
            ArrayList<Problem> problems = parser.getProblemListFromContestURL(url);
            assertTrue(problems.size() == 6);            
        } catch (ParserException | InterruptedException ex) {
            fail("Exception was thrown.");
        }
    }
    
    @Test
    public void testMujinWithoutCredentials() {
        try {
            final String url = "http://mujin-pc-2017.contest.atcoder.jp/assignments"; // has "assignments" at the end
            AuthenticationInfo.setUsername("");
            AuthenticationInfo.setPassword("");
            
            ContestParser parser = SupportedSites.getContestParser(url);
            ArrayList<Problem> problems = parser.getProblemListFromContestURL(url);
            assertTrue(problems.size() == 4);
        } catch (ParserException | InterruptedException ex) {
            fail("Exception was thrown.");
        }
    }
}

