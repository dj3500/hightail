package org.hightail;

import java.io.*;
import java.util.Calendar;
import java.util.concurrent.Callable;
import org.hightail.diff.OutputDiff;

public class Testcase implements Callable<ExecutionResult> {
    protected int index = 0;
    protected String input;
    protected String expectedOutput;
    protected String programOutput = "";
    protected String programError = "";
    protected int timeLimit = 3;
    protected ExecutionResult executionResult = new ExecutionResult();
    protected Process executionProcess;
    private String pathToExecFile;
    
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
    
    public void setExecutionResultCode(ExecutionResultCode result) {
        emptyResultsOfTestCase();
        this.executionResult.setResult(result);
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
    
    public int getTimeLimit() {
        return timeLimit;
    }
    
    public Testcase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }
    
    public void save() {
        // testcase has been changed by user, so results are obsolete
        emptyResultsOfTestCase();
    }
    
    public void emptyResultsOfTestCase() {
        programOutput = "";
        executionResult = new ExecutionResult();
    }
    
    public void killTest() {
        try {
            executionProcess.exitValue();
        } catch (IllegalThreadStateException ex) {
            executionProcess.destroy();
        }
    }
    
    @Override
    public ExecutionResult call() {
        emptyResultsOfTestCase();
        try {
            String line;
            BufferedReader br;
            executionResult.setResult(ExecutionResultCode.RUNNING);
            
            double startTime = Calendar.getInstance().getTimeInMillis();
            // TODO: measure CPU time of executionProcess instead
            
            // TODO: change path to running file
            executionProcess = Runtime.getRuntime().exec(pathToExecFile);
            
            OutputStream stdin = executionProcess.getOutputStream();
            InputStream stderr = executionProcess.getErrorStream();
            InputStream stdout = executionProcess.getInputStream();
            
            // writing input
            stdin.write(input.getBytes());
            stdin.flush();
            stdin.close();
            
            int execRes = executionProcess.waitFor();
            double endTime = Calendar.getInstance().getTimeInMillis();
            executionResult.setTime((endTime - startTime) / 1000.0);
            
            // reading stdout
            br = new BufferedReader(new InputStreamReader(stdout));
            programOutput = "";
            while ((line = br.readLine()) != null) {
                programOutput = programOutput + line + "\n";
            }
            br.close();
            
            // reading stderr
            br = new BufferedReader(new InputStreamReader(stderr));
            programError = "";
            while ((line = br.readLine()) != null) {
                programError = programError + line + "\n";
            }
            br.close();
            
            
            if (execRes == 0) {
                String res = OutputDiff.diff(expectedOutput, programOutput);
                if (res.equals("OK")) {
                    executionResult.setResult(ExecutionResultCode.OK);
                } else {
                    executionResult.setResult(ExecutionResultCode.WA);
                    executionResult.setMsg(res);
                }
            } else if (executionResult.getResult() == ExecutionResultCode.RUNNING) {
                executionResult.setResult(ExecutionResultCode.RUNTIME);
            }
            
            
            
        } catch (InterruptedException | IOException ex) {
            //            Logger.getLogger(Testcase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executionResult;
    }
    
    void setPathToExecFile(String pathToExecFile) {
        this.pathToExecFile = pathToExecFile;
    }
}
