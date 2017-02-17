/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.util;

/**
 *
 * @author Joseph
 */
public class ProblemNameFormatter {
    public static String getFormatedName(String originalName, String regularExpression){
        return originalName.replaceAll(regularExpression, "");
    }
    public static String getFormatedName(String originalName){
        return getFormatedName(originalName, "[^A-Za-z0-9 ._-]");
    }
}
