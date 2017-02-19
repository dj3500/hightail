package org.hightail.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joseph
 */
public class ProblemNameFormatterTest {
    private final String PROBLEM_NAME_A = "E. 09 - _ .test"; 
    private final String PROBLEM_NAME_B = "+T?E]S'T"; 
    private final String PROBLEM_NAME_EXPECTED_B = "TEST";
    
    @Test
    public void testGetFormattedNameAllowedCharacters() {
        String formattedName = ProblemNameFormatter.getFormattedName(PROBLEM_NAME_A);
        assertEquals(PROBLEM_NAME_A, formattedName);
    }
    
    @Test
    public void testGetFormattedNameNotAllowedCharacters() {
        String formattedName = ProblemNameFormatter.getFormattedName(PROBLEM_NAME_B);
        assertEquals(PROBLEM_NAME_EXPECTED_B, formattedName);
    }
}
