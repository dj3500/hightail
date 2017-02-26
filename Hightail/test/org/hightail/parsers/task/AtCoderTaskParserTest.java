package org.hightail.parsers.task;

import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.SupportedSites;
import org.hightail.Testcase;
import org.htmlparser.util.ParserException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AtCoderTaskParserTest {

    private final String URL = "http://arc069.contest.atcoder.jp/tasks/arc069_b",
                         SHORT_PROBLEM_NAME = "D",
                         EXPECTED_THIRD_INPUT = "10\noxooxoxoox",
                         EXPECTED_THIRD_OUTPUT = "SSWWSSSWWS";
   
    @BeforeClass
    public static void setUpClass() {
        Config.fillInUnsetValuesWithDefaults();
    }

    @Test
    public void testParseProblem() {
        try {
            TaskParser parser = SupportedSites.getTaskParser(URL);
            Problem problem = parser.parse(URL);
            assertEquals(SHORT_PROBLEM_NAME, problem.getName());
            Testcase thirdTestcase = problem.getTestcase(2);
            assertEquals(thirdTestcase.getInput(), EXPECTED_THIRD_INPUT);
            assertEquals(thirdTestcase.getExpectedOutput(), EXPECTED_THIRD_OUTPUT);
        } catch (ParserException | InterruptedException ex) {
            fail("Exception was thrown.");
        }
    }
}
