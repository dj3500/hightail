package org.hightail.util;

import javax.swing.AbstractAction;

public abstract class AbstractActionWithInteger extends AbstractAction {
    private final int i;
    
    public AbstractActionWithInteger(int i) {
        super();
        this.i = i;
    }
    
    public int getInteger() {
        return i;
    }
}
