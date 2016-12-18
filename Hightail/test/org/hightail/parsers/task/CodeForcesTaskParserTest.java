package org.hightail.parsers.task;

import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.htmlparser.util.ParserException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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

    public CodeForcesTaskParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Config.fillInUnsetValuesWithDefaults();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Testing to put the first problem's name letter as a name of the problem
     */
    @Test
    public void testParseNamePropertieShortName() {
        try {
            TaskParser parser = SupportedSites.getTaskParser(URL);
            Problem problem = parser.parse(URL, false);
            assertEquals(SHORT_PROBLEM_NAME, problem.getName());
        } catch (ParserException ex) {
            fail("thrown exception ParserException");
        } catch (InterruptedException ex) {
           fail("thrown exception InterruptedException");
        }
    }
    
    /**
     * Testing to put the whole problem's name as a name of the problem
     */
    @Test
    public void testParseNamePropertieWholeName() {
        try {
            TaskParser parser = SupportedSites.getTaskParser(URL);
            Problem problem = parser.parse(URL, true);
            assertEquals(COMPLETE_PROBLEM_NAME, problem.getName());
        } catch (ParserException ex) {
            fail("thrown exception ParserException");
        } catch (InterruptedException ex) {
           fail("thrown exception InterruptedException");
        }
    }

}
