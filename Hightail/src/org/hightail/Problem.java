package org.hightail;

import org.hightail.util.TestingListener;

public class Problem {

    protected String name;
    public static final int PROBLEM_NAME_MAX_LENGTH = 50;
    protected TestcaseSet testcaseSet = new TestcaseSet();
    private SupportedSites originSite = null;
    private String pathToExec;
    private String contestNumber;

    public Problem(String name) {
        name = processName(name, "");
        this.name = name;
        pathToExec = Config.get("workingDirectory") + java.io.File.separator
                + Config.get("pathFromWorkingDirToExec").replace("%P", name).replace("%L", name.toLowerCase());
        contestNumber = "";
    }

    public Problem(String name, TestcaseSet testcaseSet, SupportedSites originSite) {
        name = processName(name, "");
        this.name = name;
        this.testcaseSet = testcaseSet;
        this.originSite = originSite;
        pathToExec = Config.get("workingDirectory") + java.io.File.separator
                + Config.get("pathFromWorkingDirToExec").replace("%P", name).replace("%L", name.toLowerCase());
        contestNumber = "";
    }

    public Problem(String name, TestcaseSet testcaseSet, SupportedSites originSite, String contestNumber) {
        name = processName(name, contestNumber);
        this.name = name;
        this.testcaseSet = testcaseSet;
        this.originSite = originSite;
        pathToExec = Config.get("workingDirectory") + java.io.File.separator
                + Config.get("pathFromWorkingDirToExec").replace("%P", name).replace("%L", name.toLowerCase());
        this.contestNumber = contestNumber;
    }

    /*
        make problem name such that it works well with Codeblocks IDE
    */
    private String processName(String name, String contestNumber) {
        StringBuilder name2 = new StringBuilder();
        int len = name.length();
        boolean prevSpace = true;
        for (int i = 0; i < len; i++) {
            if (Character.isAlphabetic(name.charAt(i)) || Character.isDigit(name.charAt(i))) {
                name2.append(name.charAt(i));
                prevSpace = false;
            } else if (name.charAt(i) == ' ' && !prevSpace) {
                name2.append(name.charAt(i));
                prevSpace = true;
            }
        }
        return contestNumber + name2.toString();
    }

    public void setName(String name) {
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

    public void abortCurrentTest() {
        testcaseSet.abortCurrent();
    }

    public void abortAllTests() {
        testcaseSet.abortAll();
    }

    public void runTests(TestingListener callback, String pathToExecFile) {
        testcaseSet.run(callback, pathToExecFile);
    }

    public String getPathToExec() {
        return pathToExec;
    }

    public void setWorkingDirectory(String workingDirectory) {
        pathToExec = workingDirectory + java.io.File.separator
                + Config.get("pathFromWorkingDirToExec").replace("%P", name).replace("%L", name.toLowerCase());
    }

    public void setPathToExec(String pathToExec) {
        this.pathToExec = pathToExec;
    }
}
