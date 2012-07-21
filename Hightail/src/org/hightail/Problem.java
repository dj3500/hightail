package org.hightail;

import org.hightail.exception.CommandExecutionException;
import org.hightail.exception.FileCopyException;

public class Problem {

    protected String name;
    public static final int PROBLEM_NAME_MAX_LENGTH = 50;
    protected TestcaseSet testcaseSet = new TestcaseSet();

    public Problem(String name) throws FileCopyException, CommandExecutionException {
        this.name = name;
        createFile();
        openEditor();
    }

    public String getName() {
        return name;
    }

    private void createFile() throws FileCopyException {
        // TODO
    }

    private void openEditor() throws CommandExecutionException {
        // TODO
    }

    public void addTestcase(Testcase testcase) {
        testcaseSet.add(testcase);
    }

    public Testcase getTestcase(int index) {
        return testcaseSet.get(index);
    }

    public void deleteTestcase(int index) {
        testcaseSet.remove(index);
    }

    public TestcaseSet getTestcaseSet() {
        return testcaseSet;
    }
}
