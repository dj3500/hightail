package org.hightail.parsers.task;

import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.htmlparser.util.ParserException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joseph
 */
public class CodeForcesTaskParserTest {

    private final String URL = "http://codeforces.com/problemset/problem/744/E";
    private final String COMPLETE_PROBLEM_NAME = "E. Hongcow Masters the Cyclic Shift";
    private final String SHORT_PROBLEM_NAME = "E";

   
    @BeforeClass
    public static void setUpClass() {
        Config.fillInUnsetValuesWithDefaults();
    }

    /**
     * Putting the first problem's name letter as the name of the problem
     */
    @Test
    public void testParseShortName() {
        try {
            TaskParser parser = SupportedSites.getTaskParser(URL);
            Problem problem = parser.parse(URL);
            assertEquals(SHORT_PROBLEM_NAME, problem.getName());
        } catch (ParserException ex) {
            fail("thrown exception ParserException");
        } catch (InterruptedException ex) {
           fail("thrown exception InterruptedException");
        }
    }
    
    /**
     * Putting the whole problem's name as the name of the problem
     */
    @Test
    public void testParseWholeName() {
        try {
            Config.setBoolean("putWholeName", true);
            TaskParser parser = SupportedSites.getTaskParser(URL);
            Problem problem = parser.parse(URL);
            assertEquals(COMPLETE_PROBLEM_NAME, problem.getName());
        } catch (ParserException ex) {
            fail("thrown exception ParserException");
        } catch (InterruptedException ex) {
           fail("thrown exception InterruptedException");
        }
    }

}
