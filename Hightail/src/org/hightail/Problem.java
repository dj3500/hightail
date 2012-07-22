package org.hightail;

import org.hightail.ui.ProblemJPanel;

public class Problem {

    protected String name;
    public static final int PROBLEM_NAME_MAX_LENGTH = 50;
    protected TestcaseSet testcaseSet = new TestcaseSet();

    public Problem(String name) {
        this.name = name;
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

    public void abortTests() {
        testcaseSet.abort();
    }

    public void runTests(ProblemJPanel callback) {
        testcaseSet.run(callback);
    }
}
