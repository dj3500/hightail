package org.hightail;

import java.text.DecimalFormat;

public class ExecutionResult {
    static public final int NOT_RUN = -1, OK = 1, WA = 2, ABORTED = 3;
    // TODO: other codes (like TLE)

    protected double time = NOT_RUN;
    protected int result = NOT_RUN;
    
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

    public String getFormattedResult() {
        switch (result) {
            case NOT_RUN:
                return "-";
            case OK:
                return "<span style='color: green;'>OK</span>";
            case WA:
                return "<span style='color: red;'>WA</span>";
            case ABORTED:
                return "aborted";
            default:
                throw new UnsupportedOperationException("Implementation error: getFormattedResult doesn't know how to format result");
        }
    }

    public String getFormattedTime() {
        if (result == NOT_RUN) {
            return "-";
        } else {
            return new DecimalFormat("0.00").format(time) + "s";
        }
    }

}
