/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public void testGetFormatedNameAllowedCharacters() {
        String formatedName = ProblemNameFormatter.getFormatedName(PROBLEM_NAME_A);
        assertEquals(PROBLEM_NAME_A, formatedName);
    }
    
    @Test
    public void testGetFormatedNameNotAllowedCharacters() {
        String formatedName = ProblemNameFormatter.getFormatedName(PROBLEM_NAME_B);
        assertEquals(PROBLEM_NAME_EXPECTED_B, formatedName);
    }
}
