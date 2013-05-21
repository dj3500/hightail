package org.hightail.util;

import javax.swing.AbstractAction;

/**
 *
 * @author krig
 */
public abstract class AbstractActionWithInteger extends AbstractAction {
    private int i;
    
    public AbstractActionWithInteger(int i) {
        super();
        this.i = i;
    }
    
    public int getInteger() {
        return i;
    }
}
