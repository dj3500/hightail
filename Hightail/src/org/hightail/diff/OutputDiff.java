/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.diff;

import java.util.StringTokenizer;

/**
 *
 * @author robertrosolek
 */
public class OutputDiff {
    String diff(String expectedOutput, String actualOutput) {
        
        StringTokenizer expectedOutputStringTokenizer = new StringTokenizer(expectedOutput),
                actualOutputStringTokenizer = new StringTokenizer(actualOutput);
        
        while (expectedOutputStringTokenizer.hasMoreTokens() && 
                actualOutputStringTokenizer.hasMoreTokens()) {
            
            String expectedToken = expectedOutputStringTokenizer.nextToken(),
                    actualToken = actualOutputStringTokenizer.nextToken();
            
            if (!expectedToken.equals(actualToken))
                return String.format("WA - expected %s and received %s", 
                        expectedToken, actualToken);
        }
        
        if (expectedOutputStringTokenizer.hasMoreElements()) {
            String expectedToken = expectedOutputStringTokenizer.nextToken();
            return String.format("WA - expected %s and received EOF",
                    expectedToken);
        }
        
        if (actualOutputStringTokenizer.hasMoreElements()) {
            String actualToken = actualOutputStringTokenizer.nextToken();
            return String.format("WA - expected EOF and received %s",
                    actualToken);
        }
        
        return "OK";
    }
}
