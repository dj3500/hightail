package org.hightail;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.hightail.parsers.contest.ContestParser;
import org.hightail.ui.MainJFrame;
import org.htmlparser.util.ParserException;

public abstract class ContestScheduler {
    private static final Timer timer = new Timer();
    
    public static void schedule(final String URL, final String workingDirectory, final MainJFrame mainFrame, Date date) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ContestParser parser = SupportedSites.getContestParser(URL);
                    ArrayList<Problem> problems = parser.getProblemListFromContestURL(URL);
                    for (Problem p : problems) {
                        p.setWorkingDirectory(workingDirectory);
                    }
                    mainFrame.addProblems(problems);
                } catch (InterruptedException | ParserException ex) {
                    new JOptionPane("Scheduled contest error " + ex.getMessage(), JOptionPane.ERROR_MESSAGE).createDialog("Hightail").setVisible(true);
                    return;
                }
                new JOptionPane("Scheduled contest added.", JOptionPane.INFORMATION_MESSAGE).createDialog("Hightail").setVisible(true);
                
            }
        }, date);
    }

}
