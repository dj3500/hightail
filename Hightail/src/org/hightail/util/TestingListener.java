package org.hightail.util;

public interface TestingListener {
    void notifyResultsOfSingleTestcase(int index);
    void notifyEndOfTesting();
}

