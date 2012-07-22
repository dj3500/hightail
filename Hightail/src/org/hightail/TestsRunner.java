package org.hightail;

import java.util.ArrayList;

/**
 *
 * @author piotrek
 */
public class TestsRunner {
    private TestcaseSet testsSet;
    private ArrayList<Thread> procSet;
    
    
    public TestsRunner(TestcaseSet testcaseSet) {
        testsSet = testcaseSet;
    }
    
    public void run() {
        for (Testcase test: testsSet) {
            Thread thr = new Thread(test);
            thr.start();
            procSet.add(thr);
       }
    }
    
    public void abort() {
        
    }
}
