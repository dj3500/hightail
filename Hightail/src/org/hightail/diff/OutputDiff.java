package org.hightail.diff;

import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import java.util.StringTokenizer;

public class OutputDiff {
    private static final String format = "expected %s\n"
                        + "received %s";
    private static final double MAX_ACCEPTED_FLOATING_POINT_ERROR = 1e-7;
    public static String diff(String expectedOutput, String actualOutput) {
        
        double maxFloatingPointErrorObtained = 0;
        
        StringTokenizer expectedOutputStringTokenizer = new StringTokenizer(expectedOutput),
                        actualOutputStringTokenizer = new StringTokenizer(actualOutput);
        
        while (expectedOutputStringTokenizer.hasMoreTokens() &&
               actualOutputStringTokenizer.hasMoreTokens()) {
            
            String expectedToken = expectedOutputStringTokenizer.nextToken(),
                   actualToken = actualOutputStringTokenizer.nextToken();
            
            if (!expectedToken.equals(actualToken)) {
                // if they are e.g. 23.0 and 23.1, or 23 and 23.1, or 23.1 and 23, then treat them as floating point
                if (looksLikeIntegerOrFloatingPoint(expectedToken)
                        && looksLikeIntegerOrFloatingPoint(actualToken)
                        && (looksLikeFloatingPoint(expectedToken) || looksLikeFloatingPoint(actualToken))) {
                    double expectedValue = Double.parseDouble(expectedToken),
                           actualValue = Double.parseDouble(actualToken);
                    double absoluteError = abs(expectedValue - actualValue);
                    double relativeError = abs(expectedValue) > 1e-9 ? absoluteError / abs(expectedValue) : 1e30;
                    // we consider "absolute or relative precision of at least ..."
                    double minError = min(absoluteError, relativeError);
                    if (minError < MAX_ACCEPTED_FLOATING_POINT_ERROR) {
                        // we let it pass, but update maxFloatingPointErrorObtained
                        maxFloatingPointErrorObtained = max(maxFloatingPointErrorObtained, minError);
                    } else {
                        // too big difference
                        return String.format(format,
                                             expectedToken,
                                             actualToken);
                    }
                } else {
                    return String.format(format,
                                         expectedToken,
                                         actualToken);
                }
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
        
        if (maxFloatingPointErrorObtained != 0) {
            return "OK\n" + "with " + -round(log10(maxFloatingPointErrorObtained)) + " digits of precision";
        } else {
            return "OK";
        }
    }

    private static boolean looksLikeFloatingPoint(String token) {
        return token.matches("(-)?[0-9]+\\.[0-9]+");
    }

    private static boolean looksLikeIntegerOrFloatingPoint(String token) {
        return token.matches("(-)?[0-9]+(\\.[0-9]+)?");
    }
}
