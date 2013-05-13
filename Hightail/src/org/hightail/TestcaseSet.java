package org.hightail;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hightail.util.TestingListener;

public class TestcaseSet extends ArrayList<Testcase> {
    private int noOfFinishedTests;
    private TestingListener callback;
    
    public void run(TestingListener callback, String pathToExecFile) {
        this.callback = callback;
        noOfFinishedTests = 0;
        for (Testcase test : this) {
            test.setCallback(this);
            test.setPathToExecFile(pathToExecFile);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            ArrayList<Callable<ExecutionResult>> tasksList = new ArrayList<>();
            tasksList.add(test);
            try {
                executor.invokeAll(tasksList, 3, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestcaseSet.class.getName()).log(Level.SEVERE, null, ex);
            }
            executor.shutdown();
            //            Thread thr = new Thread(test);
            //            thr.start();
        }
    }
    
    public void abort() {
        for (Testcase test : this) {
            test.killTest();
        }
    }
    
    void notifyResultsOfSingleTestcase(int index) {
        callback.notifyResultsOfSingleTestcase(index);
        noOfFinishedTests++;
        if (noOfFinishedTests == this.size()) {
            callback.notifyEndOfTesting();
        }
    }
}
