package org.hightail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.KeyStroke;

/**
 *
 * @author krig
 */
public class KeyboardShortcuts implements Iterable<Entry<String,String>> {
    private static final Map<String,String> shortcuts = new HashMap<>();
    static {
        shortcuts.put("run tests", "ctrl R");
        shortcuts.put("new testcase", "ctrl T");
        shortcuts.put("copy input", "ctrl C");
        shortcuts.put("abort tests", "ctrl A");
        shortcuts.put("abort current test", "ctrl shift A");
        shortcuts.put("save testcase", "ctrl ENTER");
    }
    
    public static KeyStroke getShortcut(String action) {
        if (!shortcuts.containsKey(action)) {
            throw new RuntimeException("No shortcut found for " + action);
        }
        return KeyStroke.getKeyStroke(shortcuts.get(action));
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        final Iterator<Entry<String,String>> iterator = shortcuts.entrySet().iterator();
        return new Iterator<Entry<String, String>>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Entry<String, String> next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
}
