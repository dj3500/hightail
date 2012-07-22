package org.hightail;

import java.util.ArrayList;
import org.hightail.ui.ProblemJPanel;

public class TestcaseSet extends ArrayList<Testcase> {
    private int noOfFinishedTests = 0;
    private ProblemJPanel callback;
        
    public void run(ProblemJPanel callback) {
        this.callback = callback;
        for (Testcase test : this) {
            test.setCallback(this);
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
