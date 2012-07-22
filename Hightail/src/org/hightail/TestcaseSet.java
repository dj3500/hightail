package org.hightail;

import java.util.ArrayList;

public class TestcaseSet extends ArrayList<Testcase> {
   private ArrayList<Thread> procSet;
        
    public void run() {
        procSet = new ArrayList<Thread>();
        for (Testcase test : this) {
            Thread thr = new Thread(test);
            thr.start();
            procSet.add(thr);
       }
    }
    
    public void abort() {
        // TODO
    }
}
