package org.hightail.ui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import org.hightail.Config;
import org.hightail.Problem;
import org.hightail.server.HTTPServer;
import org.hightail.util.AbstractActionWithInteger;

// button-green.png icon comes from http://openiconlibrary.sourceforge.net/gallery2/?./Icons/others/button-green.png
public class MainJFrame extends javax.swing.JFrame {

    private HTTPServer httpServer;

    @SuppressWarnings("LeakingThisInConstructor")
    public MainJFrame() {
        initComponents();

        makeShortcuts();

        //Set the system proxy if there is one
        try {
            System.setProperty("java.net.useSystemProxies", "true");
        } finally {
        }

        // We load the configuration
        boolean ok = Config.load();
        if (!ok) { // couldn't load
            JOptionPane.showMessageDialog(this,
                    "If you're a new user, welcome!\n"
                    + "Hightail uses a config file, which resides in the same directory as the .jar file.\n"
                    + "This config file could not be loaded (probably this is the first run of Hightail).\n"
                    + "A new one will be created now and you will be taken to settings.\n"
                    + "\n"
                    + "Wish you high rating!",
                    "Hightail",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                Config.save();
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(this,
                        "The configuration file could not be created. Make sure Hightail has write rights to its directory.",
                        "Output error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            // show them the config dialog
            new ConfigJDialog(this).setVisible(true);
        }

        addPopupMenu();

        // on the first run of a version with this feature,
        // inform the user about the Competitive Companion browser extension
        // (in particular, that the Windows Firewall is going to complain)
        if (!Config.containsKey("userInformedOfCompetitiveCompanionAndFirewall")) {
            JOptionPane.showMessageDialog(this,
                    "Hightail now supports Competitive Companion - a browser plugin\n"
                    + "that parses problems and contests directly from the browser via a single click.\n"
                    + "Competitive Companion supports a wide variety of online judges and contests\n"
                    + "(more than the built-in parsers of Hightail - e.g. Google Code Jam, Facebook Hacker Cup, ...).\n"
                    + "Try it! (Find the link in Help->About, or google for Competitive Companion.)\n"
                    + "\n"
                    + "(Due to this, Hightail now runs a local HTTP server on port " + HTTPServer.PORT + ".)\n"
                    + "\n"
                    + "\n"
                    + "(You are receiving this message because either this the first run of Hightail,\n"
                    + "or you updated from a version that did not have this feature.)",
                    "Competitive Companion support is new in this release",
                    JOptionPane.INFORMATION_MESSAGE);
            Config.set("userInformedOfCompetitiveCompanionAndFirewall", "1");
            try {
                Config.save();
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(this,
                        "The configuration file could not be created. Make sure Hightail has write rights to its directory.",
                        "Output error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        httpServer = new HTTPServer();
        httpServer.start(problem -> {
            // Add the problem
            ArrayList<Problem> problems = new ArrayList<>();
            problems.add(problem);
            addProblems(problems);

            // Focus the window
            // toFront() doesn't work on Ubuntu 18.04, but this little workaround does
            boolean currentAlwaysOnTop = this.isAlwaysOnTop();
            this.setAlwaysOnTop(true);
            this.setAlwaysOnTop(currentAlwaysOnTop);
        });

        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newFromURL = new javax.swing.JMenuItem();
        newContest = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        openConfig = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();
        shortcuts = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Hightail");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("resources/button-green.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabbedPane.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        fileMenu.setText("File");

        newFromURL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFromURL.setText("New problem...");
        newFromURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFromURLActionPerformed(evt);
            }
        });
        fileMenu.add(newFromURL);

        newContest.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        newContest.setText("New contest...");
        newContest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newContestActionPerformed(evt);
            }
        });
        fileMenu.add(newContest);
        fileMenu.add(jSeparator1);

        openConfig.setText("Settings...");
        openConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openConfigActionPerformed(evt);
            }
        });
        fileMenu.add(openConfig);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");

        about.setText("About...");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        helpMenu.add(about);

        shortcuts.setText("Shortcuts");
        shortcuts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shortcutsActionPerformed(evt);
            }
        });
        helpMenu.add(shortcuts);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addPopupMenu() {
        // adds popup menu to tabs with option to delete a tab
        final JPopupMenu singleTabJPopupMenu = new JPopupMenu();
        JMenuItem deleteJMenuItem = new JMenuItem("Delete");
        deleteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.remove(tabbedPane.getSelectedComponent());
            }
        });
        singleTabJPopupMenu.add(deleteJMenuItem);
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            private void doPop(MouseEvent e) {
                singleTabJPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    private void makeShortcuts() {
        for (int index = 1; index <= 9; index++) {
            tabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt " + index), "switch tab " + index);
            tabbedPane.getActionMap().put("switch tab " + index, new AbstractActionWithInteger(index) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tabbedPane.getTabCount() >= getInteger()) {
                        tabbedPane.setSelectedIndex(getInteger() - 1);
                    }
                }
            });
        }
    }

    protected void addTabForProblem(Problem problem) {
        ProblemJPanel panel = new ProblemJPanel(problem, tabbedPane, this);
        // as recommended here: http://stackoverflow.com/questions/476678/tabs-with-equal-constant-width-in-jtabbedpane
        tabbedPane.addTab("<html><body><table width='150'>" + problem.getName() + "</table></body></html>", panel);
        tabbedPane.setSelectedComponent(panel);
    }

    private void confirmAndClose() {
        // Display confirm dialog
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure?",
                "Confirm quit",
                JOptionPane.YES_NO_OPTION);

        // Close iff user confirmed
        if (confirmed == JOptionPane.YES_OPTION) {
            httpServer.stop();
            this.dispose();
            System.exit(0);
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        confirmAndClose();
    }//GEN-LAST:event_formWindowClosing

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        confirmAndClose();
    }//GEN-LAST:event_exitActionPerformed

    private void openConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openConfigActionPerformed
        new ConfigJDialog(this).setVisible(true);
    }//GEN-LAST:event_openConfigActionPerformed

    public void addProblems(ArrayList<Problem> problems) {
        if (problems == null || problems.isEmpty()) {
            return;
        }
        Component firstProblem = null;
        String[] commandAndArg = new String[2];
        commandAndArg[0] = "explorer.exe";
        boolean first = true;
        for (Problem problem : problems) {
            addTabForProblem(problem);
            if (firstProblem == null) {
                firstProblem = tabbedPane.getComponentAt(tabbedPane.getTabCount() - 1);
            }
            try {
                commandAndArg[1] = addCodeblocksIDEProjectFiles(problem);
                if (first) {
                    openCodeBlocksFile(commandAndArg);
                    first = false;
                }
            } catch (IOException e) {
                System.err.println(Arrays.toString(e.getStackTrace()));
            }
        }
        tabbedPane.setSelectedComponent(firstProblem);
//        commandAndArg[1] = new File(commandAndArg[1]).getParent().toString();
//        openCodeBlocksFile(commandAndArg);
    }

    private void newContestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newContestActionPerformed
        NewContestJDialog dialog = new NewContestJDialog(this);
        dialog.setVisible(true); // this is modal; it will block until window is closed
        addProblems(dialog.getProblemList());
    }//GEN-LAST:event_newContestActionPerformed

    private void newFromURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFromURLActionPerformed
        // show user a dialog to type the name, and the URL
        NewProblemJDialog dialog = new NewProblemJDialog(this);
        dialog.setVisible(true); // this is modal; it will block until window is closed
        if (dialog.getProblem() != null) { // a problem has been created
            addTabForProblem(dialog.getProblem());
        }
    }//GEN-LAST:event_newFromURLActionPerformed

    private void shortcutsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shortcutsActionPerformed
        new ShortcutsJDialog(this).setVisible(true);
    }//GEN-LAST:event_shortcutsActionPerformed

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        new AboutJDialog(this).setVisible(true);
    }//GEN-LAST:event_aboutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // We set the look and feel for Swing
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.put("TabbedPane.tabInsets", new Insets(5,20,6,20));
        } catch (Exception e) {
            // We fall back to Metal
        }

        // And we let the application run
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newContest;
    private javax.swing.JMenuItem newFromURL;
    private javax.swing.JMenuItem openConfig;
    private javax.swing.JMenuItem shortcuts;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

    private String cbpText = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n"
            + "<CodeBlocks_project_file>\n"
            + "	<FileVersion major=\"1\" minor=\"6\" />\n"
            + "	<Project>\n"
            + "		<Option title=\"%s\" />\n"
            + "		<Option pch_mode=\"2\" />\n"
            + "		<Option compiler=\"gcc\" />\n"
            + "		<Build>\n"
            + "			<Target title=\"Debug\">\n"
            + "				<Option output=\"%s\" prefix_auto=\"1\" extension_auto=\"1\" />\n"
            + "				<Option object_output=\".\" />\n"
            + "				<Option type=\"1\" />\n"
            + "				<Option compiler=\"gcc\" />\n"
            + "				<Compiler>\n"
            + "					<Add option=\"-g\" />\n"
            + "				</Compiler>\n"
            + "			</Target>\n"
            + "			<Target title=\"Release\">\n"
            + "				<Option output=\"bin/Release/Chef and Typing\" prefix_auto=\"1\" extension_auto=\"1\" />\n"
            + "				<Option object_output=\"obj/Release/\" />\n"
            + "				<Option type=\"1\" />\n"
            + "				<Option compiler=\"gcc\" />\n"
            + "				<Compiler>\n"
            + "					<Add option=\"-O2\" />\n"
            + "				</Compiler>\n"
            + "				<Linker>\n"
            + "					<Add option=\"-s\" />\n"
            + "				</Linker>\n"
            + "			</Target>\n"
            + "		</Build>\n"
            + "		<Compiler>\n"
            + "			<Add option=\"-Wall\" />\n"
            + "			<Add option=\"-fexceptions\" />\n"
            + "		</Compiler>\n"
            + "		<Unit filename=\"%s.cpp\" />\n"
            + "		<Extensions>\n"
            + "			<code_completion />\n"
            + "			<envvars />\n"
            + "			<debugger />\n"
            + "			<lib_finder disable_auto=\"1\" />\n"
            + "		</Extensions>\n"
            + "	</Project>\n"
            + "</CodeBlocks_project_file>";

    private String cppTemplate
            = "#include <bits/stdc++.h>\n"
            + "\n"
            + "using namespace std;\n"
            + "int main(int argc, char* argv[])\n"
            + "{\n"
            + "    cout << \"Hello World\" << endl;\n"
            + "    return 0;\n"
            + "}";

    private String addCodeblocksIDEProjectFiles(Problem problem) throws IOException {
        String cbpFileName = Config.get("workingDirectory") + "\\" + problem.getName() + ".cbp";
        String cppFileName = Config.get("workingDirectory") + "\\" + problem.getName() + ".cpp";
//        System.out.println(cbpFileName);
        File cbpFile = new File(cbpFileName);
        if (!cbpFile.exists()) {
            cbpFile.createNewFile();
            try (FileWriter fw = new FileWriter(cbpFile)) {
                fw.write(String.format(cbpText, problem.getName(), problem.getName(), problem.getName()));
            }
        }
        String ret = "\"" + cbpFile.getAbsolutePath() + "\"";
        File cppTemplateFile = new File("template.cpp");
        if (!cppTemplateFile.exists()) {
            cppTemplateFile.createNewFile();
            try (FileWriter fw = new FileWriter(cppTemplateFile)) {
                fw.write(cppTemplate);
            }
        }
        File cppFile = new File(cppFileName);
        if (!cppFile.exists()) {
            cppFile.createNewFile();
            try (FileWriter fw = new FileWriter(cppFile);
                    BufferedReader br = new BufferedReader(new FileReader(cppTemplateFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fw.write(line + "\n");
                }
            }
        }
        return ret;
    }

    private void openCodeBlocksFile(String[] s) {
        try {
            Runtime.getRuntime().exec(s);
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
