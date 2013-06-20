package org.hightail;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hightail.util.TestingListener;

public class TestcaseSet extends ArrayList<Testcase> {
    private ExecutorService executor;
    private Testcase currentTest;
    private boolean aborted;
    
    public void run(TestingListener callback, String pathToExecFile) {
        aborted = false;
        for (Testcase test : this) {
            currentTest = test;
            test.setPathToExecFile(pathToExecFile);
            executor = Executors.newSingleThreadExecutor();
            try {
                executor.submit(test);
                executor.shutdown();
                if (!executor.awaitTermination(test.getTimeLimit(), TimeUnit.MILLISECONDS)) {
                    test.killTest();
                    test.setExecutionResultCode(ExecutionResultCode.TLE);
                }
            } catch (InterruptedException ex) {
                test.setExecutionResultCode(ExecutionResultCode.INT);
                Logger.getLogger(TestcaseSet.class.getName()).log(Level.SEVERE, null, ex);
            }
            callback.notifyResultsOfSingleTestcase(test.getIndex());
            if (aborted) {
                break;
            }
        }
        callback.notifyEndOfTesting();
    }
    
    public void abortCurrent() {
        if (executor == null || !executor.isShutdown()) {
            return;
        }
        currentTest.killTest();
        executor.shutdownNow();
        currentTest.setExecutionResultCode(ExecutionResultCode.ABORTED);
    }
    
    public void abortAll() {
        aborted = true;
        abortCurrent();
    }
}
