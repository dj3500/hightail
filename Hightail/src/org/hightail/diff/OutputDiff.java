package org.hightail.diff;

import java.util.StringTokenizer;

public class OutputDiff {
    private static String format = "WA\n"
                                    + "expected %s\n"
                                    + "received %s";
    
    public static String diff(String expectedOutput, String actualOutput) {
        
        StringTokenizer expectedOutputStringTokenizer = new StringTokenizer(expectedOutput),
                        actualOutputStringTokenizer = new StringTokenizer(actualOutput);
        
        while (expectedOutputStringTokenizer.hasMoreTokens() && 
               actualOutputStringTokenizer.hasMoreTokens()) {
            
            String expectedToken = expectedOutputStringTokenizer.nextToken(),
                   actualToken = actualOutputStringTokenizer.nextToken();
            
            if (!expectedToken.equals(actualToken)) {
                return String.format(format, 
                                     expectedToken, actualToken);
            }
        }
        
        if (expectedOutputStringTokenizer.hasMoreElements()) {
            String expectedToken = expectedOutputStringTokenizer.nextToken();
            return String.format(format,
                                 expectedToken,
                                 "EOF");
        }
        
        if (actualOutputStringTokenizer.hasMoreElements()) {
            String actualToken = actualOutputStringTokenizer.nextToken();
            return String.format(format,
                                 "EOF",
                                 actualToken);
        }
        
        return "OK";
    }
}
