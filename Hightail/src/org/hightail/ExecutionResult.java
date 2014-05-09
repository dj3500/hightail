package org.hightail;

import java.awt.Color;
import java.text.DecimalFormat;

public class ExecutionResult {
    protected double time = -1.0;
    protected ExecutionResultCode result = ExecutionResultCode.NOT_RUN;
    protected String msg = "";
    
    public ExecutionResult() {
    }
    public ExecutionResult(ExecutionResultCode result) {
        this.result = result;
    }
    public ExecutionResultCode getResult() {
        return result;
    }
    
    public void setResult(ExecutionResultCode result) {
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
        return result.getFormattedResult();
    }
    
    public String getFormattedTime() {
        if (result == ExecutionResultCode.NOT_RUN || 
                result == ExecutionResultCode.TLE) {
            return "-";
        } else {
            return new DecimalFormat("0.00").format(time) + "s";
        }
    }
    
    @Override
    public String toString() {
        String str = result.getFormattedResult();
        switch (result) {
            case OK:
                str = msg + "\n" + time;
            break;
            case WA:
            case RUNTIME:
                if (!msg.isEmpty()) {
                    str = str + "\n" + msg;
                }
            break;
        }
        return str;
    }
    
    public Color getColor() {
        return result.getColor();
    }
}
