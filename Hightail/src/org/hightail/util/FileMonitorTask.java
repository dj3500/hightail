/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.util;

import java.io.File;
import java.util.TimerTask;

/**
 *
 * @author krig
 */
public class FileMonitorTask extends TimerTask {
    private FileChangeListener listener;
    private File file;
    private boolean exists;
    private long lastModified;
    
    FileMonitorTask(FileChangeListener listener, File file) {
        this.listener = listener;
        this.file = file;
        this.exists = false;
        this.lastModified = -1;
    }

    @Override
    public void run() {
        if (exists != file.exists()) {
            exists = file.exists();
            if (exists) {
                lastModified = file.lastModified();
                listener.onFileCreate();
            } else {
                lastModified = -1;
                listener.onFileDelete();
            }
        } else if (exists) {
            if (lastModified != file.lastModified()) {
                lastModified = file.lastModified();
                listener.onFileChange();
            }
        }
    }
    
}
