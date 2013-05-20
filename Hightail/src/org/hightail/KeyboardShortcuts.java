/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail;

import java.util.HashMap;
import java.util.Map;
import javax.swing.KeyStroke;

/**
 *
 * @author krig
 */
public abstract class KeyboardShortcuts {
    private static final Map<String,String> shortcuts = new HashMap<>();
    static {
        shortcuts.put("run tests", "ctrl R");
        shortcuts.put("new testcase", "ctrl T");
        shortcuts.put("copy input", "ctrl shift C");
        shortcuts.put("abort tests", "ctrl A");
        shortcuts.put("abort current test", "ctrl shift A");
        shortcuts.put("save testcase", "ctrl ENTER");
    }
    
    public static KeyStroke getShortcut(String action) {
        if(!shortcuts.containsKey(action)) {
            throw new RuntimeException("No shortcut found for " + action);
        }
        return KeyStroke.getKeyStroke(shortcuts.get(action));
    }
}
