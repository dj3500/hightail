package org.hightail;

import javax.swing.KeyStroke;

public enum KeyboardShortcuts {
    RUN_TESTS       ("run tests", "ctrl R"),
    NEW_TESTCASE    ("new testcase", "ctrl T"),
    COPY_INPUT      ("copy input", "ctrl C"),
    ABORT_ALL       ("abort tests", "ctrl A"),
    ABORT_CURRENT   ("abort current test", "ctrl shift A"),
    SAVE_TESTCASE   ("save testcase", "ctrl ENTER"),
    SAVE_TESTS      ("save tests", "ctrl S"),
    ;
    
    private final String action;
    private final String defaultCode;

    private KeyboardShortcuts(String action, String defaultCode) {
        this.action = action;
        this.defaultCode = defaultCode;
    }
    
    public String getAction() {
        return action;
    }
    
    public String getDefaultCode() {
        return defaultCode;
    }
    
    public String getCode() {
        return Config.get("shortcut " + getAction(), defaultCode);
    }
    
    public KeyStroke getKeyStroke() {
        return KeyStroke.getKeyStroke(getCode());
    }
    
    @Override
    public String toString() {
        return action;
    }
    
    public static KeyStroke getShortcut(String action) {
        for (KeyboardShortcuts shortcut : values()) {
            if (shortcut.getAction().equals(action)) {
                return shortcut.getKeyStroke();
            }
        }
        throw new RuntimeException("No shortcut found for " + action);
    }

}
