package org.hightail.util;

import java.io.File;
import java.util.Timer;

public abstract class FileMonitor {
    private static final long period = 1000;
    private static final Timer timer = new Timer(true);

    public static void addFileChangeListener(FileChangeListener listener, String fileName) {
        addFileChangeListener(listener, new File(fileName));
    }
    
    public static void addFileChangeListener(FileChangeListener listener, File file) {
        FileMonitorTask task = new FileMonitorTask(listener, file);
        timer.schedule(task, 0, period);
    }
}
