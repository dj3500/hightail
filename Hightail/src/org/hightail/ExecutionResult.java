package org.hightail;

import java.awt.Color;
import java.text.DecimalFormat;

public class ExecutionResult {
    static public final int NOT_RUN = -1,
            RUNNING = 0,
            OK = 1,
            WA = 2,
            ABORTED = 3,
            RUNTIME = 4,
            TLE = 5;
    // TODO: other codes (like TLE)
    
    protected double time = NOT_RUN;
    protected int result = NOT_RUN;
    protected String msg = "";
    
    public ExecutionResult() {
    }
    
    public int getResult() {
        return result;
    }
    
    public void setResult(int result) {
        this.result = result;
    }
    
    public double getTime() {
        return time;
    }
    
    public void setTime(double time) {
        this.time = time;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getFormattedResult() {
        switch (result) {
            case NOT_RUN:
                return "-";
            case RUNNING:
                return "running...";
            case OK:
                return "OK";
            case WA:
                return "WA";
            case ABORTED:
                return "aborted";
            case RUNTIME:
                return "runtime error";
            case TLE:
                return "time limit exceeded";
            default:
                throw new UnsupportedOperationException("Implementation error: getFormattedResult doesn't know how to format result");
        }
    }
    
    public String getFormattedTime() {
        if (result == NOT_RUN || result == TLE) {
            return "-";
        } else {
            return new DecimalFormat("0.00").format(time) + "s";
        }
    }
    
    @Override
    public String toString() {
        switch (result) {
            case NOT_RUN:
                return "-";
            case RUNNING:
                return "running...";
            case OK:
                return "OK " + time;
            case WA:
                return "WA\n" + msg;
            case ABORTED:
                return "aborted";
            case RUNTIME:
                return "runtime error";
            case TLE:
                return "time limit exceeded";
            default:
                throw new UnsupportedOperationException("Implementation error: toString doesn't know how to format result");
        }
    }
    
    public Color getColor() {
        switch (result) {
            case NOT_RUN:
            case RUNNING:
                return Color.black;
            case OK:
                return new Color(0, 170, 0); // green
            case WA:
            case TLE:
                return Color.red;
            case ABORTED:
                return Color.magenta;
            case RUNTIME:
                return Color.magenta;
            default:
                throw new UnsupportedOperationException("Implementation error: getColor doesn't know how to format result");
        }
    }
}
