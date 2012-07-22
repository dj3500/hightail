package org.hightail;

import java.io.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Testcase implements Runnable{

    protected String input;
    protected String expectedOutput;
    protected String programOutput = "";
    protected String programError = "";
    protected ExecutionResult executionResult = new ExecutionResult();
    protected Process executionProcess;

    public Testcase() {
        this.input = "";
        this.expectedOutput = "";
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getProgramOutput() {
        return programOutput;
    }

    public void setProgramOutput(String programOutput) {
        this.programOutput = programOutput;
    }

    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(ExecutionResult result) {
        this.executionResult = result;
    }

    public Testcase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }
    
    public void save () {
        // testcase has been changed by user, so results are obsolete
        emptyResultsOfTestCase();
    }

    public void emptyResultsOfTestCase() {
        programOutput = "";
        executionResult = new ExecutionResult();
    }

    @Override
    public void run() {
        try {
            String line;
            
            Calendar cal = Calendar.getInstance();
            double startTime = cal.getTimeInMillis();
            
            // TODO: change path to running file
            executionProcess = Runtime.getRuntime().exec("/Users/piotrek/hackaton/a");
            
            OutputStream stdin = executionProcess.getOutputStream ();
            InputStream stderr = executionProcess.getErrorStream();
            InputStream stdout = executionProcess.getInputStream();
            
            // writing input
            stdin.write(input.getBytes());
            stdin.flush();
            stdin.close();
            
            // reading stdout
            BufferedReader br = new BufferedReader (new InputStreamReader (stdout));
            while ((line = br.readLine ()) != null) {
                programOutput += line;
            }
            br.close();

            // reading stderr
            br = new BufferedReader (new InputStreamReader (stderr));
            while ((line = br.readLine ()) != null) {
                programError += line;
            }
            br.close();
            
            int execRes = executionProcess.waitFor();
            
            double endTime = cal.getTimeInMillis();
            
            executionResult.setTime(endTime-startTime);
            executionResult.setResult(1); // STUB
            
            
            //TODO: update stats
        } catch (InterruptedException ex) {
            Logger.getLogger(Testcase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Testcase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
