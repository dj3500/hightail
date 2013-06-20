package org.hightail.util;

public interface FileChangeListener {
    void onFileCreate();
    void onFileChange();
    void onFileDelete();
}
