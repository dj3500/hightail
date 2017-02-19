package org.hightail.util;

/**
 *
 * @author Joseph
 */
public class ProblemNameFormatter {
    // gets rid of weird special characters
    public static String getFormattedName (String originalName) {
        return originalName.replaceAll("[^A-Za-z0-9 ._-]", "");
     }
}
