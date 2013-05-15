package org.hightail;

import org.hightail.util.TestingListener;

public class Problem {

    protected String name;
    public static final int PROBLEM_NAME_MAX_LENGTH = 50;
    protected TestcaseSet testcaseSet = new TestcaseSet();

    public Problem(String name) {
        this.name = name;
    }
    
    public Problem(String name, TestcaseSet testcaseSet) {
        this.name = name;
        this.testcaseSet = testcaseSet;
    }

    public String getName() {
        return name;
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

    public void emptyResultsOfAllTestcases() {
        for (Testcase t : testcaseSet) {
            t.emptyResultsOfTestCase();
        }
    }

    public void abortCurrentTest() {
        testcaseSet.abortCurrent();
    }
    
    public void abortAllTests() {
        testcaseSet.abortAll();
    }

    public void runTests(TestingListener callback, String pathToExecFile) {
        testcaseSet.run(callback, pathToExecFile);
    }

    public String getDefaultExecutableFilename() {
        return Config.get("workingDirectory") + java.io.File.separator + 
               Config.get("pathFromWorkingDirToExec").replace("%P", name).replace("%L", name.toLowerCase());
    }
}
