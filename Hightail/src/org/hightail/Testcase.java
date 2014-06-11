package org.hightail;

import java.io.*;
import java.util.Calendar;
import java.util.concurrent.Callable;
import org.hightail.diff.OutputDiff;

public class Testcase implements Callable<ExecutionResult> {
    public static final int DEFAULT_TIME_LIMIT = 3000; // in milliseconds
    private static final int OUTPUT_MAX_LEN = 300*1024; // 300 kb
    
    protected int index = 0;
    protected String input;
    protected String expectedOutput;
    protected String programOutput = "";
    protected String programError = "";
    protected int timeLimit = DEFAULT_TIME_LIMIT;
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
    
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public int getTimeLimit() {
        return timeLimit;
    }
    
    public double getTimeLimitInSeconds() {
        return getTimeLimit()/1000.;
    }
    
    public Testcase(String input, String expectedOutput) {
        this(input, expectedOutput, DEFAULT_TIME_LIMIT);
    }
    
    public Testcase(String input, String expectedOutput, int timeLimit) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.timeLimit = timeLimit;
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
    
    protected String getCommandToExecute () {
        if (!Config.isPrependingCommandNonempty()) {
            // there is no prepending command; we just run the executable file
            return pathToExecFile;
        } else {
            // we run the executable as in "java ..." or "python ..."
            return Config.get("prependingCommand") + " " + pathToExecFile;
        }
    }
    
    @Override
    public ExecutionResult call() {
        emptyResultsOfTestCase();
        try {
            BufferedReader br;
            StringBuilder sb;
            int len;
            char[] buf = new char[1024];
            executionResult.setResult(ExecutionResultCode.RUNNING);
            
            double startTime = Calendar.getInstance().getTimeInMillis();
            // TODO: measure CPU time of executionProcess instead
            
            executionProcess = Runtime.getRuntime().exec(getCommandToExecute());
            
            OutputStream stdin = executionProcess.getOutputStream();
            InputStream stderr = executionProcess.getErrorStream();
            InputStream stdout = executionProcess.getInputStream();
            
            // writing input
            stdin.write(input.getBytes());
            stdin.flush();
            stdin.close();
            
            // reading stdout
            br = new BufferedReader(new InputStreamReader(stdout));
            sb = new StringBuilder();
            while ((len = br.read(buf)) != -1 && sb.length() < OUTPUT_MAX_LEN) {
                sb.append(buf, 0, len);
            }
            if (sb.length() >= OUTPUT_MAX_LEN) {
                executionResult.setResult(ExecutionResultCode.RUNTIME);
                executionResult.setMsg("Output limit exceeded");
                killTest();
                throw new IOException("Output limit exceeded");
            }
            programOutput = sb.toString();
            br.close();
            
            // reading stderr
            br = new BufferedReader(new InputStreamReader(stderr));
            sb = new StringBuilder();
            while ((len = br.read(buf)) != -1 && sb.length() < OUTPUT_MAX_LEN) {
                sb.append(buf, 0, len);
            }
            if (sb.length() >= OUTPUT_MAX_LEN) {
                executionResult.setResult(ExecutionResultCode.RUNTIME);
                executionResult.setMsg("Output limit exceeded");
                killTest();
                throw new IOException("Output limit exceeded");
            }
            programError = sb.toString();
            br.close();
            
            int execRes = executionProcess.waitFor();
            double endTime = Calendar.getInstance().getTimeInMillis();
            executionResult.setTime((endTime - startTime) / 1000.0);
            
            if (execRes == 0) {
                String res = OutputDiff.diff(expectedOutput, programOutput);
                if (res.startsWith("OK")) {
                    executionResult.setResult(ExecutionResultCode.OK);
                } else {
                    executionResult.setResult(ExecutionResultCode.WA);
                }
                executionResult.setMsg(res);
            } else if (executionResult.getResult() == ExecutionResultCode.RUNNING) {
                executionResult.setResult(ExecutionResultCode.RUNTIME);
            }
            
            
            
        } catch (InterruptedException | IOException ex) {
            // time out or abort or something else
        }
        return executionResult;
    }
    
    void setPathToExecFile(String pathToExecFile) {
        this.pathToExecFile = pathToExecFile;
    }
}
