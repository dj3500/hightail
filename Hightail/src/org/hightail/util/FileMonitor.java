/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.util;

import java.io.File;
import java.util.Timer;

/**
 *
 * @author krig
 */
public class FileMonitor {
    private static final FileMonitor instance = new FileMonitor();
    private static final long period = 1000;
    private Timer timer;

    private FileMonitor() {
        this.timer = new Timer(true);
    }
    
    public FileMonitor getInstance() {
        return instance;
    }
    
    public void addFileChangeListener(FileChangeListener listener, String fileName) {
        addFileChangeListener(listener, new File(fileName));
    }
    
    public void addFileChangeListener(FileChangeListener listener, File file) {
        FileMonitorTask task = new FileMonitorTask(listener, file);
        timer.schedule(task, 0, period);
    }
}
