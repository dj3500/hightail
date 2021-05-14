package org.hightail.diff;

import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import java.util.StringTokenizer;
import java.util.Arrays;

public class OutputDiff {
    private static final String format = "expected %s\n"
                        + "received %s";
    private static final double MAX_ACCEPTED_FLOATING_POINT_ERROR = 1e-7;
    public static String diff(String expectedOutput, String actualOutput) {

        double maxFloatingPointErrorObtained = 0;

        String[] expectedLines = expectedOutput.split("\n");
        //TODO inform users that output lines starting with "DBG#~ " are not used for comparision
        String[] actualLines = Arrays.stream(actualOutput.split("\n")).filter(s -> !s.startsWith("DBG#~ ")).toArray(String[]::new);

        for (int i = 0; i < min(expectedLines.length, actualLines.length); ++i) {
            StringTokenizer expectedLineStringTokenizer = new StringTokenizer(expectedLines[i]),
                            actualLineStringTokenizer = new StringTokenizer(actualLines[i]);

            int cntToken = 0;

            while (expectedLineStringTokenizer.hasMoreTokens() &&
                   actualLineStringTokenizer.hasMoreTokens()) {

                cntToken++;

                String expectedToken = expectedLineStringTokenizer.nextToken(),
                       actualToken = actualLineStringTokenizer.nextToken();

                if (!expectedToken.equals(actualToken)) {
                    // if they are e.g. 23.0 and 23.1, or 23 and 23.1, or 23.1 and 23, then treat them as floating point
                    boolean ok = false;
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
                            ok = true;
                        }
                    }
                    if (!ok) {
                        return "Difference in line " + (i+1) + ", token " + cntToken + ":\n" + String.format(format,
                                                                                                            expectedToken,
                                                                                                            actualToken);
                    }
                }
            }

            if (expectedLineStringTokenizer.hasMoreTokens()) {
                String expectedToken = expectedLineStringTokenizer.nextToken();
                return "Difference in line " + (i+1) + ":\n" + String.format(format,
                                                                             expectedToken,
                                                                             "EOL");
            }

            if (actualLineStringTokenizer.hasMoreTokens()) {
                String actualToken = actualLineStringTokenizer.nextToken();
                return "Difference in line " + (i+1) + ":\n" + String.format(format,
                                                                             "EOL",
                                                                             actualToken);
            }
        }


        if (expectedLines.length > actualLines.length) {
            for (int i = actualLines.length; i < expectedLines.length; ++i) {
                StringTokenizer expectedLineStringTokenizer = new StringTokenizer(expectedLines[i]);

                if (expectedLineStringTokenizer.hasMoreTokens()) {
                    String expectedToken = expectedLineStringTokenizer.nextToken();
                    return String.format(format,
                                         expectedToken,
                                         "EOF");
                }
            }
        } else if (actualLines.length > expectedLines.length) {
            for (int i = expectedLines.length; i < actualLines.length; ++i) {
                StringTokenizer actualLineStringTokenizer = new StringTokenizer(actualLines[i]);

                if (actualLineStringTokenizer.hasMoreTokens()) {
                    String actualToken = actualLineStringTokenizer.nextToken();
                    return String.format(format,
                                         "EOF",
                                         actualToken);
                }
            }
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
