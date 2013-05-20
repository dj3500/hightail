/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.util;

import java.awt.event.ActionEvent;
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
