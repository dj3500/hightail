/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hightail.parsers.contest.ContestParser;
import org.hightail.parsers.contest.ContestParserGetter;
import org.hightail.ui.MainJFrame;
import org.htmlparser.util.ParserException;

/**
 *
 * @author krig
 */
public class ContestScheduler extends TimerTask {
    private String URL; 
    private MainJFrame mainFrame;
    
    public ContestScheduler(String URL, MainJFrame mainFrame) {
        this.URL = URL;
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        try {
            ContestParser parser = ContestParserGetter.getContestParser(URL);
            mainFrame.addProblems(parser.parse(URL));
        } catch (ParserException ex) {
            new JOptionPane("Scheduled contest error " + ex.getMessage(), JOptionPane.ERROR_MESSAGE).createDialog("Hightail").setVisible(true);
            return;
        }
        new JOptionPane("Scheduled contest added.", JOptionPane.INFORMATION_MESSAGE).createDialog("Hightail").setVisible(true);
    }
}
