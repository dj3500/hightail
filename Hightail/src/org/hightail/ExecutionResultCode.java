package org.hightail;

import java.awt.Color;

/**
 *
 * @author krig
 */
public enum ExecutionResultCode {
    NOT_RUN("-",
            Color.black),
    RUNNING("running...",
            Color.black),
    OK(     "OK",
            new Color(0, 170, 0)),
    RUNTIME("runtime error",
            Color.magenta),
    TLE(    "time limit exceeded",
            Color.red),
    WA(     "WA",
            Color.red),
    ABORTED("aborted",
            Color.magenta),
    INT(    "internal error",
            Color.magenta);
    
    private final String formattedResult;
    private final Color color;
    
    ExecutionResultCode(String formattedResult, Color color) {
        this.formattedResult = formattedResult;
        this.color = color;
    }
    
    public String getFormattedResult() {
        return formattedResult;
    }
    
    public Color getColor() {
        return color;
    }
}
