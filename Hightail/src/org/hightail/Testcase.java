package org.hightail;

public class Testcase {

    protected String input;
    protected String expectedOutput;
    protected String programOutput = "";
    protected ExecutionResult executionResult = new ExecutionResult();

    public Testcase() {
        this.input = "";
        this.expectedOutput = "";
    }

    /**
     * Get the value of input
     *
     * @return the value of input
     */
    public String getInput() {
        return input;
    }

    /**
     * Set the value of input
     *
     * @param input new value of input
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Get the value of expectedOutput
     *
     * @return the value of expectedOutput
     */
    public String getExpectedOutput() {
        return expectedOutput;
    }

    /**
     * Set the value of expectedOutput
     *
     * @param expectedOutput new value of expectedOutput
     */
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    /**
     * Get the value of programOutput
     *
     * @return the value of programOutput
     */
    public String getProgramOutput() {
        return programOutput;
    }

    /**
     * Set the value of programOutput
     *
     * @param programOutput new value of programOutput
     */
    public void setProgramOutput(String programOutput) {
        this.programOutput = programOutput;
    }

    /**
     * Get the value of executionResult
     *
     * @return the value of executionResult
     */
    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    /**
     * Set the value of executionResult
     *
     * @param executionResult new value of executionResult
     */
    public void setExecutionResult(ExecutionResult result) {
        this.executionResult = result;
    }

    public Testcase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    public void save() {
        // TODO save it to a disk file maybe
    }

}
