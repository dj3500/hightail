package org.hightail;

import java.util.ArrayList;
import org.hightail.util.TestingListener;

public class TestcaseSet extends ArrayList<Testcase> {
    private int noOfFinishedTests = 0;
    private TestingListener callback;
        
    public void run(TestingListener callback, String pathToExecFile) {
        this.callback = callback;
        for (Testcase test : this) {
            test.setCallback(this);
            test.setPathToExecFile(pathToExecFile);
            Thread thr = new Thread(test);
            thr.start();
        }
    }
    
    public void abort(){
        for (Testcase test : this) {
            test.killTest();
        }
    }
    
    void notifyResultsOfSingleTestcase(int index) {
        callback.notifyResultsOfSingleTestcase(index);
        noOfFinishedTests++;
        if (noOfFinishedTests >= this.size()) {
            callback.notifyEndOfTesting();
        }
    }
}
