/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.util;

/**
 *
 * @author krig
 */
public interface FileChangeListener {
    void onFileCreate();
    void onFileChange();
    void onFileDelete();
}
