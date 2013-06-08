package org.hightail;

import javax.swing.KeyStroke;

public enum KeyboardShortcuts {
    RUN_TESTS       ("run tests", "ctrl R"),
    NEW_TESTCASE    ("new testcase", "ctrl T"),
    COPY_INPUT      ("copy input", "ctrl C"),
    ABORT_ALL       ("abort tests", "ctrl A"),
    ABORT_CURRENT   ("abort current test", "ctrl shift A"),
    SAVE_TESTCASE   ("save testcase", "ctrl ENTER"),
    ;
    
    private String name;
    private String code;

    private KeyboardShortcuts(String name, String code) {
        this.name = name;
        this.code = Config.get("shortcut " + name, code);
    }
    
    public String getName() {
        return name;
    }
    
    public String getText() {
        return Config.get("shortcut " + getName(), code);
    }
    
    public KeyStroke getKeyStroke() {
        return KeyStroke.getKeyStroke(getText());
    }
    
    @Override
    public String toString() {
        return name;
    }
    
//    private static final Map<String,String> shortcuts = new HashMap<>();
//    static {
//        shortcuts.put("run tests", "ctrl R");
//        shortcuts.put("new testcase", "ctrl T");
//        shortcuts.put("copy input", "ctrl C");
//        shortcuts.put("abort tests", "ctrl A");
//        shortcuts.put("abort current test", "ctrl shift A");
//        shortcuts.put("save testcase", "ctrl ENTER");
//    }
    
    public static KeyStroke getShortcut(String action) {
        for (KeyboardShortcuts shortcut : values()) {
            if (shortcut.getName().equals(action)) {
                return shortcut.getKeyStroke();
            }
        }
        throw new RuntimeException("No shortcut found for " + action);
    }

}
